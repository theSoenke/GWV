package PuzzleSolver;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PuzzleState implements Comparable<PuzzleState>
{
	private final int[][] _puzzle;
	private Cell _emptyCell;
	private PuzzleState _parentState;
	private int _moves;

	/*
	 * Move direction of the empty cell
	 */
	private enum moveDirection
	{
		up, down, left, right
	}

	public PuzzleState(int[][] puzzle)
	{
		if (puzzle.length != 4 && puzzle[0].length != 4)
		{
			throw new RuntimeException("Puzzle is not 4x4");
		}

		_puzzle = puzzle;
		_emptyCell = getEmptyCell();
	}

	public void setParentState(PuzzleState parent)
	{
		_moves += parent.getMoves() + 1;
		_parentState = parent;
	}

	public PuzzleState getParentState()
	{
		return _parentState;
	}

	public int getMoves()
	{
		return _moves;
	}

	public int[][] getArray()
	{
		return _puzzle;
	}

	/*
	 * Returns true when the puzzle has as solution
	 */
	public boolean isSolvable()
	{
		if (getManhattanDistance() == 0)
		{
			return true;
		}

		int[] flatarray = new int[16];
		int parity = 0;

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				flatarray[(i * _puzzle.length) + j] = _puzzle[i][j];
			}
		}

		for (int i = 0; i < flatarray.length; i++)
		{
			for (int j = i + 1; j < flatarray.length; j++)
			{
				if (flatarray[i] > flatarray[j] && !(i == 3 && j == 3 && flatarray[i] == 0))
				{
					parity++;
				}
			}
		}

		if (parity % 2 == 0)
		{
			return true;
		}
		return false;
	}

	public int getValue(int x, int y)
	{
		if (x < 0 || y < 0 || x > 3 || y > 3)
		{
			throw new RuntimeException("Zugriff auf ungültigen Index");
		}

		return _puzzle[y][x];
	}

	@Override
	public int hashCode()
	{
		return Arrays.deepHashCode(_puzzle);
	}

	@Override
	public boolean equals(Object o)
	{
		return Arrays.deepEquals(_puzzle, ((PuzzleState) o).getArray());
	}

	@Override
	public PuzzleState clone()
	{
		return new PuzzleState(_puzzle);
	}

	/*
	 * Prints the puzzle on the console
	 */
	public void printPuzzle()
	{
		for (int i = 0; i < 4; i++)
		{
			String line = "";
			for (int j = 0; j < 4; j++)
			{
				line += _puzzle[i][j] + "\t";
			}
			System.out.println(line + "\n");
			line = "";
		}

		System.err.println("\n");
	}

	/*
	 * Returns Manhattan distance of puzzle state
	 */
	public int getManhattanDistance()
	{
		int distance = 0;

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				int value = _puzzle[i][j];
				if (value != 0)
				{
					int x = ((value - 1) / 4);
					int y = ((value - 1) % 4);
					distance += Math.abs(i - x) + Math.abs(j - y);
				}
			}
		}

		return distance;
	}

	/*
	 * Returns all possible neighbor states
	 */
	public List<PuzzleState> getNeighborStates()
	{
		List<PuzzleState> neighbors = new LinkedList<PuzzleState>();

		PuzzleState left = clone();
		if (left.moveCell(moveDirection.left))
		{
			neighbors.add(left);
		}

		PuzzleState right = clone();
		if (right.moveCell(moveDirection.right))
		{
			neighbors.add(right);
		}

		PuzzleState up = clone();
		if (up.moveCell(moveDirection.up))
		{
			neighbors.add(up);
		}

		PuzzleState down = clone();

		if (down.moveCell(moveDirection.down))
		{
			neighbors.add(down);
		}

		return neighbors;
	}

	/*
	 * Tries to move
	 */
	private boolean moveCell(moveDirection dir)
	{
		if (dir == moveDirection.up)
		{
			if (_emptyCell.y > 0)
			{
				_puzzle[_emptyCell.y][_emptyCell.x] = _puzzle[_emptyCell.y - 1][_emptyCell.x];
				_emptyCell = new Cell(_emptyCell.x, _emptyCell.y - 1);
				_puzzle[_emptyCell.y][_emptyCell.x] = 0;
				return true;
			}
			return false;
		}
		else if (dir == moveDirection.down)
		{
			if (_emptyCell.y < 3)
			{
				_puzzle[_emptyCell.y][_emptyCell.x] = _puzzle[_emptyCell.y + 1][_emptyCell.x];
				_emptyCell = new Cell(_emptyCell.x, _emptyCell.y + 1);
				_puzzle[_emptyCell.y][_emptyCell.x] = 0;
				return true;
			}
			return false;
		}
		else if (dir == moveDirection.left)
		{
			if (_emptyCell.x > 0)
			{
				_puzzle[_emptyCell.y][_emptyCell.x] = _puzzle[_emptyCell.y][_emptyCell.x - 1];
				_emptyCell = new Cell(_emptyCell.x - 1, _emptyCell.y);
				_puzzle[_emptyCell.y][_emptyCell.x] = 0;
				return true;
			}
			return false;
		}
		else if (dir == moveDirection.right)
		{
			if (_emptyCell.x < 3)
			{
				_puzzle[_emptyCell.y][_emptyCell.x] = _puzzle[_emptyCell.y][_emptyCell.x + 1];
				_emptyCell = new Cell(_emptyCell.x + 1, _emptyCell.y);
				_puzzle[_emptyCell.y][_emptyCell.x] = 0;
				return true;
			}
			return false;
		}

		return false;
	}

	private Cell getEmptyCell()
	{
		Cell emptyCell = null;

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (_puzzle[i][j] == 0)
				{
					if (emptyCell == null)
					{
						emptyCell = new Cell(j, i);
					}
					else
					{
						throw new RuntimeException("Puzzle contains more than 1 empty cell");
					}
				}
			}
		}

		if (emptyCell == null)
		{
			throw new RuntimeException("Puzzle does not contain an empty cell");
		}
		else
		{
			return emptyCell;
		}
	}

	private class Cell
	{
		public final int x;
		public final int y;

		public Cell(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}

	@Override
	public int compareTo(PuzzleState state)
	{
		float cost = _moves + getManhattanDistance();
		float compareCost = state.getMoves() + state.getManhattanDistance();

		if (cost < compareCost)
		{
			return -1;
		}
		else if (cost > compareCost)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
