package com.sstinson.chess;

import java.lang.Math;

// A class for storing the x and y coordinates of an object
// and methods for performing calculations with them.
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



    // Basic operations
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


    // More complex operations

    // Returns the square of the distance between this position and position1
    public int distanceSquared(Position position1){
        Position difference = minus(position1);
        return difference.x * difference.x + difference.y * difference.y;
    }

    // Returns true if this position is contained in a square grid starting at
    // the origin with side length = gridSize - 1
    public boolean isInGrid(int gridSize){
        return (x < gridSize && x >= 0) && (y < gridSize && y >= 0);
    }

    // Returns true if this position and position1 are in the same row
    // but are not equal
    public boolean sameRow(Position position1){
        Position difference = minus(position1);
        return difference.y == 0 && difference.x !=0;
    }

    // Returns true if this position and position1 are in the same column
    // but are not equal
    public boolean sameColumn(Position position1){
        Position difference = minus(position1);
        return difference.x == 0 && difference.y !=0;
    }

    // Returns true if this position and position1 are on the same diagonal line
    public boolean isDiagonal(Position position1){
        Position difference = minus(position1);
        // If both are true then position1 and position are the same
        return (difference.x == difference.y) ^ (difference.x == -difference.y);
    }

    // Returns the positions in between (exclusive) this position and position1
    // Returns null if this position and position1 are not on the same row, column or diagonal
    // Returns null if there are no positions in between this position and position1
    public Position[] getPositionsInBetween(Position position1){

        Position unit = getUnitVector(position1);
        if(unit==null){
            return null;
        }

        // Calculate the number of steps in the direction of unit are needed to reach position1
        int steps=0;
        Position temp = new Position(x,y);
        while(!temp.equals(position1)){
            steps++;
            temp = temp.add(unit);
        }

        // Return null if this position and position1 are equal or adjacent
        if(steps <=1){
            return null;
        }

        // Create array of positions in between this position and position1
        Position[] positions = new Position[steps-1];
        temp = new Position(x,y);
        temp = temp.add(unit);
        for(int i = 0; i < steps -1 ; i++){
            positions[i] = temp;
            temp = temp.add(unit);
        }
        return positions;
    }



    // Returns a position with x and y values in the set {-1, 0, 1}
    // that points from this position to position1
    // Returns null if this position and position1 are not on the same row, column or diagonal
    public Position getUnitVector(Position position1){
        Position difference = position1.minus(this);
        if(sameRow(position1)){
            return new Position( difference.x/Math.abs(difference.x) , 0);
        }else if(sameColumn(position1)){
            return new Position(0, difference.y/Math.abs(difference.y));
        } else if(isDiagonal(position1)){
            return new Position( difference.x/Math.abs(difference.x), difference.y/Math.abs(difference.y) );
        }
        return null;
    }

    // Returns the corner of a square grid with side length = gridSize - 1
    // that is closest to this position
    // If the position is equidistant to all corners then the origin is returned
    public Position closestCorner(int gridSize){
        int[] endPoints = new int[] {0, gridSize - 1};
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

    // The x and y coordinates are incremented by 1 to convert
    // from indices starting at zero to indices starting at 1
    public String toString(){
        return (x+1) + " " + (y+1);
    }
}
