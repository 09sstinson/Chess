package com.sstinson.chess;

public class Board {

    Piece[][] pieces;

    //setUpBoard
    //Initialise pieces to start game

    public void movePiece(Piece piece, int[] pos){
        piece.pos = pos;
    }

    public void takePiece(){

    }

    public void initialiseBoard(){

    }

    public void initialiseFrontRow(){
        int[] myarray = {1,1};
        Piece pawn = new Pawn(Colour.BLACK, myarray);
    }

    public void initialiseBackRow(){

    }

    public boolean isFilled(){
        return false;
    }
}
