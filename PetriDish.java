/**
 * Name: Matthew Kong
 * ID: A16660796
 * Email: mkong@ucsd.edu
 * Sources used: Piazza
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

        // setup 2d array
        dish = new Cell[board.length][board[0].length];

        // fill petridish
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                String input = board[i][j];

                // check for null strings
                if (input == null) {
                    continue;
                }

                // split input from board
                String [] boardInfo = input.strip().split(" ");

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
            }
        }
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
        neighbors.add(dish[(row - 1) % dish.length][(col - 1) % dish[0].length]); // northwest
        neighbors.add(dish[(row - 1) % dish.length][col]); // north
        neighbors.add(dish[(row - 1) % dish.length][(col + 1) % dish[0].length]); // northeast
        neighbors.add(dish[row][(col + 1) % dish[0].length]); // east
        neighbors.add(dish[row][(col - 1) % dish[0].length]); // west
        neighbors.add(dish[(row + 1) % dish.length][(col - 1) % dish[0].length]); // southwest
        neighbors.add(dish[(row + 1) % dish.length][col]); // south
        neighbors.add(dish[(row + 1) % dish.length][(col + 1) % dish[0].length]); // southeast

        return neighbors;
    }

    // move all cells in petridish based on each cell's getMove() behavior
    public void move() {

        // initialize movables
        movables = new ArrayList<>();

        // stores state of dish as cells move
        Cell[][] next = new Cell[dish.length][dish[0].length];

        // stores ties at dish
        boolean[][] ties = new boolean[dish.length][dish[0].length];

        // stores which movables we should delete from instance field
        List<Movable> movablesToRemove = new ArrayList<>();

        // go through list and move them to new position in petridish
        for (int i = 0; i < movables.size(); i++) {

            // get cells new position and wrap new position
            Movable cell = movables.get(i);
            int[] pos = cell.getMove();

            handleWrap(pos);
            int newRow = pos[0];
            int newCol = pos[1];

            // Decide what to do next
            if(next[newRow][newCol] == null) {
                // No cell on their new position
                // Put the cell there
                next[newRow][newCol] = (Cell)cell;
                // update position
                ((Cell)cell).updatePosition(pos);
            }
            else if (((Cell)cell).compareTo(next[newRow][newCol]) > 0) {
                // If there is a cell2 at that position and cell2 has a smaller mass
                // Check if cell is instanceof Movable
                if (!(cell instanceof Movable)) {
                    ((Cell)cell).apoptosis();
                }
                  // add cell2 to movablesToRemove
                  movablesToRemove.add((Movable)next[newRow][newCol]);
                  // Put the cell there
                  next[newRow][newCol] = (Cell)cell;
                  // update position
                  ((Cell)cell).updatePosition(pos);
            }
            else if (((Cell)cell).compareTo(next[newRow][newCol]) == 0) {
                // If there is a cell2 there and they have the same mass
                  // Set ties to true
                  ties[newRow][newCol] = true;
                  // Check for apoptosis
                  ((Cell)cell).apoptosis();
                  // Add cell to movableToRemove
                  movablesToRemove.add(cell);
            }
            else {
                // If there is a cell2 there and cell2 has a larger mass
                  // Check for apoptosis
                  ((Cell)cell).apoptosis();
                  // Add cell to movableToRemove
                  movablesToRemove.add(cell);
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

        // initialize divisibles
        divisibles = new ArrayList<>();

        // stores state of dish as cells divide
        Cell[][] next = new Cell[dish.length][dish[0].length];

        // stores ties at dish
        boolean[][] ties = new boolean[dish.length][dish[0].length];

        // Step 1: Divide
        // Loop through instance field divisibles
        for (int i = 0; i < divisibles.size(); i++) {
            Divisible newCell = divisibles.get(i);
            // get location of where we are dividing
            int[] pos = newCell.getDivision();
            // handle wrapping
            handleWrap(pos);
            // get r & c of the position they are dividing
            int r = pos[0];
            int c = pos[1];

            // get a cell that has potentially divided to this spot
            Cell oldCell = next[r][c];
            if (dish[r][c] == null && (oldCell == null || 
                                      ((Cell)newCell).compareTo(oldCell) > 0)) {
                next[r][c] = ((Cell)newCell).newCellCopy();
                next[r][c].updatePosition(pos);
                ties[r][c] = false;
            }
            else if (oldCell != null && 
                    ((Cell)newCell).compareTo(oldCell) == 0) {
                ties[r][c] = true;
            }
        }

        // Step 2. Put other cells back on board and handle ties
        for (int r = 0; r < dish.length; r++) {
            for (int c = 0; c < dish[0].length; c++) {
            // copy over from next if conditions are right
                if (next[r][c] != null && !ties[r][c]) {
                    dish[r][c] = next[r][c];
                    divisibles.add((Divisible)dish[r][c]);
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

        // initializes divisibles
        divisibles = new ArrayList<>();

        // Step 1: Loop through dish and get cell at each row & col (cell can be null)
        for (int i = 0; i < dish.length; i++) {
            for (int j = 0; j < dish[0].length; j++) {
                // assume that getNeighbors is filled out in Petridish
                List<Cell> neighbors = getNeighborsOf(i, j);

                // cells going into apoptosis
                if (dish[i][j].checkApoptosis(neighbors)) {
                    dish[i][j].apoptosis();
                    dish[i][j] = null;
                }

                // check if space is empty
                if (dish[i][j] == null) {
                    if (neighbors.size() == 2 || neighbors.size() == 3) {
                        next[i][j] = getNeighborsOf(i, j).get(0).newCellCopy();
                    }
                }

                // check if cell is an instanceof Movable
                if (next[i][j] instanceof Movable) {
                    movables.add((Movable)next[i][j]);
                }

                // check if cell is an instanceof Divisible
                if (next[i][j] instanceof Divisible) {
                    divisibles.add((Divisible)next[i][j]);
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

