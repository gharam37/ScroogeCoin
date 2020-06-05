import java.util.ArrayList;

public class Block extends Entity{

	int MaxTransactions=3;
	ArrayList<Transaction> Transactions;
	public Block(String PrevHash,int MaxTransactions) {
		super(PrevHash);
		this.MaxTransactions=MaxTransactions;
		this.id="block_"+this.id;
		Transactions=new ArrayList<Transaction>();
		this.ValueToEncode=Transactions+PrevHash;
		Hash(ValueToEncode);
		
	}
	
	
	public String toString() {
		String def= this.id+"\n"+"Hash Value:" +Hash+"\n"+"Number of transaction:"+this.Transactions.size();
		def+="\nTransactions ids:\n";

		for(int i=0;i<Transactions.size();i++) {
			def+="\n"+this.Transactions.get(i)+"\n";
			
		}
		
		return def;
	}
	
	
	public static void main(String[]args) throws Exception {
		
		User s= new User();
		Block b= new Block(null,3);
		System.out.println(b.id);
		b.Sign(s);
		for(int i=0;i<5;i++) {
			Block b2=new Block(b.Hash,3);
			b2.Sign(s);
			System.out.println(b2.id);
			System.out.println(b2.Hash);
			System.out.println(b2.Signature);
			System.out.println("Encoding"+b2.ValueToEncode);

			b=b2;

			
		}

		
	}
	

}
