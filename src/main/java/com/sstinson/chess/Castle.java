package com.sstinson.chess;

public class Castle extends Piece {

    Castle(Colour colour, int x, int y){
        super(colour, x , y);
        type = PieceType.CASTLE;
    }

    // Castles can move to positions that are in the same row or columns as their current position
    // sameRow and sameColumn return false if the current position equals positons
    @Override
    public boolean isValidMove(Position position1){
        return position.sameRow(position1) || position.sameColumn(position1);
    }
}