
/**
 * Die Klasse Field erstellt ein neues Field
 * @param int mX X-Koordinate
 * @param int mY Y-Koordiante
 * @param FieldType mType Gibt den Zustand des Feldes an
 * @param Field mParent Feld von dem das aktuelle Feld erreicht wurde
 * @param boolean mVisited Gibt an ob das Feld bereits besucht wurde
 */
public class Field
{
	private int mX;
	private int mY;
	private FieldType mType; 
	private Field mParent; 
	private boolean mVisited;

	/**
	 * Art des Feldes
	 */
	public enum FieldType
	{
		start, goal, blocked, empty, path
	}

	public Field(int x, int y, FieldType type)
	{
		mX = x;
		mY = y;
		mType = type;
	}
	
	public Field(FieldType type)
	{
		mType = type;
	}

	/**
	 *  Gibt die X-Koordinate zurück
	 */
	public int getX()
	{
		return mX;
	}
	/**
     *  Gibt die Y-Koordinate zurück
     */
	public int getY()
	{
		return mY;
	}
	/**
     *  Gibt den Zustand eines Feldes zurück
     */
	public FieldType getType()
	{
		return mType;
	}
	/**
     *  Gibt zurück ob ein Feld bereits besucht wurde
     */
	public boolean isVisisted()
	{
		return mVisited;
	}
	/**
     *  Setzt ein Feld als schon besucht.
     */
	public void setVisited()
	{
		mVisited = true;
	}
	/**
     *  Setzt den Vorgänger des aktuellen Feldes
     */
	public void setParent(Field parent)
	{
		mParent = parent;
	}
	/**
     *  Gibt den Vorgänger des aktuellen Feldes zurück
     */
	public Field getParent()
	{
		return mParent;
	}
	
	/**
	 * Wandelt ein char in ein FieldType um
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
	 * @param field
	 * @return char
	 */
	public static char fieldToChar(FieldType field)
	{
		if(field == FieldType.empty)
		{
			return ' ';
		}
		else if(field == FieldType.blocked)
		{
			return 'x';
		}
		else if(field == FieldType.start)
		{
			return 's';
		}
		else if(field == FieldType.goal)
		{
			return 'g';
		}
		else if(field == FieldType.path)
		{
			return 'o';
		}
		else
		{
			return ' ';
		}
	}
}
