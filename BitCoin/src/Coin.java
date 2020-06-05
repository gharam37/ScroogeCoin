import java.util.ArrayList;
import java.util.UUID;

public class Coin {
	
	String id;
	
	
	public Coin() {
		id = "Coin_"+UUID.randomUUID().toString();

	}
	
	public void addTransaction() {
		
		
	}
	
	public String toString() {
		return this.id;
	}
	
	public static void main(String[]args) {
		/*for(int i=0;i<3;i++) {
			System.out.println(new Coin().id);
		}*/
	}

}
