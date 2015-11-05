package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Search.Field.FieldType;

public class AStar
{
	private List<ArrayList<Field>> _map;
	private Field _start;
	private Field _goal;
	private List<Field> _closed;

	public AStar(List<ArrayList<Field>> map, Field start, Field goal)
	{
		_map = map;
		_start = start;
		_goal = goal;
		_closed = new ArrayList<Field>();
	}

	public void findShortestPath()
	{
		// Die Felder, die noch nicht durchsucht wurden
		SortedList open = new SortedList();

		open.add(_start);

		while (!open.isEmpty())
		{
			Field currentField = open.getFirst();

			if (_closed.size() > 0)
			{
				currentField.setParent(_closed.get(_closed.size() - 1));
			}

			if (currentField.getType() == Field.FieldType.goal)
			{
				_closed.add(currentField);
				return;
			}

			for (Field field : currentField.getNeighbors())
			{
				if (!field.isVisited() && !open.contains(field))
				{
					open.add(field);
					field.setVisited();
				}
			}

			open.remove(currentField);
			_closed.add(currentField);
		}
		
		System.out.println("Es existiert kein Weg zum Ziel");
	}

	public void printPath()
	{
		for (Field field : _closed)
		{
			if (field.getType() != FieldType.goal && field.getType() != FieldType.start)
			{
				field.setAsPath();
			}
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

	public void printPathByParent()
	{
		Field field = _goal.getParent();

		while (field.getType() != FieldType.start)
		{
			field.setAsPath();
			field = field.getParent();
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

		public boolean isEmpty()
		{
			return list.isEmpty();
		}

		public boolean contains(Field o)
		{
			return list.contains(o);
		}
	}
}
