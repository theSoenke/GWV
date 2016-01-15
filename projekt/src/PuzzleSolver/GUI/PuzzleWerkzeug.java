package PuzzleSolver.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import PuzzleSolver.PuzzleGenerator;
import PuzzleSolver.PuzzleState;
import PuzzleSolver.GUI.PuzzleGUI.GraphicsPanel;
import PuzzleSolver.Search.AStar;

public class PuzzleWerkzeug
{
	private static final int ROWS = 4;
	private static final int COLS = 4;

	private PuzzleState _currentState;
	private Timer _timer;

	public PuzzleWerkzeug()
	{
		reset();
	}

	public int getNumber(int r, int c)
	{
		int pos = (c * 4) + r;
		return _currentState.getArray()[pos];
	}

	/*
	 * Creates a new random puzzle
	 */
	public void reset()
	{
		_currentState = PuzzleGenerator.createPuzzleBySliding(true);
	}

	/*
	 * Solves current puzzle
	 */
	public void solve(GraphicsPanel panel)
	{
		AStar aStar = new AStar(_currentState);
		List<PuzzleState> path = aStar.getSolution();

		for (int i = 0; i < path.size(); i++)
		{
			waitInBackground(500 * (i + 1), path.get(i), panel);
		}
	}
	
	public void help()
	{
		
	}

	public void waitInBackground(int timeInSeconds, final PuzzleState state, final GraphicsPanel panel)
	{
		_timer = new Timer(timeInSeconds, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				_currentState = state;
				panel.repaint();
				_timer.stop();
			}
		});
		_timer.setRepeats(false);
		_timer.start();
	}

	public boolean moveTile(int r, int c)
	{
		if (moveEmptyTile(r, c - 1))
		{
			PuzzleState tmpState = _currentState.moveRight();
			if (tmpState != null)
			{
				_currentState = tmpState;
				isGameOver();
				return true;
			}
		}
		else if (moveEmptyTile(r, c + 1))
		{
			PuzzleState tmpState = _currentState.moveLeft();
			if (tmpState != null)
			{
				_currentState = tmpState;
				isGameOver();
				return true;
			}
		}
		else if (moveEmptyTile(r - 1, c))
		{
			PuzzleState tmpState = _currentState.moveDown();
			if (tmpState != null)
			{
				_currentState = tmpState;
				isGameOver();
				return true;
			}
		}
		else if (moveEmptyTile(r + 1, c))
		{
			PuzzleState tmpState = _currentState.moveUp();
			if (tmpState != null)
			{
				_currentState = tmpState;
				isGameOver();
				return true;
			}
		}
		return false;
	}

	private boolean moveEmptyTile(int r, int c)
	{
		int pos = (c * 4) + 1;
		if (_currentState.getEmptyCell() == pos)
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
			JOptionPane.showMessageDialog(null, "You've got it.", "Congratulations", JOptionPane.PLAIN_MESSAGE);
			return true;

		}
		return false;
	}
}
