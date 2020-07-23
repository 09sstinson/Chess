package com.sstinson.chess;

public class King extends Piece {

    King(Colour colour, int[] pos){
        super(colour, pos);
    }

    @Override
    public boolean isValidMove(int[] pos){
        int[] diff = minusIntArray(pos,this.pos);
        if( absoluteValue(diff) <= 2 && absoluteValue(diff) != 0 ){
            return true;
        }
        return false;
    }
}
