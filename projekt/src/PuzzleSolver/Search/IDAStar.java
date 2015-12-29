package PuzzleSolver.Search;

import java.util.HashSet;
import java.util.PriorityQueue;

import PuzzleSolver.PuzzleState;

public class IDAStar
{
	private final PuzzleState _startState;
	private boolean _foundGoal;

	public IDAStar(PuzzleState initialState)
	{
		int depth = 0;
		_startState = initialState;

		while (!_foundGoal && depth < 10)
		{
			depth = depthBoundSearch(_startState, depth);
		}

		if (!_foundGoal)
		{
			System.out.println("No solution found");
		}
		else
		{
			System.out.println("Found best path");
		}
	}

	private int depthBoundSearch(PuzzleState root, int bound)
	{
		PriorityQueue<PuzzleState> frontier = new PriorityQueue<PuzzleState>();
		HashSet<PuzzleState> closed = new HashSet<PuzzleState>();
		int depth = 0;
		PuzzleState currentState = null;

		frontier.add(root);

		while (!frontier.isEmpty() && depth < bound)
		{
			currentState = frontier.poll();
			int manhattanDist = currentState.getManhattanDistance();
			depth = currentState.getMoves() + manhattanDist;
			System.out.println(depth);

			if (manhattanDist == 0)
			{
				_foundGoal = true;
				return depth;
			}

			for (PuzzleState neighbor : currentState.getNeighborStates())
			{
				if (!closed.contains(neighbor) && !frontier.contains(neighbor))
				{
					frontier.add(neighbor);
					currentState.setParentState(currentState);
				}
			}
			
			closed.add(currentState);
		}

		return depth;
	}
}