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

	public void solve()
	{
		HashSet<PuzzleState> closed = new HashSet<PuzzleState>();
		PriorityQueue<PuzzleState> open = new PriorityQueue<PuzzleState>();
		open.add(_startState);

		while (!open.isEmpty())
		{
			PuzzleState currentState = open.poll();

			if (currentState.getManhattanDistance() == 0)
			{
				closed.add(currentState);
				System.out.println("Found solution");
				return;
			}

			for (PuzzleState state : currentState.getNeighborStates())
			{
				state.printPuzzle();
				
				if (!closed.contains(state) && !open.contains(state))
				{
					open.add(state);
					state.setParentState(currentState);
				}
			}

			open.remove(currentState);
			closed.add(currentState);
		}

		System.out.println("No solution found");
	}
}
