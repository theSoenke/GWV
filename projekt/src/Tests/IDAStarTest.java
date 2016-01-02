package Tests;

import org.junit.Test;

import PuzzleSolver.PuzzleState;
import PuzzleSolver.Search.IDAStar;

public class IDAStarTest
{
	@Test
	public void testIDAStarSolution1()
	{
		int[][] puzzle = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 0, 11 }, { 13, 14, 15, 12 } };

		PuzzleState startState = new PuzzleState(puzzle);
		//startState.printPuzzle();
		//IDAStar ida = new IDAStar(startState);
	}

	@Test
	public void testIDAStarSolution2()
	{
		int[][] puzzle = { { 12, 13, 11, 9 }, { 10, 15, 14, 1 }, { 2, 8, 0, 4 }, { 3, 7, 6, 5 } };

		PuzzleState startState = new PuzzleState(puzzle);
		//startState.printPuzzle();
		//IDAStar ida = new IDAStar(startState);
	}
	
	@Test
	public void testIDAStarSolution3()
	{
		int[][] puzzle = { { 2, 0, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 1 } };

		PuzzleState startState = new PuzzleState(puzzle);
		startState.printPuzzle();
		IDAStar ida = new IDAStar(startState);
	}
}
