package PuzzleSolver;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import PuzzleSolver.Search.Heuristic.FringePattern;
import PuzzleSolver.Search.Heuristic.Heuristic;

public class PuzzleState implements Comparable<PuzzleState>
{
	private byte[] _puzzle;
	private int _emptyCell;
	private PuzzleState _parentState;
	private int _moves;
	private Heuristic _heuristic;
	private int _heuristicDistance;

	/*
	 * Move direction of the empty cell
	 */
	private enum moveDirection
	{
		up, down, left, right
	}

	public PuzzleState(byte[] puzzle, Heuristic heuristic)
	{
		initState(puzzle, heuristic);
	}

	public PuzzleState(byte[] puzzle, int moves, PuzzleState parent, Heuristic heuristic)
	{
		initState(puzzle, heuristic);

		_moves = moves;
		_parentState = parent;

	}

	private void initState(byte[] puzzle, Heuristic heuristic)
	{
		if (puzzle.length != 16)
		{
			throw new RuntimeException("Puzzle is not 4x4");
		}

		_puzzle = puzzle;
		_emptyCell = getEmptyCell();

		_heuristic = heuristic;
		_heuristicDistance = heuristic.calculate(_puzzle);
	}

	public PuzzleState getParentState()
	{
		return _parentState;
	}

	public int getMoves()
	{
		return _moves;
	}

	public byte[] getArray()
	{
		return _puzzle;
	}

	public boolean isSolved()
	{
		return _heuristic.isSolved();
	}

	/*
	 * Returns true when the puzzle has as solution
	 */
	public boolean isSolvable()
	{
		int parity = 0;
		int gridWidth = 4;
		int row = 0;
		int blankRow = 0;

		for (int i = 0; i < _puzzle.length; i++)
		{
			if (i % gridWidth == 0)
			{
				row++;
			}
			if (_puzzle[i] == 0)
			{
				blankRow = row;
			}
			for (int j = i + 1; j < _puzzle.length; j++)
			{
				if (_puzzle[i] > _puzzle[j] && _puzzle[j] != 0)
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

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(_puzzle);
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof PuzzleState))
		{
			return false;
		}
		PuzzleState state = (PuzzleState) o;
		return Arrays.equals(_puzzle, state.getArray());
	}

	@Override
	public PuzzleState clone()
	{
		byte[] puzzleClone = new byte[16];

		for (int i = 0; i < _puzzle.length; i++)
		{
			puzzleClone = Arrays.copyOf(_puzzle, _puzzle.length);
		}
		PuzzleState cloneState = new PuzzleState(puzzleClone, _moves + 1, this, _heuristic);
		return cloneState;
	}
	
	public PuzzleState getStartState()
	{
		byte[] puzzleClone = new byte[16];

		for (int i = 0; i < _puzzle.length; i++)
		{
			puzzleClone = Arrays.copyOf(_puzzle, _puzzle.length);
		}
		PuzzleState cloneState = new PuzzleState(puzzleClone, 0, null, _heuristic);
		return cloneState;
	}

	/*
	 * Prints the puzzle on the console
	 */
	public void printPuzzle()
	{
		String line = "";
		for (int i = 0; i < 16; i++)
		{
			line += _puzzle[i] + "\t";
			if ((i + 1) % 4 == 0)
			{
				System.out.println(line + "\n");
				line = "";
			}
		}

		System.out.println("\n");
	}

	/*
	 * Returns cached heuristic
	 */
	public int getHeuristic()
	{
		return _heuristicDistance;
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
		int emptyX = _emptyCell % 4;
		int emptyY = _emptyCell / 4;

		if (dir == moveDirection.up)
		{
			if (emptyY > 0)
			{
				if (_heuristic instanceof FringePattern)
				{
					if (!(emptyY == 1 && ((FringePattern) _heuristic).isFringePattern()))
					{
						return false;
					}
				}

				_puzzle[_emptyCell] = _puzzle[_emptyCell - 4];
				_emptyCell = _emptyCell - 4;
				_puzzle[_emptyCell] = 0;
				return true;
			}
			return false;
		}
		else if (dir == moveDirection.down)
		{
			if (emptyY < 3)
			{
				_puzzle[_emptyCell] = _puzzle[_emptyCell + 4];
				_emptyCell = _emptyCell + 4;
				_puzzle[_emptyCell] = 0;
				return true;
			}
			return false;
		}
		else if (dir == moveDirection.left)
		{
			if (emptyX > 0)
			{
				if (_heuristic instanceof FringePattern)
				{
					if (!(emptyX == 1 && ((FringePattern) _heuristic).isFringePattern()))
					{
						return false;
					}
				}

				_puzzle[_emptyCell] = _puzzle[_emptyCell - 1];
				_emptyCell = _emptyCell - 1;
				_puzzle[_emptyCell] = 0;
				return true;
			}
			return false;
		}
		else if (dir == moveDirection.right)
		{
			if (emptyX < 3)
			{
				_puzzle[_emptyCell] = _puzzle[_emptyCell + 1];
				_emptyCell = _emptyCell + 1;
				_puzzle[_emptyCell] = 0;
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

	public int getEmptyCell()
	{
		for (int i = 0; i < _puzzle.length; i++)
		{
			if (_puzzle[i] == 0)
			{
				return i;
			}
		}

		throw new RuntimeException("Puzzle does not contain an empty cell");
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
}
