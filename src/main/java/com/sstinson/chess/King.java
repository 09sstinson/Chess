package com.sstinson.chess;

public class King extends Piece {

    King(Colour colour, int x, int y){
        super(colour, x , y);
    }

    @Override
    public boolean isValidMove(Position position1){
        Position difference = position.minus(position1);
        if( position.distanceSquared(position1) <= 2 && position.distanceSquared(position1) != 0 ){
            return true;
        }
        return false;
    }
}
