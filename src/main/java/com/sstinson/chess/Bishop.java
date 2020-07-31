package com.sstinson.chess;

public class Bishop extends Piece {

    Bishop(Colour colour, int x, int y){
        super(colour, x , y);
        type = PieceType.BISHOP;
    }

    // Bishops can move to any position that is diagonal to their current position
    @Override
    public boolean isValidMove(Position position1){
        return position.isDiagonal(position1);
    }
}