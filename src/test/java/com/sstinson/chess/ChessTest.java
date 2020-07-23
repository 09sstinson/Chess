package com.sstinson.chess;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChessTest {

    Board b = new Board();
    Position p1 = new Position(8, 9);
    Position p2 = new Position(7, 7);

    @Test
    public void testIsInBoard(){
        assertEquals(p2.isInBoard(b.size), true);
        assertEquals(p1.isInBoard(b.size), false);
    }

    @Test
    public void testDistanceSquared(){
        assertEquals(p1.distanceSquared(p2), 5);
    }

    @Test
    public void testIsPosFilled(){
//        Board b = new Board();
//        b.pieces[0] = new Pawn(Colour.BLACK, new int[] {1,1});
//        b.pieces[1] = new Pawn(Colour.BLACK, new int[] {3,1});
//        b.pieces[2] = new Pawn(Colour.BLACK, new int[] {2,2});
//        assertEquals(b.isPositionFilled(new int[] {2,2}), true);
//        assertEquals(b.isPositionFilled(new int[] {1,2}), false);
    }
}
