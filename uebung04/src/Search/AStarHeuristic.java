package Search;

public class AStarHeuristic
{
    public float getDistanceToGoal(Field start, Field goal)
    {
        float distanceX = goal.getX() - start.getX();
        float distanceY = goal.getY() - start.getY();
        
        float result = (float) (Math.sqrt((distanceX*distanceX)+(distanceY*distanceY)));
        
        return result;
    }
}
