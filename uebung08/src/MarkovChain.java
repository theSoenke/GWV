import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

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
					predecessor.addNextWord(word);
				}

				predecessor = word;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void printSentence(String start, int length)
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
}
