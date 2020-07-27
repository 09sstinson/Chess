package com.sstinson.chess;

import java.util.Arrays;

public class Pawn extends Piece{

    int direction = 1;
    {type = PieceType.PAWN; }

    Pawn(Colour colour, int x, int y){
        super(colour, x, y);
        if(colour == colour.WHITE){
            direction = -1;
        }
    }

    @Override
    public boolean isValidMove(Position position1){
        // x position must be the same and y position must be one more
        Position difference = position1.minus(position);
        return difference.equals(0, direction);
    }

    public boolean isValidFirstMove(Position position1){
        Position difference = position1.minus(position);
        return difference.equals(0,direction * 2) && moveCounter==0;
    }

    public boolean isValidTake(Position position1){
        return (position1.minus(position).equals(1,direction ) ||
                position1.minus(position).equals(-1,direction ) );
    }

}
