import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MarkovChain 
{
	private HashMap<String, Word> _words;
	
	public MarkovChain(String path)
	{
		_words = new HashMap<String, Word>();

		readTextFile(path);
		System.out.println("Unique words: " + _words.size());
	}
	
	/**
	 * Liest die Textdatei ein
	 * @param path
	 */
	private void readTextFile(String path)
	{
		File filePath = new File(path);

		FileReader fileReader = null;
		BufferedReader reader = null;
		List<Word> trigramList = new LinkedList<Word>();
		
		try 
		{
			fileReader = new FileReader(filePath);
			reader = new BufferedReader(fileReader);
			Word predecessor = null;
			String line;

			while ((line = reader.readLine()) != null)
			{
				Word word = null;
				
				if(!_words.containsKey(line))
				{
					word = new Word(line);
					_words.put(line, word);
				}
				else
				{
					word = _words.get(line);
				}
				
				if(predecessor != null)
				{
					predecessor.addBigram(word);
				}
				
				trigramList.add(word);
				
				if(trigramList.size() == 3)
				{
					trigramList.get(0).addTrigram(trigramList.get(1), trigramList.get(2));
					trigramList.remove(0);
				}

				predecessor = word;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void printBigramSentence(String start, int length)
	{
		if(_words.containsKey(start))
		{
			StringBuffer buffer = new StringBuffer();
			
			Word word = _words.get(start);
			
			for(int i = 0; i < length; i++)
			{
				buffer.append(word.getWord() + " ");
				word = word.getNextState();
			}
			
			System.out.println(buffer.toString());
		}
		else
		{
			System.out.println("Word does not exist");
		}
	}
	
	public void printTrigramSentence(String start, int length)
	{
		if(_words.containsKey(start))
		{
			StringBuffer buffer = new StringBuffer(start + " ");
			
			Word word = _words.get(start);
			
			for(int i = 0; i < length; i++)
			{
				List<Word> trigram = word.getNextTrigramState();
				word = trigram.get(1);
				buffer.append(trigram.get(0).getWord() + " " + trigram.get(1).getWord() + " ");
			}
			
			System.out.println(buffer.toString());
		}
		else
		{
			System.out.println("Word does not exist");
		}
	}
}
