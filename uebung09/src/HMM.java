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
		//System.out.println("Unique words: " + _words.size());
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
			Tag predecessor = Tag.START_TAG;
			Tag tag;
			String line;
			_tags.put(Tag.START_TAG.toString(), Tag.START_TAG);

			while ((line = reader.readLine()) != null)
			{
				if (!line.isEmpty())
				{
					// split in word and tag
					line = new StringBuilder(line).reverse().toString();
					splittedLine = line.split("\\s", 2);

					String wordStr = new StringBuilder(splittedLine[1]).reverse().toString();
					String tagStr = new StringBuilder(splittedLine[0]).reverse().toString();

					tag = trainHMM(wordStr, tagStr, predecessor);
					predecessor = tag;
				}
				else
				{
					predecessor = Tag.START_TAG;
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private Tag trainHMM(String wordStr, String tagStr, Tag predecessor)
	{
		Word word = getOrCreateWord(wordStr);
		Tag tag = getOrCreateTag(tagStr);

		word.addTag(tag);
		tag.addPredecessor(predecessor);
		predecessor = tag;

		return tag;
	}

	/*
	 * String to Tag
	 */
	private Tag getOrCreateTag(String tagStr)
	{
		Tag tag;

		if (_tags.containsKey(tagStr))
		{
			tag = _tags.get(tagStr);
		}
		else
		{
			tag = new Tag(tagStr);
			_tags.put(tagStr, tag);
		}

		return tag;
	}

	/*
	 * String to Word
	 */
	public Word getOrCreateWord(String wordStr)
	{
		Word word;

		if (_words.containsKey(wordStr))
		{
			word = _words.get(wordStr);
		}
		else
		{
			word = new Word(wordStr);
			_words.put(wordStr, word);
		}

		return word;
	}

	/*
	 * String to Word. Does not create a new word if not already in _words
	 */
	public Word getWord(String wordStr)
	{
		Word word;

		if (_words.containsKey(wordStr))
		{
			word = _words.get(wordStr);
		}
		else
		{
			word = null;
		}

		return word;
	}

	/**
	 * Returns probability for transmission from tag1 to tag2
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

	public double getEmissionProbability(Tag tag, Word word)
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

		return (double) word.getTagFrequenzy(tag) / (double) frequenzy;
	}

	/*
	 * Checks whether model contains word
	 */
	public boolean checkWord(String word)
	{
		if (_words.containsKey(word))
		{
			return true;
		}
		return false;
	}
}
