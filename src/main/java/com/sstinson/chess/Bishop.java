package com.sstinson.chess;

public class Bishop extends Piece {

    Bishop(Colour colour, int x, int y){
        super(colour, x , y);
    }

    @Override
    public boolean isValidMove(Position position){
        return false;
    }
}