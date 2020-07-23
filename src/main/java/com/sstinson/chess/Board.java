package com.sstinson.chess;
import java.util.Arrays;


public class Board {

    public final int size = 8;
    public Piece[] pieces = new Piece[3];

    //setUpBoard
    //Initialise pieces to start game

    public void movePiece(Piece piece, int[] pos){

        // split up logic could make return boolean
        if(isInBoard(pos) && piece.isValidMove(pos)) {
            if(hasFriendlyPiece()){
                piece.pos = pos;
                //removePieceAtPosition();
            }else{
                //Move not valid
            }
        }
    }

    public boolean hasFriendlyPiece(){

        return false;
    }

    public boolean isPositionFilled(int[] pos){

            for(Piece piece : pieces){
                if(Arrays.equals(pos, piece.pos)){
                    return true;
                }
            }
        return false;
    }


    public void takePiece(){

    }

    public void initialiseBoard(){

    }

    public void initialiseFrontRow(){
        Piece pawn = new Pawn(Colour.BLACK, new int[] {1,1});
    }

    public void initialiseBackRow(){

    }

    public boolean isFilled(){
        return false;
    }

    public boolean isInBoard(int[] pos){

        return isInRange(pos[0]) && isInRange(pos[1]);

    }

    public boolean isInRange(int pos){
        return pos < size && pos >=0;
    }

    public int getIndexOfPiece(){

        return 0;
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

    public void appendPiece(Piece piece){
        Piece[] newPieces = new Piece[pieces.length + 1];
        for(int i = 0; i < pieces.length ; i++){
            newPieces[i] = pieces[i];
        }
        newPieces[pieces.length] = piece;
        pieces = newPieces;
    }
}
