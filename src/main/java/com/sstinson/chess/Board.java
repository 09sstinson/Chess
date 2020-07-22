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

    }

    public void initialiseBackRow(){

    }
    
    public boolean isFilled(){
        return false;
    }
}
