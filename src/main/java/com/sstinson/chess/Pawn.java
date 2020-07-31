package com.sstinson.chess;

public class Pawn extends Piece{


    // Pawns can only move in one direction, determined by their colour
    // direction should take values in {1,-1} only
     int direction = 1;


    Pawn(Colour colour, int x, int y){
        super(colour, x, y);
        type = PieceType.PAWN;
        if(colour == colour.YELLOW){
            direction = -1;
        }
    }

    // Pawns can move 1 step forward or 2 steps forward if they have not moved
    @Override
    public boolean isValidMove(Position position1){
        Position difference = position1.minus(position);
        return difference.equals(0, direction) || difference.equals(0,direction * 2) && moveCounter==0 ;
    }

    // Pawns can only take by moving one step diagonally
    public boolean isValidTake(Position position1){
        Position difference = position1.minus(position);
        return difference.equals(1,direction ) ||
                difference.equals(-1,direction ) ;
    }

    // Returns the y value the pawn must reach on the board in order to be promoted
    // This y value depends on the direction of the piece
    public int getPromotionYValue(){
        if(direction < 0){
            return 0;
        } else{
            return Board.size - 1;
        }
    }

}
