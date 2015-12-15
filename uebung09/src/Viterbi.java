
public class Viterbi
{
	private HMM _hmm;
	
	public Viterbi(HMM hmm)
	{
		_hmm = hmm;
	}
	
	// TODO Satz taggen
	public String mostLikelySequence(String sentence)
	{
		String[] words = sentence.split("\\s+");
		StringBuffer buffer = new StringBuffer();
		String tag;
		
		for(int i = 0; i < words.length; i++)
		{
			// TODO get tag of words[i]
			tag = "";
			
			buffer.append(words[i] + " " + tag);
		}
		
		return buffer.toString();
	}
}
