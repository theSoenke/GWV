package PuzzleSolver.Search.Heuristic;

public class LinearConflict extends ManhattanDistance
{
	private byte[] _puzzle;

	/*
	 * Returns linear conflict of puzzle state
	 */
	@Override
	public int calculate(byte[] puzzle)
	{
		_puzzle = puzzle;
		int distance = super.calculate(puzzle);
		distance += horizontalConflict();
		distance += verticalConflict();

		return distance;
	}

	private int horizontalConflict()
	{
		int linearConflict = 0;

		for (int i = 0; i < _puzzle.length; i++)
		{
			int max = -1;
			int value = _puzzle[i];
			if (value != 0 && value % 4 == i + 1)
			{
				if (value > max)
				{
					max = value;
				}
				else
				{
					linearConflict += 2;
				}
			}
		}
		return linearConflict;
	}

	private int verticalConflict()
	{
		int linearConflict = 0;

		for (int i = 0; i < 4; i++)
		{
			int max = -1;
			int value = (int) _puzzle[i];
			if (value != 0 && (value - 1) / 4 == i)
			{
				if (value > max)
				{
					max = value;
				}
				else
				{
					linearConflict += 2;
				}
			}
		}
		return linearConflict;
	}
}
