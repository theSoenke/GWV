import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main
{

	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		PuzzleState puzzle = createPuzzle();
		System.out.println("Initial State:");
		puzzle.printPuzzle();
		Solver solver = new Solver(puzzle);
	}

	/*
	 * Erstellt ein zufälliges Puzzle. Kann unlösbar sein
	 */
	private PuzzleState createPuzzle()
	{
		int[][] puzzle = new int[4][4];

		List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
		Collections.shuffle(numbers);

		int i = 0;
		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				puzzle[x][y] = numbers.get(i);
				i++;
			}
		}

		return new PuzzleState(puzzle);
	}
}
