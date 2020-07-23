package com.sstinson.chess;

public class Position {
    int x;
    int y;

    Position(int x, int y){
        this.x = x;
        this.y = y;
    }


    public boolean isEqual(Position position){
        return x == position.x && y == position.y;
    }

    public boolean isEqual(int x, int y){
        return this.x == x && this.y == y;
    }

    public Position minus(Position position1){
        return new Position(x -position1.x , y - position1.y);
    }

    public int distanceSquared(Position position1){
        Position difference = minus(position1);
        return difference.x * difference.x + difference.y * difference.y;
    }

    public boolean isInBoard(int size){
        boolean b1 = x < size && x >= 0;
        boolean b2 = y < size && y >= 0;
        return b1 && b2;
    }
}
