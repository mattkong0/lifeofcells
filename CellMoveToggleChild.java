/**
 * Name: Matthew Kong
 * ID: A16660796
 * Email: mkong@ucsd.edu
 * Sources used: none
 * 
 * This file is a subclass of CellMoveToggle.
 * This file has a static instance variable.
 */

import java.util.List;

/**
 * This class checks if CellMoveToggle's conditions for apoptosis 
 * are satisfied and if there are fewer than 10 CellMoveToggleChild(s) alive.
 */

public class CellMoveToggleChild extends CellMoveToggle{
    
    // instance field for CellMoveToggleChild
    public static int numAlive;

    // Constructor for CellMoveToggleChild
    public CellMoveToggleChild(int currRow, int currCol, int mass) {
        super(currRow, currCol, mass);

        // increment numAlive
        numAlive++;
    }

    // copy Constructor for CellMoveToggleChild
    public CellMoveToggleChild(CellMoveToggleChild otherCellMoveToggleChild) {
        super(otherCellMoveToggleChild);

        // increment numAlive
        numAlive++;
    }

    // override apoptosis method in parent class
    public void apoptosis() {

        // call parent constructor
        super.apoptosis();

        // decrement numAlive
        numAlive--;
    }

    // returns true or false depending on given conditions
    public boolean checkApoptosis(List<Cell> neighbors) {
        if (super.checkApoptosis(neighbors) && 
            numAlive < 10) {// given conditions
            return true;
        }

        return false;
    }

    // NEW CODE for PA8
    // this method returns a deep copy of CellMoveToggleChild
    public Cell newCellCopy() {
        return new CellMoveToggleChild(currRow, currCol, mass);
    }

    // this method should not override super's method
    public int[] getMove() {

        // return position of the cell
        return new int[] {currRow - 1, currCol};
    }
}
