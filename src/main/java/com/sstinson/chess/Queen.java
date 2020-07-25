package com.sstinson.chess;

public class Queen extends Piece {

    {type = PieceType.QUEEN; }
    Queen(Colour colour, int x, int y){
        super(colour, x , y);
    }

    @Override
    public boolean isValidMove(Position position1){
        return position.isDiagonal(position1) || position.sameColumn(position1) || position.sameRow(position1);
    }
}
