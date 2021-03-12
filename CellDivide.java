/**
 * Name: Matthew Kong
 * ID: A16660796
 * Email: mkong@ucsd.edu
 * Sources used: zybook, piazza
 * 
 * This file is a subclass of Cell.
 * This file has a string representation of "+".
 */

import java.util.List;

/**
 * This class has an extra instance variable, direction.
 * This class checks whether the cell has exactly 3 neighboring cells.
 */

public class CellDivide extends Cell implements Divisible{
    
    // instance field for CellDivide
    public int direction;

    // Constructor for CellDivide
    public CellDivide(int currRow, int currCol, int mass) {

        // fill out constructor
        super(currRow, currCol, mass);

        // initialize instance field
        this.direction = 1;
    }

    // copy Constructor for CellDivide
    public CellDivide(CellDivide otherCellDivide) {

        // fill out copy constructor
        super(otherCellDivide);
        this.direction = otherCellDivide.direction;
    }

    // returns string representation of CellDivide
    public String toString() {
        return "+";
    }

    // returns true or false depending on the given conditions
    @Override
    public boolean checkApoptosis(List<Cell> neighbors) {
        if (neighbors.size() == 3) {// given condition
            return true;
        }
        return false;
    }

    // NEW CODE for PA8
    // this method returns a deep copy of CellDivide
    public Cell newCellCopy() {
        return new CellDivide(currRow, currCol, mass);
    }

    // define where a cell that is divided by original ceiling cell
    // will be placed
    public int[] getDivision() {

        // update direction
        direction += 1;

        // cases for each direction
        if (direction == 1) {// down
            currRow += 1;
        }
        if (direction == 2) {// up
            currRow -= 1;
        }
        if (direction == 3) {// left
            currCol -= 1;
        }
        if (direction == 4) {// right
            direction = 0;
            currCol += 1;
        }

        return new int[] {currRow, currCol};

    }
}
