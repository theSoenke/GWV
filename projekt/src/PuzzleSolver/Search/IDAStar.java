package PuzzleSolver.Search;

import java.util.HashSet;
import java.util.PriorityQueue;

import PuzzleSolver.PuzzleState;

public class IDAStar
{
	private final PuzzleState _initialState;
	private boolean _foundGoal;

	public IDAStar(PuzzleState initialState)
	{
		int depth = initialState.getManhattanDistance();
		int maxDepth = depth * 10;
		_initialState = initialState;

		while (!_foundGoal && depth < maxDepth)
		{
			depth = depthBoundSearch(_initialState, depth);
			depth++;
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

	/*
	 * Starts a depth bound DFS with a manhattan heuristic
	 */
	private int depthBoundSearch(PuzzleState root, int bound)
	{
		PriorityQueue<PuzzleState> frontier = new PriorityQueue<PuzzleState>();
		HashSet<PuzzleState> closed = new HashSet<PuzzleState>();
		int minDepth = 0;
		PuzzleState currentState = null;

		frontier.add(root);

		while (!frontier.isEmpty() && minDepth < bound)
		{
			currentState = frontier.poll();
			minDepth = Integer.MAX_VALUE;

			if (currentState.getManhattanDistance() == 0)
			{
				_foundGoal = true;
				return minDepth;
			}

			for (PuzzleState neighbor : currentState.getNeighborStates())
			{
				if (!closed.contains(neighbor) && !frontier.contains(neighbor))
				{
					frontier.add(neighbor);
					neighbor.setParentState(currentState);

					int cost = neighbor.getMoves() + neighbor.getManhattanDistance();
					if (cost < minDepth)
					{
						minDepth = cost;
					}
				}
			}

			closed.add(currentState);
		}

		return minDepth;
	}
}