package PuzzleSolver.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import PuzzleSolver.PuzzleGenerator;
import PuzzleSolver.PuzzleState;
import PuzzleSolver.GUI.PuzzleGUI.GraphicsPanel;
import PuzzleSolver.Search.IDAStar;
import PuzzleSolver.Search.Heuristic.ManhattanDistance;

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
		_currentState = PuzzleGenerator.createPuzzleBySliding(new ManhattanDistance());
	}

	/*
	 * Solves current puzzle
	 */
	public void solve(GraphicsPanel panel)
	{
		IDAStar idaStar = new IDAStar(_currentState);
		List<PuzzleState> path = idaStar.getSolution();
		Queue<PuzzleState> pathQueue = new LinkedList<PuzzleState>();

		for (int i = 0; i < path.size(); i++)
		{
			pathQueue.add(path.get(i));
		}
		waitInBackground(1000, pathQueue, panel);
	}

	public void help()
	{
		System.out.println(_currentState.isSolved());
		System.out.println("Heuristic: " + _currentState.getHeuristic() + " Moves: " + _currentState.getMoves());
		System.out.println("Hashcode: " + _currentState.hashCode());
		_currentState.printPuzzle();
	}

	public void waitInBackground(int timeInSeconds, final Queue<PuzzleState> states, final GraphicsPanel panel)
	{
		if (!states.isEmpty())
		{
			_currentState = states.poll();
			panel.repaint();
		}

		_timer = new Timer(timeInSeconds, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!states.isEmpty())
				{
					_currentState = states.poll();
					panel.repaint();
				}
				else
				{
					_timer.stop();
				}
			}
		});
		_timer.setRepeats(true);
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
