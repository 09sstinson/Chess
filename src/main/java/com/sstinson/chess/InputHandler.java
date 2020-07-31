package com.sstinson.chess;


import java.util.Scanner;

public class InputHandler {
    Scanner scn = new Scanner(System.in);
    Board board;

    InputHandler(Board board){
        this.board = board;
    }

    public Piece getPieceFromPlayer(Player player){
        Position position;

        System.out.println(player.colour + " player: enter the coordinates of the piece you want to move");
        position = getPositionFromPlayer();
        while(!board.hasFriendlyPiece(player.colour, position)){
            System.out.println("There is not a friendly piece at that position\n" +
                    "Enter the coordinates of the friendly piece you want to move");
            position = getPositionFromPlayer();
        }
        Piece piece = board.getPieceAtPosition(position);
        System.out.println("You have selected the " + piece.getType() + " at position " + position.toString());
        if(!board.canTakeTurn(piece)){
            System.out.println("That piece cannot make any moves.");
            piece = getPieceFromPlayer(player);
        }
        return piece;
    }

    public Position getMoveFromPlayer(Player player, Piece piece){
        Position position;

        System.out.println("Enter the coordinates you want to move the " + piece.getType() + " to");

        position = getPositionFromPlayer();
        while(!board.tryTakeTurn(piece, position)  ){
            System.out.println("Cannot move there. Enter a different position");
            position = getPositionFromPlayer();
        }
        return position;
    }

    public Position getPositionFromPlayer(){
        Position position;

        //System.out.println("Enter a position on the board");
        int[] vals = getIntsFromPlayer();
        position = new Position(vals[0]-1, vals[1]-1);
        while (!position.isInGrid(Board.size)){
            System.out.println("Position not on board. Enter a position that is on the board");
            vals = getIntsFromPlayer();
            position = new Position(vals[0]-1, vals[1]-1);
        }

        return position;
    }

    public int[] getIntsFromPlayer(){

        int[] out = tryGetIntsFromPlayer();
        while(out == null){
            System.out.println("Enter integers in the format 1 1");
            scn = new Scanner(System.in);
            out = tryGetIntsFromPlayer();
        }
        scn = new Scanner(System.in);
        // resets the scanner
        return out;
    }

    public PieceType getPieceTypeFromPlayer(){
        System.out.println("Promotion! Enter the piece you want to promote the pawn to: Q, B, H, or C");
        PieceType type = tryGetPieceTypeFromPlayer();
        while(type == null){
            System.out.println("Enter the piece you want to promote the pawn to: Q, B, H, or C");
            scn = new Scanner(System.in);
            type = tryGetPieceTypeFromPlayer();
        }
        scn = new Scanner(System.in);
        return type;
    }

    public PieceType tryGetPieceTypeFromPlayer(){
        String str = scn.nextLine();
        switch(str){
            case "Q": return PieceType.QUEEN;
            case "H": return PieceType.KNIGHT;
            case "C": return PieceType.CASTLE;
            case "B": return PieceType.BISHOP;
            default: return null;

        }
    }

    public int[] tryGetIntsFromPlayer(){

        int x,y;
        if(scn.hasNextInt()){
            x = scn.nextInt();
            if(scn.hasNextInt()){
                y = scn.nextInt();
                 return new int[] {x,y};
            } else{
                return null;
            }
        } else {
            return null;
        }
    }
}
