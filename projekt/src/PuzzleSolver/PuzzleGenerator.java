package PuzzleSolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PuzzleGenerator
{
	/*
	 * Returns a puzzle that is solvable
	 */
	public static PuzzleState createSolvablePuzzle(boolean linearConflict)
	{
		PuzzleState puzzle = null;
		do
		{
			puzzle = createRandomPuzzle(linearConflict);
		}
		while (!puzzle.isSolvable());

		return puzzle;
	}

	/*
	 * Returns a random puzzle created by sliding tiles
	 */
	public static PuzzleState createPuzzleBySliding(boolean linearConflict)
	{
		byte[] defaultPuzzle = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };
		PuzzleState puzzle = new PuzzleState(defaultPuzzle, linearConflict);
		Random random = new Random();

		for (int i = 0; i < 50; i++)
		{
			List<PuzzleState> neighbors = puzzle.getNeighborStates();
			int rand = random.nextInt(neighbors.size());
			puzzle = neighbors.get(rand);
		}

		puzzle.printPuzzle();

		return puzzle;
	}

	/*
	 * Create a random puzzle. Can be unsolvable
	 */
	public static PuzzleState createRandomPuzzle(boolean linearConflict)
	{
		byte[] puzzle = new byte[16];

		List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
		Collections.shuffle(numbers);

		for (int i = 0; i < 16; i++)
		{
			puzzle[i] = numbers.get(i).byteValue();
		}

		return new PuzzleState(puzzle, linearConflict);
	}
}
