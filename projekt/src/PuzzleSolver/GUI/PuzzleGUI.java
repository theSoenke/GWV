package PuzzleSolver.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class PuzzleGUI
{
	private JPanel mainpanel;
	private JButton solve;
	private JButton reset;
	private JFrame frame;
	private GraphicsPanel puzzleGraphics;

	public PuzzleGUI()
	{
		initGUI();
	}

	private void implementButtonPanel()
	{
		mainpanel = new JPanel();
		GridLayout main = new GridLayout();
		mainpanel.setLayout(main);
		frame.getContentPane().add(mainpanel, BorderLayout.SOUTH);
		mainpanel.setBackground(new Color(255, 255, 255));
		implementSolveButton();
		implementResetButton();
	}

	private void implementPuzzle()
	{
		puzzleGraphics = new GraphicsPanel();
		frame.getContentPane().add(puzzleGraphics, BorderLayout.CENTER);
	}

	private void initGUI()
	{
		frame = new JFrame("15-Puzzle");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setTitle("15-Puzzle");
		implementButtonPanel();
		implementPuzzle();
		frame.pack();
		frame.setSize(337, 409);
	}

	private void implementSolveButton()
	{
		solve = new JButton();
		mainpanel.add(solve);
		solve.setText("Solve!");
		solve.setPreferredSize(new java.awt.Dimension(180, 50));
		solve.setFont(new Font("Tahoma", Font.BOLD, 14));
		solve.setBackground(new Color(200, 200, 200));
		solve.setSelected(true);
	}

	private void implementResetButton()
	{
		reset = new JButton();
		mainpanel.add(reset);
		reset.setText("Reset");
		reset.setPreferredSize(new java.awt.Dimension(180, 50));
		reset.setFont(new Font("Tahoma", Font.BOLD, 14));
		reset.setBackground(new Color(200, 200, 200));
		reset.setSelected(true);
	}

	public void showUI()
	{
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}

class GraphicsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
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
				if (r == 3 && c == 3)
				{
					break;
				}
				else
				{
					g.setColor(Color.gray);
					g.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
					g.setColor(Color.black);
					g.setFont(_biggerFont);

					if (r == 0)
					{
						Integer a = new Integer(c);
						number = a.toString();
						g.drawString(number, x + 20, y + (3 * CELL_SIZE) / 4);
					}
					else if (r == 1)
					{
						Integer a = new Integer(c + 4);
						number = a.toString();
						g.drawString(number, x + 20, y + (3 * CELL_SIZE) / 4);
					}
					else if (r == 2)
					{
						Integer a = new Integer(c + 8);
						number = a.toString();
						g.drawString(number, x + 20, y + (3 * CELL_SIZE) / 4);
					}
					else
					{
						Integer a = new Integer(c + 12);
						number = a.toString();
						g.drawString(number, x + 20, y + (3 * CELL_SIZE) / 4);

					}
				}
			}
		}
	}
}
