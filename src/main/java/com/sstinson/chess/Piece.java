package com.sstinson.chess;

public abstract class Piece {

    Colour colour;
    Position position;

    Piece(Colour colour, int x, int y){
        this.colour = colour;
        this.position = new Position(x,y);
    }

    public abstract boolean isValidMove(Position position);


}
