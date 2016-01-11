package PuzzleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PuzzleState implements Comparable<PuzzleState>
{
	private int[][] _puzzle;
	private Tile _emptyCell;
	private PuzzleState _parentState;
	private int _moves;
	private int _heuristic;
	private boolean _linearConflict;

	/*
	 * Move direction of the empty cell
	 */
	private enum moveDirection
	{
		up, down, left, right
	}

	public PuzzleState(int[][] puzzle, boolean linearConflict)
	{
		initState(puzzle, linearConflict);
	}

	public PuzzleState(int[][] puzzle, int moves, PuzzleState parent, boolean linearConflict)
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

		_heuristic = calcManhattanDistance();

		if (linearConflict)
		{
			_heuristic += calcLinearConflict();
		}
	}

	/*
	 * Returns a puzzle that is solvable
	 */
	public static PuzzleState createSolvablePuzzle(boolean linearConflict)
	{
		PuzzleState puzzle = null;
		do
		{
			puzzle = createRandomPuzzle(linearConflict);
		}
		while (!puzzle.isSolvable());

		return puzzle;
	}

	/*
	 * Returns a random puzzle created by sliding tiles
	 */
	public static PuzzleState createPuzzleBySliding(boolean linearConflict)
	{
		int[][] defaultPuzzle = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
		PuzzleState puzzle = new PuzzleState(defaultPuzzle, linearConflict);
		Random random = new Random();

		for (int i = 0; i < 10; i++)
		{
			List<PuzzleState> neighbors = puzzle.getNeighborStates();
			int rand = random.nextInt(neighbors.size());
			puzzle = neighbors.get(rand);
		}

		return puzzle;
	}

	/*
	 * Create a random puzzle. Can be unsolvable
	 */
	public static PuzzleState createRandomPuzzle(boolean linearConflict)
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

		return new PuzzleState(puzzle, linearConflict);
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

	public boolean isSolvable2()
	{
		if (_heuristic == 0)
		{
			return true;
		}

		int parity = 0;
		int emptyRow = 0;
		List<Integer> flatpuzzle = convertToFlatArray();

		for (int i = 0; i < flatpuzzle.size(); i++)
		{
			for (int j = i + 1; j < flatpuzzle.size(); j++)
			{
				if (flatpuzzle.get(i) == 0)
				{
					emptyRow = i;
				}
				if (flatpuzzle.get(i) > flatpuzzle.get(j) && flatpuzzle.get(i) != 0 && flatpuzzle.get(j) != 0)
				{
					parity++;
				}
			}
		}

		if (emptyRow % 2 == 0)
		{
			return (parity % 2 == 0);
		}
		else
		{
			return (parity % 2 != 0);
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
		PuzzleState cloneState = new PuzzleState(puzzleClone, _moves + 1, this, _linearConflict);
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
			if (_emptyCell.x > 0)
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

	public PuzzleState moveRight()
	{
		PuzzleState cloneState = clone();
		if (cloneState.moveCell(moveDirection.right))
		{
			return cloneState;
		}
		return null;
	}

	public PuzzleState moveLeft()
	{
		PuzzleState cloneState = clone();
		if (cloneState.moveCell(moveDirection.left))
		{
			return cloneState;
		}
		return null;
	}

	public PuzzleState moveUp()
	{
		PuzzleState cloneState = clone();
		if (cloneState.moveCell(moveDirection.up))
		{
			return cloneState;
		}
		return null;
	}

	public PuzzleState moveDown()
	{
		PuzzleState cloneState = clone();
		if (cloneState.moveCell(moveDirection.down))
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
	public int compareTo(PuzzleState state)
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
