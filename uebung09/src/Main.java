
public class Main
{

	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		POSTagger tagger = new POSTagger("hdt-1-10000-train.tags");
		tagger.printBigramSentence("Silicon");
	}
}