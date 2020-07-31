package com.sstinson.chess;

public class King extends Piece {

    King(Colour colour, int x, int y){
        super(colour, x , y);
        type = PieceType.KING;
    }

    // The king can take one step in any direction
    // This means the maximum distance the king can move is sqrt(2)
    // It is not valid for the king to move to its current position
    @Override
    public boolean isValidMove(Position position1){
        return position.distanceSquared(position1) <= 2 && position.distanceSquared(position1) != 0;
    }

    // The king has a special move called a castle that allows it to move
    // 2 steps along x axis in either direction
    public boolean isValidCastle(Position position1){
        return position1.minus(position).equals(2,0) || position1.minus(position).equals(-2,0);
    }
}
