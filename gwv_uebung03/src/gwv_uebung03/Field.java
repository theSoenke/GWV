package gwv_uebung03;

public class Field
{
	private int mX;
	private int mY;
	private FieldType mType;
	private Field mParent;
	private boolean mVisited = false;

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

	public int getX()
	{
		return mX;
	}

	public int getY()
	{
		return mY;
	}

	public FieldType getType()
	{
		return mType;
	}

	public boolean isVisisted()
	{
		return mVisited;
	}

	public void setVisited()
	{
		mVisited = true;
	}

	public void setParent(Field parent)
	{
		mParent = parent;
	}

	public Field getParent()
	{
		return mParent;
	}
	
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
