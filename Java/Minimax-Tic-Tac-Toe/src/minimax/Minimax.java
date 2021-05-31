package minimax;

import java.util.Random;

public class Minimax {

	
	public int[] getTurn() {
		// TODO for now returns random turn
		Random r = new Random();
		return new int[] { r.nextInt(3), r.nextInt(3) };
	}
	
	//private int value(int state, int player) {
		
	//}
}
