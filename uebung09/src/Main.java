
public class Main
{

	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		HMM hmm = new HMM("hdt-1-10000-train.tags");

		Viterbi viterbi = new Viterbi(hmm);
		String taggedSentence = viterbi.mostLikelySequence("Das ist New York");
		System.out.println(taggedSentence);
	}
}