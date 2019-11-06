public class Board {

	private Node[][] board;
	private int size;
	private Board next;

	public Board(int size) {
		board = new Node[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Node newNode = new Node();
				newNode.setRow(i);
				newNode.setCol(j);
				board[i][j] = newNode;
			}
		}
		this.size = size;
		next = null;
	}

	public Node[][] getBoard() {
		return board;
	}

	public void setBoard(char c, int row, int col) {
		board[row][col].setValue(c);
	}

	public int getSize() {
		return size;
	}

	public Board getNext() {
		return next;
	}

	public void setNext(Board b) {
		next = b;
	}

	public boolean isFull() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j].getValue() == '_') {
					return false;
				}
			}
		}
		return true;
	}

	public boolean valid() {
		return Constraint2() && Constraint3() && Constraint4();
	}

	public boolean Constraint1() {
		return isFull();
	}

	public boolean Constraint2() {
		for (int i = 0; i < size; i++) {
		    if(isRowFull(i)) {
                int zeros = 0;
                int ones = 0;
                for (int j = 0; j < size; j++) {
                    if (board[i][j].getValue() == '0') {
                        zeros++;
                    } else if (board[i][j].getValue() == '1') {
                        ones++;
                    }
                }
                if (zeros != ones) {
                    return false;
                }
            }
		}

		for (int j = 0; j < size; j++) {
		    if(isColFull(j)) {
                int zeros = 0;
                int ones = 0;
                for (int i = 0; i < size; i++) {
                    if (board[i][j].getValue() == '0') {
                        zeros++;
                    } else if (board[i][j].getValue() == '1') {
                        ones++;
                    }
                }
                if (zeros != ones) {
                    return false;
                }
            }
		}

		return true;
	}

	public boolean Constraint3() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j].getValue() != '_') {
					// left top
					if (i < 2 && j < 2) {
						if (sameDown(i, j) || sameRight(i, j)) {
							return false;
						}
					}
					// right top
					else if (i < 2 && j >= size - 2) {
						if (sameDown(i, j) || sameLeft(i, j)) {
							return false;
						}
					}
					// left bottom
					else if (i >= size - 2 && j < 2) {
						if (sameTop(i, j) || sameRight(i, j)) {
							return false;
						}
					}
					// right bottom
					else if (i >= size - 2 && j >= size - 2) {
						if (sameTop(i, j) || sameLeft(i, j)) {
							return false;
						}
					}
					// top
					else if (i < 2) {
						if (sameDown(i, j) || sameLeft(i, j) || sameRight(i, j)) {
							return false;
						}
					}
					// down
					else if (i >= size - 2) {
						if (sameTop(i, j) || sameLeft(i, j) || sameRight(i, j)) {
							return false;
						}
					}
					// left
					else if (j < 2) {
						if (sameTop(i, j) || sameDown(i, j) || sameRight(i, j)) {
							return false;
						}
					}
					// right
					else if (j >= size - 2) {
						if (sameTop(i, j) || sameLeft(i, j) || sameDown(i, j)) {
							return false;
						}
					}
					// middle
					else {
						if (sameTop(i, j) || sameLeft(i, j) || sameRight(i, j) || sameDown(i, j)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public boolean Constraint4() {
		Node[][] cols = allColumn(board);
		for (int i = 0; i < size - 1; i++) {
			// compare with each row
			for (int j = i + 1; j < size; j++) {
				if ((isRowFull(i)&&isRowFull(j)&&equals(board[i], board[j]))||(isColFull(i)&&isColFull(j)&&equals(cols[i],cols[j]))) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean sameTop(int i, int j) {
		return board[i][j].getValue() == board[i - 1][j].getValue()
				&& board[i][j].getValue() == board[i - 2][j].getValue();
	}

	public boolean sameDown(int i, int j) {
		return board[i][j].getValue() == board[i + 1][j].getValue()
				&& board[i][j].getValue() == board[i + 2][j].getValue();
	}

	public boolean sameLeft(int i, int j) {
		return board[i][j].getValue() == board[i][j - 1].getValue()
				&& board[i][j].getValue() == board[i][j - 2].getValue();
	}

	public boolean sameRight(int i, int j) {
		return board[i][j].getValue() == board[i][j + 1].getValue()
				&& board[i][j].getValue() == board[i][j + 2].getValue();
	}

	public boolean equals(Node[] a, Node[] b) {
		for (int i = 0; i < a.length; i++) {
			if (a[i].getValue() != b[i].getValue()) {
				return false;
			}
		}
		return true;
	}

	public boolean isRowFull(int row){
	    for(int i=0; i<size;i++){
	        if(board[row][i].getValue() == '_') return false;
        }
        return true;
    }

    public boolean isColFull(int col){
        for(int i=0; i<size;i++){
            if(board[i][col].getValue() == '_') return false;
        }
        return true;
    }

	public Board clone() {
		Board newBoard = new Board(size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				newBoard.board[i][j].setValue(board[i][j].getValue());
			}
		}
		return newBoard;
	}

	public Node[][] allColumn(Node[][] board) {
		Node[][] result = new Node[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result[i][j] = new Node();
			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result[i][j] = board[j][i];
			}
		}

		return result;
	}

	public void print() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j].toString());
			}
			System.out.println();
		}
	}
}
