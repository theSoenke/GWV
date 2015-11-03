package Search;

import java.util.ArrayList;

public class AStar
{
    private Field[][] _map;
    private AStarHeuristic _heuristic;
    private Field _start;
    private Field _goal;

    public AStar(Field[][] map, AStarHeuristic heuristic, Field start, Field goal)
    {
        _map = map;
        _heuristic = heuristic;
        _start = start;
        _goal = goal;
                
    }
    
    public String findPath()
    {
        if(goalBlocked())
        {
            return "Das Ziel ist von diesem Startpunkt nicht erreichbar!";
        }
        return "42";
    }

    private boolean goalBlocked()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
}

