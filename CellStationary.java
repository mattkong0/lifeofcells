/**
 * Name: Matthew Kong
 * ID: A16660796
 * Email: mkong@ucsd.edu
 * Sources used: none
 * 
 * This file represents a child class of Cell.
 * This file has a string representation of ".".
 */

import java.util.List;

/**
 * This class checks for whether this cell has fewer than or 
 * equal to 7 and greater than or equal to 3 neighbors.
 */

public class CellStationary extends Cell{
    
    // Constructor for CellStationary
    public CellStationary(int currRow, int currCol, int mass) {
        super(currRow, currCol, mass);
    }

    // copy constructor for CellStationary
    public CellStationary(CellStationary otherCellStationary) {
        super(otherCellStationary);
    }

    // returns string representation for CellStationary
    public String toString() {
        return ".";
    }

    // this method returns true or false depending on the given conditions
    @Override
    public boolean checkApoptosis(List<Cell> neighbors) {
        if ( (neighbors.size() <= 7) && 
             (neighbors.size() >= 3) ) {// given conditions
            return true;
        }

        return false;
    }

    // NEW CODE for PA8
    // this method returns a deep copy of CellStationary
    public Cell newCellCopy() {
        return new CellStationary(currRow, currCol, mass);
    }

    // this method evaluates how cells move in petridish
    public int[] getMove() {

        // return position of the cell
        return new int[] {currRow, currCol}; // no change in position
    }
}
