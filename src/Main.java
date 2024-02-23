import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main{
	public static void main( String[] args ){
		BufferedReader br= new BufferedReader( new InputStreamReader( System.in ) );
		try{
			String s;
			while( null != ( s= br.readLine() ) ){
				for( int i= 0; i < s.length(); i++ ){
					System.out.print( s.charAt( i ) );
					Thread.sleep( 10 );
				}
				System.out.println( "" );
			}
		}catch( Exception e ){//readLine(),sleep()
			e.printStackTrace();
		}
	}
}
