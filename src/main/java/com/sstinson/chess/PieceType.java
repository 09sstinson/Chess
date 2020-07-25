package com.sstinson.chess;

public enum PieceType {
    PAWN, CASTLE, KNIGHT, BISHOP, QUEEN, KING;

    @Override
    public String toString() {
        switch(this) {
            case PAWN: return "P";
            case CASTLE: return "C";
            case KNIGHT: return "K";
            case BISHOP: return "B";
            case QUEEN: return "Q";
            case KING: return "X";
            default: return "ERROR";
        }
    }
}
