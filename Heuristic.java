import java.util.*;

public class Heuristic{
	
	public Heuristic() {
		
	}
	
	public int[] getMove(int heuristic, Board curr) {
		if(heuristic==1) {
			return heuristic1(curr);
		}else if(heuristic==2) {
			return heuristic2(curr);
		}else {
			return heuristic3(curr);
		}
	}
	
	public int[] heuristic1(Board curr) {
		int[] result = new int[2];
		int max = curr.getSize();
		Random rand = new Random();
		int row = rand.nextInt(max);
		int col = rand.nextInt(max);
		while (curr.getBoard()[row][col].getValue() != '_') {
			row = rand.nextInt(max);
			col = rand.nextInt(max);
		}
		result[0] = row;
		result[1] = col;
		return result;
	}
	
	public int[] heuristic2(Board curr) {
		int[] result = new int[2];
		PriorityQueue allMoves = new PriorityQueue();
		for (int i = 0; i < curr.getSize(); i++) {
			for (int j = 0; j < curr.getSize(); j++) {
				Node currNode = curr.getBoard()[i][j];
				if (currNode.getValue() == '_') {
					Board check0 = curr.clone();
					check0.setBoard('0', i, j);
					Board check1 = curr.clone();
					check1.setBoard('1', i, j);
					if (check0.Constraint3() && check1.Constraint3()) {
						currNode.setPriority(2);
					} else {
						currNode.setPriority(1);
					}
					allMoves.enQueue(currNode);
				}
			}
		}
		Node first = allMoves.deQueue();
		if (first != null) {
			result[0] = first.getRow();
			result[1] = first.getCol();
		}
		return result;
	}
	
	public int[] heuristic3(Board curr) {
		int[] result = new int[2];
		Node maxNode = null;
		int maxCount = 0;
		for(int i=0;i<curr.getSize();i++) {
			for(int j=0;j<curr.getSize();j++) {
				Node currNode = curr.getBoard()[i][j];
				if(currNode.getValue()=='_') {
					Board check0 = curr.clone();
					check0.setBoard('0',i,j);
					Board check1 = curr.clone();
					check1.setBoard('1',i,j);
					int count0 = constraintNodeNumber(check0,i,j);
					int count1 = constraintNodeNumber(check1,i,j);
					if(count0>=maxCount||count1>=maxCount) {
						maxNode = currNode;
						if(count0>count1) {
							maxCount=count0;
						}else {
							maxCount=count1;
						}
					}
				}
			}
		}
		if(maxNode!=null) {
			result[0] = maxNode.getRow();
			result[1] = maxNode.getCol();
		}
		return result;
	}
	

	
	private int constraintNodeNumber(Board curr, int i, int j) {
		int count = 0;
        ArrayList<int[]> needToCheck = new ArrayList<>();
        Board b = curr.clone();
        addNeighbour(curr,i,j,needToCheck);
        while (needToCheck.size()!=0) {
            int[] move = needToCheck.remove(0);
            if(checkNode(move[0], move[1], curr, b)){
            	count++;
                addNeighbour(curr,move[0],move[1],needToCheck);
            }
        }
        return count;
    }

    private boolean checkNode(int row, int col, Board curr, Board b) {
        boolean removed = false;
        Node currN = b.getBoard()[row][col];
        if(currN.getValue()=='_') {
            if (currN.getVariable().size() == 1) {
                currN.setValue(currN.getVariable().get(0));
                if(!b.valid()){
                    currN.setValue('_');
                    currN.getVariable().remove(0);
                    curr.getBoard()[row][col].getVariable().remove(0);
                    removed = true;
                }
            } else if(currN.getVariable().size() == 2){
                currN.setValue('0');
                currN.getVariable().add(1,'1');
                if(!b.valid()){
                    currN.getVariable().remove(0);
                    curr.getBoard()[row][col].getVariable().remove(0);
                    removed = true;
                }
                currN.setValue('_');

                currN.setValue('1');
                if(!removed) currN.getVariable().add(0,'0');
                if(!b.valid()){
                    if(currN.getVariable().size() == 1){
                        currN.getVariable().remove(0);
                        curr.getBoard()[row][col].getVariable().remove(0);
                    }else{
                        currN.getVariable().remove(1);
                        curr.getBoard()[row][col].getVariable().remove(1);
                    }
                    removed = true;
                }
                currN.setValue('_');

                if(currN.getVariable().size() == 1) {
                    currN.setValue(currN.getVariable().get(0));
                }
            }
        }
        return removed;
    }

    private void addNeighbour(Board newBoard,int row, int col, ArrayList<int[]> needToCheck) {
        for(int i=0; i<newBoard.getSize(); i++){
            if(i!=row){
                int[] move  = new int [2];
                move[0] = row;
                move[1] = i;
                needToCheck.add(move);
            }
        }
        for(int i=0; i<newBoard.getSize(); i++){
            if(i!=col){
                int[] move  = new int [2];
                move[0] = i;
                move[1] = col;
                needToCheck.add(move);
            }
        }
    }

}
