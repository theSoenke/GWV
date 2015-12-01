import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Word 
{
	private final String _word;
	private HashMap<Word, Integer> _probabilities;
	
	public Word(String word)
	{
		_word = word;
		_probabilities = new HashMap<Word, Integer>();
	}
	
	public String getWord()
	{
		return _word;
	}
	
	public void addNextWord(Word word)
	{
		if(_probabilities.containsKey(word))
		{
			int frequency = _probabilities.get(word);
			_probabilities.put(word, ++frequency);
		}
		else
		{
			_probabilities.put(word, 1);
		}
	}
	
	public Word getSuccessor()
	{
		Iterator<Entry<Word, Integer>> it = _probabilities.entrySet().iterator();
		Word successor = null;
		int frequenzy = 0;
		    
		while (it.hasNext()) 
		{
		    Map.Entry<Word, Integer> pair = it.next();
		      
		    if(pair.getValue() >= frequenzy)
		    {
		     	successor = pair.getKey();
		      	frequenzy = pair.getValue();
		    }
		    else if(successor == null)
		    {
		    	successor = pair.getKey();
		    }
		}
		
		return successor;
	}
}
