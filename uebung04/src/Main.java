import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import Search.AStar;
import Search.Field;
import Search.Field.FieldType;

public class Main
{
	private List<ArrayList<Field>> _map;
	private Field _start;
	private Field _goal;

	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		processAllLevels();
	}
	
	private void processAllLevels()
	{
		// figure1
		readLevelFile("blatt3_environment.txt");
		setNeighbors();

		if (_start == null || _goal == null)
		{
			System.out.println("Start oder Ziel nicht gefunden");
		}
		else
		{
			Field._goal = _goal;
			AStar astar = new AStar(_map, _start, _goal);
			if(astar.findShortestPath())
			{
				System.out.println("Figure1");
				astar.printPath();
			}
		}
		System.out.println("\n");

		
		//figure2
		System.out.println("Figure2");
		readLevelFile("blatt4_environment_a.txt");
		setNeighbors();

		if (_start == null || _goal == null)
		{
			System.out.println("Start oder Ziel nicht gefunden");
		}
		else
		{
			Field._goal = _goal;
			AStar astar = new AStar(_map, _start, _goal);
			if(astar.findShortestPath())
			{
				astar.printPath();
			}
		}
		System.out.println("\n");
		
		
		//figure3
		readLevelFile("blatt4_environment_b.txt");
		setNeighbors();

		if (_start == null || _goal == null)
		{
			System.out.println("Start oder Ziel nicht gefunden");
		}
		else
		{
			Field._goal = _goal;
			AStar astar = new AStar(_map, _start, _goal);
			if(astar.findShortestPath())
			{
				System.out.println("Figure3");
				astar.printPath();
			}
		}
		System.out.println("\n");
	}

	/**
	 * Liest eine Level datei ein
	 * @param path
	 */
	private void readLevelFile(String path)
	{
		List<ArrayList<Field>> map = new ArrayList<ArrayList<Field>>();

		File filePath = new File(path);
		
		Field teleport1 = null;
		Field teleport2 = null;

		StringBuffer content = new StringBuffer();
		FileReader fileReader = null;
		
		try 
		{
			fileReader = new FileReader(filePath);
			BufferedReader reader = new BufferedReader(fileReader);
			String line;

			int y = 0;
			while ((line = reader.readLine()) != null)
			{
				content.append(line + "\n");

				map.add(new ArrayList<Field>(line.length()));

				for (int x = 0; x < line.length(); x++)
				{
					Field.FieldType type = Field.charToField(line.charAt(x));
					Field field = new Field(x, y, type);
					map.get(y).add(field);

					if (type == Field.FieldType.start)
					{
						_start = field;
					}
					else if (type == Field.FieldType.goal)
					{
						_goal = field;
					}
					
					// check for teleport fields
					
					if(field.getType() == FieldType.teleport1)
					{
						if(teleport1 == null)
						{
							teleport1 = field;
						}
						else
						{
							teleport1.addNeighbor(field);
							teleport1.setTeleport(field);
						}
					}
					else if(field.getType() == FieldType.teleport2)
					{
						if(teleport2 == null)
						{
							teleport2 = field;
						}
						else
						{
							teleport2.addNeighbor(field);
							teleport2.setTeleport(field);
						}
					}
				}
				y++;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		_map = map;
	}

	/*
	 * Erzeugt eines Graphen des Levels
	 */
	private void setNeighbors()
	{
		for (int y = 0; y < _map.size(); y++)
		{
			for (int x = 0; x < _map.get(y).size(); x++)
			{
				Field field = _map.get(y).get(x);

				// check top
				if (field.getY() > 0 && field.getX() < _map.get(0).size())
				{
					Field fieldTop = _map.get(field.getY() - 1).get(field.getX());
					if (!isBlocked(fieldTop))
					{
						field.addNeighbor(fieldTop);
					}
				}

				// check right
				if (field.getX() < _map.get(0).size() - 1)
				{
					Field fieldRight = _map.get(field.getY()).get(field.getX() + 1);
					if (fieldRight.getType() == Field.FieldType.empty)
					{
						field.addNeighbor(fieldRight);
					}
				}

				// check bottom
				if (field.getY() < _map.size() - 1)
				{
					Field fieldBottom = _map.get(field.getY() + 1).get(field.getX());
					if (!isBlocked(fieldBottom))
					{
						field.addNeighbor(fieldBottom);
					}
				}

				// check left
				if (field.getX() > 0)
				{
					Field fieldLeft = _map.get(field.getY()).get(field.getX() - 1);
					if (!isBlocked(fieldLeft))
					{
						field.addNeighbor(fieldLeft);
					}
				}
			}
		}
	}

	/**
	 * Prüft, ob ein Feld nicht begehbar ist
	 * 
	 * @param field
	 * @return true or false
	 */
	private boolean isBlocked(Field field)
	{
		return (field.getType() == Field.FieldType.blocked);
	}
}
