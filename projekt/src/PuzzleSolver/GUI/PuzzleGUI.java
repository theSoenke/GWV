package GUI;

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
    private JPanel mainpanel;
    private JButton solve;
    private JButton reset;
    private JFrame frame;
    private GraphicsPanel puzzleGraphics;
    private PuzzleWerkzeug werkzeug = new PuzzleWerkzeug();

    public PuzzleGUI()
    {
        initGUI();
    }

    private void implementButtonPanel()
    {
        mainpanel = new JPanel();
        GridLayout main = new GridLayout();
        mainpanel.setLayout(main);
        frame.getContentPane()
            .add(mainpanel, BorderLayout.SOUTH);
        mainpanel.setBackground(new Color(255, 255, 255));
        implementSolveButton();
        implementResetButton();
    }

    private void implementPuzzle()
    {
        puzzleGraphics = new GraphicsPanel();
        frame.getContentPane()
            .add(puzzleGraphics, BorderLayout.CENTER);
    }

    private void initGUI()
    {
        frame = new JFrame("15-Puzzle");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane()
            .setBackground(new Color(255, 255, 255));
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
        reset.addActionListener(new NewGameAction());
    }

    public void zeigeFenster()
    {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    class GraphicsPanel extends JPanel implements MouseListener
    {
        private static final int ROWS = 4;
        private static final int COLS = 4;
        private static final int CELL_SIZE = 80; // Pixels
        private Font _biggerFont;
        String number;

        public GraphicsPanel()
        {
            _biggerFont = new Font("SansSerif", Font.BOLD, CELL_SIZE / 2);
            this.setPreferredSize(
                    new Dimension(CELL_SIZE * COLS, CELL_SIZE * ROWS));
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
                    String text = werkzeug.getNumber(r, c);
                    if (text != null)
                    {
                        g.setColor(Color.gray);
                        g.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                        g.setColor(Color.black);
                        g.setFont(_biggerFont);
                        g.drawString(text, x + 20, y + (3 * CELL_SIZE) / 4);
                    }
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent arg0){}

        @Override
        public void mouseEntered(MouseEvent arg0){}

        @Override
        public void mouseExited(MouseEvent arg0){}

        @Override
        public void mousePressed(MouseEvent e)
        {
            int col = e.getX()/CELL_SIZE;
            int row = e.getY()/CELL_SIZE;
            
            if (!werkzeug.moveTile(row, col))
            {
                Toolkit.getDefaultToolkit().beep();
            }
            
            this.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent arg0){}
    }

    public class NewGameAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            werkzeug.reset();
            puzzleGraphics.repaint();
        }
    }

}
