package GUI;

public class GUIWerkzeug
{
    private static final int ROWS = 4;
    private static final int COLS = 4;

    private Tile[][] contents;
    private Tile emptyTile;

    public GUIWerkzeug()
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
                switchTiles(r, c, (int) (Math.random() * ROWS),
                        (int) (Math.random() * COLS));
            }
        }
    }

    private void switchTiles(int r1, int c1, int r2, int c2)
    {
        Tile temp = contents[r1][c1];
        contents[r1][c1] = contents[r2][c2];
        contents[r2][c2] = temp;
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
}