import java.io.IOException;
import java.util.Random;

/**
 * Created by deepak on 4/05/17.
 */
public class GameOfLife {

    private int size; //size of the grid
    private Boolean[][] grid;  //true => alive false => dead
    private int genNum = 0;

    public GameOfLife(int size){
        if(size <=0) throw new IllegalArgumentException("Grid size can't be <= 0. Given grid size: " + size);
        this.size = size;
        grid = new Boolean[size][size];
    }

    public void startGameWithRandomInput(){
        ++genNum;
        Random random = new Random();
        for(int i = 0; i<size; ++i){
            for(int j = 0; j<size; ++j){
                grid[i][j] = random.nextBoolean();
            }
        }
    }

    public void goToNextGeneration(){
        ++genNum;
        Boolean[][] nextgen =  new Boolean[size][size];
        for(int i = 0; i<size; ++i){
            for(int j = 0; j<size; ++j){
                int aliveNeighbors = getAliveNeighborsForCell(i, j);
                if(aliveNeighbors < 2 || aliveNeighbors > 3){
                    nextgen[i][j] = false;
                }
                if(aliveNeighbors == 2){
                    nextgen[i][j] = grid[i][j];
                }
                if(aliveNeighbors == 3){
                    nextgen[i][j] = true;
                }
            }
        }
        grid = nextgen;
    }

    public void startGame(){
        startGameWithRandomInput();
        while(true){
            displayGrid();
            System.out.println("Please press Enter to go to next generation. Press Ctrl+C to exit");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace(); // todo -> handle excpetion properly
            }
            goToNextGeneration();
        }
    }

    public void displayGrid(){
        System.out.println("Current Generation: " + genNum);
        for(int i = 0; i<size; ++i){
            for(int j = 0; j<size; ++j){
                if(grid[i][j]) {
                    System.out.print("A ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private int getAliveNeighborsForCell(int row, int col) {
        int aliveNeighbors = 0;
        if(isValidInput(row-1, col-1) && grid[row-1][col-1]) ++aliveNeighbors;
        if(isValidInput(row-1, col) && grid[row-1][col]) ++aliveNeighbors;
        if(isValidInput(row-1, col+1) && grid[row-1][col+1]) ++aliveNeighbors;
        if(isValidInput(row, col-1) && grid[row][col-1]) ++aliveNeighbors;
        if(isValidInput(row, col+1) && grid[row][col+1]) ++aliveNeighbors;
        if(isValidInput(row+1, col-1) && grid[row+1][col-1]) ++aliveNeighbors;
        if(isValidInput(row+1, col) && grid[row+1][col]) ++aliveNeighbors;
        if(isValidInput(row+1, col+1) && grid[row+1][col+1]) ++aliveNeighbors;
        return aliveNeighbors;
    }

    private boolean isValidInput(int row, int col) {
        if(row<0 || col<0 || row>=size || col>=size) return false;
        return true;
    }

    public static void main(String[] args){
        int size = 7;
        if(args != null && args.length > 0){
            size = Integer.parseInt(args[0]);
        }
        GameOfLife game = new GameOfLife(size);
        game.startGame();
    }



}
