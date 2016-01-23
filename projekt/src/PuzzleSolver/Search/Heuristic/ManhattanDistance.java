package PuzzleSolver.Search.Heuristic;

public class ManhattanDistance implements Heuristic
{
	private int _distance = 1;

	/*
	 * Returns Manhattan distance of puzzle state
	 */
	@Override
	public int calculate(byte[] puzzle)
	{
		_distance = 0;

		for (int i = 0; i < puzzle.length; i++)
		{
			if (puzzle[i] != 0)
			{
				int desX = (puzzle[i] - 1) % 4;
				int desY = (puzzle[i] - 1) / 4;

				int posX = i % 4;
				int posY = i / 4;

				_distance += (Math.abs(desX - posX) + Math.abs(desY - posY));
			}
		}

		return _distance;
	}

	public boolean isSolved()
	{
		if (_distance == 0)
		{
			return true;
		}
		return false;
	}
}
