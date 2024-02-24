import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main{
	public static void main( String[] args ){
		ThreadA t = new ThreadA();
		t.start();

		BufferedReader br= null;
		try{
			br= new BufferedReader( new FileReader( new File( "starwars.txt" ) ) );
			String s;
			while( t.isOn  && null != ( s= br.readLine() ) ){
				for( int i= 0; t.isOn  && i < s.length(); i++ ){
					System.out.print( s.charAt( i ) );
					Thread.sleep( 10 );
				}
				System.out.println( "" );
			}
		}catch( Exception e ){//readLine(),sleep()
			e.printStackTrace();
		}finally{
			t.isOn= false;
			if( null != br ){
				try{
					br.close();
				}catch( Exception e ){
					e.printStackTrace();
				}
			}
		}
	}

	public static class ThreadA extends Thread{
		boolean isOn= true;

		public void run(){
			System.out.println( "=== THREAD START ===" );
			InputStreamReader br = new InputStreamReader( System.in );
			while( isOn ){
				try{
					if( ! br.ready() ) continue;
					int i= br.read();//int i= System.in.read();
				//	if( 'q' == (char)i ){
						System.out.println( "\naborted" );
						isOn= false;
				//	}
				}catch( Exception e ){
					e.printStackTrace();
				}
			}
			System.out.println( "=== THREAD END ===" );
		}
	}
}
