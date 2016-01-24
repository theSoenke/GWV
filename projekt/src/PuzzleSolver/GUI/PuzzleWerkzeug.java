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
		if (_timer != null)
		{
			_timer.stop();
		}
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
		animateSolution(1000, pathQueue, panel);
	}

	/*
	 * Animates the solution in the GUI
	 */
	public void animateSolution(int timeInSeconds, final Queue<PuzzleState> states, final GraphicsPanel panel)
	{
		if (!states.isEmpty())
		{
			_currentState = states.poll();
			panel.repaint();
		}
		
		if (_timer != null)
		{
			_timer.stop();
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

	/*
	 * Move tile to empty position when possible
	 */
	public boolean moveTile(int r, int c)
	{
		List<PuzzleState> neighbors = _currentState.getNeighborStates();
		int valuePos = (r * 4) + c;

		for (PuzzleState neighbor : neighbors)
		{
			if (neighbor.getEmptyCell() == valuePos)
			{
				_currentState = neighbor;
				return true;
			}
		}

		return false;
	}

	/*
	 * Returns true when the puzzle is solved and shows a dialog
	 */
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
