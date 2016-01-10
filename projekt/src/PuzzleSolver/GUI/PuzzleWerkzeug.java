package PuzzleSolver.GUI;

import PuzzleSolver.PuzzleState;

public class PuzzleWerkzeug
{
	private static final int ROWS = 4;
	private static final int COLS = 4;

	private PuzzleState _currentState;

	public PuzzleWerkzeug()
	{
		reset();
	}

	public int getNumber(int r, int c)
	{
		return _currentState.getArray()[r][c];
	}

	/*
	 * Creates a new random puzzle
	 */
	public void reset()
	{
		_currentState = PuzzleState.createPuzzleBySliding(true);
	}
	
	 public void solve()
	 {
	       
     }

	public boolean moveTile(int r, int c)
	{
		if (moveEmptyTile(r, c - 1))
		{
			PuzzleState tmpState = _currentState.moveRight();
			if (tmpState != null)
			{
				_currentState = tmpState;
				return true;
			}
		}
		else if (moveEmptyTile(r, c + 1))
		{
			PuzzleState tmpState = _currentState.moveLeft();
			if (tmpState != null)
			{
				_currentState = tmpState;
				return true;
			}
		}
		else if (moveEmptyTile(r - 1, c))
		{
			PuzzleState tmpState = _currentState.moveDown();
			if (tmpState != null)
			{
				_currentState = tmpState;
				return true;
			}
		}
		else if (moveEmptyTile(r + 1, c))
		{
			PuzzleState tmpState = _currentState.moveUp();
			if (tmpState != null)
			{
				_currentState = tmpState;
				return true;
			}
		}

		return false;
	}

	private boolean moveEmptyTile(int r, int c)
	{
		if (_currentState.getEmptyCell().y == r && _currentState.getEmptyCell().x == c)
		{
			return true;
		}
		return false;
	}

	public boolean isLegal(int r, int c)
	{
		return r >= 0 && r < ROWS && c >= 0 && c < COLS;
	}

	public boolean isGameOver()
	{
		if (_currentState.isSolved())
		{
			return true;
		}
		return false;
	}

}

