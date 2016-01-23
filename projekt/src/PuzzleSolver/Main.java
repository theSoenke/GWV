package PuzzleSolver;

import PuzzleSolver.GUI.PuzzleGUI;

public class Main
{
	private PuzzleGUI _gui;

	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		_gui = new PuzzleGUI();
		_gui.showUI();
	}
}
