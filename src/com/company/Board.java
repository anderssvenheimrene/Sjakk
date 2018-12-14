package com.company;
import java.security.SignatureException;
import java.util.Scanner;

//static methods are general methods that do not need an instance of the object to function
public class Board {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[30m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private static final int DIM = 8;
    public Piece[][] board; // don't have to allocate new instance in memory yet, we do it in the constructor.
    public String[][] boardWithBorders;


    public Board() { //board constructor. Don't need any inputs here, as we know how a chessboard looks like in the beginning
        board = new Piece[DIM][DIM]; //create a new instance of the 8x8 Piece array'


        for(int i =0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                board[i][j] = new Piece(i,j);
            }
        }
        for(int i =0;i<DIM; i++){
            board[1][i] = new Piece(PieceType.PAWN,PieceColor.BLACK,1,i);
            board[6][i] = new Piece(PieceType.PAWN,PieceColor.WHITE,1,i);
        }
        //black pieces
        board[0][0] = new Piece(PieceType.ROOK, PieceColor.BLACK,0,0);
        board[0][7] = new Piece(PieceType.ROOK, PieceColor.BLACK,0,7);

        board[0][1] = new Piece(PieceType.KNIGHT, PieceColor.BLACK,0,1);
        board[0][6] = new Piece(PieceType.KNIGHT, PieceColor.BLACK,0,6);

        board[0][2] = new Piece(PieceType.BISHOP, PieceColor.BLACK,0,2);
        board[0][5] = new Piece(PieceType.BISHOP, PieceColor.BLACK,0,5);

        board[0][3] = new Piece(PieceType.QUEEN, PieceColor.BLACK,0,3);
        board[0][4] = new Piece(PieceType.KING, PieceColor.BLACK,0,4);

        //white pieces
        board[7][0] = new Piece(PieceType.ROOK, PieceColor.WHITE,7,0);
        board[7][7] = new Piece(PieceType.ROOK, PieceColor.WHITE,7,7);

        board[7][1] = new Piece(PieceType.KNIGHT, PieceColor.WHITE,7,1);
        board[7][6] = new Piece(PieceType.KNIGHT, PieceColor.WHITE,7,6);

        board[7][2] = new Piece(PieceType.BISHOP, PieceColor.WHITE,7,2);
        board[7][5] = new Piece(PieceType.BISHOP, PieceColor.WHITE,7,5);

        board[7][3] = new Piece(PieceType.QUEEN, PieceColor.WHITE,7,3);
        board[7][4] = new Piece(PieceType.KING, PieceColor.WHITE,7,4);

        //create the permanent elements in the board with borders
        boardWithBorders = new String[9][9];
        for(int i = 0; i<DIM; i++) {
            boardWithBorders[i][0] = Integer.toString(8-i);                     //print 1-8 on the first row
            boardWithBorders[8][i+1] = Character.toString((char)(65+i));  //print A-H (string(char(int i + 65)) on the first column
        }
        boardWithBorders[8][0] = ""; //bottom left corner should be empty
    }

    public Coord takePlayerInput(){
        boolean validInput = false;
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        //take some input, check that it is a coordinate, then return the Coord
        while(!validInput){
            System.out.println("skriv inn koordinat");
            String playerInput = reader.nextLine(); // Scans the next token of the input as an int.
            try{
                if(playerInput.length() != 2) {
                    throw new StringIndexOutOfBoundsException("the input is more than a letter and a number");
                }
                int letterInput = Character.getNumericValue(playerInput.charAt(0));
                int numberInput = Character.getNumericValue(playerInput.charAt(1));
                if(letterInput < 10 || letterInput > 17){
                    throw new SignatureException();
                }
                if(numberInput < 1 || numberInput > 8){
                    throw new SecurityException();
                }
                return new Coord(playerInput);
            }
            catch(StringIndexOutOfBoundsException e) {
                System.out.print("Innskrevet koordinat må være en bokstav [A-H] og et tall [1-8]! \n");
            }
            catch(SignatureException e) {
                System.out.print("bokstaven må være mellom A-H \n");
            }
            catch(SecurityException e) {
                System.out.print("tallet må være mellom 1-8 \n");
            }
        }
        return new Coord("FOO"); // for some reason there has to be a return here, although this can never be reached
    }

