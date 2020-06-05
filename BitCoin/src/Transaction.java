import java.util.UUID;
import java.math.BigInteger;  
import java.nio.charset.StandardCharsets; 
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
  

public class Transaction extends Entity{
	
	public int coins=1;
	public String Sender;
	public String Receiver;
	public String CoinId; // Check in the blockain for the last transaction that included this coin, If sender = last receiver then owner
	
	public String ValueToEncode;
	
	
	public Transaction(String Sender,String Receiver,String CoinId,String PrevHash) {
		super(PrevHash);
		
		this.Sender=Sender;
		this.Receiver=Receiver;
		this.CoinId=CoinId;
		this.id="transaction_"+this.id;
		//System.out.println("Sender"+Sender);
		//System.out.println("Receiver"+Receiver);
		//System.out.println("Coin"+CoinId);
		//System.out.println("Prev Hash"+PrevHash);
		ValueToEncode="User: "+Sender+" \n"+"is attempting to send "+CoinId+"\nTo user:" +Receiver+"\nGiven this Previous Hash "+PrevHash;
	
		Hash(ValueToEncode);




	}

	public String toString() {
		return this.id;
	}
	

	    
	    
	
	public static void main(String[]args) throws Exception {
		
		User s= new User();
		Coin c= new Coin();
		Transaction b= new Transaction(s.id,s.id,c.id,null);
		//System.out.println(b.id);
		//b.Hash();
		//System.out.println(b.ValueToEncode+"sba7 el fol");
		b.Hash(b.ValueToEncode);

		b.Sign(s);
		for(int i=0;i<5;i++) {
			 s= new User();
			 c= new Coin();
			Transaction b2=new Transaction(s.id,s.id,c.id,b.Hash);
			b2.Sign(s);
			//System.out.println(b2.id);
			//System.out.println(b2.Hash);
			//System.out.println(b2.Signature);
			//System.out.println("Encoding"+b2.ValueToEncode);

			b=b2;
			System.out.println(b2.ValueToEncode);


			
		}

		
	}
	
	
	
	

}
