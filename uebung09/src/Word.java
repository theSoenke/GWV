
import java.util.HashMap;

public class Word
{
	private final String _word;
	private HashMap<Tag, Integer> _tags;

	public Word(String word)
	{
		_word = word;
		_tags = new HashMap<Tag, Integer>();
	}

	@Override
	public String toString()
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

	/*
	 * Returns how often the word is tagged with the tag
	 */
	public int getTagFrequenzy(Tag tag)
	{
		int freq = 0;
		if (_tags.containsKey(tag))
		{
			freq = _tags.get(tag);
		}

		return freq;
	}

	/*
	 * Returns all tags
	 */
	public Tag[] getTags()
	{
		return _tags.keySet().toArray(new Tag[_tags.size()]);
	}
}