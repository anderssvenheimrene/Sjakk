package com.company;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordTest {

    @Test
         public void coordTests(){
        Coord c1 = new Coord(7,0);
        Coord c2 = new Coord("a1");
        Coord c3 = new Coord("d4");
        Coord c4 = new Coord(4,3);
        Piece p = new Piece(PieceType.ROOK,PieceColor.BLACK,new Coord(1,1));
        Piece p1 = new Piece(PieceType.ROOK,PieceColor.BLACK,1,1);

        Piece p2 = new Piece(PieceType.ROOK,PieceColor.BLACK,c1.row,c1.col);
        Piece p3 = new Piece(PieceType.ROOK,PieceColor.BLACK,c1);
        Piece p4 = new Piece(7,0);

        assertEquals(c3,c4);
        assertEquals(p.pos,p1.pos);

        assertEquals(p2.pos,p3.pos);
        assertEquals(p2.pos,p4.pos);

    }
}


