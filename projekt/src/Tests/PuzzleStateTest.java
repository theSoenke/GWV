package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.Test;

import PuzzleSolver.PuzzleState;
import PuzzleSolver.Search.Heuristic.FringePattern;
import PuzzleSolver.Search.Heuristic.LinearConflict;
import PuzzleSolver.Search.Heuristic.ManhattanDistance;

public class PuzzleStateTest
{
	private byte[] _testPuzzle1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };
	private byte[] _testPuzzle2 = { 2, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };
	private byte[] _testPuzzle3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14, 0 };
	private byte[] _testPuzzle4 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15 };
	private byte[] _testPuzzle5 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 11, 13, 14, 15, 12 };
	private byte[] _testPuzzle6 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 13, 14, 15, 12 };
	private byte[] _testPuzzle7 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15 };
	private byte[] _testPuzzleFringe = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };

	@Test
	public void testManhattanDistance()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1, new ManhattanDistance());
		assertEquals(0, state1.getHeuristic());

		PuzzleState state2 = new PuzzleState(_testPuzzle2, new ManhattanDistance());
		assertEquals(2, state2.getHeuristic());

		PuzzleState state4 = new PuzzleState(_testPuzzle4, new ManhattanDistance());
		assertEquals(1, state4.getHeuristic());

		PuzzleState state6 = new PuzzleState(_testPuzzle6, new ManhattanDistance());
		assertEquals(1, state6.getHeuristic());
	}

	/*@Test
	public void testLinearConflictDistance()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1, new LinearConflict());
		assertEquals(0, state1.getHeuristic());

		PuzzleState state2 = new PuzzleState(_testPuzzle2, new LinearConflict());
		assertEquals(4, state2.getHeuristic());

		PuzzleState state4 = new PuzzleState(_testPuzzle4, new LinearConflict());
		assertEquals(1, state4.getHeuristic());

		PuzzleState state7 = new PuzzleState(_testPuzzle7, new LinearConflict());
		assertEquals(1, state7.getHeuristic());
	}*/

	@Test
	public void testFringePatternDistance()
	{
		FringePattern fringePattern1 = new FringePattern();
		int dist1 = fringePattern1.calculate(_testPuzzleFringe);

		assertEquals(0, dist1);
		
		FringePattern fringePattern2 = new FringePattern();
		int dist2 = fringePattern2.calculate(_testPuzzleFringe);

		assertEquals(0, dist2);
	}

	@Test
	public void testNeighborStates()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1, new ManhattanDistance());
		List<PuzzleState> neighbors = state1.getNeighborStates();
		assertEquals(2, neighbors.size());

		byte[] stateUpValues = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 13, 14, 15, 12 };
		byte[] stateLeftValues = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15 };
		PuzzleState stateUp = new PuzzleState(stateUpValues, new ManhattanDistance());
		PuzzleState stateLeft = new PuzzleState(stateLeftValues, new ManhattanDistance());

		assertTrue(neighbors.contains(stateUp));
		assertTrue(neighbors.contains(stateLeft));
	}

	@Test
	public void testIsSolvable()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1, new ManhattanDistance());
		assertTrue(state1.isSolvable());

		PuzzleState state2 = new PuzzleState(_testPuzzle2, new ManhattanDistance());
		assertFalse(state2.isSolvable());

		PuzzleState state3 = new PuzzleState(_testPuzzle3, new ManhattanDistance());
		assertFalse(state3.isSolvable());

		PuzzleState state4 = new PuzzleState(_testPuzzle5, new ManhattanDistance());
		assertTrue(state4.isSolvable());
	}

	@Test
	public void testStateEquals()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1, new ManhattanDistance());
		PuzzleState state2 = new PuzzleState(_testPuzzle1, new ManhattanDistance());
		PuzzleState state3 = new PuzzleState(_testPuzzle2, new ManhattanDistance());

		assertTrue(state1.equals(state2));
		assertFalse(state1.equals(state3));
		assertTrue(state1.hashCode() == state2.hashCode());
		assertTrue(state1.hashCode() != state3.hashCode());
	}

	@Test
	public void testPritorityQueueManhattanDistance()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1, new ManhattanDistance());
		PuzzleState state2 = new PuzzleState(_testPuzzle4, new ManhattanDistance());

		Queue<PuzzleState> queue = new PriorityQueue<PuzzleState>();
		queue.add(state1);
		queue.add(state2);

		assertEquals(state1, queue.poll());
	}

	@Test
	public void testIsSolved()
	{
		PuzzleState state1 = new PuzzleState(_testPuzzle1, new ManhattanDistance());
		PuzzleState state2 = new PuzzleState(_testPuzzle7, new ManhattanDistance());

		assertTrue(state1.isSolved());
		assertFalse(state2.isSolved());
	}
}
