package PuzzleSolver;

import PuzzleSolver.GUI.PuzzleGUI;
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
		_gui = new PuzzleGUI();
		_gui.showUI();

		// PuzzleState puzzle = PuzzleGenerator.createPuzzleBySliding(true);
		// System.out.println("Initial State:");

		// byte[] puzzleTest = { 14, 7, 4, 10, 15, 1, 6, 13, 0, 2, 9, 8, 12, 5,
		// 11, 3 };
		// PuzzleState puzzle = new PuzzleState(puzzleTest, false);
		// puzzle.printPuzzle();

		// IDAStar idastar = new IDAStar(puzzle);
	}
}
