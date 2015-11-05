package Search;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Field erstellt ein neues Field
 * 
 * @param int
 *            mX X-Koordinate
 * @param int
 *            mY Y-Koordiante
 * @param FieldType
 *            mType Gibt den Zustand des Feldes an
 * @param Field
 *            mParent Feld von dem das aktuelle Feld erreicht wurde
 * @param boolean
 *            mVisited Gibt an ob das Feld bereits besucht wurde
 */
public class Field implements Comparable<Field>
{
	protected int _x;
	protected int _y;
	private FieldType _type;
	protected float _cost;
	protected float _heuristic;
	protected Field _parent;
	protected int _depth;
	private List<Field> _neighbors;
	public static Field _goal;

	/**
	 * Art des Feldes
	 */
	public enum FieldType
	{
		start, goal, blocked, empty, path
	}

	public Field(int x, int y, FieldType type)
	{
		_x = x;
		_y = y;
		_type = type;
		_neighbors = new ArrayList<Field>();
	}

	public Field(FieldType type)
	{
		_type = type;
	}

	/**
	 * Gibt die X-Koordinate zurück
	 */
	public int getX()
	{
		return _x;
	}

	/**
	 * Gibt die Y-Koordinate zurück
	 */
	public int getY()
	{
		return _y;
	}

	/**
	 * Gibt den Zustand eines Feldes zurück
	 */
	public FieldType getType()
	{
		return _type;
	}

	/**
	 * Setzt den Vorgänger des aktuellen Feldes
	 */
	public int setParent(Field parent)
	{
		_depth = parent._depth + 1;
		_parent = parent;

		return _depth;
	}

	/**
	 * Gibt den Vorgänger des aktuellen Feldes zurück
	 */
	public Field getParent()
	{
		return _parent;
	}

	public void addNeighbor(Field neighbor)
	{
		_neighbors.add(neighbor);
	}

	public List<Field> getNeighbors()
	{
		return _neighbors;
	}

	public void setAsPath()
	{
		_type = FieldType.path;
	}

	/**
	 * Wandelt ein char in ein FieldType um
	 * 
	 * @param field
	 * @return FieldType
	 */
	public static FieldType charToField(char field)
	{
		switch (field)
		{
		case ' ':
			return FieldType.empty;
		case 's':
			return FieldType.start;
		case 'g':
			return FieldType.goal;
		case 'x':
			return FieldType.blocked;
		default:
			System.out.println("Field does not exist");
			return FieldType.blocked;
		}
	}

	/**
	 * Wandelt FieldType in einen char um
	 * 
	 * @param field
	 * @return char
	 */
	public char typeAsChar()
	{
		if (_type == FieldType.empty)
		{
			return ' ';
		}
		else if (_type == FieldType.blocked)
		{
			return 'x';
		}
		else if (_type == FieldType.start)
		{
			return 's';
		}
		else if (_type == FieldType.goal)
		{
			return 'g';
		}
		else if (_type == FieldType.path)
		{
			return 'o';
		}
		else
		{
			return ' ';
		}
	}

	@Override
	public int compareTo(Field o)
	{
		Field field = o;

		float c = _heuristic + _cost;
		float fieldc = field._heuristic + field._cost;

		if (c < fieldc)
		{
			return -1;
		}
		else if (c > fieldc)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
