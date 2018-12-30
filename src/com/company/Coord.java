package com.company;

public class Coord {
    private String coordinate; // 'A5'
    public final int row; // i, 5,[1-8]
    public final int col; // j, A, [A-H]

    public Coord(String coordinate){
        this.coordinate = (coordinate.toUpperCase());
        row = 8 - Character.getNumericValue(coordinate.charAt(1));  //row, 0 -> 8, 1 -> 7 osv.
        col = Character.getNumericValue(coordinate.charAt(0)) - 10; //col, 0 -> A, 1 -> B osv.

    }
    public Coord(int row, int col){ //
        this.row = row;
        this.col = col;
        coordinate = ((char)(col +97)+ "" + (row +1));
    }
    public String toString(){
        return("(" + this.row + "," + this.col + ")");
    }

    public boolean equals(Object o){
        if(!(o instanceof Coord))
            return false;
        Coord c = (Coord)o;
        return(c.col == this.col && c.row == this.row);
    }
}

/**
 * nå: A[i][j] = A[row][col] = A[row][col]
 * fiks: bytt om row -> row col -> col slik at A[i][j] = A[row][col]
 * Coord constructoren gjør om en stringkoordinat til plass på brettet.
 * Den må også gjøre om et [col,row] koordinat til plass på brettet. gjør den det? Den gjør kun det dersom man vet hva som er hvor
 * A[0][0] = A8
 * Enten må alt av Coord constructorer være (x,y) og konstruktoren gjør omberegning,
 * ellers må alt av constructorer være (row,col) slik som board blir hentet ut
 * y -> row
 * x -> col
 *
 *
 */
