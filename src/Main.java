import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.SortedMap;

public class Main{
	public static void main( String[] a ){
		boolean isRepeat= false;
		int iDelay= 10;//[ms]
		Charset charset= Charset.defaultCharset();
		File file= null;
		FileThread t= null;
		try{
			System.err.println( "paramaters:" + a.length );
			for( int i= 0; a.length > i; i++ ){
				if( a[i].toLowerCase().startsWith( "-r" ) ){
					isRepeat= true;
					System.err.print( String.format( "%d:repeat,", i ) );
				}else if( a[i].toLowerCase().startsWith( "-d" ) ){
					System.err.print( String.format( "%d:delay,", i ) );
					try{//must delay time, next
						if( a.length <= ++i ) throw new NumberFormatException( "None delay time." );
						System.err.print( String.format( "%d:%s,", i, a[i] ) );
						iDelay= Integer.valueOf( a[i] );
					}catch( NumberFormatException e ){
						System.err.println( "\n" + e.toString() );
						throw new IllegalArgumentException();
					}
				}else if( a[i].toLowerCase().startsWith( "-e" ) ){
					System.err.print( String.format( "%d:encoding,", i ) );
					try{//must encoding, next
						if( a.length <= ++i ) throw new IllegalArgumentException( "None encoding." );
						System.err.print( String.format( "%d:%s,", i, a[i] ) );
						if( Charset.isSupported( a[i] ) ){
							charset= Charset.forName( a[i] );
						}else{
							throw new IllegalArgumentException( String.format( "Bad encoding.[%s]", a[i] ) );
						}
					}catch( IllegalArgumentException e ){
						System.err.println( "\n" + e.toString() );
						final SortedMap<String,Charset> charsets= Charset.availableCharsets();
						for( String s : charsets.keySet() ){
							System.out.print( String.format( "[%s]", s ) );
						}
						throw new IllegalArgumentException();
					}
				}else{
					System.err.print( String.format( "%d:%s,", i, a[i] ) );
					file= new File( a[i] );
				}
			}
			if( null == file ) throw new IllegalArgumentException();
			System.err.println( String.format( "\n---\nDelay: %dms", iDelay ) );
			System.err.println( String.format( "Repeat: %s", ( isRepeat ? "ON" : "OFF") ) );
			System.err.println( String.format( "Encoding: %s", charset.name() ) );
			System.err.println( String.format( "file: %s",  file.getAbsolutePath() ) );

			t= new FileThread( file, iDelay, isRepeat, charset );
//			InputStreamReader br = new InputStreamReader( System.in );
			InputStream br = System.in;
			while( true ){
				if( ! t.isOn ) break;
//				if( ! br.ready() ) continue;
				if( 0 >= br.available() ) continue;
				br.read();
				System.out.println( "\n=== ABORTED ===" );
				break;
			}
		}catch( IllegalArgumentException e ){
			System.err.println( "\nUsage:\n\tjava Main [-Repeet] [-Delay <ms>] [-Encoding <name>] <TEXT_FILE>\n\tStop by [Enter]." );
			System.err.println( "Example:\n\tjava Main starwars.txt 2>nul" );
			System.err.println( "\tjava Main -r -d 10 -e UTF-8 starwars.txt" );
		}catch( Exception e ){
			e.printStackTrace();
		}finally{
			if( null != t ) t.isOn= false;
		}
	}

	public static class FileThread extends Thread{
		boolean isOn= true;
		boolean isRepeat= false;
		int iDelay= 10;//[ms]
		Charset charset= null;
		File file= null;
		BufferedReader br= null;

		public FileThread( File f, int i, boolean b, Charset c ){
			file= f;
			isRepeat= b;
			iDelay= i;
			charset= c;
			start();
		}

		BufferedReader bufferedReader( Charset c ) throws java.io.FileNotFoundException{
			return new BufferedReader( new InputStreamReader( new FileInputStream( file.getAbsolutePath() ), c ) );
		}

		public void run(){
			System.err.println( "=== THREAD START ===" );
			try{
				br= bufferedReader( charset );

				String s;
				while( isOn ){
					if( null == ( s= br.readLine() ) ){
						if( isRepeat ){
							br.close();
							br= bufferedReader( charset );
							continue;
						}else{
							break;
						}
					}
					for( int i= 0; isOn  && i < s.length(); i++ ){
						System.out.print( s.charAt( i ) );
						Thread.sleep( iDelay );
					}
					System.out.println( "" );
				}
			}catch( Exception e ){//readLine(),sleep()
				e.printStackTrace();
			}finally{
				isOn= false;
				if( null != br ){
					try{
						br.close();
					}catch( Exception e ){
						e.printStackTrace();
					}
				}
			}
			System.err.println( "=== THREAD END ===" );
		}
	}
}
