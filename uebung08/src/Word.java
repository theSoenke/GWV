import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Word 
{
	private final String _word;
	private HashMap<Word, Integer> _bigrams;
	private HashMap<Word, HashMap<Word, Integer>> _trigrams;
	
	public Word(String word)
	{
		_word = word;
		_bigrams = new HashMap<Word, Integer>();
		_trigrams = new HashMap<Word,HashMap<Word, Integer>>();
	}
	
	public String getWord()
	{
		return _word;
	}
	
	public void addBigram(Word word)
	{
		if(_bigrams.containsKey(word))
		{
			int frequency = _bigrams.get(word);
			_bigrams.put(word, ++frequency);
		}
		else
		{
			_bigrams.put(word, 1);
		}
	}
	
	public void addTrigram(Word word1, Word word2)
	{
		if(_trigrams.containsKey(word1))
		{
			HashMap<Word, Integer> trigram = _trigrams.get(word1);
			
			if(trigram.containsKey(word2))
			{
				int frequency = trigram.get(word2);				
				trigram.put(word1, ++frequency);
			}
			else
			{
				trigram.put(word1, 1);
			}
		}
		else
		{
			HashMap<Word, Integer> trigram = new HashMap<Word, Integer>();
			trigram.put(word2, 1);
			_trigrams.put(word1, trigram);
		}
	}
	
	public Word getNextState()
	{
		Iterator<Entry<Word, Integer>> it = _bigrams.entrySet().iterator();
		Word nextState = null;
		int frequenzy = 0;
		    
		while (it.hasNext()) 
		{
		    Map.Entry<Word, Integer> pair = it.next();
		      
		    if(pair.getValue() >= frequenzy)
		    {
		     	nextState = pair.getKey();
		      	frequenzy = pair.getValue();
		    }
		    else if(nextState == null)
		    {
		    	nextState = pair.getKey();
		    	frequenzy = pair.getValue();
		    }
		}
		
		return nextState;
	}
	
	public List<Word> getNextTrigramState()
	{
		Iterator<Entry<Word, HashMap<Word, Integer>>> it = _trigrams.entrySet().iterator();
		List<Word> nextStates = new ArrayList<Word>();
		int frequenzy = 0;
		    
		while (it.hasNext()) 
		{
		    Map.Entry<Word, HashMap<Word, Integer>> pair = it.next();
		    
			Iterator<Entry<Word, Integer>> it2 = pair.getValue().entrySet().iterator();
			
			while(it2.hasNext())
			{
			    Map.Entry<Word, Integer> pair2 = it2.next();
			    
			    if(pair2.getValue() >= frequenzy)
			    {
			     	nextStates.add(pair.getKey());
			     	nextStates.add(pair2.getKey());
			      	frequenzy = pair2.getValue();
			    }
			}
		      
		}
		
		return nextStates;
	}
}
