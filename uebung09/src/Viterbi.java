public class Viterbi
{
	private HMM _hmm;

	public Viterbi(HMM hmm)
	{
		_hmm = hmm;
	}

	/*
	 * Returns tagged sentence with the highest sequence tag probability
	 */
	public String mostLikelySequence(String sentence)
	{
		String[] words = sentence.split("\\s+");
		StringBuffer buffer = new StringBuffer();
		Tag previousTag = Tag.START_TAG;

		for (int i = 0; i < words.length; i++)
		{
			Word word = _hmm.getWord(words[i]);
			Tag tag = null;
			double transmissionProb, emissionPro, tagProb = 0;

			if (_hmm.checkWord(words[i]))
			{
				Tag[] tags = word.getTags();

				for (Tag t : tags)
				{
					transmissionProb = _hmm.getTransmissionProbability(t, previousTag);
					emissionPro = _hmm.getEmissionProbability(t, word);

					double newProb = transmissionProb * emissionPro;
					if (newProb >= tagProb)
					{
						tagProb = newProb;
						tag = t;
					}
				}

				buffer.append(words[i] + "\t" + tag.toString() + "\n");
			}
			else
			{
				buffer.append(words[i] + "\t" + "Does not exist in traningsdata" + "\n");
			}
		}

		return buffer.toString();
	}
}
