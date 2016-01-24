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

			if (currentState.isSolved())
			{
				_goalState = currentState;
				return;
			}

			for (PuzzleState neighbor : currentState.getNeighborStates())
			{
				if (!closed.contains(neighbor.hashCode()) && !open.contains(neighbor.hashCode()))
				{
					frontier.add(neighbor);
					open.add(neighbor.hashCode());
				}
			}

			open.remove(currentState.hashCode());
			closed.add(currentState.hashCode());

			if (closed.size() % 10000 == 0)
			{
				System.out.println("closed: " + closed.size() + " frontier: " + frontier.size());
				System.out.println(currentState.getHeuristic());
				System.out.println(currentState.getMoves());
				currentState.printPuzzle();
				// System.out.println(currentState.getHeuristic());
			}
		}

		System.out.println("No solution found");
	}

	public List<PuzzleState> getSolution()
	{
		List<PuzzleState> path = new ArrayList<PuzzleState>();
		PuzzleState currentState = _goalState;

		while (currentState != null && !currentState.equals(_startState))
		{
			currentState = currentState.getParentState();
			path.add(currentState);
		}

		Collections.reverse(path);

		return path;
	}

	private void printSolution()
	{
		List<PuzzleState> path = getSolution();

		System.out.println("Solution:");

		for (PuzzleState state : path)
		{
			state.printPuzzle();
		}
	}
}
