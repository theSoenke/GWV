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

	int i = 100;

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

			if (i < 100)
			{
				System.out.println("Current" + " moves: " + currentState.getMoves() + " manhattan: " + currentState.getManhattanDistance());
				currentState.printPuzzle();
			}

			for (PuzzleState neighbor : currentState.getNeighborStates())
			{
				if (!closed.contains(neighbor.hashCode()) && !open.contains(neighbor.hashCode()))
				{
					if (i < 100)
					{
						System.out.println("Neighbor" + " moves: " + neighbor.getMoves() + " manhattan: " + neighbor.getManhattanDistance());
						neighbor.printPuzzle();
					}
					frontier.add(neighbor);
					open.add(neighbor.hashCode());
				}
			}
			
			//System.out.println(currentState.isSolvable());
			//System.out.println("cost: " + (currentState.getMoves() + currentState.getManhattanDistance()) );

			open.remove(currentState.hashCode());
			closed.add(currentState.hashCode());

			if (closed.size() % 10000 == 0)
			{
				System.out.println("closed: " + closed.size() + " frontier: " + frontier.size());
			}
			i++;
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
