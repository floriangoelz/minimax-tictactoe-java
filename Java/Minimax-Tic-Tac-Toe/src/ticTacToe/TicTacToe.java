package ticTacToe;

import java.util.Random;

public class TicTacToe {

	private static final int[] WINNING_STATES = new int[8];

	private int field = 0;
	private char[] symbols = new char[] { 'X', 'O' };
	private boolean turn = new Random().nextBoolean();
	private Status gameStatus;

	public enum Status {
		RUNNING, PLAYER_X_WON, PLAYER_O_WON, DRAW
	}

	public TicTacToe() {
		initializeWinningStates();
		gameStatus = Status.RUNNING;
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

	// TODO logic
	private boolean isValidMove(int player, int row, int col) {
		if (row < 0 || row > 2 || col < 0 || col > 2) { // out of bounds
			return false;
		}
		if (setBit(this.field, row * 3 + col) == this.field) { // already placed by player 0
			return false;
		}
		if (setBit(this.field, row * 3 + col + 9) == this.field) { // already placed by player 1
			return false;
		}
		return true;
	}

	public boolean doMove(int player, int row, int col) {
		if (isValidMove(player, row, col)) {
			field = setBit(this.field, row * 3 + col + player * 9);
			turn = !turn; // other players turn
			gameStatus = currentGameStatus();
			draw();
			return true;
		}
		return false;
	}

	// TODO performance foreach test
	private int setBits(int[] array) {
		int state = 0;
		for (int i = 0; i < array.length; i++) {
			state = setBit(state, array[i]);
		}
		return state;
	}

	private Status currentGameStatus() {
		int current;
		for (int i = 0; i < 8; i++) {
			current = WINNING_STATES[i];
			if ((field & current) == current) {
				return Status.PLAYER_X_WON; // player X has won
			}
			if (((field >> 9) & current) == current) {
				return Status.PLAYER_O_WON; // player O has won
			}
		}
		if (((field & 511) | (field >> 9)) != 511) { // board isn't full
			return Status.RUNNING;
		}
		return Status.DRAW; // draw
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

	public boolean getTurn() {
		return this.turn;
	}

	public Status getGameStatus() {
		return gameStatus;
	}
}
