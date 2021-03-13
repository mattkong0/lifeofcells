/**
 * Name: Matthew Kong
 * ID: A16660796
 * Email: mkong@ucsd.edu
 * Sources used: Piazza, tutors
 * 
 * This file contains all of the cells growing on the petridish.
 * This file fills the petridish with a variety of cells.
 */

import java.util.List;
import java.util.ArrayList;

/**
 * This class contains a board that holds 
 * all the cells growing on the petri dish.
 */

public class PetriDish {
    
    // instance variable for Petridish
    public Cell[][] dish;

    // NEW
    public List<Movable> movables;
    public List<Divisible> divisibles;

    // Constructor for Petridish
    public PetriDish(String[][] board) {

        // initialize movables and divisibles
        movables = new ArrayList<>();
        divisibles = new ArrayList<>();

        // setup 2d array
        dish = new Cell[board.length][board[0].length];

        // fill petridish
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                String input = board[i][j];

                // split input from board
                String [] boardInfo = input.strip().split(" ");

                // check for null strings
                if (boardInfo == null || 
                    boardInfo.length == 1 || 
                    boardInfo.length == 0) {
                    continue;
                }

                // get info from string
                String boardType = boardInfo[0];

                // get position of cell from the string
                int mass = Integer.parseInt(boardInfo[1]);

                // cases
                switch(boardType) {
                    case "CellStationary":
                        dish[i][j] = new CellStationary(i, j, mass);
                        break;
                    case "CellMoveUp":
                        dish[i][j] = new CellMoveUp(i, j, mass);
                        break;
                    case "CellDivide":
                        dish[i][j] = new CellDivide(i, j, mass);
                        break;
                    case "CellMoveToggle":
                        dish[i][j] = new CellMoveToggle(i, j, mass);
                        break;
                    case "CellMoveDiagonal":
                        dish[i][j] = new CellMoveDiagonal(i, j, mass);
                        break;
                    case "CellMoveToggleChild":
                        dish[i][j] = new CellMoveToggleChild(i, j, mass);
                        break;
                }

                // check if cell is an instanceof Movable
                if (dish[i][j] instanceof Movable) {
                    movables.add((Movable)dish[i][j]);
                }

                // check if cell is an instanceof Divisible
                else if (dish[i][j] instanceof Divisible) {
                    divisibles.add((Divisible)dish[i][j]);
                }
            }
        }
    }

    // print dish to visualize PetriDish
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(2 * dish.length + 3); //display the dish nicely 
        for(int i = 0; i < dish.length; i++){
            sb.append("|");
            for(int j = 0; j < dish[0].length; j++){
                sb.append(dish[i][j] == null ? " " : dish[i][j].toString());
                sb.append("|"); 
            }
            sb.append("\n");
            sb.append(2 * dish.length + 3); 
        }
        return sb.toString(); 
    }
         

    // NEW CODE for PA8
    // return list of cells neighboring input location
    public List<Cell> getNeighborsOf(int row, int col) {

        // check if row or col is out of bounds
        if ( (row < 0) || (col < 0) ) {
            return null;
        }

        // find neighbors of current cell
        List<Cell> neighbors = new ArrayList<>();
        neighbors.add(dish[(row - 1 + (dish.length - 1)) % (dish.length - 1)]
                          [(col - 1 + (dish[0].length - 1)) % 
                           (dish[0].length - 1)]); // northwest
        neighbors.add(dish[(row - 1 + (dish.length - 1)) % 
                           (dish.length - 1)][col]); // north
        neighbors.add(dish[(row - 1 + (dish.length - 1)) % (dish.length - 1)]
                          [(col + 1 + (dish[0].length - 1)) % 
                           (dish[0].length - 1)]); // northeast
        neighbors.add(dish[row][(col + 1 + (dish[0].length - 1)) % 
                                (dish[0].length - 1)]); // east
        neighbors.add(dish[row][(col - 1 + (dish[0].length - 1)) % 
                                (dish[0].length - 1)]); // west
        neighbors.add(dish[(row + 1 + (dish.length - 1)) % (dish.length - 1)]
                          [(col - 1 + (dish[0].length - 1)) % 
                           (dish[0].length - 1)]); // southwest
        neighbors.add(dish[(row + 1 + (dish.length - 1)) % 
                                      (dish.length - 1)][col]); // south
        neighbors.add(dish[(row + 1 + (dish.length - 1)) % (dish.length - 1)]
                          [(col + 1 + (dish[0].length - 1)) % 
                           (dish[0].length - 1)]); // southeast

        return neighbors;
    }

    // move all cells in petridish based on each cell's getMove() behavior
    public void move() {

        // stores state of dish as cells move
        Cell[][] next = new Cell[dish.length][dish[0].length];

        // stores ties at dish
        boolean[][] ties = new boolean[dish.length][dish[0].length];

        // stores which movables we should delete from instance field
        List<Movable> movablesToRemove = new ArrayList<>();

        // go through list and move them to new position in petridish
        for (int i = 0; i < movables.size(); i++) {

            // get cells new position and wrap new position
            Cell cell = (Cell)(movables.get(i));
            int[] pos = ((Movable)cell).getMove();

            handleWrap(pos);
            int newRow = pos[0];
            int newCol = pos[1];

            // Decide what to do next
            if(next[newRow][newCol] == null) {
                // No cell on their new position
                // Put the cell there
                next[newRow][newCol] = cell;
                // update position
                cell.updatePosition(pos);
            }
            else if (curCell.compareTo(next[newRow][newCol]) > 0 || 
                     !(next[newRow][newCol] instanceof Movable)) {
                // If there is a cell2 at that position and 
                // cell2 has a smaller mass
                  // add cell2 to movablesToRemove
                  movablesToRemove.add((Movable)next[newRow][newCol]);
                  // call apoptosis
                  next[newRow][newCol].apoptosis();
                  // Put the cell there
                  next[newRow][newCol] = curCell;
                  // update position
                  curCell.updatePosition(pos);
            }
            else if (cell.compareTo(next[newRow][newCol]) == 0) {
                // If there is a cell2 there and they have the same mass
                  // Set ties to true
                  ties[newRow][newCol] = true;
                  // Call apoptosis
                  next[newRow][newCol].apoptosis();
                  cell.apoptosis();
                  // set location to null
                  next[newRow][newCol] = null;
                  // Add cell to movableToRemove
                  movablesToRemove.add((Movable)cell);
            }
            else {
                // If there is a cell2 there and cell2 has a larger mass
                  // Check for apoptosis
                  cell.apoptosis();
                  // Add cell to movableToRemove
                  movablesToRemove.add((Movable)cell);
            }
        }

        // Go through movableToRemove and remove each cell in that list from movables
        for (Movable cell : movablesToRemove) {
            // remove each cell
            movables.remove(cell);
        }

        // Look at each tie
        for (int i = 0; i < ties.length; i++) {
            for (int j = 0; j < ties[0].length; j++) {
                if (ties[i][j]) {
                    // Remove that cell from movables
                    movables.remove((Movable)next[i][j]);
                    // Overthrow the cell
                    next[i][j].apoptosis();
                    // Set that position to be null
                    next[i][j] = null;
                }
            }
        }

    }

    // "divide" all cells in petridish based on 
    // each cell's getDivision() behvaior
    public void divide() {

        // stores state of dish as cells divide
        Cell[][] next = new Cell[dish.length][dish[0].length];

        // stores ties at dish
        boolean[][] ties = new boolean[dish.length][dish[0].length];

        // Step 1: Divide
        // Loop through instance field divisibles
        for (int i = 0; i < divisibles.size(); i++) {
            Cell newCell = (Cell)(divisibles.get(i));
            // get location of where we are dividing
            int[] pos = ((Divisible)newCell).getDivision();
            // handle wrapping
            handleWrap(pos);
            // get r & c of the position they are dividing
            int r = pos[0];
            int c = pos[1];

            // get a cell that has potentially divided to this spot
            Cell oldCell = next[r][c];
            if (dish[r][c] == null && (oldCell == null || 
                                       newCell.compareTo(oldCell) > 0)) {
                next[r][c] = newCell.newCellCopy(); // makes new copy
                next[r][c].updatePosition(pos); // update position of new cell
                ties[r][c] = false; 
            }
            else if (oldCell != null && 
                     newCell.compareTo(oldCell) == 0) {
                ties[r][c] = true;
            }
        }

        // Step 2. Put other cells back on board and handle ties
        for (int r = 0; r < dish.length; r++) {
            for (int c = 0; c < dish[0].length; c++) {
            // copy over from next if conditions are right
                if (next[r][c] != null && !ties[r][c]) {
                    dish[r][c] = next[r][c]; // update instance field from dish
                    divisibles.add((Divisible)dish[r][c]); // update divisibles
                }
            }
        }
    }

    // Simultaneously initiate apoptosis for all eligible cells and 
    // spawn new cells for eligible empty spaces
    public void update() {

        // stores state of dish
        Cell[][] next = new Cell[dish.length][dish[0].length];

        // initializes movables
        movables = new ArrayList<>();

        // Step 1: Loop through dish and get cell at each row & col (cell can be null)
        for (int i = 0; i < dish.length; i++) {
            for (int j = 0; j < dish[0].length; j++) {
                // assume that getNeighbors is filled out in Petridish
                List<Cell> neighbors = getNeighborsOf(i, j);

                // cells going into apoptosis
                if (dish[i][j].checkApoptosis(neighbors)) {
                    dish[i][j].apoptosis();
                    next[i][j] = null;
                }

                // check if space is empty
                if (next[i][j] == null) {
                    if (neighbors.size() == 2 || neighbors.size() == 3) {
                        next[i][j] = getNeighborsOf(i, j).get(0).newCellCopy();
                    }
                }

                // check if cell is an instanceof Movable
                if (next[i][j] instanceof Movable) {
                    movables.add((Movable)next[i][j]);
                }
            }
        }

        // Step 2: Once everything has been updated set globe to be the new update array next
        dish = next;
    }

    // run through one iteration
    public void iterate() {
        move(); // Step 1
        divide(); // Step 2
        update(); // Step 3
    }

    // helper method to handle wrapping
    private void handleWrap(int[] pos) {
        // handle first position
        // If the row position < 0 it should be set to the last row in dish
        if (pos[0] < 0) {
            pos[0] = dish.length - 1;
        }
        // If the row position > the last row in dish it should be set to 0
        if (pos[0] > dish.length - 1) {
            pos[0] = 0;
        }

        // handle second position
        // If the col position < 0 it should be set to the last col in dish
        if (pos[1] < 0) {
            pos[1] = dish[0].length - 1;
        }
        // If the col position > the last col in dish it should be set to 0
        if (pos[1] > dish[0].length - 1) {
            pos[1] = 0;
        }
    }
         
}
