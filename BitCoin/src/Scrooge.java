import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

//This is our main class
public class Scrooge extends User {
	
	ArrayList<User> Users;
	public Block CurrentBlock;
	ArrayList<Block> Blockchain;
	PrintWriter writer = new PrintWriter("Simulation.txt", "UTF-8");
	public String FinalBlockSignature;

	public Scrooge() throws Exception {
		super();
		writer.println("Creating Scrooge");
		System.out.println("Creating Scrooge");
		this.id= "Scrooge_"+UUID.randomUUID().toString();

		this.Blockchain= new ArrayList<Block>();
		CurrentBlock= new Block(null,2);
    	Scrooge=true;
    	
		writer.println("Creating users and Assigning coins");
		System.out.println("Creating users and Assigning coins");


		
		// 10 users , 10 coins

		CreateUsers(2,2);

	}
	
	public String DisplayBlockChain() {
		String def="Blockchain after a new block is added\n"+"Number of blocks:"+this.Blockchain.size()+"\n";
		def+="Signature of final block:"+this.FinalBlockSignature+"\n";
		
		for(int i=0;i<Blockchain.size();i++) {
			Block current=Blockchain.get(i);
			def+="Block:"+i+"\n"+"Hash value:" +current.Hash+"\nHash pointer to previous block:"+current.PrevHash
					+"\n"+"Number Of transactions:"+current.Transactions.size()+"\n";
			
		}
		return def;
		
	}
	
	public void CreateUsers(int NumberOfUsers,int NumberOfCoins) throws Exception {
		this.Users= new ArrayList<User>();

		for(int i=0;i<NumberOfUsers;i++) {
			User s=new User();
			CreateCoins(NumberOfCoins,s);
			Users.add(s);
		}
		System.out.println("Finished creating users\n");
		writer.println("Finished creating users\n");
		for(int i=0;i<Users.size();i++) {
			System.out.println(Users.get(i)+"\n");
			writer.println(Users.get(i)+"\n");

			
		}
	}

