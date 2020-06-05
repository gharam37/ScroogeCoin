import java.util.ArrayList;
import java.util.UUID;

public class Coin {
	
	String id;
	ArrayList<Transaction> CoinTransactions;
	
	
	public Coin() {
		id = "Coin_"+UUID.randomUUID().toString();

		CoinTransactions= new ArrayList<Transaction>();
	}
	
	public void addTransaction() {
		
		
	}
	
	public static void main(String[]args) {
		/*for(int i=0;i<3;i++) {
			System.out.println(new Coin().id);
		}*/
	}

}
