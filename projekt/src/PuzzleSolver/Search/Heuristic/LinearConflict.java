package PuzzleSolver.Search.Heuristic;

/*
 * Returns manhattan distance + linear conflict of puzzle state
 */
public class LinearConflict extends ManhattanDistance
{
	private byte[][] _puzzle;

	@Override
	public int calculate(byte[] puzzle)
	{
		_puzzle = convertTo2D(puzzle);
		int distance = super.calculate(puzzle);
		distance += horizontalConflict();
		distance += verticalConflict();

		return distance;
	}

	private byte[][] convertTo2D(byte[] puzzle)
	{
		byte[][] twoDim = new byte[4][4];

		for (int i = 0; i < puzzle.length; i++)
		{
			byte value = puzzle[i];
			int y = i / 4;
			int x = i % 4;
			twoDim[y][x] = value; 
		}
		
		return twoDim;
	}

	private int horizontalConflict()
	{
		int linearConflict = 0;

		for (int i = 0; i < 4; i++)
		{
			int max = -1;
			for (int j = 0; j < 4; j++)
			{
				int valuealue = _puzzle[i][j];
				if (valuealue != 0 && valuealue % 4 == i + 1)
				{
					if (valuealue > max)
					{
						max = valuealue;
					}
					else
					{
						linearConflict += 2;
					}
				}

			}

		}
		return linearConflict;
	}

	private int verticalConflict()
	{
		int linearConflict = 0;

		for (int r = 0; r < 4; r++)
		{
			int max = -1;
			for (int c = 0; c < 4; c++)
			{
				int value = (int) _puzzle[r][c];
				if (value != 0 && (value - 1) / 4 == r)
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

		}
		return linearConflict;
	}
}
