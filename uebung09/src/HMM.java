import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HMM
{
	private HashMap<String, Word> _words;

	public HMM(String path)
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
		String[] splittedLine = new String[2];

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
					line = new StringBuilder(line).reverse().toString();
					// split in word and Tag
					splittedLine = line.split("\\s", 2);

					String wordStr = new StringBuilder(splittedLine[1]).reverse().toString();
					String tagStr = new StringBuilder(splittedLine[0]).reverse().toString();

					word = new Word(wordStr);

					if (!_words.containsKey(line))
					{
						_words.put(wordStr, word);
						predecessor = word;
					}
					else
					{
						word = _words.get(line);
					}
					word.addBigram(tagStr);
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
}
