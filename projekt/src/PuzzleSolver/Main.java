package PuzzleSolver;

import PuzzleSolver.GUI.PuzzleGUI;
import PuzzleSolver.Search.IDAStar;
import PuzzleSolver.Search.Heuristic.FringePattern;
import PuzzleSolver.Search.Heuristic.ManhattanDistance;

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
		byte[] puzzleTest1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15 };

		byte[] puzzleTest = { 14, 7, 4, 10, 15, 1, 6, 13, 0, 2, 9, 8, 12, 5, 11, 3 };
		PuzzleState puzzle = new PuzzleState(puzzleTest, new ManhattanDistance());
		puzzle.printPuzzle();

		
		IDAStar idastar = new IDAStar(puzzle);
	}
}
