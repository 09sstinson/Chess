package com.sstinson.chess;

public class Pawn extends Piece{

    int direction = 1;
    {type = PieceType.PAWN; }

    Pawn(Colour colour, int x, int y){
        super(colour, x, y);
        if(colour == colour.YELLOW){
            direction = -1;
        }
    }

    @Override
    public boolean isValidMove(Position position1){
        // x position must be the same and y position must be one more
        Position difference = position1.minus(position);
        return difference.equals(0, direction) || difference.equals(0,direction * 2) && moveCounter==0 ;
    }

//    public boolean isValidFirstMove(Position position1){
//        Position difference = position1.minus(position);
//        return difference.equals(0,direction * 2) && moveCounter==0;
//    }

    public boolean isValidTake(Position position1){
        Position difference = position1.minus(position);
        return difference.equals(1,direction ) ||
                difference.equals(-1,direction ) ;
    }

    public int getPromotionYValue(){
        if(direction < 0){
            return 0;
        } else{
            return Board.size - 1;
        }
    }

}
