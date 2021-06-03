package minimax;

import java.util.ArrayList;
import java.util.Random;

import ticTacToe.TicTacToe;

public class Minimax {
	
	TicTacToe ttt;
	
	/**
	 * returns the best state for the AI to choose
	 * 
	 * @param state of the board
	 * @param player current player
	 * @param ttt instance of TicTacToe game
	 * @return best possible next state
	 */
	public int getTurn(int state, int player, TicTacToe ttt) {
		int bestState = bestMove(state, player, ttt)[0];
		return bestState;
	}
	
	/**
	 * returns the player that has the next turn
	 * 
	 * @param player whose turn it is
	 * @return other player
	 */
	public int other(int player) {
		if(player == 0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	/**
	 * calculates the value of a given state.
	 * <p>
	 * Example: the return value for the following board state is 1 because there's at least one possible turn that leads to victory
	 *    O  |  O  |   
	 *  -----------------
	 *	  X  |     |   
	 *  -----------------
	 *	     |  X  |   
	 * 
	 * @param state of the board
	 * @param player whose value shall be calculated
	 * @param ttt instance of TicTacToe game
	 * @return value for the given state
	 */
	private int value(int state, int player, TicTacToe ttt) {
		ArrayList<Integer> nextStates = new ArrayList<Integer>();
		if(ttt.finished(state)) {
			return ttt.utility(state, player);
		}
		nextStates = ttt.nextStates(state, player);
		int maxValue = -2;
		for(int i = 0; i < nextStates.size(); i++) {
			int val = -value(nextStates.get(i), other(player), ttt);
			if(val > maxValue) {
				maxValue = val;
			}
		}
		return maxValue;
	}
	
	/**
	 * calculates the best possible move for a given state and player
	 * <p>
	 * @param state of the board
	 * @param player whose move shall be calculated
	 * @param ttt instance of TicTacToe game
	 * @return best next state and value of the state
	 */
	private int[] bestMove(int state, int player, TicTacToe ttt) {
		Random r = new Random();
		ArrayList<Integer> nextStates = ttt.nextStates(state, player);
		int bestVal = value(state, player, ttt);
		ArrayList<Integer> bestMoves = new ArrayList<Integer>();
		for(int i = 0; i < nextStates.size(); i++) {
			int nextVal = -value(nextStates.get(i), other(player), ttt);
			if(nextVal == bestVal) {
				bestMoves.add(nextStates.get(i));
			}
		}
		int bestState = bestMoves.get(r.nextInt(bestMoves.size()));
		int[] returnValue = {bestState, bestVal};
		return returnValue;
	}
	
}
