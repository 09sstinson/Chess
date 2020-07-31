package com.sstinson.chess;

public class Knight extends Piece {



    Knight(Colour colour, int x, int y){
        super(colour, x , y);
        type = PieceType.KNIGHT;
    }

    // Knights can move 2 steps along one axis and 1 step along the other axis
    // This is equivalent to moving to any position that is a distance of sqrt(5) away
    @Override
    public boolean isValidMove(Position position1){
        return position.distanceSquared(position1) == 5;
    }
}