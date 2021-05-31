import java.util.Scanner;

import minimax.Minimax;
import ticTacToe.TicTacToe;

public class Main {

	// Scanner for inputs
	private Scanner sc;
	private TicTacToe game;
	private Minimax minimax;
	
	private int state = 0;

	public static void main(String[] args) {
		new Main().run();
	}

	/**
	 * Starts the whole program
	 */
	private void run() {
		// initialize Scanner for inputs
		sc = new Scanner(System.in);
		minimax = new Minimax();

		boolean running = true;
		while (running) {
			game = new TicTacToe(state);
			do {
				nextMove();
			} while (game.getGameStatus() == TicTacToe.Status.RUNNING);
			gameEnded();
			running = anotherGameInput();
		}
		sc.close();
		System.out.println("End of program");
	}

	/**
	 * Gets a user input and converts it to lower case
	 * 
	 * @return lowercase user input
	 */
	private String getUserInput() {
		return sc.next().toLowerCase();
	}

	/**
	 * Getting and processing the next move of a player
	 */
	private void nextMove() {
		int[] move;
		if (game.getTurn()) {
			// Player (O) turn
			boolean validTurn = false;
			do {
				move = turnInput();
				state = game.setMarker(state, 0, move[0], move[1]);
				//if (!validTurn)
				//	System.out.println("Invalid move.");
			} while (!validTurn);
		} else {
			// Computer (X) turn -> Minimax
			System.out.println("The computer is planning its next move...");
			int nextState;
			do { // loop not needed when minimax doesn't do wrong turns
				move = minimax.getTurn();
				nextState = game.setMarker(state, 1, move[0], move[1]);
			} while (state == nextState);
			state = nextState;
		}
	}

	/**
	 * Gets the input of the turn the player wants to make and repeats it until the
	 * input is valid
	 * 
	 * @return
	 */
	private int[] turnInput() {
		boolean valid = false;
		int[] move = new int[2];
		String turn;
		String[] turnSplit;
		do {
			System.out.print("Type your turn in the format \"row,col\": ");
			turn = getUserInput();
			turnSplit = turn.split(",");
			try {
				move[0] = Integer.parseInt(turnSplit[0]);
				move[1] = Integer.parseInt(turnSplit[1]);
				valid = true;
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				System.out.println("Invalid number.");
			}
		} while (!valid);
		return move;
	}

	/**
	 * Shows the game result after the game ended
	 */
	private void gameEnded() {
		switch (game.getGameStatus()) {
		case PLAYER_X_WON:
			System.out.println("Congratulations. You beat the algorithm");
			break;
		case PLAYER_O_WON:
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
			input = getUserInput();
		} while (!input.equals("y") && !input.equals("n"));
		return input.equals("y") ? true : false;
	}
}
