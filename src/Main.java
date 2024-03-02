import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.InputStream;

public class Main{
	public static void main( String[] a ){
		boolean isRepeat= false;
		int iDelay= 10;//[ms]
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
				}else{
					System.err.print( String.format( "%d:%s,", i, a[i] ) );
					file= new File( a[i] );
				}
			}
			if( null == file ) throw new IllegalArgumentException();
			System.err.println( String.format( "\n---\ndelay: %dms", iDelay ) );
			System.err.println( String.format( "repeat: %s", ( isRepeat ? "ON" : "OFF") ) );
			System.err.println( String.format( "file: %s",  file.getAbsolutePath() ) );

			t= new FileThread( file, iDelay, isRepeat );
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
			System.err.println( "\nUsage:\n\tjava Main [-Repeet] [-Delay ms] TEXT_FILE\n\tStop by [Enter]." );
			System.err.println( "Example:\n\tjava Main starwars.txt 2>nul" );
			System.err.println( "\tjava Main -r -d 10 starwars.txt" );
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
		File file= null;
		BufferedReader br= null;

		public FileThread( File f, int i, boolean b ){
			file= f;
			isRepeat= b;
			iDelay= i;
			start();
		}

		public void run(){
			System.err.println( "=== THREAD START ===" );
			try{
				br= new BufferedReader( new InputStreamReader( new java.io.FileInputStream( file.getAbsolutePath() ), "UTF-8" ) ); 

				String s;
				while( isOn ){
					if( null == ( s= br.readLine() ) ){
						if( isRepeat ){
							br.close();
							br= new BufferedReader( new FileReader( file ) );
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
