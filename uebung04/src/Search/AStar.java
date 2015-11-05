package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AStar
{
    private List<ArrayList<Field>> _map;
    private Field _start;
    private Field _goal;
    private ArrayList<Field> closed; // Die Felder die schon durchsucht wurden
    private SortedList open; // Die Felder die noch nicht durchsucht wurden
    private int _maxSearchDistance;

    public AStar(List<ArrayList<Field>> map, Field start, Field goal, int mSD)
    {
        _map = map;
        _start = start;
        _goal = goal;
        open = new SortedList();
        closed = new ArrayList<Field>();
        _maxSearchDistance = mSD;
    }
    
    public void findShortestPath()
    {
        _start.cost = 0;
        _start.depth = 0;
        closed.clear();
        open.clear();
        open.add(_start);
        
        _goal._parent = null;
        int maxDepth = 0;
        
        
        while((maxDepth < _maxSearchDistance) && (open.size() != 0))
        {
            Field search = open.getFirst();
            
            if(search.getType() == Field.FieldType.goal)
            {
                System.out.println("42");
            }
            
            open.remove(search);
            closed.add(search);
            
            for(Field neighbor: search.getNeighbors())
            {
                boolean betterNeighbor;
                if(closed.contains(neighbor))
                {
                    continue;
                }
                if(!isBlocked(neighbor))
                {
                    float stepCosts = (search.cost + getDistanceToGoal(search,neighbor));
                    if(!open.contains(neighbor))
                    {
                        open.add(neighbor);
                        betterNeighbor = true;
                    }
                    else if(stepCosts < search.cost)
                    {
                        betterNeighbor = true;
                    }
                    else
                    {
                        betterNeighbor = false;
                    }
                    
                    if(betterNeighbor)
                    {
                       
                        neighbor.cost = stepCosts;
                        neighbor.heuristic= getDistanceToGoal(neighbor,_goal);
                        maxDepth= Math.max(maxDepth, neighbor.setParent(search));
                    }
                }
            }
        }
    }
    
    public void printPath()
    {
        List<Field> path = new ArrayList<Field>();

        Field field = _goal;
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
   
    public float getDistanceToGoal(Field start, Field goal)
    {
        float distanceX = goal.getX() - start.getX();
        float distanceY = goal.getY() - start.getY();
        
        float result = (float) (Math.sqrt((distanceX*distanceX)+(distanceY*distanceY)));
        
        return result;
    }
    
    
    
    
    private class SortedList
    {
       private ArrayList<Field> list = new ArrayList<Field>();
       
       public Field getFirst()
       {
           return list.get(0);
       }
       
       public void clear()
       {
           list.clear();
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
    
    
    /**
     * Prüft, ob ein Feld nicht begehbar ist
     * @param field
     * @return true or false
     */
    private boolean isBlocked(Field field)
    {
        return (field.getType() == Field.FieldType.blocked);
    }
}

