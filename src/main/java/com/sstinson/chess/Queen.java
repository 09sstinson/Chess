package com.sstinson.chess;

public class Queen extends Piece {

    Queen(Colour colour, int x, int y){
        super(colour, x , y);
        type = PieceType.QUEEN;
    }

    // Queens can move to any position that is in the
    // same row, same column or is diagonal to their current position
    @Override
    public boolean isValidMove(Position position1){
        return position.isDiagonal(position1) || position.sameColumn(position1) || position.sameRow(position1);
    }
}
