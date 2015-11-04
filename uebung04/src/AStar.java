package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar
{
    private Field[][] _map;
    private AStarHeuristic _heuristic;
    private Field _start;
    private Field _goal;
    private ArrayList<Field> closed; // Die Felder die schon durchsucht wurden
    private SortedList open; // Die Felder die noch nicht durchsucht wurden
    private int _maxSearchDistance;

    public AStar(Field[][] map, AStarHeuristic heuristic, Field start, Field goal, int mSD)
    {
        _map = map;
        _heuristic = heuristic;
        _start = start;
        _goal = goal;
        closed = new ArrayList<Field>();
        open = new SortedList();
        _maxSearchDistance = mSD;
                
    }
    
    public String findPath()
    {
        start().cost = 0;
        start().depth = 0;
        closed.clear();
        open.clear();
        open.add( _map[_start.getX()][_start.getY()]);
        
        goal().mParent = null;
        int maxDepth = 0;
        
        if(goalBlocked())
        {
            return "Das Ziel ist von diesem Startpunkt nicht erreichbar!";
        }
        while((maxDepth < _maxSearchDistance) && (open.size() != 0))
        {
            Field search = open.getFirst();
            if(search == goal())
            {
                break;
            }
            
            open.remove(search);
            closed.add(search);
            
            for(int x=-1;x<2;x++)
            {
                for(int y=-1;y<2;y++)
                {
                    if((x != 0) && (y != 0))
                    {
                        continue;
                    }
                    
                    int xp = x + search.mX;
                    int yp = y + search.mY;
                }
            }
            
        }
       
        
    }

    private boolean goalBlocked()
    {
        // TODO Auto-generated method stub
        return false;
    }
    private Field start()
    {
        return _map[_start.getX()][_start.getY()];
    }
    
    private Field goal()
    {
        return _map[_goal.getX()][_goal.getY()];
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

