package com.sstinson.chess;

import java.util.Arrays;

public class Pawn extends Piece{

    {type = PieceType.PAWN; }

    Pawn(Colour colour, int x, int y){
        super(colour, x, y);
    }

    @Override
    public boolean isValidMove(Position position1){
        // x position must be the same and y position must be one more
        Position difference = position1.minus(position);
        return difference.equals(0,1);
    }

    public boolean isValidTake(){
        return false;
    }
}
