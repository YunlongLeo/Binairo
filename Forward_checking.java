import java.util.ArrayList;
import java.util.Stack;

public class Forward_checking {
    private Board board;
    private Stack<Board> stack;
    private GUI draw;
    private boolean gui;

    public Forward_checking(Board board, boolean gui){
        this.board = board.clone();
        stack = new Stack<Board>();
        this.gui = gui;
		if(gui) {
			draw = new GUI(board);
		}
    }

    public void simulation(int heuristic) throws InterruptedException {
        checkInitialVariables();
        Heuristic h = new Heuristic();
        stack.push(board.clone());
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
                if(curr.getBoard()[nextMove[0]][nextMove[1]].getVariable().size() == 1){
                    curr.setBoard(curr.getBoard()[nextMove[0]][nextMove[1]].getVariable().get(0),nextMove[0],nextMove[1]);
                    updateVariable(curr,nextMove);
                    stack.push(curr);
                }else {
                    Board curr1 = curr.clone();
                    curr1.setBoard('1', nextMove[0], nextMove[1]);
                    if (curr1.valid()) {
                        if (updateVariable(curr1, nextMove)) {
                            stack.push(curr1.clone());
                        }
                    }
                    curr.setBoard('0', nextMove[0], nextMove[1]);
                    if (curr.valid()) {
                        if (updateVariable(curr, nextMove)) {
                            stack.push(curr.clone());
                        }
                    }
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

    private boolean checkVariables(Board curr) {
        Node[][] board = curr.getBoard();
        for (int i = 0; i < curr.getSize(); i++) {
            for (int j = 0; j < curr.getSize(); j++) {
                if(board[i][j].getVariable().size() == 0){
                    return false;
                }
            }
        }
        if(!curr.Constraint2()||!curr.Constraint4()) return false;
        return true;
    }

    private void checkInitialVariables() {
        Board newBoard = board.clone();

        for(int i=0; i<board.getSize(); i++){
            for(int j=0; j<board.getSize(); j++){
                if (newBoard.getBoard()[i][j].getValue() == '_'){
                    newBoard.setBoard('0',i,j);
                    if(!newBoard.Constraint3())
                    {board.getBoard()[i][j].getVariable().remove(0);}
                    newBoard.setBoard('1',i,j);
                    if(!newBoard.Constraint3()) board.getBoard()[i][j].getVariable().remove(1);
                    newBoard.setBoard('_',i,j);
                }
            }
        }
    }

    private boolean updateVariable(Board curr, int[] nextMove) {
        ArrayList<int[]> needToCheck = new ArrayList<>();
        Board b = curr.clone();
        addNeighbour(curr,nextMove[0],nextMove[1],needToCheck);
        while (needToCheck.size()!=0) {
            int[] move = needToCheck.remove(0);
            if(checkNode(move[0], move[1], curr, b)){
                if(!checkVariables(b)) return false;
                addNeighbour(curr,move[0],move[1],needToCheck);
            }
        }
        return  true;
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
