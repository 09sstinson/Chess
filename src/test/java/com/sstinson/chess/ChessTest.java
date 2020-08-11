package com.sstinson.chess;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChessTest {

    Board b = new Board();
    Position p1 = new Position(8, 9);
    Position p2 = new Position(2, 2);

    Piece piece1 = new Pawn(Colour.RED, 1 , 1);
    Piece piece2 = new Pawn(Colour.RED, 3 , 1);
    Piece piece3 = new Pawn(Colour.RED, 2 , 2);
    Piece piece4 = new Pawn(Colour.RED, 2 , 2);
    Piece piece5 = new Pawn(Colour.YELLOW, 2 ,2);



    @Test
    public void testIsInGrid(){
        assertEquals(p2.isInGrid(Board.size), true);
        assertEquals(p1.isInGrid(Board.size), false);
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
    public void testWillKingBeChecked(){
        b.printBoard();
        assertTrue(b.willFriendlyKingBeChecked(new King(Colour.YELLOW,3,3), new Position(3,2)));
    }


    @Test
    public void testPrintBoard(){
        b.printBoard();
        b.pieces[0].position = new Position(2,3);
        b.printBoard();
    }

    @Test
    public void testGetInputFromPlayer(){
        InputHandler i = new InputHandler(b);
        i.getPositionFromPlayer();

    }

    @Test
    public void testGetPositionInBetween(){
        Position position = new Position(0,0);
        Position position1 = new Position(6,6);
        Position[] positions = position.getPositionsInBetween(position1);
        if(positions!=null) {
            for (Position p : positions) {
                System.out.println(p.toString());
            }
        }
    }

    @Test
    public void testGetAllPositions(){
        Position[] positions = b.getAllPositionsOnBoard();
        for(Position position: positions){
            System.out.println(position.toString());
        }
    }

    @Test
    public void testPromotePiece(){
        b.printBoard();
        b.resolvePromotion(b.pieces[0], PieceType.QUEEN);
        b.printBoard();
    }

    @Test
    public void testClosestCorner(){
        Position p = new Position(2,6);
        System.out.println(p.closestCorner(Board.size));
    }

}
