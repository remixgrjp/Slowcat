import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main{
	public static void main( String[] args ){
		BufferedReader br= null;
		try{
			br= new BufferedReader( new FileReader( new File( "starwars.txt" ) ) );
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
		}finally{
			if( null != br ){
				try{
					br.close();
				}catch( Exception e ){
					e.printStackTrace();
				}
			}
		}
	}
}
