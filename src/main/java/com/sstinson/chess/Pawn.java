package com.sstinson.chess;

import java.util.Arrays;

public class Pawn extends Piece{

    Pawn(Colour colour, int x, int y){
        super(colour, x, y);
    }

    @Override
    public boolean isValidMove(Position position1){
        // x position must be the same and y position must be one more

        Position difference = position.minus(position1);
        return difference.isEqual(0,1);
    }

    public boolean isValidTake(){
        return false;
    }
}
