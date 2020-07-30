package com.sstinson.chess;

public class Game {
    static Board board = new Board();
    static InputHandler inputHandler = new InputHandler(board);
    static Player[] players = new Player[] {new Player(Colour.YELLOW), new Player(Colour.BLUE)};
    static int turnCount = 0;
    //players
    public static void main(String[] args){

        int i = 0;
        System.out.println("Welcome to my chess game. Have fun!");
        while( !isGameOver(players[i]) ){
            takeTurn(players[i]);
            turnCount++;
            i = turnCount%2;
        }
        if( checkCheckMate(players[i]) == BoardState.CHECKMATE ){
            System.out.println("Checkmate! " + players[(i+1)%2].colour + " player wins!");
        }else{
            System.out.println("Draw!");
        }

    }



    public static boolean isGameOver(Player player){
        return checkCheckMate(player) == BoardState.CHECKMATE ||
                checkStaleMate(player) == BoardState.STALEMATE;
    }

    public static BoardState checkCheckMate(Player player){
        return board.checkCheckMate(player.colour);
    }

    public static BoardState checkStaleMate(Player player){
        return board.checkStaleMate(player.colour);
    }

    public static void takeTurn(Player player){
        board.printBoard();
        Piece piece = inputHandler.getPieceFromPlayer(player);
        Position position = inputHandler.getMoveFromPlayer(player, piece);
        board.resolveMove(piece,position);
        resolvePromotion(piece);
    }

    public static void resolvePromotion(Piece piece){
        if(board.checkPromotion(piece)){
            PieceType type = inputHandler.getPieceTypeFromPlayer();
            board.resolvePromotion(piece, type);
        }
    }


}
