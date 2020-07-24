package com.sstinson.chess;

public class Knight extends Piece {

    Knight(Colour colour, int x, int y){
        super(colour, x , y);
    }

    @Override
    public boolean isValidMove(Position position){
        return false;
    }
}