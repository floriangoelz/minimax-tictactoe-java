import java.util.Scanner;

import minimax.Minimax;
import ticTacToe.TicTacToe;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TicTacToe game;
		Minimax minimax = new Minimax();
		String again;
		int row = 0;
		int col = 0;
		int[] move;
		boolean running = true;
		boolean validTurn;
		while (running) {
			game = new TicTacToe();
			do {
				if (game.getTurn()) {
					// Player (X) turn
					do {
						System.out.print("Type your turn in the format \"row,col\": ");
						String turn = sc.next();
						String[] turnSplit = turn.split(",");
						try {
							row = Integer.parseInt(turnSplit[0]);
							col = Integer.parseInt(turnSplit[1]);
						} catch (NumberFormatException | IndexOutOfBoundsException e) {
							System.out.println("Invalid number.");
							validTurn = false;
						}
						if (game.doMove(0, row, col)) {
							validTurn = true;
						} else {
							System.out.println("Invalid move.");
							validTurn = false;
						}
					} while (!validTurn);
				} else {
					// Computer (O) turn -> Minimax
					System.out.println("The computer is planning its next move...");
					do { // loop not needed when minimax doesn't do wrong turns
						move = minimax.getTurn();
					} while (!game.doMove(1, move[0], move[1]));
				}
			} while (game.getGameStatus() == TicTacToe.Status.RUNNING);
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
			do {
				System.out.print("You want to play another round (y,n)? Let us know: ");
				again = sc.next().toLowerCase();
			} while (!again.equals("y") && !again.equals("n"));
			if (again.equals("n")) {
				running = false;
			}
		}
		sc.close();
		System.out.println("Program ended.");
	}

}
