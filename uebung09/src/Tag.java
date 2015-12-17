import java.util.HashMap;

public class Tag
{
	public static Tag START_TAG = new Tag("START");

	private String _tag;
	private HashMap<String, Integer> _transmissions;
	private int _frequenzy;

	public Tag(String tag)
	{
		_tag = tag;
		_transmissions = new HashMap<String, Integer>();
	}

	@Override
	public String toString()
	{
		return _tag;
	}

	public void addPredecessor(Tag tag)
	{
		if (_transmissions.containsKey(tag.toString()))
		{
			int frequency = _transmissions.get(tag.toString());
			_transmissions.put(tag.toString(), ++frequency);
		}
		else
		{
			_transmissions.put(tag.toString(), 1);
		}
	}

	public int getNumTransmission()
	{
		return _transmissions.size();
	}

	/*
	 * Frequenzy of tranmission to tag
	 */
	public int getFrequenzyOfTransmissions(Tag tag)
	{
		if (_transmissions.containsKey(tag.toString()))
		{
			return _transmissions.get(tag.toString());
		}
		return 0;
	}

	public void increaseFrequenzy()
	{
		_frequenzy++;
	}

	public int getFrequenzy()
	{
		return _frequenzy;
	}
}
