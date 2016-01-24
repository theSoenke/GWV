package PuzzleSolver.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class PuzzleGUI
{
	private JPanel _mainpanel;
	private JButton _solveBtn;
	private JButton _resetBtn;
	private JFrame _frame;
	private GraphicsPanel _puzzleGraphics;

	private PuzzleWerkzeug _werkzeug;

	public PuzzleGUI()
	{
		_werkzeug = new PuzzleWerkzeug();
		initGUI();
	}

	private void implementButtonPanel()
	{
		_mainpanel = new JPanel();
		GridLayout main = new GridLayout();
		_mainpanel.setLayout(main);
		_frame.getContentPane().add(_mainpanel, BorderLayout.SOUTH);
		_mainpanel.setBackground(new Color(255, 255, 255));
		implementResetButton();
		implementSolveButton();
	}

	private void implementPuzzle()
	{
		_puzzleGraphics = new GraphicsPanel();
		_frame.getContentPane().add(_puzzleGraphics, BorderLayout.CENTER);
	}

	private void initGUI()
	{
		_frame = new JFrame("15-Puzzle");
		_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		_frame.getContentPane().setBackground(new Color(255, 255, 255));
		_frame.setTitle("15-Puzzle");
		implementButtonPanel();
		implementPuzzle();
		_frame.pack();
		_frame.setSize(320, 400);
		_frame.setResizable(false);
	}

	private void implementSolveButton()
	{
		_solveBtn = new JButton();
		_mainpanel.add(_solveBtn);
		_solveBtn.setText("Solve!");
		_solveBtn.setPreferredSize(new java.awt.Dimension(180, 50));
		_solveBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		_solveBtn.setBackground(new Color(200, 200, 200));
		_solveBtn.setSelected(true);
		_solveBtn.addActionListener(new SolveAction());
	}

	private void implementResetButton()
	{
		_resetBtn = new JButton();
		_mainpanel.add(_resetBtn);
		_resetBtn.setText("New");
		_resetBtn.setPreferredSize(new java.awt.Dimension(180, 50));
		_resetBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		_resetBtn.setBackground(new Color(200, 200, 200));
		_resetBtn.setSelected(true);
		_resetBtn.addActionListener(new NewGameAction());
	}

	public void showUI()
	{
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}

	class GraphicsPanel extends JPanel implements MouseListener
	{
		private static final long serialVersionUID = 497341847642354581L;
		private static final int ROWS = 4;
		private static final int COLS = 4;
		private static final int CELL_SIZE = 80; // Pixels
		private Font _biggerFont;
		String number;

		public GraphicsPanel()
		{
			_biggerFont = new Font("SansSerif", Font.BOLD, CELL_SIZE / 2);
			this.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE * ROWS));
			this.setBackground(Color.black);
			this.addMouseListener(this);
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			for (int r = 0; r < ROWS; r++)
			{
				for (int c = 0; c < COLS; c++)
				{
					int x = c * CELL_SIZE;
					int y = r * CELL_SIZE;
					int value = _werkzeug.getNumber(c, r);
					if (value != 0)
					{
						g.setColor(Color.gray);
						g.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
						g.setColor(Color.black);
						g.setFont(_biggerFont);
						g.drawString(Integer.toString(value), x + 20, y + (3 * CELL_SIZE) / 4);
					}
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			int col = e.getX() / CELL_SIZE;
			int row = e.getY() / CELL_SIZE;

			if (!_werkzeug.moveTile(row, col))
			{
				Toolkit.getDefaultToolkit().beep();
			}

			this.repaint();

			_werkzeug.isGameOver();
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}

	public class NewGameAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			_werkzeug.reset();
			_puzzleGraphics.repaint();
		}
	}

	public class SolveAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			_werkzeug.solve(_puzzleGraphics);
		}
	}
}
