
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Word
{
	private final String _word;
	private HashMap<Tag, Integer> _tags;

	public Word(String word)
	{
		_word = word;
		_tags = new HashMap<Tag, Integer>();
	}

	public String getWord()
	{
		return _word;
	}

	public void addTag(Tag tag)
	{
		if (_tags.containsKey(tag))
		{
			int frequency = _tags.get(tag);
			_tags.put(tag, ++frequency);
		}
		else
		{
			_tags.put(tag, 1);
		}
	}

	public Tag getTags()
	{
		// TO-DO Ausrechnen was der aktuelle Tag ist
		Iterator<Entry<Tag, Integer>> it = _tags.entrySet().iterator();
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