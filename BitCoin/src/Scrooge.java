import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

//This is our main class
public class Scrooge extends User {
	
	ArrayList<User> Users;
	public Block CurrentBlock;
	ArrayList<Block> Blockchain;
	public Scrooge() throws Exception {
		super();
		this.id= "Scrooge_"+UUID.randomUUID().toString();

		this.Blockchain= new ArrayList<Block>();
		CurrentBlock= new Block(null,6);
    	Scrooge=true;

		CreateUsers(2,2);
	}
	
	public void CreateUsers(int NumberOfUsers,int NumberOfCoins) throws Exception {
		this.Users= new ArrayList<User>();

		for(int i=0;i<NumberOfUsers;i++) {
			User s=new User();
			CreateCoins(NumberOfCoins,s);
			Users.add(s);
		}
	}

	public void CreateCoins(int NumberOfCoins,User s) throws Exception {
		
		for(int i=0;i<NumberOfCoins;i++) {
			Coin c= new Coin();
			PayUser(this,s,c);
			s.Wallet.add(c);
			
		}
		
		
		
		
		
	}
	public String CheckOwnerShip() {
		
		//Return prev block is i was the last receiver
		return null;
	}
	public boolean PayUser(User Sender,User Receiver, Coin c) throws Exception {
		Transaction t;
		if(this.Scrooge) {
		 t= new Transaction(Sender.id,Receiver.id,c.id,null);
		}
		else {
		  //Check Blockchain for ownership. Allow double spending if current block isn't published
			
			t=null;
			
			
		}
		System.out.println(t.ValueToEncode);
		System.out.println("\n");
	    t.Sign(Sender);
		boolean Verification = verify(t.Hash,t.Signature); //Scrooge Verifying Signature
		//System.out.println(Verification);
		if(!Verification)
			return false;
		
		//Double Spending
		if(DoubleSpending(t)) 
			return false;
		
		//Adding to Block to blockchain

		if(CurrentBlock.Transactions.size()==CurrentBlock.MaxTransactions) {
			CurrentBlock.Sign(this); //Scrooge Signing
			Blockchain.add(CurrentBlock);
			//Update Memberships
			
			
			//LOOP over current block change wallets accordingly 
			UpdateMemberships();
			CurrentBlock= new Block(CurrentBlock.Hash,CurrentBlock.MaxTransactions);
		}
		
		
		return true;
		
		
		
		
		
		
	}
	public void UpdateMemberships() {
		
	}
	public boolean DoubleSpending(Transaction t) {
		for(int i=0;i<CurrentBlock.Transactions.size();i++) {
			if(t.PrevHash!=null && CurrentBlock.Transactions.get(i).PrevHash.equals(t.PrevHash)) {
				System.out.println("Double spending attack ");
				return true;
			}
		}
		CurrentBlock.Transactions.add(t);
		return false;
		
	}
	public void Run() {
		
		 Scanner input = new Scanner(System.in);
		   while(true) {
			   //Pick 2 random users and a number of coins 
			   
			   //Loop to add several transactions
				if(input.nextLine().equals(" ")) {

			    	System.out.println("Terminating");
			    	break;
			    	}

				else {
					System.out.println("Executing code");
				}
		    
		    }
		 
	}
	
	public static void main(String[] args) throws Exception {
		//Run();
		
		Scrooge Scrooge= new Scrooge();
		String Something=null;
		//System.out.println(Something.equals(""));
		//System.out.println(null+"");
		//System.out.print(Scrooge.id);
		
		
	}

}
