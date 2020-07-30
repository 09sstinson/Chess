package com.sstinson.chess;

import javafx.geometry.Pos;

import java.lang.Math;

public class Position {
    int x;
    int y;

    Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    Position(int[] ints){
        this.x = ints[0];
        this.y = ints[1];
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

    public Position add(Position position){
        return new Position(x + position.x, y + position.y);
    }

    public int distanceSquared(Position position1){
        Position difference = minus(position1);
        return difference.x * difference.x + difference.y * difference.y;
    }

    public boolean isInBoard(){
        return (x < Board.size && x >= 0) && (y < Board.size && y >= 0);
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

    public String toString(){
        return (x+1) + " " + (y+1);
    }

    public Position[] getPositionsInBetween(Position position){

        Position unit = getUnitVector(position);
        int steps=0;
        if(unit==null){
            return null;
        }
        Position temp = new Position(x,y);
        while(!temp.equals(position)){
            steps++;
            temp = temp.add(unit);
        }
        if(steps <=1){
            return null;
        }
        Position[] positions = new Position[steps-1];
        temp = new Position(x,y);
        temp = temp.add(unit);
        for(int i = 0; i < steps -1 ; i++){
            positions[i] = temp;
            temp = temp.add(unit);
        }
        return positions;
    }


    public Position getUnitVector(Position position){
        Position difference = position.minus(this);
        if(sameRow(position)){
            return new Position( difference.x/Math.abs(difference.x) , 0);
        }else if(sameColumn(position)){
            return new Position(0, difference.y/Math.abs(difference.y));
        } else if(isDiagonal(position)){
            return new Position( difference.x/Math.abs(difference.x), difference.y/Math.abs(difference.y) );
        }
        return null;
    }

    public Position closestCorner(){
        int[] endPoints = new int[] {0, Board.size - 1};
        int minDistance = Integer.MAX_VALUE;
        Position position = new Position(-1,-1);
        for(int i : endPoints){
            for(int j: endPoints){
                if(distanceSquared(new Position (i,j)) < minDistance){
                    minDistance = distanceSquared(new Position (i,j));
                    position = new Position(i,j);
                }
            }
        }
        return position;
    }
}
