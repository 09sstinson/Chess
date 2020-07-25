package com.sstinson.chess;

public class King extends Piece {

    {type = PieceType.KING; }

    King(Colour colour, int x, int y){
        super(colour, x , y);
    }

    @Override
    public boolean isValidMove(Position position1){
        return position.distanceSquared(position1) <= 2 && position.distanceSquared(position1) != 0;
    }
}
