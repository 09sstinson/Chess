package com.sstinson.chess;
import java.util.Arrays;


public class Board {
    // need to initialise board
    public static final int size = 8;
    public Piece[] pieces;

    //setUpBoard
    //Initialise pieces to start game

    Board(){
        initialiseBoard();
    }

    public boolean tryMovePiece(Piece piece, Position position){

        return position.isInBoard() && piece.isValidMove(position) && !hasFriendlyPiece(piece, position);
    }

    public boolean trySpecialMove(Piece piece, Position position){
        // move diagonal check in to pawn class
        if(piece instanceof Pawn){
            Pawn pawn = (Pawn) piece;
            boolean take =  (hasEnemyPiece(piece, position) && pawn.isValidTake(position));
            boolean firstMove = pawn.isValidFirstMove(position);
            return firstMove || take;
        }
        return false;
    }


    public boolean hasEnemyPiece(Piece piece, Position position){
        return isPositionFilled(position) && !hasFriendlyPiece(piece, position);
    }

    public void movePiece(Piece piece, Position position){

        if(isPositionFilled(position)){
            Piece newPiece = getPieceAtPosition(position);
            removePiece(newPiece);
        }
        piece.position = position;
        piece.moveCounter++;
    }



    public boolean hasFriendlyPiece(Piece piece, Position position){
            Piece newPiece = getPieceAtPosition(position);
            if(newPiece == null){
                return false;
            } else{
                return piece.colour == newPiece.colour;
            }
    }

    public boolean hasFriendlyPiece(Colour colour, Position position){
        Piece newPiece = getPieceAtPosition(position);
        if(newPiece == null){
            return false;
        } else{
            return colour == newPiece.colour;
        }
    }

    public boolean isPositionFilled(Position position){
        return isPositionFilled(position.x, position.y);
    }

    public boolean isPositionFilled(int x, int y){

        for(Piece piece : pieces){
            if(piece.position.equals(x,y)){
                return true;
            }
        }
        return false;
    }

    public Piece getPieceAtPosition(Position position){
        for(Piece piece: pieces){
            if(piece.position.equals(position)){
                return piece;
            }
        }
        return null;
    }

    public Piece getPieceAtPosition(int x, int y){
        Position position = new Position(x,y);
        return getPieceAtPosition(position);
    }


    public void takePiece(){

    }

    public void initialiseBoard(){
        initialisePawns();
        initialiseBackRow();
    }

    public void initialisePawns(){
        for(int i = 0; i<size; i++){
            appendPiece(new Pawn(Colour.BLACK, i, 1 ), new Pawn(Colour.WHITE, i, 6));
        }
    }

    public void initialiseBackRow(){

        PieceType[] types;
        types = new PieceType[] {PieceType.CASTLE, PieceType.KNIGHT, PieceType.BISHOP,
                                    PieceType.QUEEN, PieceType.KING, PieceType.BISHOP,
                                                    PieceType.KNIGHT, PieceType.CASTLE};
        initialiseRow(types, 0, Colour.BLACK);
        initialiseRow(types, 7, Colour.WHITE);
    }

    public void initialiseRow(PieceType[] types, int row, Colour colour){
        // be given some names of pieces and initialise them; e,g (pawn, pawn, pawn, castle, castle,... king)
        if(types.length != size){
            System.out.println("ERROR too few types");
            return;
        }
            int i = 0;
            for (PieceType type : types) {
                switch (type) {
                    case PAWN: appendPiece(new Pawn(colour, i++, row )); break;
                    case CASTLE: appendPiece(new Castle(colour, i++, row)); break;
                    case KNIGHT: appendPiece(new Knight(colour, i++, row)); break;
                    case BISHOP: appendPiece(new Bishop(colour, i++, row)); break;
                    case QUEEN: appendPiece(new Queen(colour, i++, row)); break;
                    case KING: appendPiece(new King(colour, i++, row)); break;
                }
            }

    }




    public void removePiece(Piece piece1){
        int i=0;
        for(Piece piece : pieces){
            if(piece.equals(piece1)){
                removePieceAtIndex(i);
            }
            i++;
        }
    }

    public void removePieceAtIndex(int index){

        if(pieces == null || index < 0 || index >= pieces.length){
            System.out.println("Array is empty or index out of bounds");
            return;
        }
        Piece[] newPieces = new Piece[pieces.length - 1];

        for(int i = 0, k = 0; i < pieces.length ; i++){
            if(i == index){
                continue;
            }
            newPieces[k++] = pieces[i];
        }
        pieces = newPieces;
    }

    //Make work with multiple piece arguments to clean up initialisePawn method (ellipse)
    public void appendPiece(Piece piece){

        if(pieces == null){
            pieces = new Piece[] {piece};
            return;
        }

        Piece[] newPieces = new Piece[pieces.length + 1];
        for(int i = 0; i < pieces.length ; i++){
            newPieces[i] = pieces[i];
        }
        newPieces[pieces.length] = piece;
        pieces = newPieces;
    }

    public void appendPiece(Piece... newPieces){
        for(Piece piece : newPieces){
            appendPiece(piece);
        }
    }
    public void printPieces(){
        for(Piece piece : pieces){
            System.out.println(piece.getType() + " position " + piece.position.x + " " + piece.position.y);
        }
    }

    public void printBoard(){
        Piece p;
        int index = size;
        String row = "";
        for(int j = size - 1 ; j >= 0 ; j--){
            for(int i = 0; i < size; i++){
                p = getPieceAtPosition(i,j);
                if(p == null ){
                    row = row + "[ ] ";
                }else{
                    switch(p.colour) {
                        case WHITE:
                            row = row + (char)27 + "[30m" + "[" + p.getType() + "] ";
                            break;
                        case BLACK:
                            row = row + (char)27 + "[30m" + "[" + (char)27 + "[31m";
                            row = row + p.getType() + (char)27 + "[30m" + "] ";
                            break;
                    }
                }
            }
            System.out.println(row + index--);
            row = "";
        }
        printLowerIndex();
    }

    public void printLowerIndex(){
        String row = " ";
        for(int i = 1; i <= size; i++){
            row = row + i + "   " ;
        }
        System.out.println(row);
        System.out.println("\n");
    }
}
