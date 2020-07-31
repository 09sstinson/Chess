package com.sstinson.chess;

public class Game {
    static Board board = new Board();
    static InputHandler inputHandler = new InputHandler(board);
    static Player[] players = new Player[] {new Player(Colour.YELLOW), new Player(Colour.RED)};
    static int turnCount = 0;


    // Run the game
    public static void main(String[] args){

        int i = 0;
        System.out.println("Welcome to my chess game. Have fun!");
        while( !isGameOver(players[i]) ){
            takeTurn(players[i]);
            turnCount++;
            i = turnCount%2;
        }
        printEndOfGameMessage();

    }


    public static void printEndOfGameMessage(){
        if( board.checkCheckMate(players[turnCount%2].colour) == BoardState.CHECKMATE ){
            System.out.println("Checkmate! " + players[(turnCount+1)%2].colour + " player wins!");
        }else{
            System.out.println("Draw!");
        }
    }

    // Returns true if the given player is in checkmate or stalemate
    public static boolean isGameOver(Player player){
        return board.checkCheckMate(player.colour) == BoardState.CHECKMATE ||
                board.checkStaleMate(player.colour) == BoardState.STALEMATE;
    }

    // Performs the operations necessary for a player to take their turn
    public static void takeTurn(Player player){
        board.printBoard();
        Piece piece = inputHandler.getPieceFromPlayer(player);
        Position position = inputHandler.getMoveFromPlayer(player, piece);
        board.resolveTurn(piece,position);
        resolvePromotion(piece);
    }

    // If a piece needs to be promoted then get the choice
    // of promotion from the player and update the board accordingly
    public static void resolvePromotion(Piece piece){
        if(board.checkPromotion(piece)){
            PieceType type = inputHandler.getPieceTypeFromPlayer();
            board.resolvePromotion(piece, type);
        }
    }


}
