package com.sstinson.chess;

public class Castle extends Piece {

    Castle(Colour colour, int x, int y){
        super(colour, x , y);
    }

    @Override
    public boolean isValidMove(Position position){
        return false;
    }
}