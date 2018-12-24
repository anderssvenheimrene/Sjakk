package com.company;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardTest {



    @Test
    public void getInterSectionSquares() {
        Board board = new Board();
        Coord kingCoord = new Coord("H8");

        Piece myBishop = new Piece(PieceType.BISHOP, PieceColor.BLACK, new Coord("b2"));
        Coord b2 = new Coord("b2");
        Coord c3 = new Coord("c3");
        Coord d4 = new Coord("d4");
        Coord e5 = new Coord("e5");
        Coord f6 = new Coord("f6");
        Coord g7 = new Coord("g7");
        ArrayList<Coord> bishopSquares = new ArrayList<>(Arrays.asList(c3,d4,e5,f6,g7));
        assertEquals(bishopSquares,board.getInterSectionSquares(myBishop,kingCoord));

        Piece myRook = new Piece(PieceType.ROOK, PieceColor.BLACK, new Coord("H5"));
        Coord h2 = new Coord("h2");
        Coord h3 = new Coord("h3");
        Coord h4 = new Coord("h4");
        Coord h5 = new Coord("h5");
        Coord h6 = new Coord("h6");
        Coord h7 = new Coord("h7");
        ArrayList<Coord> rookSquares = new ArrayList<>(Arrays.asList(h7,h6));
        assertEquals((ArrayList<Coord>)rookSquares, (ArrayList<Coord>)board.getInterSectionSquares(myRook,kingCoord));

        Piece queen = new Piece(PieceType.QUEEN, PieceColor.BLACK, new Coord("a8"));
        Coord king2 = new Coord("h1");
        Coord b7 = new Coord("b7");
        Coord c6 = new Coord("c6");
        Coord d5 = new Coord("d5");
        Coord e4 = new Coord("e4");
        Coord f3 = new Coord("f3");
        Coord g2 = new Coord("g2");
        ArrayList<Coord> bishopSquares2 = new ArrayList<>(Arrays.asList(b7,c6,d5,e4,f3,g2));
        assertEquals((ArrayList<Coord>)bishopSquares2, (ArrayList<Coord>)board.getInterSectionSquares(queen,king2));


    }
}