package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;

import PuzzleSolver.PuzzleState;

public class PuzzleStateTest
{
	private int[][] _testPuzzle1 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
	private int[][] _testPuzzle2 = { { 2, 1, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
	private int[][] _testPuzzle3 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 15, 14, 0 } };
	private int[][] _testPuzzle4 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 0, 15 } };
	private int[][] _testPuzzle5 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 0, 11 }, { 13, 14, 15, 12 } };

	@Test
	public void testManhattanDistance()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1);
		assertEquals(0, state1.getManhattanDistance());

		PuzzleState state2 = new PuzzleState(_testPuzzle2);
		assertEquals(2, state2.getManhattanDistance());

		PuzzleState state4 = new PuzzleState(_testPuzzle4);
		assertEquals(1, state4.getManhattanDistance());
	}

	@Test
	public void testNeighborStates()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1);
		List<PuzzleState> neighbors = state1.getNeighborStates();
		assertEquals(2, neighbors.size());

		int[][] stateUpValues = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 0 }, { 13, 14, 15, 12 } };
		int[][] stateLeftValues = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 0, 15 } };
		PuzzleState stateUp = new PuzzleState(stateUpValues);
		PuzzleState stateLeft = new PuzzleState(stateLeftValues);

		assertTrue(neighbors.contains(stateUp));
		assertTrue(neighbors.contains(stateLeft));
	}

	@Test
	public void testIsSolvable()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1);
		assertTrue(state1.isSolvable());

		PuzzleState state2 = new PuzzleState(_testPuzzle2);
		assertFalse(state2.isSolvable());

		PuzzleState state3 = new PuzzleState(_testPuzzle3);
		assertFalse(state3.isSolvable());

		PuzzleState state4 = new PuzzleState(_testPuzzle5);
		assertTrue(state4.isSolvable());
	}

	@Test
	public void testStateEquals()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1);
		PuzzleState state2 = new PuzzleState(_testPuzzle1);
		PuzzleState state3 = new PuzzleState(_testPuzzle2);

		assertTrue(state1.equals(state2));
		assertFalse(state1.equals(state3));
		assertTrue(state1.hashCode() == state2.hashCode());
		assertTrue(state1.hashCode() != state3.hashCode());
	}

	@Test
	public void testPritorityQueue()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1);
		PuzzleState state2 = new PuzzleState(_testPuzzle4);

		PriorityQueue<PuzzleState> queue = new PriorityQueue<PuzzleState>();
		queue.add(state1);
		queue.add(state2);

		assertEquals(state1, queue.poll());
	}
}
