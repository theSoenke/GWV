import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class POSTagger
{
	private HashMap<String, Word> _words;

	public POSTagger(String path)
	{
		_words = new HashMap<String, Word>();
		readTextFile(path);
		System.out.println("Unique words: " + _words.size());
	}

	/**
	 * Liest die Textdatei ein
	 * 
	 * @param path
	 */
	private void readTextFile(String path)
	{
		File filePath = new File(path);

		FileReader fileReader = null;
		BufferedReader reader = null;
		String[] array = new String[2];

		try
		{
			fileReader = new FileReader(filePath);
			reader = new BufferedReader(fileReader);
			Word predecessor = null;
			String line;

			while ((line = reader.readLine()) != null)
			{
				Word word = null;

				if (!line.isEmpty())
				{

					// split in word and Tag
					array = line.split("\\s+", 2);
					// split altering for whitespace words just as "New York"
					if (array[1].matches("\\w*\\S*\\w*\\s+\\w*"))
					{
						String[] step;
						step = array[1].toString().split("\\s+");
						array[0] = array[0] + " " + step[0];
						array[1] = step[1];
					}

					// split altering for 2 whitespace words just as "Wall
					// Street Trader"
					else
						if (array[1].matches("\\w*\\S*\\w*\\s+\\w*\\S*\\w*\\s+\\w*"))
						{
							String[] step;
							step = array[1].toString().split("\\s+", 2);
							array[0] = array[0] + " " + step[0];
							array[1] = step[1];
							if (array[1].matches("\\w*\\S*\\w*\\s+\\w*"))
							{
								String[] step2;
								step2 = array[1].toString().split("\\s+");
								array[0] = array[0] + " " + step2[0];
								array[1] = step2[1];
							}
						}
					// change all signs to $
					if (array[1].matches("(.*)\\p{Punct}"))
					{
						array[1] = "$";
					}

					word = new Word(array[0]);

					Tag tag = Tag.valueOf(array[1]);
					if (!_words.containsKey(line))
					{
						if (tag == null)
						{
							System.out.println("Tag doesn't exist.");
						}
						_words.put(array[0], word);
						predecessor = word;
					}
					else
					{
						word = _words.get(line);
					}
					word.addBigram(tag);

				}
				else
				{
					predecessor = null;
				}

				if (predecessor != null)
				{
				}

				predecessor = word;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void printBigramSentence(String sentence)
	{
		// TO-DO Satz taggen
	}

}
