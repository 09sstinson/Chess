package com.sstinson.chess;

public class Pawn extends Piece{

    @Override
    public boolean isValidMove(int[] pos){
        int[] diff = minusIntArray(pos,this.pos);
        if( absoluteValue(diff) == 2 ){
            return true;
        }
        return false;
    }
}
