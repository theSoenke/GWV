package PuzzleSolver;

import GUI.GUI;
import PuzzleSolver.Search.AStar;
import PuzzleSolver.Search.IDAStar;

public class Main
{

    final GUI gui = new GUI();
    
	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		
	    gui.zeigeFenster();
	    PuzzleState puzzle = PuzzleState.createPuzzleBySliding();
		System.out.println("Initial State:");
		puzzle.printPuzzle();

		//IDAStar idastar = new IDAStar(puzzle);
		AStar aStar = new AStar(puzzle);
	}
}
