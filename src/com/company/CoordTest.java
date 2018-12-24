package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordTest {
    Coord c1 = new Coord(0,7);
    Coord c2 = new Coord("a1");

    @Test
         public void coordTests(){
        assertEquals(c1,c2);
    }
}