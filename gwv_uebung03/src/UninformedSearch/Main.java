package UninformedSearch;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main
{
	private Field mStart;
	private Field mGoal;

	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		Field[][] map = createLevelMap("gwv_uebung03/blatt3_environment.txt");
		UninformedSearch uf = new UninformedSearch(map, mStart, mGoal);
		uf.startDFS();
		System.out.println("DFS");
		System.out.println(uf.getLevelPath());
		
		map = createLevelMap("gwv_uebung03/blatt3_environment.txt");
		uf = new UninformedSearch(map, mStart, mGoal);
		System.out.println("BFS");
		uf.startBFS();
		System.out.println(uf.getLevelPath());
		
		
	}

	private Field[][] createLevelMap(String path)
	{
		List<String> lines = new ArrayList<String>();
		try
		{
			lines = Files.readAllLines(Paths.get(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		

		Field[][] map = new Field[lines.size()][lines.get(0).length()];

		for (int y = 0; y < lines.size(); y++)
		{
			String line = lines.get(y);

			for (int x = 0; x < line.length(); x++)
			{
				Field field = new Field(x, y, Field.charToField(line.charAt(x)));
				map[y][x] = field;

				if (field.getType() == Field.FieldType.start)
				{
					mStart = field;
				}
				else if (field.getType() == Field.FieldType.goal)
				{
					mGoal = field;
				}
			}
		}

		return map;
	}
}
