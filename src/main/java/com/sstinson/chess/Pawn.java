package com.sstinson.chess;

import java.util.Arrays;

public class Pawn extends Piece{

    Pawn(Colour colour, int[] pos){
        super(colour, pos);
    }

    @Override
    public boolean isValidMove(int[] pos){
        // x position must be the same and y position must be one more

        int[] diff = minusIntArray(pos,this.pos);
        return Arrays.equals(diff, new int[] {0,1});
    }

    public boolean isValidTake(){
        return false;
    }
}
