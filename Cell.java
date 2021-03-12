/**
 * Name: Matthew Kong
 * ID: A16660796
 * Email: mkong@ucsd.edu
 * Sources used: none
 * 
 * This file is an abstract class.
 * This file sets up the main instance fields.
 * This file is also called upon by its subclasses.
 */

import java.util.List;

/**
 * This class is an abstract class.
 * This class determines if cell undergoes apoptosis.
 */

public abstract class Cell implements Comparable<Cell>{
    
    // Instance variables for Cell
    public int currRow;
    public int currCol;
    public int mass;

    // Constructor for Cell
    public Cell(int currRow, int currCol, int mass) {
        if (currRow < 0) {// if invalid
            this.currRow = 0;
        }
        else {
            this.currRow = currRow;
        }
        if (currCol < 0) {// if invalid
            this.currCol = 0;
        }
        else {
            this.currCol = currCol;
        }
        if (mass < 0) {// if invalid
            this.mass = 0;
        }
        else {
            this.mass = mass;
        }
    }

    // Copy constructor for cell
    public Cell(Cell otherCell) {

        // get new row from otherCell
        int newRow = otherCell.getCurrRow();

        // get new col from otherCell
        int newCol = otherCell.getCurrCol();

        // get new mass from otherCell
        int newMass = otherCell.getMass();

        // fill out currRow
        this.currRow = newRow;

        // fill out currCol
        this.currCol = newCol;

        // fill out mass
        this.mass = newMass;
    }

    // This method called when apoptosis happens 
    public void apoptosis() {
        this.currRow = -1;
        this.currCol = -1;
        this.mass = -1;
    }

    // Getter 1
    public int getCurrRow() {
        return currRow;
    }

    // Getter 2
    public int getCurrCol() {
        return currCol;
    }

    // Getter 3
    public int getMass() {
        return mass;
    }

    public abstract boolean checkApoptosis(List<Cell> neighbors);

    // NEW CODE for PA8
    // compares the masses of two cells
    public int compareTo(Cell otherCell) {
        return mass - otherCell.mass;
    }

    public abstract Cell newCellCopy();

    // updates position of the cell
    public void updatePosition(int[] newPosition) {
        currRow = newPosition[0];
        currCol = newPosition[1];
    }
}
