package PuzzleSolver;

import PuzzleSolver.GUI.PuzzleGUI;
import PuzzleSolver.Search.AStar;

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
		PuzzleState puzzle = PuzzleState.createPuzzleBySliding(false);
		System.out.println("Initial State:");
		
//		int[][] puzzleTest = {{1,2,3,4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 0, 15}};
//		PuzzleState puzzle = new PuzzleState(puzzleTest, false);
//		puzzle.printPuzzle();

		// IDAStar idastar = new IDAStar(puzzle);
		AStar aStar = new AStar(puzzle);
	}
}
