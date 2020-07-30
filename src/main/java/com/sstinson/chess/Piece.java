package com.sstinson.chess;

public abstract class Piece {

    Colour colour;
    Position position;
    PieceType type;
    int moveCounter = 0;

    Piece(Colour colour, int x, int y){
        this.colour = colour;
        this.position = new Position(x,y);
    }

    public abstract boolean isValidMove(Position position);

    public boolean isValidTake(Position position){
        return isValidMove(position);
    }

    public String getType(){
        return type.toString();
    }

    @Override
    public boolean equals(Object o){

        if(o == this){
            return true;
        }

        if(!(o instanceof Piece)){
            return false;
        }

        Piece piece = (Piece) o;

        return piece.position.equals(this.position) && this.colour == piece.colour;
    }

    public Colour getEnemyColour(){
        switch(colour){
            case YELLOW: return Colour.BLUE;
            case BLUE: return Colour.YELLOW;
            default: System.out.println("Error in getEnemyColour()");return null;
        }
    }


}
