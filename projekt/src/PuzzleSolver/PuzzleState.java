package PuzzleSolver;

import java.util.Arrays;
import java.util.Collections;
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

	public PuzzleState(int[][] puzzle, int moves)
	{
		if (puzzle.length != 4 && puzzle[0].length != 4)
		{
			throw new RuntimeException("Puzzle is not 4x4");
		}

		_moves = moves;
		_puzzle = puzzle;
		_emptyCell = getEmptyCell();
	}

	/*
	 * Returns a puzzle that is solvable
	 */
	public static PuzzleState createSolvablePuzzle()
	{
		PuzzleState puzzle = null;
		do
		{
			puzzle = createRandomPuzzle();
		}
		while (!puzzle.isSolvable());

		return puzzle;
	}

	/*
	 * Create a random puzzle. Can be unsolvable
	 */
	public static PuzzleState createRandomPuzzle()
	{
		int[][] puzzle = new int[4][4];

		List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
		Collections.shuffle(numbers);

		int i = 0;
		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				puzzle[x][y] = numbers.get(i);
				i++;
			}
		}

		return new PuzzleState(puzzle);
	}

	int a = 0;
	public void setParentState(PuzzleState parent)
	{
		_moves = parent.getMoves() + 1;
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

		int inversions = 0;

		for (int i = 0; i < _puzzle.length; i++)
		{
			for (int j = 0; j < _puzzle.length; j++)
			{
				for (int j2 = i + j; j2 < _puzzle.length; j2++)
				{
					if (_puzzle[i][j] < _puzzle[j][j2])
					{
						inversions++;
					}
				}
			}
		}

		if (inversions % 2 == 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return Arrays.deepHashCode(_puzzle);
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof PuzzleState))
		{
			return false;
		}
		PuzzleState state = (PuzzleState) o;
		return Arrays.deepEquals(_puzzle, state.getArray());
	}

	@Override
	public PuzzleState clone()
	{
		int[][] puzzleClone = new int[4][4];

		for (int i = 0; i < _puzzle.length; i++)
		{
			puzzleClone[i] = Arrays.copyOf(_puzzle[i], _puzzle.length);
		}
		PuzzleState cloneState = new PuzzleState(puzzleClone, _moves);
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
	 * Tries to move empty cell
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

	int i = 0;
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
