package ticTacToe;

import java.util.Random;

public class TicTacToe {

	// -----------------------------------------
	// Variables with static context
	// -----------------------------------------

	// all states a player wins the game with
	private static final int[] WINNING_STATES = new int[8];
	// the random instance to determine who starts the game
	private static Random r = new Random();
	// initialize winning states static (for all future games)
	static {
		initializeWinningStates();
	}

	// enum for all possible game states
	public enum Status {
		RUNNING, PLAYER_X_WON, PLAYER_O_WON, DRAW
	}

	// -----------------------------------------
	// Instance variables
	// -----------------------------------------

	// the current state of the game as a bitmask
	private int field = 0;
	// the possible symbols that can be on the board
	private char[] symbols = new char[] { 'O', 'X', ' ' };
	// true if its player 0 (O)s turn, false for player 1 (X)
	private boolean turn;
	// holds the current Status of the game (e.g DRAW)
	private Status gameStatus;

	// -----------------------------------------
	// Constructors
	// -----------------------------------------
	public TicTacToe() {
		// initialize the game as running
		gameStatus = Status.RUNNING;
		// randomly decide who starts the match
		turn = r.nextBoolean();
		// draw the empty board
		drawGame();
	}

	// -----------------------------------------
	// Static methods without instance context
	// -----------------------------------------

	/**
	 * Sets all the winning possibilities into the WINNING_STATES variable
	 */
	private static void initializeWinningStates() {
		WINNING_STATES[0] = setBits(new int[] { 0, 1, 2 });
		WINNING_STATES[1] = setBits(new int[] { 3, 4, 5 });
		WINNING_STATES[2] = setBits(new int[] { 6, 7, 8 });
		WINNING_STATES[3] = setBits(new int[] { 0, 3, 6 });
		WINNING_STATES[4] = setBits(new int[] { 1, 4, 7 });
		WINNING_STATES[5] = setBits(new int[] { 2, 5, 8 });
		WINNING_STATES[6] = setBits(new int[] { 0, 4, 8 });
		WINNING_STATES[7] = setBits(new int[] { 2, 4, 6 });
	}

	/**
	 * Sets a specific bit inside a given state.
	 * <p>
	 * Example: state = 5 (Binary: 101), bitToSet = 1 would return 7 (Binary: 111)
	 * 
	 * @param state a bit shall be set in
	 * @param bit   to set inside the state (0 = least significant bit)
	 * @return the given state with the specified bit set
	 */
	private static int setBit(int state, int bit) {
		return state |= 1 << bit;
	}

	/**
	 * Creates an integer that has all bits specified in the array set to 1
	 * <p>
	 * Example: array = [0,2] would return 5 (Binary: 101)
	 * 
	 * @param array with all bits to set (0 = least significant bit)
	 * @return an integer with all chosen bits set to one
	 */
	private static int setBits(int[] array) {
		int state = 0;
		for (int i = 0; i < array.length; i++) {
			state = setBit(state, array[i]);
		}
		return state;
	}

	// -----------------------------------------
	// Instance-Methods
	// -----------------------------------------

	/**
	 * Function to determine whether a given move is valid by checking whether the
	 * location is inside bounds and not already taken
	 * 
	 * @param row the row to set the marker (0-2)
	 * @param col the column to set the marker (0-2)
	 * @return true if the move is valid, otherwise false
	 */
	private boolean isValidMove(int row, int col) {
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

	/**
	 * Tries to set the players marker on the specified position. If successful this
	 * function returns true, otherwise false
	 * 
	 * @param player whos marker will be placed
	 * @param row    the marker will be placed on (0-2)
	 * @param col    the marker will be placed on (0-2)
	 * @return true if successful, otherwise false
	 */
	public boolean setMarker(int player, int row, int col) {
		if (isValidMove(row, col)) {
			field = setBit(this.field, row * 3 + col + player * 9);
			turn = !turn; // swap turns
			gameStatus = currentGameStatus();
			drawGame();
			return true;
		}
		return false;
	}

	/**
	 * Determines the current status of the game
	 * 
	 * @return the status of the game
	 */
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
		return Status.DRAW; // full field and draw
	}

	/**
	 * Gets the symbol at the given position of the field
	 * <p>
	 * Example: row = 1, col = 1 will return the symbol in the middle
	 * 
	 * @param row the symbol shall be fetched from
	 * @param col the symbol shall be fetched from
	 * @return the symbol at the given position
	 */
	private char getSymbol(int row, int col) {
		int mask = setBit(0, row * 3 + col);
		if ((mask & this.field) == mask) {
			return symbols[0];
		} else if ((mask & (this.field >> 9)) == mask) {
			return symbols[1];
		}
		return symbols[2];
	}

	/**
	 * Draws the current state of the game to the console window
	 */
	private void drawGame() {
		final String HORIZONTAL = "\n-----------------\n  ";
		String print = "\n  ";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				print += getSymbol(i, j);
				if (j != 2)
					print += "  |  ";
			}
			if (i != 2)
				print += HORIZONTAL;
		}
		System.out.println(print + "\n");
	}

	/**
	 * Returns which players turn it is
	 * 
	 * @return true if it's player 0s turn, otherwise false
	 */
	public boolean getTurn() {
		return this.turn;
	}

	/**
	 * Returns the game status (e.g. RUNNING, DRAW)
	 * 
	 * @return the games status
	 */
	public Status getGameStatus() {
		return this.gameStatus;
	}
}
