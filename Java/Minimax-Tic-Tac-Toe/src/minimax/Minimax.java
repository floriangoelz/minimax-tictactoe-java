package minimax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ticTacToe.TicTacToe;

public class Minimax {

	private Map<Integer, Integer> cache = new HashMap<>();
	private Random r = new Random();

	/**
	 * returns the best state for the AI to choose
	 * 
	 * @param state  of the board
	 * @param player current player
	 * @return best possible next state
	 */
	public int getTurn(int state, int player) {
		int bestState = bestMove(state, player)[0];
		return bestState;
	}

	/**
	 * returns the player that has the next turn
	 * 
	 * @param player whose turn it is
	 * @return other player
	 */
	private int other(int player) {
		if (player == 0)
			return 1;
		return 0;
	}

	/**
	 * calculates the value of a given state.
	 * <p>
	 * Example: the return value for the following board state is 1 because there's
	 * at least one possible turn that leads to victory
	 * 
	 * TODO edit
	 * 
	 * @param state  of the board
	 * @param player whose value shall be calculated
	 * @return value for the given state
	 */
	public int value(int state, int player) { // public for performance test
		ArrayList<Integer> nextStates = new ArrayList<>();
		if (TicTacToe.finished(state)) {
			return TicTacToe.utility(state, player);
		}
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
			if (val > maxValue) {
				maxValue = val;
			}
		}
		return maxValue;
	}

	/**
	 * calculates the best possible move for a given state and player
	 * <p>
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
			if (currentVal == bestVal) {
				bestMoves.add(currentState);
			}
		}
		int bestState = bestMoves.get(r.nextInt(bestMoves.size()));
		int[] returnValue = { bestState, bestVal };
		return returnValue;
	}

}
