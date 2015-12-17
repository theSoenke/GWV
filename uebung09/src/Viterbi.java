public class Viterbi
{
	private HMM _hmm;

	public Viterbi(HMM hmm)
	{
		_hmm = hmm;
	}

	public String mostLikelySequence(String sentence)
	{
		String[] words = sentence.split("\\s+");
		StringBuffer buffer = new StringBuffer();
		Tag previousTag = Tag.START_TAG;

		for (int i = 0; i < words.length; i++)
		{
			Word word = _hmm.getWord(words[i]);
			Tag tag = null;
			double tp, ep, tagProb = 0;

			if (word != null)
			{
				Tag[] tags = word.getTags();

				for (Tag t : tags)
				{
					tp = _hmm.getTransmissionProbability(previousTag, t);
					ep = _hmm.getEmissionProbability(t, word.getWord());

					double newProb = tp * ep;
					if (newProb >= tagProb)
					{
						tagProb = newProb;
						tag = t;
					}

					System.out.println(t.toString() + "tp: " + tp);
				}
				buffer.append(words[i] + " \\ " + tag.toString() + " ");
				System.out.println(words[i] + " " + tag.toString());
			}
			else
			{
				System.out.println("Word does not exist in tranings data");
				continue;
			}
		}

		return buffer.toString();
	}
}
