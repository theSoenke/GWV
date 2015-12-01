

public class Main 
{
	
	public static void main(String[] args)
	{
		new Main();
	}
	
	public Main()
	{
		MarkovChain markowChain = new MarkovChain("heiseticker-text.txt");
		markowChain.printSentence("Valley", 10);
	}
}
