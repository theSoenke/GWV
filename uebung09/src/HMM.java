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
			Tag predecessor = null;
			String line;
			_tags.put(Tag.START_TAG.toString(), Tag.START_TAG);

			while ((line = reader.readLine()) != null)
			{
				if (predecessor == null)
				{
					predecessor = Tag.START_TAG;
				}
				Tag tag;

				if (!line.isEmpty())
				{
					line = new StringBuilder(line).reverse().toString();
					// split in word and Tag
					splittedLine = line.split("\\s", 2);

					String wordStr = new StringBuilder(splittedLine[1]).reverse().toString();
					String tagStr = new StringBuilder(splittedLine[0]).reverse().toString();
					// System.out.println(splittedLine[1]);

					word = new Word(wordStr);
					tag = new Tag(tagStr);

					tag.addPredecessor(predecessor);
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
		if (_words.containsKey(word.getWord()))
		{
			word = _words.get(word.getWord());
		}
		else
		{
			_words.put(word.getWord(), word);
		}

		if (_tags.containsKey(tag.toString()))
		{
			tag = _tags.get(tag.toString());
			tag.increaseFrequenzy();
		}
		else
		{
			_tags.put(tag.toString(), tag);
		}

		word.addTag(tag);
	}

	/**
	 * Returns probability for transmission between two tags
	 */
	public double getTransmissionProbability(Tag tag1, Tag tag2)
	{
		int size = tag1.getNumTransmission();
		int frequenzy = tag1.getFrequenzyOfTransmissions(tag2);

		if (size == 0)
		{
			throw new RuntimeException("Size cannot be 0");
		}

		if (frequenzy == 0)
		{
			throw new RuntimeException("Frequenzy cannot be 0");
		}

		return (double) size / (double) frequenzy;
	}

	public double getEmissionProbability(Tag tag, String word)
	{
		int frequenzy; // overall frequenzy of tag
		if (_tags.containsKey(tag.toString()))
		{
			frequenzy = _tags.get(tag.toString()).getFrequenzy();
		}
		else
		{
			frequenzy = 0;
			System.out.println("Tag does not exist");
		}

		Word w = getWord(word);

		return (double) w.getTagFrequenzy(tag) / (double) frequenzy;
	}

	public Word getWord(String word)
	{
		Word w = null;
		if (_words.containsKey(word))
		{
			w = _words.get(word);
		}
		else
		{
			System.out.println("Word does not exist");
		}

		return w;
	}
}