    public void PrintBoard(){

        //update the board with boarders
        for(int i = 0; i<DIM; i++) {
            for (int j = 0; j <DIM; j++) {
                boardWithBorders[i][j + 1] = board[i][j].pieceType.name(); //copy the board into the top right corner of the new table
            }
        }

        //print the board with boarders
        for(int i = 0; i<DIM+1; i++){
            for(int j = 0; j<DIM+1; j++){
                if(j> 0 && i < DIM) {
                    switch(board[i][j - 1].pieceColor){
                        case WHITE:
                            System.out.print(ANSI_WHITE +String.format("%1$8s",boardWithBorders[i][j]));
                            System.out.print(ANSI_RESET + String.format("%1$5s", "|"  ));
                            break;
                        case BLACK:
                            System.out.print(ANSI_BLUE + String.format("%1$8s",  boardWithBorders[i][j]) );
                            System.out.print(ANSI_RESET + String.format("%1$5s", "|"));
                            break;
                        default:
                            System.out.print(String.format("%1$8s", boardWithBorders[i][j]));
                            System.out.print(String.format("%1$5s", "|"));
                    }
                }
                else{
                    System.out.print(String.format("%1$8s", boardWithBorders[i][j]));
                    System.out.print(String.format("%1$5s", "|"));
                }
            }
            System.out.print("\n");
        }

    }

    public Piece[][] getBoard(){
        return board;
    }

    public PieceType getPieceType(Coord coord){
        return board[coord.y][coord.x].pieceType;
    }

    public void testMoves(){

        Scanner stringScanner = new Scanner(System.in);  // Reading from System.in
        //take some input, check that it is a coordinate, then return the Coord
        while(true) {
            System.out.println("skriv inn type");
            PieceType pieceType = PieceType.valueOf(stringScanner.nextLine());


            System.out.println("skriv inn farge");
            PieceColor pieceColor = PieceColor.valueOf(stringScanner.nextLine());

            System.out.println("Lokasjon:");
            Coord pieceCoord = this.takePlayerInput();

            System.out.println("Destinasjon:");
            Coord destinationCoord = this.takePlayerInput();

            this.board[pieceCoord.y][pieceCoord.x] = new Piece(pieceType, pieceColor, pieceCoord);

            this.PrintBoard();
            System.out.println("can the piece move to the desired coordinate? " +  this.board[pieceCoord.y][pieceCoord.x].isValidMove(this,destinationCoord));
        }


        //Piece newPiece = new Piece()
    }

    private int checkCheckOrMate(PieceColor notKingColor, Piece king){
        Piece checkingPieces[] = new Piece[2];
        int k = 0;
        boolean isChecked = false;
        for(int i = 0; i<this.board.length; i++){
            for(int j = 0; j< this.board.length; j++){
                if(this.board[i][j].pieceColor.equals(notKingColor)){
                    if(this.board[i][j].isValidMove(this,king.pos)){
                        checkingPieces[k] = this.board[i][j];
                        isChecked = true;
                    }
                }
            }
        }
        if (isChecked){
            return checkForMate(notKingColor,king,checkingPieces);
        } else {
            return 0;
        }
    }

    private int checkForMate(PieceColor notKingColor, Piece king, Piece[] checkingPieces){
        //iterate around the king and check for valid moves
        boolean isMated = true;
        for(int i = Math.max(0,king.pos.x-1); i< Math.min(7,king.pos.x+1); i++){
            for(int j = Math.max(0,king.pos.y-1); j< Math.min(7,king.pos.y+1); j++){
                Coord myCord = new Coord(i,j);
                if(king.isValidKingMove(this,myCord)){
                    isMated = false;
                }
            }
        }


        for(int i = 0; i<this.board.length; i++){
            for(int j= 0; j<this.board.length; j++){
                if(this.board.)
            }
        }
        //sjekk om kongen kan flytte seg
        //sjekk om man kan blokke sjakken(e)
        //må vite hvor sjakken kommer fra
        //checkForCheck må returnere en Piece. må kjøre gjennom alle mulige moves for å sjekke om den fortsatt har isValidMove (king)
    }
}

