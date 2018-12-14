package com.company;

public class Main {

    public static void main(String[] args) {
//	 write your code here

        Board myBoard = new Board();
        myBoard.PrintBoard();
        //myBoard.testMoves();


        Coord a4Coord = new Coord("A4");
        Coord a2Coord = new Coord("A2");
        Coord b2Coord = new Coord("b2");
        Coord g7Coord = new Coord("g7");
        Coord g6Coord = new Coord("g5");
        Coord f5Coord = new Coord("f5");


//        Piece g7Pawn = new Piece(PieceType.PAWN,PieceColor.BLACK,g7Coord);
//        Piece a2Pawn = new Piece(PieceType.PAWN,PieceColor.WHITE,a2Coord);
//        Piece b3Pawn = new Piece(PieceType.PAWN,PieceColor.BLACK,b2Coord);



        //myBoard.board[5][1] = b3Pawn;
        //boolean validMove = a2Pawn.isValidPawnMove(myBoard,a4Coord);



//        Coord hei = myBoard.takePlayerInput();
//        System.out.println(hei.x);
//        System.out.println(hei.y);


    //end of method main
    }
//end of class main
}
