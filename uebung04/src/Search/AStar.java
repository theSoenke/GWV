package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar
{
    private Field[][] _map;
    private Field _start;
    private Field _goal;
    private ArrayList<Field> closed; // Die Felder die schon durchsucht wurden
    private SortedList open; // Die Felder die noch nicht durchsucht wurden
    private int _maxSearchDistance;

    public AStar(Field[][] map, Field start, Field goal, int mSD)
    {
        _map = map;
        _start = start;
        _goal = goal;
        open = new SortedList();
        closed = new ArrayList<Field>();
        _maxSearchDistance = mSD;
                
    }
    
    public String findShortestPath()
    {
        StringBuffer buffer = new StringBuffer();
        start().cost = 0;
        start().depth = 0;
        closed.clear();
        open.clear();
        open.add(start());
        
        goal().mParent = null;
        int maxDepth = 0;
        
        if(isBlocked(goal()))
        {
            return "Das Ziel ist von diesem Startpunkt nicht erreichbar!";
        }
        
        while((maxDepth < _maxSearchDistance) && (open.size() != 0))
        {
            Field search = open.getFirst();
            
            if(search == goal())
            {
                return "42";
            }
            
            open.remove(search);
            closed.add(search);
            
            for(Field neighbor: getNeighbors(search))
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
                        neighbor.setParent(search);
                        neighbor.cost = stepCosts;
                        neighbor.heuristic= getDistanceToGoal(neighbor,_goal);
                        buffer.append(Field.fieldToChar(neighbor.getType()));
                    }
                }
            }
        }
        
        return buffer.toString();
    }
    
    
    private List<Field> getNeighbors(Field field)
    {
        List<Field> neighbors = new ArrayList<Field>();

        // check top
        if (field.getY() > 0 && field.getX() < _map[0].length)
        {
            Field fieldTop = _map[field.getY() - 1][field.getX()];
            if (!isBlocked(fieldTop) && !fieldTop.isVisisted())
            {
                fieldTop.setVisited();
                neighbors.add(fieldTop);
            }
        }

        // check right
        if (field.getX() < _map[0].length - 1)
        {
            Field fieldRight =_map[field.getY()][field.getX() + 1];
            if (!isBlocked(fieldRight) && !fieldRight.isVisisted())
            {
                fieldRight.setVisited();
                neighbors.add(fieldRight);
            }
        }

        // check bottom
        if (field.getY() < _map.length - 1)
        {
            Field fieldBottom = _map[field.getY() + 1][field.getX()];
            if (!isBlocked(fieldBottom) && !fieldBottom.isVisisted())
            {
                fieldBottom.setVisited();
                neighbors.add(fieldBottom);
            }
        }

        // check left
        if (field.getX() > 0)
        {
            Field fieldLeft = _map[field.getY()][field.getX() - 1];
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
     * @return true or false
     */
    private boolean isBlocked(Field field)
    {
        return (field.getType() == Field.FieldType.blocked);
    }


   
    private Field start()
    {
        return _map[_start.getX()][_start.getY()];
    }
    
    
    
    private Field goal()
    {
        return _map[_goal.getX()][_goal.getY()];
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
    
}

