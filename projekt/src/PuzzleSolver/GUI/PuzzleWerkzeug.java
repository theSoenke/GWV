package PuzzleSolver.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import PuzzleSolver.PuzzleGenerator;
import PuzzleSolver.PuzzleState;
import PuzzleSolver.GUI.PuzzleGUI.GraphicsPanel;
import PuzzleSolver.Search.IDAStar;

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
		IDAStar idaStar = new IDAStar(_currentState);
		List<PuzzleState> path = idaStar.getSolution();

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
		List<PuzzleState> neighbors = _currentState.getNeighborStates();
		int valuePos = (r * 4) + c;

		for (PuzzleState neighbor : neighbors)
		{
			if (neighbor.getEmptyCell() == valuePos)
			{
				isGameOver();
				_currentState = neighbor;
				return true;
			}
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
