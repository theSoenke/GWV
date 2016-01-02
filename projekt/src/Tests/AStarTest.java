package Tests;

import org.junit.Test;

import PuzzleSolver.PuzzleState;
import PuzzleSolver.Search.AStar;

public class AStarTest
{
	@Test
	public void testAstarSolution1()
	{
		int[][] puzzle = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 0, 11 }, { 13, 14, 15, 12 } };

		PuzzleState startState = new PuzzleState(puzzle);
		//startState.printPuzzle();
		//AStar astar = new AStar(startState);
	}

	@Test
	public void testAstarSolution2()
	{
		int[][] puzzle = { { 12, 13, 11, 9 }, { 10, 15, 14, 1 }, { 2, 8, 0, 4 }, { 3, 7, 6, 5 } };

		PuzzleState startState = new PuzzleState(puzzle);
		 startState.printPuzzle();
		 AStar astar = new AStar(startState);
	}

	@Test
	public void testAstarSolution3()
	{
		int[][] puzzle = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 0, 11 }, { 13, 14, 15, 12 } };

		PuzzleState startState = new PuzzleState(puzzle);
		// startState.printPuzzle();
		// AStar astar = new AStar(startState);
	}

	@Test
	public void testAstarSolution4()
	{
		int[][] puzzle = { { 7, 13, 11, 14 }, { 5, 10, 1, 12 }, { 8, 15, 2, 0 }, { 6, 4, 3, 9 } };

		PuzzleState startState = new PuzzleState(puzzle);
		//startState.printPuzzle();
		//AStar astar = new AStar(startState);
	}
}
