package com.sstinson.chess;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChessTest {

    @Test
    public void testIsInBoard(){
        Board b = new Board();
        assertEquals(b.isInBoard(new int[] {7,3}), true);
        assertEquals(b.isInBoard(new int[] {7,8}), false);
    }

    @Test
    public void testIsPosFilled(){
        Board b = new Board();
        b.pieces[0] = new Pawn(Colour.BLACK, new int[] {1,1});
        b.pieces[1] = new Pawn(Colour.BLACK, new int[] {3,1});
        b.pieces[2] = new Pawn(Colour.BLACK, new int[] {2,2});
        assertEquals(b.isPositionFilled(new int[] {2,2}), true);
        assertEquals(b.isPositionFilled(new int[] {1,2}), false);
    }
}
