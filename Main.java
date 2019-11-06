import java.io.*;

public class Main{
	
	private static int size;
	protected static Board board;
	
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        
        BufferedReader input = new BufferedReader(new FileReader(args[0]));
        try {
            String line = input.readLine();
            while (line != null) {
                if (line.compareTo("#Start") == 0) {
                    line = input.readLine();
                    String[] token = line.split(" ");
                    size = Integer.parseInt(token[0]);
                    board = new Board(size);
                    for(int i=0; i<size; i++){
                        line = input.readLine();
                        char[]numbers = line.toCharArray();
                        for(int j=0; j<size; j++){
                            board.setBoard(numbers[j], i, j);
                        }
                    }
                }
                line = input.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        board.print();

        boolean gui = false;
        
        if(args[3].compareTo("Gui-on")==0) {
        	gui = true;
        }
        
        if(args[1].compareTo("BS")==0) {
        	long start = System.currentTimeMillis();
        	Backtrack bs = new Backtrack(board,gui);
        	bs.Simulation(Integer.parseInt(args[2]));
        	long finish = System.currentTimeMillis();
        	System.out.println((float)(finish-start)/1000);
        }else if (args[1].compareTo("FC")==0){
        	long start = System.currentTimeMillis();
        	Forward_checking fc = new Forward_checking(board,gui);
        	fc.simulation(Integer.parseInt(args[2]));
        	long finish = System.currentTimeMillis();
        	System.out.println((float)(finish-start)/1000);
        }else {
        	System.out.println("The form is java Filename BS/FC 1/2/3 Gui-on/Gui-off");
        }
        
    }
 
    
}
