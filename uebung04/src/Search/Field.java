package Search;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Field erstellt ein neues Field
 * @param int mX X-Koordinate
 * @param int mY Y-Koordiante
 * @param FieldType mType Gibt den Zustand des Feldes an
 * @param Field mParent Feld von dem das aktuelle Feld erreicht wurde
 * @param boolean mVisited Gibt an ob das Feld bereits besucht wurde
 */
public class Field implements Comparable<Field>
{
    protected int mX;
    protected int mY;
    private FieldType mType; 
    protected float cost;
    protected float heuristic;
    protected Field _parent; 
    protected int depth;
    private List<Field> _neighbors;

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
        _neighbors = new ArrayList<Field>();
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
     *  Setzt den Vorgänger des aktuellen Feldes
     */
    public int setParent(Field parent)
    { 
        depth = parent.depth + 1;
        _parent = parent;
        
        return depth;
    }
    /**
     *  Gibt den Vorgänger des aktuellen Feldes zurück
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

    @Override
    public int compareTo(Field o)
    {
        Field field =  o;
        
        float c= heuristic + cost;
        float fieldc= field.heuristic + field.cost;
        
        if(c < fieldc)
        {
            return -1;
        }
        else if(c > fieldc)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
