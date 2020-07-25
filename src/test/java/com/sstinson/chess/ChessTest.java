package com.sstinson.chess;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChessTest {

    Board b = new Board();
    Position p1 = new Position(8, 9);
    Position p2 = new Position(2, 2);

    Piece piece1 = new Pawn(Colour.BLACK, 1 , 1);
    Piece piece2 = new Pawn(Colour.BLACK, 3 , 1);
    Piece piece3 = new Pawn(Colour.BLACK, 2 , 2);
    Piece piece4 = new Pawn(Colour.BLACK, 2 , 2);
    Piece piece5 = new Pawn(Colour.WHITE, 2 ,2);



    @Test
    public void testIsInBoard(){
        assertEquals(p2.isInBoard(b.size), true);
        assertEquals(p1.isInBoard(b.size), false);
    }

    @Test
    public void testDistanceSquared(){
        assertEquals(p1.distanceSquared(p2), 85);
    }

    @Test
    public void testIsPosFilled(){
        b.appendPiece(piece1);
        b.appendPiece( piece3);
        b.appendPiece (piece2);
        assertEquals(b.isPositionFilled(2,2), true);
        assertEquals(b.isPositionFilled(1,2), false);
    }

    @Test
    public void testEqualsPiece(){
        assertFalse(piece1.equals(piece2));
        assertTrue(piece1.equals(piece1));
        assertTrue(piece3.equals(piece4));
        assertFalse(piece4.equals(piece5));

    }

    @Test
    public void testHasFriendlyPiece(){
        b.appendPiece(piece1);
        b.appendPiece(piece3);
        assertTrue(b.hasFriendlyPiece(piece1, p2));
        assertFalse(b.hasFriendlyPiece(piece1, p1));
    }

    @Test
    public void testTryMovePiece(){

    }

    @Test
    public void testPrintPieces(){
        b.printPieces();
    }

    @Test
    public void testPrintBoard(){
        b.printBoard();
        b.pieces[0].position = new Position(2,3);
        b.printBoard();
        System.out.println(b.tryMovePiece(b.pieces[0],new Position (2,4)));
    }
}
