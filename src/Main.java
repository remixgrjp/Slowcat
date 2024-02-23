import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main{
	public static void main( String[] args ){
		BufferedReader br= new BufferedReader( new InputStreamReader( System.in ) );
		try{
			String s;
			while( null != ( s= br.readLine() ) ){
				System.out.println( s );
			}
		}catch( IOException e ){
			e.printStackTrace();
		}
	}
}
