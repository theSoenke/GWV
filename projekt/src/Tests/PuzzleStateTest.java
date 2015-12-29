package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;

import PuzzleSolver.PuzzleState;

public class PuzzleStateTest
{
	private int[][] _testPuzzle1 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
	private int[][] _testPuzzle2 = { { 2, 1, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
	private int[][] _testPuzzle3 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 15, 14, 0 } };

	@Test
	public void testManhattanDistance()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1);
		assertEquals(0, state1.getManhattanDistance());

		PuzzleState state2 = new PuzzleState(_testPuzzle2);
		assertEquals(2, state2.getManhattanDistance());
	}

	@Test
	public void testNeighborStates()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1);
		List<PuzzleState> neighbors = state1.getNeighborStates();

		int[][] stateUpValues = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 0 }, { 13, 14, 15, 12 } };
		int[][] stateLeftValues = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 0, 15 } };
		PuzzleState stateUp = new PuzzleState(stateUpValues);
		PuzzleState stateLeft = new PuzzleState(stateLeftValues);

		assertEquals(2, neighbors.size());
		assertTrue(neighbors.contains(stateUp));
		assertTrue(neighbors.contains(stateLeft));
	}

	@Test
	public void testIsSolvable()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1);
		assertTrue(state1.isSolvable());

		PuzzleState state2 = new PuzzleState(_testPuzzle2);
		assertTrue(state2.isSolvable());

		PuzzleState state3 = new PuzzleState(_testPuzzle3);
		assertFalse(state3.isSolvable());
	}
}
