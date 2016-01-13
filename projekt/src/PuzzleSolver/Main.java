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

		// PuzzleState puzzle = PuzzleGenerator.createPuzzleBySliding(true);
		System.out.println("Initial State:");

		byte[][] puzzleTest = { { 2, 5, 6, 3 }, { 13, 0, 7, 8 }, { 12, 11, 4, 14 }, { 10, 15, 9, 1 } };
		PuzzleState puzzle = new PuzzleState(puzzleTest, true);
		puzzle.printPuzzle();

		IDAStar idastar = new IDAStar(puzzle);
		// AStar aStar = new AStar(puzzle);
	}
}
