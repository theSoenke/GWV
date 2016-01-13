package PuzzleSolver;

import PuzzleSolver.GUI.PuzzleGUI;
import PuzzleSolver.Search.AStar;
import PuzzleSolver.Search.IDAStar;

public class Main
{
	private PuzzleGUI _gui;

	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		// _gui = new PuzzleGUI();
		// _gui.showUI();

		// PuzzleState puzzle = PuzzleState.createPuzzleBySliding(true);
		System.out.println("Initial State:");

		int[][] puzzleTest = { { 2, 15, 0, 14 }, { 10, 3, 5, 4 }, { 13, 8, 1, 9 }, { 11, 12, 7, 6 } };
		PuzzleState puzzle = new PuzzleState(puzzleTest, false);
		puzzle.printPuzzle();

		IDAStar idastar = new IDAStar(puzzle);
		// AStar aStar = new AStar(puzzle);
	}
}
