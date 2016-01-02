package PuzzleSolver.Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import PuzzleSolver.PuzzleState;

public class AStar
{
	private final PuzzleState _startState;
	private PuzzleState _goalState;

	public AStar(PuzzleState startState)
	{
		_startState = startState;

		if (startState.isSolvable())
		{
			solve();
		}
		else
		{
			System.out.println("Puzzle not solvable");
		}
	}

	private void solve()
	{
		PriorityQueue<PuzzleState> frontier = new PriorityQueue<PuzzleState>();
		HashSet<Integer> closed = new HashSet<Integer>();
		HashSet<Integer> open = new HashSet<Integer>();
		PuzzleState currentState = null;

		frontier.add(_startState);

		while (!frontier.isEmpty())
		{
			currentState = frontier.poll();

			int manhattanDist = currentState.getManhattanDistance();

			if (manhattanDist == 0)
			{
				_goalState = currentState;
				printSolution();
				return;
			}

			for (PuzzleState neighbor : currentState.getNeighborStates())
			{
				if (!closed.contains(neighbor.hashCode()) && !open.contains(neighbor.hashCode()))
				{
					neighbor.setParentState(currentState);
					//currentState.setParentState(currentState);
					frontier.add(neighbor);
					open.add(neighbor.hashCode());
				}
			}

			open.remove(currentState.hashCode());
			closed.add(currentState.hashCode());

			System.out.println("closed: " + closed.size() + " frontier: " + frontier.size());
		}

		System.out.println("No solution found");
	}

	private void printSolution()
	{
		List<PuzzleState> path = new ArrayList<PuzzleState>();
		PuzzleState currentState = _goalState;

		while (currentState != null && !currentState.equals(_startState))
		{
			path.add(currentState);
			currentState = currentState.getParentState();
		}

		Collections.reverse(path);

		System.out.println("Solution:");

		for (PuzzleState state : path)
		{
			state.printPuzzle();
		}
	}
}
