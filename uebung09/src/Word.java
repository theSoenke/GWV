
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Word
{
	private final String _word;
	private HashMap<Tag, Integer> _bigrams;

	public Word(String word)
	{
		_word = word;
		_bigrams = new HashMap<Tag, Integer>();
	}

	public String getWord()
	{
		return _word;
	}

	public void addBigram(Tag tag)
	{
		if (_bigrams.containsKey(tag))
		{
			int frequency = _bigrams.get(tag);
			_bigrams.put(tag, ++frequency);
		}
		else
		{
			_bigrams.put(tag, 1);
		}
	}

	public Tag getTag()
	{
		// TO-DO Ausrechnen was der aktuelle Tag ist
		Iterator<Entry<Tag, Integer>> it = _bigrams.entrySet().iterator();
		Tag nextState = null;
		int frequenzy = 0;

		while (it.hasNext())
		{
			Map.Entry<Tag, Integer> pair = it.next();

			if (pair.getValue() >= frequenzy)
			{
				nextState = pair.getKey();
				frequenzy = pair.getValue();
			}
			else
				if (nextState == null)
				{
					nextState = pair.getKey();
					frequenzy = pair.getValue();
				}
		}

		return nextState;
	}

	public Tag getMostFrequentTag()
	{
		// TO-DO Ausrechnen was der aktuelle Tag ist
		Iterator<Entry<Tag, Integer>> it = _bigrams.entrySet().iterator();
		Tag nextState = null;
		int frequenzy = 0;

		while (it.hasNext())
		{
			Map.Entry<Tag, Integer> pair = it.next();

			if (pair.getValue() >= frequenzy)
			{
				nextState = pair.getKey();
				frequenzy = pair.getValue();
			}
			else
				if (nextState == null)
				{
					nextState = pair.getKey();
					frequenzy = pair.getValue();
				}
		}

		return nextState;
	}

}