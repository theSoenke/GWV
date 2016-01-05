package PuzzleSolver.GUI;

public class PuzzleWerkzeug
{
	private static final int ROWS = 4;
	private static final int COLS = 4;

	private Tile[][] contents;
	private Tile emptyTile;

	public PuzzleWerkzeug()
	{
		contents = new Tile[ROWS][COLS];
		reset();
	}

	String getNumber(int row, int col)
	{
		return contents[row][col].getNumber();
	}

	public void reset()
	{
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLS; c++)
			{
				contents[r][c] = new Tile(r, c, "" + (r * COLS + c + 1));
			}
		}

		emptyTile = contents[ROWS - 1][COLS - 1];
		emptyTile.setNumber(null);

		// Tausche Tile mit einer einer zufälligen Tile
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLS; c++)
			{
				switchTiles(r, c, (int) (Math.random() * ROWS), (int) (Math.random() * COLS));
			}
		}
	}

	private void switchTiles(int r1, int c1, int r2, int c2)
	{
		Tile temp = contents[r1][c1];
		contents[r1][c1] = contents[r2][c2];
		contents[r2][c2] = temp;
	}

	public boolean moveTile(int r, int c)
	{
		return checkEmptyTile(r, c, -1, 0) || checkEmptyTile(r, c, 1, 0) || checkEmptyTile(r, c, 0, -1)
				|| checkEmptyTile(r, c, 0, 1);
	}

	private boolean checkEmptyTile(int r, int c, int rdelta, int cdelta)
	{
		int rNeighbor = r + rdelta;
		int cNeighbor = c + cdelta;
		// --- Check to see if this neighbor is on board and is empty.
		if (isLegal(rNeighbor, cNeighbor) && contents[rNeighbor][cNeighbor] == emptyTile)
		{
			switchTiles(r, c, rNeighbor, cNeighbor);
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
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < ROWS; c++)
			{
				Tile t = contents[r][c];
				return t.isInFinalPosition(r, c);
			}
		}
		return true;
	}

}

class Tile
{

	private int row;
	private int col;
	private String number;

	public Tile(int r, int c, String n)
	{
		row = r;
		col = c;
		number = n;
	}

	public void setNumber(String newNumber)
	{
		number = newNumber;
	}

	public String getNumber()
	{
		return number;
	}

	public boolean isInFinalPosition(int r, int c)
	{
		return r == row && c == col;
	}
}