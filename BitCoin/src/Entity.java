import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Entity {
	
	public String id;
	public String PrevHash;
	public String Hash;//Goofy(Pay to Pk Alice(H(signed by skGoofy(CreateCoin [uniqueCoinID])))
	public String Signature; //Always Scrooge's Signature
	public String ValueToEncode;
	
	public Entity(String PrevHash) {

			this.PrevHash=PrevHash;
			this.id=UUID.randomUUID().toString();
			
			
		
	}
	
	public void Hash(String Value) {
		try 
        { 
            //System.out.println("HashCode Generated by SHA-256 for:");  
  
			//System.out.println("Inside hash"+Value);
            this.Hash= toHexString(getSHA(Value));

        } 
        // For specifying wrong message digest algorithms  
        catch (NoSuchAlgorithmException e) {  
            System.out.println("Exception thrown for incorrect algorithm: " + e);  
        } 
	}
	
	public void Sign(User Signer) throws Exception {
		
        String signature = Signer.sign(Hash);
        this.Signature=signature;

		
	}
	
	   public  byte[] getSHA(String input) throws NoSuchAlgorithmException 
	    {  
	        // Static getInstance method is called with hashing SHA  
	        MessageDigest md = MessageDigest.getInstance("SHA-256");  
	  
	        // digest() method called  
	        // to calculate message digest of an input  
	        // and return array of byte 
	        //System.out.println(input);
	        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
	    } 
	    
	    public static String toHexString(byte[] hash) 
	    { 
	        // Convert byte array into signum representation  
	        BigInteger number = new BigInteger(1, hash);  
	  
	        // Convert message digest into hex value  
	        StringBuilder hexString = new StringBuilder(number.toString(16));  
	  
	        // Pad with leading zeros 
	        while (hexString.length() < 32)  
	        {  
	            hexString.insert(0, '0');  
	        }  
	  
	        return hexString.toString();  
	    } 
	  

}