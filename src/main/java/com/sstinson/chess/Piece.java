package com.sstinson.chess;

// The base class for all chess pieces
public abstract class Piece {

    Colour colour;
    Position position;
    PieceType type; // Must be initialised by the subclass
    int moveCounter = 0;

    Piece(Colour colour, int x, int y){
        this.colour = colour;
        this.position = new Position(x,y);
    }

    // Returns true if the piece could theoretically move to that position
    // i.e It defines the basic way a piece can move
    // For instance, a bishop could theoretically move to any position
    // that is diagonal to its current position
    // It should never be valid for a piece to move to its current position
    public abstract boolean isValidMove(Position position);

    // Returns true if the piece could theoretically take an enemy piece in that position
    // By default every piece can move and take in the same way
    // But pawns take in a different way to how they move so need to override this method
    public boolean isValidTake(Position position){
        return isValidMove(position);
    }

    public String getType(){
        return type.toString();
    }

    // A piece is equal to another piece if they have the same position, colour, and type
    @Override
    public boolean equals(Object o){

        if(o == this){
            return true;
        }
        if(!(o instanceof Piece)){
            return false;
        }

        Piece piece = (Piece) o;
        return piece.position.equals(this.position) &&
                this.colour == piece.colour &&
                this.type == piece.type;
    }

    // Returns the colour of the enemy pieces
    public Colour getEnemyColour(){
        switch(colour){
            case YELLOW: return Colour.RED;
            case RED: return Colour.YELLOW;
            default: System.out.println("Error in getEnemyColour()");return null;
        }
    }


}
