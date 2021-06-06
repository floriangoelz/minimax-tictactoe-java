import java.util.Scanner;

import minimax.Minimax;
import ticTacToe.TicTacToe;

public class Main {

	// Runtime object for memory calculation
	private static Runtime runtime = Runtime.getRuntime();

	// Scanner for inputs
	private Scanner sc;
	// Instance of the game
	private TicTacToe game;
	// Instance of the algorithm
	private Minimax minimax;

	public static void main(String[] args) {
		new Main().run();
	}

	/**
	 * Starts the whole program
	 */
	private void run() {
		minimax = new Minimax(); // initialize minimax algorithm
		checkPerformance(); // and test its performance for comparison

		// initialize Scanner for inputs
		sc = new Scanner(System.in);
		boolean running = true;
		while (running) {
			game = new TicTacToe();
			while (game.getGameStatus() == TicTacToe.Status.RUNNING) {
				// draw the current game status
				drawGame(game);
				nextMove();
			}
			// draw the final field
			drawGame(game);
			gameEnded();
			running = anotherGameInput();
		}
		sc.close();
		System.out.println("End of program");
	}

	/**
	 * Function to print the estimated memory usage of the minimax value(state,
	 * player) function as well as the time needed to execute it (worst case, empty
	 * field)
	 */
	private void checkPerformance() {
		long memory = runtime.freeMemory();
		long time = System.currentTimeMillis();
		minimax.value(0, 0);
		System.out.println("Memory: " + (memory - runtime.freeMemory()) / 1024 + " KB");
		System.out.println("Time: " + (System.currentTimeMillis() - time) + " ms");
	}

	/**
	 * Gets a user input and converts it to lower case
	 * 
	 * @return lower case user input
	 */
	private String getLowerCaseUserInput() {
		return sc.next().toLowerCase();
	}

	/**
	 * Getting and processing the next move of a player
	 */
	private void nextMove() {
		if (game.getTurn() == 0) {
			// Players (O) turn
			int[] move;
			boolean validMove = false;
			do {
				move = moveInput();
				validMove = game.setMarker(0, move[0], move[1]);
				if (!validMove)
					System.out.println("Invalid move.");
			} while (!validMove);
		} else {
			// Computers (X) turn -> Minimax
			System.out.println("The computer is planning its next move...");
			game.setMarkerState(minimax.getMove(game.getField(), 1));
		}
	}

	/**
	 * Gets the input of the move the player wants to make and repeats it until the
	 * input is valid
	 * 
	 * @return the move as an array with [row, col]
	 */
	private int[] moveInput() {
		boolean valid = false;
		int[] move = new int[2];
		String turn;
		String[] turnSplit;
		do {
			System.out.print("Type your turn in the format \"row,col\": ");
			turn = getLowerCaseUserInput();
			turnSplit = turn.split(",");
			try {
				move[0] = Integer.parseInt(turnSplit[0]);
				move[1] = Integer.parseInt(turnSplit[1]);
				valid = true;
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				System.out.println("Invalid input.");
			}
		} while (!valid);
		return move;
	}

	/**
	 * Shows the game result after the game ended
	 */
	private void gameEnded() {
		switch (game.getGameStatus()) {
		case PLAYER_O_WON:
			System.out.println("Congratulations. You beat the algorithm");
			break;
		case PLAYER_X_WON:
			System.out.println("You lost against the computer");
			break;
		case DRAW:
			System.out.println("You ended up in a draw state");
			break;
		default:
			break;
		}
	}

	/**
	 * Asks for the input to determine whether another game will start and repeats
	 * until the input is valid
	 * 
	 * @return true if another game shall start, otherwise false
	 */
	private boolean anotherGameInput() {
		String input;
		do {
			System.out.print("Do you want to play another round (y,n)? Let us know: ");
			input = getLowerCaseUserInput();
		} while (!input.equals("y") && !input.equals("n"));
		return input.equals("y") ? true : false;
	}

	/**
	 * Draws the current state of the given game to the console window
	 * 
	 * @param game the state shall be drawn of
	 */
	private void drawGame(TicTacToe game) {
		final String HORIZONTAL = "\n-----------------\n  ";
		String print = "\n  ";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				print += game.getSymbol(i, j);
				if (j != 2)
					print += "  |  ";
			}
			if (i != 2)
				print += HORIZONTAL;
		}
		System.out.println(print + "\n");
	}
}
