package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar
{
	private List<ArrayList<Field>> _map;
	private List<Field> _path;
	private Field _start;
	private Field _goal;

	public AStar(List<ArrayList<Field>> map, Field start, Field goal)
	{
		_map = map;
		_start = start;
		_goal = goal;
	}

	public void findShortestPath()
	{
		_start._cost = 0;
		_start._depth = 0;
		List<Field> closed = new ArrayList<Field>();
		_path = new ArrayList<Field>();

		// Die Felder die noch nicht durchsucht wurden
		SortedList open = new SortedList();

		open.add(_start);

		int maxDepth = 0;

		while (open.size() != 0)
		{
			Field currentField = open.getFirst();

			if (currentField.getType() == Field.FieldType.goal)
			{
				return;
			}

			open.remove(currentField);
			closed.add(currentField);
			_path.add(currentField);

			for (Field neighbor : currentField.getNeighbors())
			{
				boolean betterNeighbor;
				if (closed.contains(neighbor))
				{
					continue;
				}
				float stepCosts = (currentField._cost + getDistanceToGoal(currentField, neighbor));
				if (!open.contains(neighbor))
				{
					open.add(neighbor);
					betterNeighbor = true;
				}
				else if (stepCosts < currentField._cost)
				{
					betterNeighbor = true;
				}
				else
				{
					betterNeighbor = false;
				}

				if (betterNeighbor)
				{

					neighbor._cost = stepCosts;
					neighbor._heuristic = getDistanceToGoal(neighbor, _goal);
					maxDepth = Math.max(maxDepth, neighbor.setParent(currentField));
				}
			}
		}
	}

	public void printPath()
	{
		for (Field field : _path)
		{
			_map.get(field.getY()).get(field.getX()).setAsPath();
		}

		for (int y = 0; y < _map.size(); y++)
		{
			String line = "";

			for (int x = 0; x < _map.get(0).size(); x++)
			{
				char character = _map.get(y).get(x).typeAsChar();
				line += character;
			}

			System.out.println(line);
		}
	}

	public float getDistanceToGoal(Field start, Field goal)
	{
		float distanceX = goal.getX() - start.getX();
		float distanceY = goal.getY() - start.getY();

		float result = (float) (Math.sqrt((distanceX * distanceX) + (distanceY * distanceY)));

		return result;
	}

	private class SortedList
	{
		private ArrayList<Field> list = new ArrayList<Field>();

		public Field getFirst()
		{
			return list.get(0);
		}

		public void add(Field o)
		{
			list.add(o);
			Collections.sort(list);
		}

		public void remove(Field o)
		{
			list.remove(o);
		}

		public int size()
		{
			return list.size();
		}

		public boolean contains(Field o)
		{
			return list.contains(o);
		}
	}
}
