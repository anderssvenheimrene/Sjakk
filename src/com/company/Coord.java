package com.company;

public class Coord {
    private String coordinate; // 'A5'
    public int y; // i, 5,[1-8]
    public int x; // j, A, [A-H]

    public Coord(String coordinate){
        this.coordinate = coordinate;
        y = 8 - Character.getNumericValue(coordinate.charAt(1));  //return the y of the number
        x = Character.getNumericValue(coordinate.charAt(0)) - 10; //return the x of the letter

    }
    public Coord(int x, int y){
        this.x = x;
        this.y = y;
        //
    }
}

