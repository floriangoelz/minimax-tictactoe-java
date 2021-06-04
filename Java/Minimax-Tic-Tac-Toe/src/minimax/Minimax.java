package minimax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ticTacToe.TicTacToe;

public class Minimax {

	// -----------------------------------------
	// Variables with static context
	// -----------------------------------------

	// Random generator
	private static Random r = new Random();

	// -----------------------------------------
	// Instance variables
	// -----------------------------------------

	// cache for storing values of states
	private Map<Integer, Integer> cache = new HashMap<>();

	// -----------------------------------------
	// Instance-Methods
	// -----------------------------------------

	/**
	 * Returns the best state for the AI to choose
	 * 
	 * @param state  of the board
	 * @param player who's move shall be determined
	 * @return best possible next state
	 */
	public int getMove(int state, int player) {
		int bestState = bestMove(state, player)[0];
		return bestState;
	}

	/**
	 * Returns the opposite player
	 * <p>
	 * If the given player is 0 returns 1, otherwise returns 0
	 * 
	 * @param player any player (0 or 1)
	 * @return the other player
	 */
	private int other(int player) {
		return player == 0 ? 1 : 0;
	}

	/**
	 * Calculates the value of a given state.
	 * <p>
	 * The more possible moves lead to victory the higher is the value of a state
	 * 
	 * @param state  of the board
	 * @param player whose value shall be calculated
	 * @return value of the given state
	 */
	public int value(int state, int player) { // public for performance test
		ArrayList<Integer> nextStates = new ArrayList<>();
		if (TicTacToe.finished(state))
			return TicTacToe.utility(state, player);
		nextStates = TicTacToe.nextStates(state, player);
		int maxValue = -2;
		int currentState;
		Integer val;
		for (int i = 0; i < nextStates.size(); i++) {
			currentState = nextStates.get(i);
			val = cache.get(currentState);
			if (val == null) {
				val = -value(currentState, other(player));
				cache.put(currentState, val);
			}
			if (val > maxValue)
				maxValue = val;
		}
		return maxValue;
	}

	/**
	 * Calculates the best possible move for a given state and player
	 * 
	 * @param state  of the board
	 * @param player whose move shall be calculated
	 * @return best next state and value of the state
	 */
	private int[] bestMove(int state, int player) {
		ArrayList<Integer> nextStates = TicTacToe.nextStates(state, player);
		ArrayList<Integer> bestMoves = new ArrayList<>();
		int bestVal = value(state, player);
		int currentState;
		int currentVal;
		for (int i = 0; i < nextStates.size(); i++) {
			currentState = nextStates.get(i);
			currentVal = -value(currentState, other(player));
			if (currentVal == bestVal)
				bestMoves.add(currentState);
			cache.clear(); // clear the cache before the next state
		}
		int bestState = bestMoves.get(r.nextInt(bestMoves.size()));
		int[] returnValue = { bestState, bestVal };
		return returnValue;
	}

}
