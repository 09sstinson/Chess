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

    public Position minus(Position position1, Position position2){
        return new Position(position1.x - position2.x, position1.y - position2.y);
    }

    public int distanceSquared(Position position1, Position position2){
        Position difference = minus(position1, position2);
        return difference.x * difference.x + difference.y * difference.y;
    }

    public boolean isInBoard(Position position, int size){
        boolean x = position.x < size && position.x >= 0;
        boolean y = position.y < size && position.y >= 0;
        return x && y;
    }
}
