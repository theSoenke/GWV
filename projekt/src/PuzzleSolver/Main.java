package PuzzleSolver;

import PuzzleSolver.GUI.PuzzleGUI;

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
		PuzzleState puzzle = PuzzleState.createPuzzleBySliding();
		System.out.println("Initial State:");
		puzzle.printPuzzle();

		// IDAStar idastar = new IDAStar(puzzle);
		// AStar aStar = new AStar(puzzle);
	}
}
