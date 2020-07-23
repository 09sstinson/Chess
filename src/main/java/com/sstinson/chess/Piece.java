package com.sstinson.chess;

public abstract class Piece {

    int[] pos;
    Colour colour;
    Position position;

    Piece(Colour colour, int x, int y){
        this.colour = colour;
        this.position = new Position(x,y);
    }

    public abstract boolean isValidMove(int[] pos);

    public int[] minusIntArray(int[] pos, int[] pos1){
        if(sameLength(pos,pos1)){
            int[] result = new int[pos.length];
            for(int i = 0; i < pos.length ; i++ ){
                result[i] = pos[i] - pos1[i];
                return result;
            }
        }
        return null;
    }

    public int absoluteValue(int[] pos){
        int sum = 0;
        for(int value : pos){
            sum += value;
        }
        return sum;
    }

    public boolean sameLength(int[] pos, int[] pos1){
        if(pos.length != pos1.length) {
            System.out.println("Warning, arrays not same size");
            return false;
        }
        return true;
    }

}
