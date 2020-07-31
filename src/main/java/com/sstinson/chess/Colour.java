package com.sstinson.chess;

public enum Colour {
    YELLOW, RED;

    public String toString(){
        switch(this){
            case YELLOW: return "Yellow";
            case RED: return "Red";
            default: System.out.println("Error"); return "";
        }
    }
}
