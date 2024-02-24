import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.InputStream;

public class Main{
	public static void main( String[] args ){
		FileThread t= new FileThread( new File( "starwars.txt" ) );

//		InputStreamReader br = new InputStreamReader( System.in );
		InputStream br = System.in;
		try{
			while( true ){
				if( ! t.isOn ) break;
//				if( ! br.ready() ) continue;
				if( 0 >= br.available() ) continue;
				br.read();
				System.out.println( "\n=== ABORTED ===" );
				break;
			}
		}catch( Exception e ){
			e.printStackTrace();
		}finally{
			t.isOn= false;
		}
	}

	public static class FileThread extends Thread{
		boolean isOn= true;
		File file= null;
		BufferedReader br= null;

		public FileThread( File f ){
			file= f;
			start();
		}

		public void run(){
			System.out.println( "=== THREAD START ===" );
			try{
				br= new BufferedReader( new FileReader( file ) );
				String s;
				while( isOn  && null != ( s= br.readLine() ) ){
					for( int i= 0; isOn  && i < s.length(); i++ ){
						System.out.print( s.charAt( i ) );
						Thread.sleep( 10 );//[ms]
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
			System.out.println( "=== THREAD END ===" );
		}
	}
}
