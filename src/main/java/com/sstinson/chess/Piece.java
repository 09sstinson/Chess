package com.sstinson.chess;

public abstract class Piece {

    Colour colour;
    Position position;

    Piece(Colour colour, int x, int y){
        this.colour = colour;
        this.position = new Position(x,y);
    }

    public abstract boolean isValidMove(Position position);

    @Override
    public boolean equals(Object o){

        if(o == this){
            return true;
        }

        if(!(o instanceof Piece)){
            return false;
        }

        Piece piece = (Piece) o;

        return piece.position.equals(this.position) && this.colour == piece.colour;
    }

}
