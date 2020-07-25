package com.sstinson.chess;

public class Bishop extends Piece {

    {type = PieceType.BISHOP; }
    Bishop(Colour colour, int x, int y){
        super(colour, x , y);
    }

    @Override
    public boolean isValidMove(Position position1){

        return position.isDiagonal(position1);
    }
}