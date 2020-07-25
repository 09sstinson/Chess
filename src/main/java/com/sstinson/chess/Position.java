package com.sstinson.chess;

public class Position {
    int x;
    int y;

    Position(int x, int y){
        this.x = x;
        this.y = y;
    }


    public boolean equals(Position position){
        return x == position.x && y == position.y;
    }

    public boolean equals(int x, int y){
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
        return (x < size && x >= 0) && (y < size && y >= 0);
    }

    public boolean sameRow(Position position1){
        Position difference = minus(position1);
        return difference.y == 0 && difference.x !=0;
    }

    public boolean sameColumn(Position position1){
        Position difference = minus(position1);
        return difference.x == 0 && difference.y !=0;
    }

    public boolean isDiagonal(Position position1){
        Position difference = minus(position1);
        // If both are true then position1 and position are the same
        return (difference.x == difference.y) ^ (difference.x == -difference.y);
    }

}
