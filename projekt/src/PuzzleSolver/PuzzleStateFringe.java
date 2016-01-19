package PuzzleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PuzzleStateFringe implements Comparable<PuzzleStateFringe>
{
	private int[][] _puzzle;
	private Tile _emptyCell;
	private PuzzleStateFringe _parentState;
	private int _moves;
	private int _heuristic;
	private boolean _linearConflict;
	private boolean _isFringePattern;

	/*
	 * Move direction of the empty cell
	 */
	private enum moveDirection
	{
		up, down, left, right
	}

	public PuzzleStateFringe(int[][] puzzle, boolean linearConflict)
	{
		initState(puzzle, linearConflict);
	}

	public PuzzleStateFringe(int[][] puzzle, int moves, PuzzleStateFringe parent, boolean linearConflict)
	{
		initState(puzzle, linearConflict);

		_moves = moves;
		_parentState = parent;

	}

	private void initState(int[][] puzzle, boolean linearConflict)
	{
		if (puzzle.length != 4 && puzzle[0].length != 4)
		{
			throw new RuntimeException("Puzzle is not 4x4");
		}

		_linearConflict = linearConflict;
		_puzzle = puzzle;
		_emptyCell = getEmptyCell();

		_heuristic = fringeDistance();

		if (linearConflict)
		{
			_heuristic += calcLinearConflict();
		}
	}

	public PuzzleStateFringe getParentState()
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

	public boolean isSolved()
	{
		if (_heuristic == 0)
		{
			return true;
		}
		return false;
	}

	/*
	 * Returns true when the puzzle has as solution
	 */
	public boolean isSolvable()
	{
		List<Integer> puzzle = convertToFlatArray();
		int parity = 0;
		int gridWidth = 4;
		int row = 0;
		int blankRow = 0;

		for (int i = 0; i < puzzle.size(); i++)
		{
			if (i % gridWidth == 0)
			{
				row++;
			}
			if (puzzle.get(i) == 0)
			{
				blankRow = row;
			}
			for (int j = i + 1; j < puzzle.size(); j++)
			{
				if (puzzle.get(i) > puzzle.get(j) && puzzle.get(j) != 0)
				{
					parity++;
				}
			}
		}

		if (gridWidth % 2 == 0)
		{
			if (blankRow % 2 == 0)
			{
				return parity % 2 == 0;
			}
			else
			{
				return parity % 2 != 0;
			}
		}
		else
		{
			return parity % 2 == 0;
		}
	}

	/*
	 * Converts 2d to 1d array
	 */
	private List<Integer> convertToFlatArray()
	{
		List<Integer> flatpuzzle = new ArrayList<Integer>();

		for (int i = 0; i < _puzzle.length; i++)
		{
			for (int j = 0; j < _puzzle.length; j++)
			{
				flatpuzzle.add(_puzzle[i][j]);
			}
		}

		return flatpuzzle;
	}

	@Override
	public int hashCode()
	{
		return Arrays.deepHashCode(_puzzle);
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof PuzzleStateFringe))
		{
			return false;
		}
		PuzzleStateFringe state = (PuzzleStateFringe) o;
		return Arrays.deepEquals(_puzzle, state.getArray());
	}

	@Override
	public PuzzleStateFringe clone()
	{
		int[][] puzzleClone = new int[4][4];

		for (int i = 0; i < _puzzle.length; i++)
		{
			puzzleClone[i] = Arrays.copyOf(_puzzle[i], _puzzle.length);
		}
		PuzzleStateFringe cloneState = new PuzzleStateFringe(puzzleClone, _moves + 1, this, _linearConflict);
		return cloneState;
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

		System.out.println("\n");
	}

	/*
	 * Returns cached heuristic
	 */
	public int getHeuristic()
	{
		return _heuristic;
	}

	/*
	 * Returns Manhattan distance of puzzle state
	 */
	private int calcManhattanDistance()
	{
		int distance = 0;

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				int value = _puzzle[i][j];
				if (value != 0)
				{
					int y = ((value - 1) / 4);
					int x = ((value - 1) % 4);
					distance += Math.abs(i - y) + Math.abs(j - x);
				}
			}
		}

		return distance;
	}

	/*
	 * Returns linear conflict of puzzle state
	 */
	private int calcLinearConflict()
	{
		int linearConflict = horizontalConflict();
		linearConflict += verticalConflict();

		return linearConflict;
	}

	private int horizontalConflict()
	{
		int linearConflict = 0;

		for (int i = 0; i < 4; i++)
		{
			int max = -1;
			for (int j = 0; j < 4; j++)
			{
				int valuealue = _puzzle[i][j];
				if (valuealue != 0 && valuealue % 4 == i + 1)
				{
					if (valuealue > max)
					{
						max = valuealue;
					}
					else
					{
						linearConflict += 2;
					}
				}

			}

		}
		return linearConflict;
	}

	private int verticalConflict()
	{
		int linearConflict = 0;

		for (int r = 0; r < 4; r++)
		{
			int max = -1;
			for (int c = 0; c < 4; c++)
			{
				int value = _puzzle[r][c];
				if (value != 0 && (value - 1) / 4 == r)
				{
					if (value > max)
					{
						max = value;
					}
					else
					{
						linearConflict += 2;
					}
				}

			}

		}
		return linearConflict;
	}

	/*
	 * Returns all possible neighbor states
	 */
	public List<PuzzleStateFringe> getNeighborStates(boolean fringePattern)
	{
		List<PuzzleStateFringe> neighbors = new LinkedList<PuzzleStateFringe>();

		PuzzleStateFringe left = clone();
		if (left.moveCell(moveDirection.left, fringePattern))
		{
			neighbors.add(left);
		}

		PuzzleStateFringe right = clone();
		if (right.moveCell(moveDirection.right, fringePattern))
		{
			neighbors.add(right);
		}

		PuzzleStateFringe up = clone();
		if (up.moveCell(moveDirection.up, fringePattern))
		{
			neighbors.add(up);
		}

		PuzzleStateFringe down = clone();

		if (down.moveCell(moveDirection.down, fringePattern))
		{
			neighbors.add(down);
		}

		return neighbors;
	}

	/*
	 * Tries to move empty cell
	 */
	private boolean moveCell(moveDirection dir, boolean fringePattern)
	{
		if (dir == moveDirection.up)
		{
			if (_emptyCell.y > 0 && (!fringePattern || !(_emptyCell.y == 1 && _isFringePattern)))
			{
				_puzzle[_emptyCell.y][_emptyCell.x] = _puzzle[_emptyCell.y - 1][_emptyCell.x];
				_emptyCell = new Tile(_emptyCell.x, _emptyCell.y - 1);
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
				_emptyCell = new Tile(_emptyCell.x, _emptyCell.y + 1);
				_puzzle[_emptyCell.y][_emptyCell.x] = 0;
				return true;
			}
			return false;
		}
		else if (dir == moveDirection.left)
		{
			if (_emptyCell.x > 0 && (!fringePattern || !(_emptyCell.x == 1 && _isFringePattern)))
			{
				_puzzle[_emptyCell.y][_emptyCell.x] = _puzzle[_emptyCell.y][_emptyCell.x - 1];
				_emptyCell = new Tile(_emptyCell.x - 1, _emptyCell.y);
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
				_emptyCell = new Tile(_emptyCell.x + 1, _emptyCell.y);
				_puzzle[_emptyCell.y][_emptyCell.x] = 0;
				return true;
			}
			return false;
		}

		return false;
	}

	private int fringeDistance()
	{
		int distance = fringePatternDistance();

		if (distance == 0)
		{
			distance = 0;
			_isFringePattern = true;
		}
		else
		{
			_isFringePattern = false;
		}

		if (_isFringePattern)
		{
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 4; j++)
				{
					int value = _puzzle[i][j];

					if (value != 0)
					{
						int y = ((value - 1) / 4);
						int x = ((value - 1) % 4);
						distance += Math.abs(i - y) + Math.abs(j - x);
					}
				}
			}
		}

		return distance;
	}

	private int fringePatternDistance()
	{
		int distance = 0;

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				int value = _puzzle[i][j];

				if (value == 1 || value == 2 || value == 3 || value == 4 || value == 5 || value == 9 || value == 13)
				{
					int y = ((value - 1) / 4);
					int x = ((value - 1) % 4);
					distance += Math.abs(i - y) + Math.abs(j - x);
					// System.out.println("value: " + value + " x: " + x + " y:"
					// + y + " distance: " + distance);
				}
			}
		}

		return distance;
	}

	public PuzzleStateFringe moveRight()
	{
		PuzzleStateFringe cloneState = clone();
		if (cloneState.moveCell(moveDirection.right, true))
		{
			return cloneState;
		}
		return null;
	}

	public PuzzleStateFringe moveLeft()
	{
		PuzzleStateFringe cloneState = clone();
		if (cloneState.moveCell(moveDirection.left, true))
		{
			return cloneState;
		}
		return null;
	}

	public PuzzleStateFringe moveUp()
	{
		PuzzleStateFringe cloneState = clone();
		if (cloneState.moveCell(moveDirection.up, true))
		{
			return cloneState;
		}
		return null;
	}

	public PuzzleStateFringe moveDown()
	{
		PuzzleStateFringe cloneState = clone();
		if (cloneState.moveCell(moveDirection.down, true))
		{
			return cloneState;
		}
		return null;
	}

	public Tile getEmptyCell()
	{
		Tile emptyCell = null;

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (_puzzle[i][j] == 0)
				{
					if (emptyCell == null)
					{
						emptyCell = new Tile(j, i);
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

	@Override
	public int compareTo(PuzzleStateFringe state)
	{
		float cost = _moves + getHeuristic();
		float compareCost = state.getMoves() + state.getHeuristic();

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

	public class Tile
	{
		public final int x;
		public final int y;

		public Tile(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
}