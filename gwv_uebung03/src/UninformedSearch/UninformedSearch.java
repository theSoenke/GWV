package UninformedSearch;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class UninformedSearch
{
	private Field mStart;
	private Field mGoal;
	private Field[][] mMap;

	public UninformedSearch(Field[][] map, Field start, Field goal)
	{
		mMap = map;
		mStart = start;
		mGoal = goal;
	}

	/**
	 * Führt eine BFS auf dem Level aus
	 */
	public void startBFS()
	{
		Queue<Field> frontier = new LinkedList<Field>();
		frontier.add(mStart);

		while (!frontier.isEmpty())
		{
			Field field = frontier.poll();
			List<Field> neighbors = getNeighbors(field);

			for (Field neighbor : neighbors)
			{
				neighbor.setParent(field);
				frontier.add(neighbor);

				if (neighbor.getType() == Field.FieldType.goal)
				{
					return;
				}
			}
		}
	}

	/**
	 * Führ eine DFS auf dem Level aus
	 */
	public void startDFS()
	{
		Stack<Field> frontier = new Stack<Field>();
		frontier.push(mStart);

		while (!frontier.empty())
		{
			Field field = frontier.pop();
			List<Field> neighbors = getNeighbors(field);

			for (Field neighbor : neighbors)
			{
				neighbor.setParent(field);
				frontier.push(neighbor);

				if (neighbor.getType() == Field.FieldType.goal)
				{
					return;
				}
			}
		}
	}

	/**
	 * Gibt alle begehbaren Nachbarfelder eine Feldes zurück
	 * @param field
	 * @return
	 */
	private List<Field> getNeighbors(Field field)
	{
		List<Field> neighbors = new ArrayList<Field>();

		// check top
		if (field.getY() > 0 && field.getX() < mMap[0].length)
		{
			Field fieldTop = mMap[field.getY() - 1][field.getX()];
			if (!isBlocked(fieldTop) && !fieldTop.isVisisted())
			{
				fieldTop.setVisited();
				neighbors.add(fieldTop);
			}
		}

		// check right
		if (field.getX() < mMap[0].length - 1)
		{
			Field fieldRight = mMap[field.getY()][field.getX() + 1];
			if (!isBlocked(fieldRight) && !fieldRight.isVisisted())
			{
				fieldRight.setVisited();
				neighbors.add(fieldRight);
			}
		}

		// check bottom
		if (field.getY() < mMap.length - 1)
		{
			Field fieldBottom = mMap[field.getY() + 1][field.getX()];
			if (!isBlocked(fieldBottom) && !fieldBottom.isVisisted())
			{
				fieldBottom.setVisited();
				neighbors.add(fieldBottom);
			}
		}

		// check left
		if (field.getX() > 0)
		{
			Field fieldLeft = mMap[field.getY()][field.getX() - 1];
			if (!isBlocked(fieldLeft) && !fieldLeft.isVisisted())
			{
				fieldLeft.setVisited();
				neighbors.add(fieldLeft);
			}
		}

		return neighbors;
	}

	/**
	 * Prüft, ob ein Feld nicht begehbar ist
	 * @param field
	 * @return
	 */
	private boolean isBlocked(Field field)
	{
		return (field.getType() == Field.FieldType.blocked);
	}

	/**
	 * Gibt alle Feldpositionen vom gefundenen Pfad der Reihe nach aus
	 */
	public void printPath()
	{
		List<Field> path = new ArrayList<Field>();

		Field field = mGoal;
		while (field.getType() != Field.FieldType.start)
		{
			field = field.getParent();
			path.add(field);
		}

		Collections.reverse(path);

		for (Field f : path)
		{
			System.out.println("Y: " + f.getY() + " X: " + f.getX());
		}
	}

	/**
	 * @return Gibt das Level mit gefundenen Pfad zurück
	 */
	public String getLevelPath()
	{
		List<Field> path = new ArrayList<Field>();
		String output = "";

		Field field = mGoal;
		while (field.getType() != Field.FieldType.start)
		{
			if (field.getType() != Field.FieldType.goal)
			{
				mMap[field.getY()][field.getX()] = new Field(Field.FieldType.path);
			}

			field = field.getParent();
			path.add(field);
		}

		for (int y = 0; y < mMap.length; y++)
		{
			for (int x = 0; x < mMap[0].length; x++)
			{
				field = mMap[y][x];
				output += Field.fieldToChar(field.getType());
			}
			output += "\n";
		}
		
		return output;
	}
}
