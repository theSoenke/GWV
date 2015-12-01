

public class Main 
{
	
	public static void main(String[] args)
	{
		new Main();
	}
	
	public Main()
	{
		MarkovChain markowChain = new MarkovChain("heiseticker-text.txt");
		markowChain.printBigramSentence("Silicon", 20);
		markowChain.printTrigramSentence("Silicon", 10);
	}
}
