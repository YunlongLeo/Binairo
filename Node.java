import java.util.ArrayList;

public class Node {

	private ArrayList<Character> variable;
	private char value;
	private int priority;
	private int row;
	private int col;
	private Node next;

	public Node() {
		variable = new ArrayList<Character>();
		variable.add('0');
		variable.add('1');
		priority = 0;
		next = null;
	}

	public ArrayList<Character> getVariable() {
		return variable;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char c) {
		if (c == '0') {
			value = '0';
			if(variable.size() == 2) variable.remove(1);
		} else if (c == '1') {
			value = '1';
            if(variable.size() == 2) variable.remove(0);
		} else {
			value = '_';
		}
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int p) {
		priority = p;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setCol(int c) {
		col = c;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node n) {
		next = n;
	}

	public String toString() {
		return String.valueOf(value);
	}
}
