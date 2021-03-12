/**
 * Name: Matthew Kong
 * ID: A16660796
 * Email: mkong@ucsd.edu
 * Sources used: none
 * 
 * This file is a subclass of CellMoveUp.
 * This file has a string representation of either "T" or "t".
 */

import java.util.List;

/**
 * This class has an extra instance variable, toggled.
 * This class checks if the cell has fewer than 2 or greater than 5 neighbors.
 */

public class CellMoveToggle extends CellMoveUp{
    
    // instance field for CellMoveToggle
    public boolean toggled;

    // Constructor for CellMoveToggle
    public CellMoveToggle(int currRow, int currCol, int mass) {
        super(currRow, currCol, mass);
        this.toggled = true;
    }

    // copy Constructor for CellMoveToggle
    public CellMoveToggle(CellMoveToggle otherCellMoveToggle) {

        // fill out copy constructor
        super(otherCellMoveToggle);
    }

    // returns string representation of CellMoveToggle depending on condition
    public String toString() {
        if (toggled) {
            return "T";
        }
        return "t";
    }

    // returns true or false depending on given condition
    public boolean checkApoptosis(List<Cell> neighbors) {
        if (neighbors.size() < 2 || neighbors.size() > 5) {// given conditions
            return true;
        }
        return false;
    }

    // NEW CODE for PA8
    // this method returns a deep copy of CellMoveToggle
    public Cell newCellCopy() {
        return new CellMoveToggle(currRow, currCol, mass);
    }

    // return updated position depending on given condition
    public int[] getMove() {

        // move up one row if toggle is true
        if (toggled) {
            super.getMove();
            toggled = false; // flip toggled
        }
        else {
            toggled = true;
        }

        return new int[] {currRow, currCol};
    }
}