	public void CreateCoins(int NumberOfCoins,User s) throws Exception {
		
		for(int i=0;i<NumberOfCoins;i++) {
			Coin c= new Coin();
			PayUser(this,s,c);
			s.Wallet.add(c);
			
		}
		
		
		
		
		
	}
	public String CheckOwnerShip(User Sender, Coin c) {
		
		//Return prev block is i was the last receiver
		//Get Last transaction
		Transaction LastTransaction=null;
		for(int i=0;i<Blockchain.size();i++) {
			Block current = Blockchain.get(i);
			for(int j=0;j<current.Transactions.size();j++) {
				
				Transaction currentTransaction=current.Transactions.get(j);
				if(currentTransaction.CoinId.equals(c.id)) {
					LastTransaction=currentTransaction;
				}
				
				
			}
		}


		//Will never happen in our simulation cuz you only know about coins if you own them
	    if(!Sender.id.equals(LastTransaction.Receiver)) {
				System.out.println("User isn't the last receiver");
				writer.println("User isn't the last receiver");
				return null;
				
			
		}
		else {
		//System.out.println("User is the owner");
		//writer.println("User is the owner");
		}
		
		return LastTransaction == null ? null : LastTransaction.Hash;

	}
	public boolean PayUser(User Sender,User Receiver, Coin c) throws Exception {
		Transaction t;
	    System.out.println("Random transaction\n");
		writer.println("Random transaction\n");
		if(Sender.Scrooge) {
		 t= new Transaction(Sender.id,Receiver.id,c.id,null);
		}
		else {
		  //Check Blockchain for ownership. Allow double spending if current block isn't published
			String prevHash= CheckOwnerShip(Sender,c);
			//System.out.println(prevHash+"\n");
			//writer.println(prevHash+"\n");

			//System.out.println("ELSEEEEEEEE");
			
			
		    t= new Transaction(Sender.id,Receiver.id,c.id,prevHash);

			
			
		}
		System.out.println(t.ValueToEncode+"\n");
		writer.println(t.ValueToEncode+"\n");
	    t.Sign(Sender);
		boolean Verification = verify(t.Hash,t.Signature,Sender.Keys); //Scrooge Verifying Signature
		//System.out.println("Verification"+Verification);
		if(!Verification)
			return false;
		
		//Double Spending

		if(DoubleSpending(t) ) { 
			//System.out.println("Double Spending"+DoubleSpending(t));
			return false;
		}
		
		//Adding to Block to blockchain

		if(CurrentBlock.Transactions.size()==CurrentBlock.MaxTransactions) {
			CurrentBlock.Sign(this); //Scrooge Signing
			this.FinalBlockSignature=CurrentBlock.Signature; //Last block signature
			Blockchain.add(CurrentBlock);
			//Update Memberships
			
			System.out.println(DisplayBlockChain());
			writer.println(DisplayBlockChain());
			//LOOP over current block change wallets accordingly 
			if(!Sender.Scrooge) {
			UpdateMemberships();
			}
			CurrentBlock= new Block(CurrentBlock.Hash,CurrentBlock.MaxTransactions);
		}
		
	    System.out.println("Block Under Construction\n"+CurrentBlock+"\n");
	    writer.println("Block Under Construction\n"+CurrentBlock+"\n");
		
		return true;
		
		
		
		
		
		
	}
	public void UpdateMemberships() {
		
		for(int i=0;i<CurrentBlock.Transactions.size();i++) {
			String SenderId=(CurrentBlock.Transactions.get(i)).Sender;
			String ReceiverId=(CurrentBlock.Transactions.get(i)).Receiver;
			String CoinId= (CurrentBlock.Transactions.get(i)).CoinId;
			User Sender=null;
			User Receiver=null;
		    Coin c=null;
			for(int j=0;j<Users.size();j++) {
				if(Users.get(j).id.equals(SenderId)) {
					Sender=Users.get(j);
				}
				if(Users.get(j).id.equals(ReceiverId)) {
					Receiver=Users.get(j);
				}
				
			}
			System.out.println("Sender In Update Membership"+Sender);
			System.out.println("Receiver In Update Membership"+Sender);

			for(int j=0;j<Sender.Wallet.size();j++) {
				if(Sender.Wallet.get(j).id.equals(CoinId)) {
					c=Sender.Wallet.get(j);
					Receiver.Wallet.add(c);
					Sender.Wallet.remove(c);
				}
				
			}

			
		}
		
		System.out.println("Updated users Wallets");
		writer.println("Updated users Wallets");
		for(int i=0;i<Users.size();i++) {
		
			System.out.println(Users.get(i));
			writer.println(Users.get(i));

		}

		
	}
	public boolean DoubleSpending(Transaction t) {
		for(int i=0;i<CurrentBlock.Transactions.size();i++) {
			System.out.println(CurrentBlock.Transactions.get(i).PrevHash);
			System.out.println(CurrentBlock.Transactions.get(i));
			if(t.PrevHash!=null && (CurrentBlock.Transactions.get(i).PrevHash.equals(t.PrevHash))) {
				System.out.println("Double spending attack ");
				writer.println("Double spending attack");
				return true;
			}
		}
		CurrentBlock.Transactions.add(t);
		return false;
		
	}
	public void Run() throws Exception {
		
		 Scanner input = new Scanner(System.in);
		   while(true) {
			   //Pick 2 random users and a number of coins 
			   
			   //Loop to add several transactions
		     // Obtain a number between [0 - 49].
               Random rand = new Random();

            	
              int n = rand.nextInt(Users.size());
              User Sender= Users.get(n);
              while(Sender.Wallet.size()<=0) {
            	   n = rand.nextInt(Users.size());
                   Sender= Users.get(n);
            	  
              }
              n = rand.nextInt(Users.size());
              User Receiver= Users.get(n);
              
              n = rand.nextInt(Sender.Wallet.size());
          
              System.out.println("User: "+Sender.id+" Is sending User:"+Receiver.id+" "+(n+1)+" Coins"+"\n");
              writer.println("User: "+Sender.id+" Is sending User:"+Receiver.id+" "+(n+1)+" Coins"+"\n");


              for(int i=0;i<n+1;i++) {
            	
              Coin c = Sender.Wallet.get(n);
           
              PayUser(Sender,Receiver,c);
              }
			

				if(input.nextLine().equals(" ")) {

			    	System.out.println("Terminating");
					writer.close();

			    	break;
			    	}

				
		    
		    }
		 
	}
	
	public static void main(String[] args) throws Exception {
		//Run();
		
		Scrooge Scrooge= new Scrooge();
		Scrooge.Run();
		

		
		
	}

}
