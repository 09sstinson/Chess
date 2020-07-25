package com.sstinson.chess;

public class Castle extends Piece {

    {type = PieceType.CASTLE; }
    Castle(Colour colour, int x, int y){
        super(colour, x , y);
    }

    @Override
    public boolean isValidMove(Position position1){
        return position.sameRow(position1) || position.sameColumn(position1);
    }
}