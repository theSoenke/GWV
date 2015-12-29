package PuzzleSolver.Search;

import java.util.HashSet;
import java.util.PriorityQueue;

import PuzzleSolver.PuzzleState;

public class AStar
{
	private final PuzzleState _startState;

	public AStar(PuzzleState startState)
	{
		_startState = startState;
		solve();
	}

	private void solve()
	{
		PriorityQueue<PuzzleState> frontier = new PriorityQueue<PuzzleState>();
		HashSet<PuzzleState> closed = new HashSet<PuzzleState>();
		PuzzleState currentState = null;

		frontier.add(_startState);

		while (!frontier.isEmpty())
		{
			currentState = frontier.poll();
			int manhattanDist = currentState.getManhattanDistance();

			if (manhattanDist == 0)
			{
				System.out.println("Found goal");
				return;
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

		System.out.println("No solution found");
	}
}
