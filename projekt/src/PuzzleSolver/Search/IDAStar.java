package PuzzleSolver.Search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import PuzzleSolver.PuzzleState;

public class IDAStar
{
	private final PuzzleState _startState;
	private boolean _foundGoal;

	public IDAStar(PuzzleState initialState)
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
			System.out.println("No solution found");
		}
	}

	private void depthBoundSearch(PuzzleState root, int bound)
	{
		List<PuzzleState> states = new LinkedList<PuzzleState>();
		HashSet<PuzzleState> visitedStates = new HashSet<PuzzleState>();

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
				neighbors = root.getNeighborStates();

				int minManhattanDist = Integer.MAX_VALUE;
				PuzzleState bestNextState = null;
				for (PuzzleState state : neighbors)
				{
					int tmpDist = state.getManhattanDistance();

					if (tmpDist == 0)
					{
						_foundGoal = true;
						states.add(state);
						System.out.println("Found goal: ");
						state.printPuzzle();
						return;
					}
					else if (tmpDist < minManhattanDist)
					{
						if (!visitedStates.contains(state))
						{
							minManhattanDist = tmpDist;
							bestNextState = state;
						}
						else
						{
							bestNextState = null;
						}
					}
				}

				if (bestNextState != null)
				{
					states.add(bestNextState);
					visitedStates.add(bestNextState);
					bestNextState.printPuzzle();
				}
			}
		}
	}
}