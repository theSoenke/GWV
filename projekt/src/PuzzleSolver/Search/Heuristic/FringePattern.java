package PuzzleSolver.Search.Heuristic;

public class FringePattern implements Heuristic
{
	private boolean _isFringePattern;
	private int _distance = 1;

	@Override
	public int calculate(byte[] puzzle)
	{
		return fringeDistance(puzzle);
	}

	public boolean isFringePattern()
	{
		return _isFringePattern;
	}

	private int fringeDistance(byte[] puzzle)
	{
		_distance = fringePatternDistance(puzzle);

		if (_distance == 0)
		{
			_isFringePattern = true;
		}
		else
		{
			_isFringePattern = false;
		}

		if (_isFringePattern)
		{
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
		}

		return _distance;
	}

	private int fringePatternDistance(byte[] puzzle)
	{
		int distance = 0;

		for (int i = 0; i < puzzle.length; i++)
		{
			if (puzzle[i] != 0)
			{
				int value = puzzle[i];

				if (value == 1 || value == 2 || value == 3 || value == 4 || value == 5 || value == 9 || value == 13)
				{
					int desX = (puzzle[i] - 1) % 4;
					int desY = (puzzle[i] - 1) / 4;

					int posX = i % 4;
					int posY = i / 4;

					distance += (Math.abs(desX - posX) + Math.abs(desY - posY));
				}
			}
		}

		return distance;
	}
}
