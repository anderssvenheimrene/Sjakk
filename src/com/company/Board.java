package com.company;
import java.security.SignatureException;
import java.util.Scanner;
import java.util.ArrayList;

//static methods are general methods that do not need an instance of the object to function
public class Board {
    private static boolean isMated = false;
    private static PieceColor winner;
    private static PieceColor currentPlayerColor;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_WHITE = "\u001B[30m";
    private static final String ANSI_BLUE = "\u001B[34m";
    static final int DIM = 8;
    static Piece[][] board; //static because the board belongs to the class, not the specific instance
    private static String[][] boardWithBorders;


    Board() { //board constructor. Don't need any inputs here, as we know how a chessboard looks like in the beginning
        board = new Piece[DIM][DIM]; //create a new instance of the 8x8 Piece array'
        currentPlayerColor = PieceColor.WHITE;

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                board[i][j] = new Piece(i, j);
            }
        }
        for (int i = 0; i < DIM; i++) {
            board[1][i] = new Piece(PieceType.PAWN, PieceColor.BLACK, 1, i);
            board[6][i] = new Piece(PieceType.PAWN, PieceColor.WHITE, 6, i);
        }
        //black pieces
        board[0][0] = new Piece(PieceType.ROOK, PieceColor.BLACK, 0, 0);
        board[0][7] = new Piece(PieceType.ROOK, PieceColor.BLACK, 0, 7);

        board[0][1] = new Piece(PieceType.KNIGHT, PieceColor.BLACK, 0, 1);
        board[0][6] = new Piece(PieceType.KNIGHT, PieceColor.BLACK, 0, 6);

        board[0][2] = new Piece(PieceType.BISHOP, PieceColor.BLACK, 0, 2);
        board[0][5] = new Piece(PieceType.BISHOP, PieceColor.BLACK, 0, 5);

        board[0][3] = new Piece(PieceType.QUEEN, PieceColor.BLACK, 0, 3);
        board[0][4] = new Piece(PieceType.KING, PieceColor.BLACK, 0, 4);

        //white pieces
        board[7][0] = new Piece(PieceType.ROOK, PieceColor.WHITE, 7, 0);
        board[7][7] = new Piece(PieceType.ROOK, PieceColor.WHITE, 7, 7);

        board[7][1] = new Piece(PieceType.KNIGHT, PieceColor.WHITE, 7, 1);
        board[7][6] = new Piece(PieceType.KNIGHT, PieceColor.WHITE, 7, 6);

        board[7][2] = new Piece(PieceType.BISHOP, PieceColor.WHITE, 7, 2);
        board[7][5] = new Piece(PieceType.BISHOP, PieceColor.WHITE, 7, 5);

        board[7][3] = new Piece(PieceType.QUEEN, PieceColor.WHITE, 7, 3);
        board[7][4] = new Piece(PieceType.KING, PieceColor.WHITE, 7, 4);

        //create the permanent elements in the board with borders
        boardWithBorders = new String[9][9];
        for (int i = 0; i < DIM; i++) {
            boardWithBorders[i][0] = Integer.toString(8 - i);                     //print 1-8 on the first row
            boardWithBorders[8][i + 1] = Character.toString((char) (65 + i));  //print A-H (string(char(int i + 65)) on the first column
        }
        boardWithBorders[8][0] = ""; //bottom left corner should be empty
    }

    public static Coord takePlayerInput() {
        boolean isValidInput = false;
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        //take some input, check that it is a coordinate, then return the Coord
        while (!isValidInput) {
            System.out.println("Enter coordinate");
            String playerInput = reader.nextLine(); // Scans the next token of the input as an int.
            try {
                if (playerInput.length() != 2) {
                    throw new StringIndexOutOfBoundsException();
                }
                int letterInput = Character.getNumericValue(playerInput.charAt(0));
                int numberInput = Character.getNumericValue(playerInput.charAt(1));
                if (letterInput < 10 || letterInput > 17) {
                    throw new SignatureException();
                }
                if (numberInput < 1 || numberInput > 8) {
                    throw new SecurityException();
                }
                return new Coord(playerInput);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.print("The coordinate has to be a letter[A-H] and a number[1-8]! \n");
            } catch (SignatureException e) {
                System.out.print("The letter has to be between A-H \n");
            } catch (SecurityException e) {
                System.out.print("The number has to be between 1-8 \n");
            }
        }
        return new Coord("FOO"); // for some reason there has to be a return here, although this can never be reached
    }

    public void printBoard() {

        //update the board with boarders
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                boardWithBorders[i][j + 1] = board[i][j].pieceType.name(); //copy the board into the top right corner of the new table
            }
        }

        //print the board with boarders
        for (int i = 0; i < DIM + 1; i++) {
            for (int j = 0; j < DIM + 1; j++) {
                if (j > 0 && i < DIM) {
                    switch (board[i][j - 1].pieceColor) {
                        case WHITE:
                            System.out.print(ANSI_WHITE + String.format("%1$8s", boardWithBorders[i][j]));
                            System.out.print(ANSI_RESET + String.format("%1$5s", "|"));
                            break;
                        case BLACK:
                            System.out.print(ANSI_BLUE + String.format("%1$8s", boardWithBorders[i][j]));
                            System.out.print(ANSI_RESET + String.format("%1$5s", "|"));
                            break;
                        default:
                            System.out.print(String.format("%1$8s", boardWithBorders[i][j]));
                            System.out.print(String.format("%1$5s", "|"));
                    }
                } else {
                    System.out.print(String.format("%1$8s", boardWithBorders[i][j]));
                    System.out.print(String.format("%1$5s", "|"));
                }
            }
            System.out.print("\n");
        }

    }

    public Piece[][] getBoard() {
        return board;
    }

    public PieceType getPieceType(Coord coord) {
        return board[coord.row][coord.col].pieceType;
    }

    public void testMoves() {

        Scanner stringScanner = new Scanner(System.in);  // Reading from System.in
        //take some input, check that it is a coordinate, then return the Coord
        while (true) {
            System.out.println("skriv inn type");
            PieceType pieceType = PieceType.valueOf(stringScanner.nextLine());


            System.out.println("skriv inn farge");
            PieceColor pieceColor = PieceColor.valueOf(stringScanner.nextLine());

            System.out.println("Lokasjon:");
            Coord pieceCoord = this.takePlayerInput();

            System.out.println("Destinasjon:");
            Coord destinationCoord = this.takePlayerInput();

            this.board[pieceCoord.row][pieceCoord.col] = new Piece(pieceType, pieceColor, pieceCoord);

            this.printBoard();
            System.out.println("can the piece move to the desired coordinate? " + this.board[pieceCoord.row][pieceCoord.col].isValidMove(this, destinationCoord));
        }


        //Piece newPiece = new Piece()
    } //function for testing moves

    private Piece getPieceAtSpecifiedCoord(Coord c){ return board[c.row][c.col]; }

    private ArrayList<Piece> getAllSameColorPieces(PieceColor pieceColor) {
        ArrayList<Piece> sameColorPieces = new ArrayList();
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (board[i][j].pieceColor == pieceColor) {
                    sameColorPieces.add(board[i][j]);
                }
            }
        }
        return sameColorPieces;
    }

    private boolean checkForCheckOrMate(PieceColor kingColor) { //
        ArrayList<Piece> allEnemyPieces = getAllSameColorPieces(getKing(kingColor).getOppositeColor());
        ArrayList<Piece> allCheckingPieces = new ArrayList<>();
        boolean isChecked = false;
        for(Piece piece : allEnemyPieces){
            if(piece.isValidMove(this,getKing(kingColor).pos)){
                isChecked = true;
                allCheckingPieces.add(piece); //may be up to 2 pieces checking the king
            }
        }
        if(isChecked){
            checkForMate(allCheckingPieces);
        }
        return isChecked;
    }

    private void checkForMate(ArrayList<Piece> checkingPieces) {
        Piece king = getKing(currentPlayerColor);
        //iterate around the king and check for valid moves
        isMated = true; // it is check mate unless we disprove it
        for (int i = Math.max(0, king.pos.col - 1); i < Math.min(7, king.pos.col + 1); i++) {
            for (int j = Math.max(0, king.pos.row - 1); j < Math.min(7, king.pos.row + 1); j++) {
                if (king.isValidKingMove(this, new Coord(i, j))) {
                    isMated = false;
                    break;
                }
            }
        } // check if a piece can block
        if (checkingPieces.size() == 1) {
            ArrayList<Piece> friendlyPieces = getAllSameColorPieces(king.pieceColor);
            ArrayList<Coord> intersectionSquares = getInterSectionSquares(checkingPieces.get(0), king.pos);
            for (Coord c : intersectionSquares) {
                for (Piece fp : friendlyPieces) {
                    if (fp.isValidMove(this, c)) { // if a friendly piece can reach an intersection square, it is not mate
                        isMated = false;
                        break;
                    }
                }
            }

        }
        if(isMated){
            winner = getKing(currentPlayerColor).getOppositeColor();
        }
    }

    public ArrayList<Coord> getInterSectionSquares(Piece attacker, Coord kingPos) {
        ArrayList<Coord> interSectionSquares = new ArrayList<>();
        switch (attacker.pieceType) {
            case ROOK:
                interSectionSquares = getStraightIntersectionSquares(interSectionSquares, attacker, kingPos);
                break;
            case BISHOP:
                interSectionSquares = getDiagonalIntersectionSquares(interSectionSquares, attacker, kingPos);
                break;
            case QUEEN:
                if (attacker.pos.col == kingPos.col || attacker.pos.row == kingPos.row) //queen is straightlined to king
                    interSectionSquares = getStraightIntersectionSquares(interSectionSquares, attacker, kingPos);
                else
                    interSectionSquares = getDiagonalIntersectionSquares(interSectionSquares, attacker, kingPos);
                break;
            default:
                break;
        }
        return interSectionSquares;
    }

    public ArrayList<Coord> getStraightIntersectionSquares(ArrayList<Coord> interSectionSquares, Piece attacker, Coord kingPos) {
        int deltaX = Math.abs(attacker.pos.col - kingPos.col);
        int deltaY = Math.abs(attacker.pos.row - kingPos.row);
        if (deltaX == 0) {
            for (int j = Math.min(attacker.pos.row, kingPos.row) + 1; j < deltaY; j++) { //for each position in the path
                interSectionSquares.add(new Coord(kingPos.col, j));
            }
        } else if (deltaY == 0) {
            for (int i = Math.min(attacker.pos.col, kingPos.col) + 1; i < deltaX; i++) { //for each position in the path
                interSectionSquares.add(new Coord(i, kingPos.row));
            }
        } else {
            System.out.println("something is awfully wrong in canBlockCheck");
        }
        return interSectionSquares;
    }

    public ArrayList<Coord> getDiagonalIntersectionSquares(ArrayList<Coord> interSectionSquares, Piece attacker, Coord kingPos) {

        ArrayList<Coord> intersectionSquares = new ArrayList<>();
        int minX = Math.min(attacker.pos.col, kingPos.col);
        int maxX = Math.max(attacker.pos.col, kingPos.col);
        int minY = Math.min(attacker.pos.row, kingPos.row);
        int maxY = Math.max(attacker.pos.row, kingPos.row);
        int j;
        if ((attacker.pos.col > kingPos.col && attacker.pos.row > kingPos.row) || (attacker.pos.col < kingPos.col && attacker.pos.row < kingPos.row)) {
            //increasing col, increasing row
            j = minY + 1;
            for (int i = minX + 1; i < maxX; i++) {
                interSectionSquares.add(new Coord(i, j));
                j++;
            }
        } else {
            //increasing col, decreasing row
            j = maxY - 1;
            for (int i = minX + 1; i < maxX; i++) {
                interSectionSquares.add(new Coord(i, j));
                j--;
            }
        }
        return interSectionSquares;
    }

    private boolean chosenMoveLeadsToCheck(Piece pieceToMove, Coord destinationCoord, Piece king){
        boolean moveLeadsToCheck = false;
        PieceColor enemyColor = king.getOppositeColor();

        Piece tempPiece = getPieceAtSpecifiedCoord(destinationCoord); //store the piece that is taken
        Coord sourceCoord = pieceToMove.pos;  //store the source coordinate
        pieceToMove.move(this,destinationCoord); //move the piece to the destination (updates its coord)

        if(checkForCheckOrMate(getKing(currentPlayerColor).getOppositeColor())) { moveLeadsToCheck = true; } //
        //the move we are trying to make leads to us checking ourselves. this is not allowed so we revert it

        tempPiece.move(this,destinationCoord); //move pieces back to original position
        pieceToMove.move(this,sourceCoord);
        System.out.println("moveLeadsToCheck" + moveLeadsToCheck);
        return moveLeadsToCheck;
    }

    private void userGivesLegalMove(){
        Piece pieceToMove;
        Coord pieceToMoveCoord, destinationCoord;
        do{
            System.out.println(currentPlayerColor + ", Choose Piece:");
            pieceToMoveCoord = takePlayerInput();
            pieceToMove = getPieceAtSpecifiedCoord(pieceToMoveCoord);
            System.out.println("Choose destination:");
            destinationCoord = takePlayerInput();
        }
        while(pieceToMove.pieceColor != currentPlayerColor || !pieceToMove.isValidMove(this,destinationCoord) ||
                !chosenMoveLeadsToCheck(pieceToMove,destinationCoord,getKing(currentPlayerColor))); //keep taking input until we have a valid piece moving to a valid square
        pieceToMove.move(this,destinationCoord);

    }

    private Piece getKing(PieceColor kingColor){
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                if(board[i][j].pieceColor == kingColor && board[i][j].pieceType == PieceType.KING) {
                    return (board[i][j]);
                }
            }
        }
        return null; //this can never happen
    }

    private void changePlayer() {
        if (currentPlayerColor == PieceColor.WHITE)
            currentPlayerColor = PieceColor.BLACK;
        else
            currentPlayerColor = PieceColor.WHITE;
    }
    private PieceColor getOppositeColor(PieceColor color) {
        if (color == PieceColor.WHITE) {
            return PieceColor.BLACK;
        } else {
            return PieceColor.WHITE;
        }
    }



    void stateMachine() {
        while (!isMated) {
            printBoard();
            userGivesLegalMove();
            checkForCheckOrMate(currentPlayerColor); //TODO sjekk om det er riktig farge, sjekk om CFCOM har riktig logikk
            changePlayer();
        }
        System.out.println(winner +" har vunnet!!!!");
    }

}