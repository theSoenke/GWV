import java.util.LinkedList;
import java.util.List;

public class Solver
{
	private final PuzzleState _startState;
	private boolean _foundGoal;

	public Solver(PuzzleState initialState)
	{
		int depth = 0;
		_startState = initialState;

		while (!_foundGoal && depth < 100)
		{
			depth++;
			depthBoundSearch(_startState, depth);
		}

		if (!_foundGoal)
		{
			System.out.println("Found no solution");
		}
	}

	private void depthBoundSearch(PuzzleState root, int bound)
	{
		List<PuzzleState> states = new LinkedList<PuzzleState>();

		if (root.getManhattanDistance() == 0)
		{
			_foundGoal = true;
			return;
		}
		else
		{
			List<PuzzleState> neighbors;

			for (int i = 0; i < bound; i++)
			{
				neighbors = root.neighborStates();

				int minManhattanDist = Integer.MAX_VALUE;
				PuzzleState bestNextState = null;
				for (PuzzleState state : neighbors)
				{
					int tmpDist = state.getManhattanDistance();

					if (tmpDist == 0)
					{
						_foundGoal = true;
						states.add(state);
						state.printPuzzle();
						return;
					}
					else if (tmpDist < minManhattanDist)
					{
						minManhattanDist = tmpDist;
						bestNextState = state;
					}
				}

				states.add(bestNextState);
			}
		}
	}
}
