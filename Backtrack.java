import java.util.*;

public class Backtrack {

	private Stack<Board> stack;
	private Board board;
	private GUI draw;
	private boolean gui;

	public Backtrack(Board initial, boolean gui) {
		stack = new Stack<Board>();
		board = initial.clone();
		this.gui = gui;
		if(gui) {
			draw = new GUI(board);
		}
	}

	public void Simulation(int heuristic) throws InterruptedException {
		Heuristic h = new Heuristic();
		stack.push(board);
		int[] nextMove = null;
		int count = 0;
		while (!stack.empty()) {
			Board curr = stack.pop();
			count++;
			if(gui) {
				draw.update(curr);
				draw.validate();
				draw.repaint();
				Thread.sleep(100);
			}
			if (!curr.isFull()) {
				nextMove = h.getMove(heuristic,curr);
				curr.setBoard('1', nextMove[0], nextMove[1]);
				if (curr.valid()) {
					stack.push(curr.clone());
				}
				curr.setBoard('0', nextMove[0], nextMove[1]);
				if (curr.valid()) {
					stack.push(curr.clone());
				}

			} else {
				if (curr.valid()) {
					System.out.println("************");
					curr.print();
					System.out.println("number of nodes: "+count);
					break;
				}
			}
		}
	}
	
}