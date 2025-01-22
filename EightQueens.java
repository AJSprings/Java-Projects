import java.util.*;
public class EightQueens {
	
	final private int[][] board = new int[8][8];
	final private int[][] boardTemp = new int[8][8];
	private int numQueens = 0;
	private int heuristic = 0;
	private int restarts = 0;
	private int moves = 0;
	private boolean restartBoard = true;
	private int neighbors = 8;
	
	
	public EightQueens( ){ //initializes board
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = 0;
            }
        }
    }
  
  
    public void randomizeBoard( ){ //randomizes board
      Random randBoard = new Random( );
 
      
      while(numQueens < 8){
            for(int i = 0; i < 8; i++){
                board[randBoard.nextInt(7)][i] = 1;
                numQueens++;
                }
            }
      heuristic = Heuristic(board);
    }
  
  
    public boolean findRowConflict(int [][] test, int a){ //finds row conflicts
        boolean rowFound = false;
        int count = 0;
      
        for(int i = 0; i < 8; i++){
            if(test[i][a] == 1){
                count++;
            }
        }
        if(count > 1){
            rowFound = true;
        }
        return rowFound;
    }
    public boolean findColConflict(int [][] test, int j){ //finds column conflicts
        boolean colFound = false;
        int count = 0;
        for(int i = 0; i < 8; i++){
            if(test[j][i] == 1){
                count++;
            }
        }
        if(count > 1){
          colFound = true;
        }
        return colFound;
    }
  
    public boolean findDiagConflict(int [][] test, int a, int b){//finds diagonal conflicts
        boolean diaFound = false;
      
        for(int i = 1; i < 8; i++){
            if(diaFound){
                break;
            }

            if((a+i < 8)&&(b+i < 8)){
                if(test[a+i][b+i] == 1){
                    diaFound = true;
                }
            }
            if((a-i >= 0)&&(b-i >= 0)){
                if(test[a-i][b-i] == 1){
                    diaFound = true;
                }
            }
            if((a+i < 8)&&(b-i >= 0)){
                if(test[a+i][b-i] == 1){
                    diaFound = true;
                }
            }  
            if((a-i >= 0)&&(b+i < 8)){
                if(test[a-i][b+i] == 1){
                    diaFound = true;
                }
            }  
        }
        return diaFound;
    }
  
    public int Heuristic(int [][] test){//Counts the number of queens in conflict
    int count = 0;
    boolean rowCon;
    boolean colCon;
    boolean diaCon;
      
        for(int i = 0; i < 8; i++){
            for(int j= 0; j < 8; j++){
                if(test[i][j] == 1){
                    rowCon = findRowConflict(test, j);
                    colCon = findColConflict(test, i);
                    diaCon = findDiagConflict(test, i, j);
                  
                    if(rowCon || colCon || diaCon){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
    public void moveQueen( ){ //moves a queen and sees if it should continue to a new state or restart or to detail solution
        int[][] arrBoard = new int[8][8];
        int colCount;
        int minCol;
        int minRow;
        int prevColQueen = 0;
        restartBoard = false;
      
        while(true){
            colCount = 0;
      
            for(int i = 0; i < 8; i++){
                System.arraycopy(board[i], 0, boardTemp[i], 0, 8);
            }
            while(colCount < 8){
                for(int i = 0; i < 8;i++){
                    boardTemp[i][colCount] = 0;
                }
                for(int i = 0; i < 8; i++){
                    if(board[i][colCount] == 1){
                        prevColQueen = i;
                    }
                    boardTemp[i][colCount] = 1;
                    arrBoard[i][colCount] = Heuristic(boardTemp);
                    boardTemp[i][colCount] = 0;
                }
                boardTemp[prevColQueen][colCount] = 1;
                colCount++;
            }
          
            if(determineRestart(arrBoard)){
                numQueens = 0;
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        board[i][j] = 0;
                    }
                }
                randomizeBoard( );
                System.out.println("Restarting");
                restarts++;
            }
      
            minCol = findMinCol(arrBoard);
            minRow = findMinRow(arrBoard);
      
            for(int i = 0; i < 8; i++){
                board[i][minCol] = 0;
            }
      
            board[minRow][minCol] = 1;
            moves++;
            heuristic = Heuristic(board);
          
            if(Heuristic(board) == 0){
                System.out.println("\nCurrent State");
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        System.out.print(board[i][j] + " ");
                    }
                    System.out.print("\n");
                }
            System.out.println("Solution Found!");
            System.out.println("State changes: " + moves);
            System.out.println("Restarts: " + restarts);
            break;
            }

            System.out.println("\n");
            System.out.println("Current h: " + heuristic);
            System.out.println("Current State");
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    System.out.print(board[i][j] + " ");
                }
                System.out.print("\n");
            }
            System.out.println("Neighbors found with lower h: " + neighbors);
            System.out.println("Setting new current State");
        }
    }
    public int findMinCol(int[][] test){ //finds column of lowest neighbor state
        int minCol = 8;
        int minVal = 8;
        int count = 0;
      
        for(int i = 0; i < 8; i++){
          for(int j = 0; j < 8; j++){
              if(test[i][j] < minVal){
                  minVal = test[i][j];
                  minCol = j;
              }
              if(test[i][j] < heuristic){
                  count++;
              }
          }
        }
        neighbors = count;
        return minCol;
    }
    public int findMinRow(int[][] test){ //finds row of lowest neighbor state
        int minRow = 8;
        int minVal = 8;
      
        for(int i = 0; i < 8; i++){
          for(int j = 0; j < 8; j++){
              if(test[i][j] < minVal){
                  minVal = test[i][j];
                  minRow = i;
              }
          }
        }
        return minRow;
    }
    public boolean determineRestart(int [][] test){// sees if  restart is needed
        int minVal = 8;
        boolean restart = false;
      
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(test[i][j] < minVal){
                    minVal = test[i][j];
                }
            }
        }
        if(neighbors == 0){
            restart = true;
        }
        return restart;
    }
	
	

	public static void main(String[] args) {
		
		
		EightQueens test = new EightQueens();
		test.randomizeBoard();
		test.moveQueen();
		
		
		
		

	}
}