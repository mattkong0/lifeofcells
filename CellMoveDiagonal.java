/**
 * Name: Matthew Kong
 * ID: A16660796
 * Email: mkong@ucsd.edu
 * Sources used: none
 * 
 * This file is a subclass of CellMoveUp.
 * This file has two new instance variables,
 * orientedRight and diagonalMoves.
 */

import java.util.List;

/**
 * This class checks if the cell has more than 3 neighbors.
 */

public class CellMoveDiagonal extends CellMoveUp{
    
    // instance fields
    public boolean orientedRight;
    public int diagonalMoves;

    // Constructor for CellMoveDiagonal
    public CellMoveDiagonal(int currRow, int currCol, int mass) {
        super(currRow, currCol, mass);

        // default for orientedRight
        this.orientedRight = true;

        // default for diagonalMoves
        this.diagonalMoves = 0;
    }

    // copy Constructor for CellMoveDiagonal
    public CellMoveDiagonal(CellMoveDiagonal otherCellMoveDiagonal) {

        // fill out copy constructor
        super(otherCellMoveDiagonal);
        this.orientedRight = otherCellMoveDiagonal.orientedRight;
        this.diagonalMoves = otherCellMoveDiagonal.diagonalMoves;
    }

    // returns string representation of CellMoveDiagonal depending on condition
    public String toString() {
        if (orientedRight) {
            return "/";
        }
        return "\\";
    }

    // returns true or false depending on given condition
    public boolean checkApoptosis(List<Cell> neighbors) {
        if (neighbors.size() > 3) {// given condition
            return true;
        }
        return false;
    }

    // NEW CODE for PA8
    // this method returns a deep copy of CellMoveDiagonal
    public Cell newCellCopy() {
        return new CellMoveDiagonal(currRow, currCol, mass);
    }

    // this method returns true or false if toggle
    public int[] getMove() {

        // updates position of cell
        if (orientedRight) {
            super.getMove(); // move up one row
            currCol += 1; // move right
            diagonalMoves += 1; // increment diagonalMoves
            if ((diagonalMoves + 4) % 4 == 0) {// check if number of moves is a
                                               // multiple of 4
                orientedRight = false; // update orientation
            }
        }
        else {
            super.getMove(); // move up one row
            currCol -= 1; // move left
            diagonalMoves += 1; // increment diagonalMoves
            if ((diagonalMoves + 4) % 4 == 0) {// check if number of moves is a
                                               // multiple of 4
                orientedRight = true; // update orientation
            }
        }

        // return updated position of cell
        return new int[] {currRow, currCol};
    }
    
}

