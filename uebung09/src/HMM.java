import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HMM
{
	private HashMap<String, Word> _words;
	private HashMap<String, Tag> _tags;

	public HMM(String path)
	{
		_words = new HashMap<String, Word>();
		_tags = new HashMap<String, Tag>();

		readTextFile(path);
		System.out.println("Unique words: " + _words.size());
	}

	/**
	 * Liest die Textdatei ein
	 */
	private void readTextFile(String path)
	{
		File filePath = new File(path);

		FileReader fileReader = null;
		BufferedReader reader = null;
		String[] splittedLine = new String[2];

		try
		{
			fileReader = new FileReader(filePath);
			reader = new BufferedReader(fileReader);
			Word word;
			Tag tag;
			Tag predecessor = null;
			String line;

			while ((line = reader.readLine()) != null)
			{
				if (!line.isEmpty())
				{
					line = new StringBuilder(line).reverse().toString();
					// split in word and Tag
					splittedLine = line.split("\\s", 2);

					String wordStr = new StringBuilder(splittedLine[1]).reverse().toString();
					String tagStr = new StringBuilder(splittedLine[0]).reverse().toString();
					System.out.println(splittedLine[1]);

					word = new Word(wordStr);
					tag = new Tag(tagStr);

					if (predecessor != null)
					{
						tag.addPreceseccor(predecessor);
					}

					predecessor = tag;
					trainHMM(word, tag);
				}
				else
				{
					predecessor = null;
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void trainHMM(Word word, Tag tag)
	{
		if (!_words.containsKey(word.getWord()))
		{
			_words.put(word.getWord(), word);
		}
		else
		{
			word = _words.get(word.getWord());
		}

		if (!_tags.containsKey(tag.getTag()))
		{
			_tags.put(tag.getTag(), tag);
		}
		else
		{
			tag = _tags.get(tag.getTag());
		}

		word.addTag(tag);
		tag.addEmmission(word);
	}

	/**
	 * Returns probability for transmission between two tags
	 */
	public double getTransmissionProbability(Tag tag1, Tag tag2)
	{
		int size = tag1.getNumTransmission();
		int frequenzy = tag1.getFrequenzyOfTransmissions(tag2);

		return (double) size / (double) frequenzy;
	}
	
	public double getEmmissionProbability(Tag tag, Word word)
	{
	    int size = tag.getNumEmission();
	    int frequenzy = tag.getFrequenzyOfEmmissions(word);
	    
	    return (double) size / (double) frequenzy;
	}
}
