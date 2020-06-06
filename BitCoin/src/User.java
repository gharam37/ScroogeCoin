import java.util.ArrayList;
import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.*;
import java.util.Base64;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
public class User {

	
	ArrayList<Coin> Wallet;
	String id;
	KeyPair Keys;
	boolean Scrooge;
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        return pair;
    }

    public User() throws Exception {
    	
    	
    	this.Keys=generateKeyPair();
    	this.id= "user_"+UUID.randomUUID().toString();
    	this.Wallet= new ArrayList<Coin>();
    	Scrooge=false;

    	
    }
    
    public String toString() {
		String Definition=id+"\n"+"Public Key: "+Keys.getPublic()+"\nNumber Of coins:"+Wallet.size()+"\nWallet Content\n";
		for(int i=0;i<Wallet.size();i++) {
			Definition+="\n"+Wallet.get(i);
		}
		Definition+="\n";
				
				
	    return Definition;
    }
    //https://niels.nu/blog/2016/java-rsa.html
    public static KeyPair getKeyPairFromKeyStore() throws Exception {


        InputStream ins = User.class.getResourceAsStream("/keystore.jks");

        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(ins, "s3cr3t".toCharArray());   //Keystore password
        KeyStore.PasswordProtection keyPassword =       //Key password
                new KeyStore.PasswordProtection("s3cr3t".toCharArray());

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("mykey", keyPassword);

        java.security.cert.Certificate cert = keyStore.getCertificate("mykey");
        PublicKey publicKey = cert.getPublicKey();
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        return new KeyPair(publicKey, privateKey);
    }


    public String sign(String plainText) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(Keys.getPrivate());
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public  boolean verify(String plainText, String signature,KeyPair UserKeys) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(UserKeys.getPublic());
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }

    public static void main(String... argv) throws Exception {

        
        User s=new User();
        String signature = s.sign("foobar");

        //Let's check the signature
        boolean isCorrect = s.verify("foobar", signature,s.Keys);
        System.out.println("Signature correct: " + isCorrect);
    }
	
}
