package PuzzleSolver.Search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;

import PuzzleSolver.PuzzleState;

public class IDAStar
{
	private final PuzzleState _startState;
	private PuzzleState _goalState;
	private int _steps = 0;

	public IDAStar(PuzzleState initialState)
	{
		int depth = initialState.getHeuristic();
		_startState = initialState;

		if (!initialState.isSolvable())
		{
			System.out.println("Puzzle not solvable");
			return;
		}

		long startTime = System.currentTimeMillis();

		while (_goalState == null && depth < 500)
		{
			depthBoundSearch(_startState, depth);
			// System.out.println(depth);
			depth += 2;
		}

		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;

		if (_goalState != null)
		{
			System.out.println("Duration: " + duration + "ms");
			System.out.println("Explored nodes: " + _steps);
			System.out.println("Path length: " + getSolution().size());
		}
		else
		{
			System.err.println("No solution found");
		}
	}

	/**
	 * Starts a depth bound DFS with a manhattan heuristic
	 */
	private void depthBoundSearch(PuzzleState root, int bound)
	{
		Queue<PuzzleState> frontier = new ArrayDeque<PuzzleState>();
		HashSet<Integer> closed = new HashSet<Integer>();
		HashSet<Integer> open = new HashSet<Integer>();

		PuzzleState currentState = null;

		PuzzleState startState = root.clone();
		frontier.add(startState);

		while (!frontier.isEmpty())
		{
			_steps++;
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
					int cost = neighbor.getMoves() + neighbor.getHeuristic();

					if (cost < bound)
					{
						frontier.add(neighbor);
						open.add(neighbor.hashCode());
					}
				}
			}

			open.remove(currentState.hashCode());
			closed.add(currentState.hashCode());
		}
	}

	public List<PuzzleState> getSolution()
	{
		List<PuzzleState> path = new ArrayList<PuzzleState>();
		PuzzleState currentState = _goalState;

		while (currentState != null && !currentState.equals(_startState))
		{
			path.add(currentState);
			currentState = currentState.getParentState();
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