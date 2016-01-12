package PuzzleSolver.Search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import PuzzleSolver.PuzzleState;

public class IDAStar
{
	private final PuzzleState _initialState;
	private boolean _foundGoal;

	public IDAStar(PuzzleState initialState)
	{
		int depth = initialState.getHeuristic();
		_initialState = initialState;

		if (!initialState.isSolvable())
		{
			System.out.println("Puzzle not solvable");
			return;
		}

		while (!_foundGoal && depth < 150)
		{
			depth = depthBoundSearch(_initialState, depth);
			System.out.println(depth);
			// depth++;
		}

		if (_foundGoal)
		{
			System.out.println("Found best path");
		}
		else
		{
			System.out.println("No solution found");
		}
	}

	/**
	 * Starts a depth bound DFS with a manhattan heuristic
	 * 
	 * @return new max depth bound
	 */
	private int depthBoundSearch(PuzzleState root, int bound)
	{
		Queue<PuzzleState> frontier = new LinkedList<PuzzleState>();
		HashSet<Integer> closed = new HashSet<Integer>();
		HashSet<Integer> open = new HashSet<Integer>();

		int minDepth = 0;
		PuzzleState currentState = null;

		PuzzleState startState = root.clone();
		frontier.add(startState);

		while (!frontier.isEmpty() && minDepth < bound)
		{
			currentState = frontier.poll();
			minDepth = Integer.MAX_VALUE;

			if (currentState.isSolved())
			{
				_foundGoal = true;
				return minDepth;
			}

			for (PuzzleState neighbor : currentState.getNeighborStates(false))
			{
				if (!closed.contains(neighbor.hashCode()) && !open.contains(neighbor.hashCode()))
				{
					int cost = neighbor.getMoves() + neighbor.getHeuristic();
					
					if (cost < minDepth)
					{
						minDepth = cost;
					}
					
					if(cost < bound)
					{
						frontier.add(neighbor);
						open.add(neighbor.hashCode());
					}
				}
			}

			open.remove(currentState.hashCode());
			closed.add(currentState.hashCode());
		}

		return minDepth;
	}
}