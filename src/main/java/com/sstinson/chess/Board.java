package com.sstinson.chess;
import java.util.Arrays;


public class Board {
    // need to initialise board
    public final int size = 8;
    public Piece[] pieces;

    //setUpBoard
    //Initialise pieces to start game

    Board(){
        initialisePawns();
    }

    public boolean tryMovePiece(Piece piece, Position position){

        return position.isInBoard(size) && piece.isValidMove(position) && !hasFriendlyPiece(piece, position);
    }

    public void movePiece(Piece piece, Position position){

        if(isPositionFilled(position)){
            Piece newPiece = getPieceAtPosition(position);
            removePiece(newPiece);
        }
        piece.position = position;
    }



    public boolean hasFriendlyPiece(Piece piece, Position position){
            Piece newPiece = getPieceAtPosition(position);
            if(newPiece == null){
                return false;
            } else{
                return piece.colour == newPiece.colour;
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


    public void takePiece(){

    }

    public void initialiseBoard(){

    }

    public void initialisePawns(){
        for(int i = 0; i<size; i++){
            Pawn whitePawn = new Pawn( Colour.WHITE, i, 1 );
            Pawn blackPawn = new Pawn(Colour.BLACK, i, 6);
            appendPiece(whitePawn);
            appendPiece(blackPawn);
        }
    }

    public void initialiseBackRow(){

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
}
