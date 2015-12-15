import java.util.HashMap;

public class Tag
{
	private String _tag;
	private HashMap<Tag, Integer> _transmissions;
	private HashMap<Word, Integer> _emmissions;

	public Tag(String tag)
	{
		_tag = tag;
		_transmissions = new HashMap<Tag, Integer>();
		_emmissions = new HashMap<Word, Integer>();

	}

	public String getTag()
	{
		return _tag;
	}

	public void addPreceseccor(Tag tag)
	{
		if (_transmissions.containsKey(tag))
		{
			int frequency = _transmissions.get(tag);
			_transmissions.put(tag, ++frequency);
		}
		else
		{
			_transmissions.put(tag, 1);
		}
	}

	public void addEmmission(Word word)
	{
		if (_emmissions.containsKey(word))
		{
			int frequency = _emmissions.get(word);
			_emmissions.put(word, ++frequency);
		}
		else
		{
			_emmissions.put(word, 1);
		}
	}

	public int getNumTransmission()
	{
		return _transmissions.size();
	}
	
	public int getFrequenzyOfTransmissions(Tag tag)
	{
		if(_transmissions.containsKey(tag))
		{
			return _transmissions.get(tag);
		}
		return 0;
	}

	public void getEmissionProbabilty(Word word)
	{
		// TODO return emission probability
	}
}
