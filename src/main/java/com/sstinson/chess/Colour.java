package com.sstinson.chess;

public enum Colour {
    YELLOW, BLUE;

    public String toString(){
        switch(this){
            case YELLOW: return "Yellow";
            case BLUE: return "Blue";
            default: System.out.println("Error"); return "";
        }
    }
}
