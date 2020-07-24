package com.sstinson.chess;

public class Queen extends Piece {

    Queen(Colour colour, int x, int y){
        super(colour, x , y);
    }

    @Override
    public boolean isValidMove(Position position){
        return false;
    }
}
