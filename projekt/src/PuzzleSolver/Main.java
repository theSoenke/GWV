package PuzzleSolver;

import PuzzleSolver.Search.AStar;
import PuzzleSolver.Search.IDAStar;

public class Main
{

	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		PuzzleState puzzle = PuzzleState.createSolvablePuzzle();
		System.out.println("Initial State:");
		puzzle.printPuzzle();

		// IDAStar idastar = new IDAStar(puzzle);
		AStar aStar = new AStar(puzzle);
	}
}
