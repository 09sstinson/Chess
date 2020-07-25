package com.sstinson.chess;

public class Knight extends Piece {

    {type = PieceType.KNIGHT; }

    Knight(Colour colour, int x, int y){
        super(colour, x , y);
    }

    @Override
    public boolean isValidMove(Position position1){
        return position.distanceSquared(position1) == 5;
    }
}