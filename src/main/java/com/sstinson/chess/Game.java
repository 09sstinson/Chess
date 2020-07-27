package com.sstinson.chess;

public class Game {
    static Board board = new Board();
    static InputHandler inputHandler = new InputHandler(board);
    static Player player1 = new Player(Colour.WHITE);
    static Player player2 = new Player(Colour.BLACK);
    //players
    public static void main(String[] args){
//        InputHandler i = new InputHandler();
//        i.getPositionFromPlayer();
//        i.getPositionFromPlayer();
        board.printBoard();
        Piece piece = inputHandler.getPieceFromPlayer(player1);
        Position position = inputHandler.getMoveFromPlayer(player1, piece);
        board.movePiece(piece,position);
        board.printBoard();

    }

}
