/**
 * Name: Matthew Kong
 * ID: A16660796
 * Email: mkong@ucsd.edu
 * Sources used: none
 * 
 * This file is another subclass of Cell.
 * This file has a string representation of "^".
 */

import java.util.List;

/**
 * This class checks for whether this cell 
 * does not have exactly 4 neighbors.
 */

public class CellMoveUp extends Cell{
    
    // Constructor for CellMoveUp
    public CellMoveUp(int currRow, int currCol, int mass) {
        super(currRow, currCol, mass);
    }

    // copy Constructor for CellMoveUp
    public CellMoveUp(CellMoveUp otherCellMoveUp) {
        super(otherCellMoveUp);
    }

    // return String representation of CellMoveUp
    public String toString() {
        return "^";
    }

    // this method returns true or false depending on the given conditions
    @Override
    public boolean checkApoptosis(List<Cell> neighbors) {
        if (neighbors.size() != 4) {// given condition
            return true;
        }

        return false;
    }

    // NEW CODE for PA8
    // returns a deep copy of CellMoveUp
    public Cell newCellCopy() {
        return new CellMoveUp(currRow, currCol, mass);
    }

    // defines how cells that are capable of moving will 
    // move around in petridish
    public int[] getMove() {

        // move up one row
        return new int[] {currRow - 1, currCol};
    }

}
