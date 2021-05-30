package ticTacToe;

public class TicTacToe {

	private static final int[] WINNING_STATES = new int[8];

	private int field = 1;
	private int[] players = new int[] { 0, 1 };
	private char[] symbols = new char[] { 'X', 'O' };
	private boolean turn = true;

	// debug
	public TicTacToe() {
		initializeWinningStates();
		draw();
	}

	private void initializeWinningStates() {
		WINNING_STATES[0] = setBits(new int[] { 0, 1, 2 });
		WINNING_STATES[1] = setBits(new int[] { 3, 4, 5 });
		WINNING_STATES[2] = setBits(new int[] { 6, 7, 8 });
		WINNING_STATES[3] = setBits(new int[] { 0, 3, 6 });
		WINNING_STATES[4] = setBits(new int[] { 1, 4, 7 });
		WINNING_STATES[5] = setBits(new int[] { 2, 5, 8 });
		WINNING_STATES[6] = setBits(new int[] { 0, 4, 8 });
		WINNING_STATES[7] = setBits(new int[] { 2, 4, 6 });
	}

	private int setBit(int state, int bitToSet) {
		int newField = state;
		newField |= 1 << bitToSet;
		return newField;
	}

	// TODO performance foreach test
	private int setBits(int[] array) {
		int state = 0;
		for (int i = 0; i < array.length; i++) {
			state = setBit(state, array[i]);
		}
		return state;
	}
	
	private hasWon() {
		int current;
		for(int i = 0; i < 8; i++) {
			current = WINNING_STATES[i];
			
		}
	}

	private char getSymbol(int row, int col) {
		int mask = setBit(0, row * 3 + col);
		if ((mask & this.field) == mask) {
			return symbols[0];
		} else if ((mask & (this.field >> 9)) == mask) {
			return symbols[1];
		}
		return ' ';
	}

	private void draw() {
		final String HORIZONTAL = "\n-----------------\n  ";
		String print = "  ";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (j != 2) {
					print += getSymbol(i, j) + "  |  ";
				} else {
					print += getSymbol(i, j);
				}
			}
			if (i != 2) {
				print += HORIZONTAL;
			}
		}
		System.out.println(print);
	}
}
