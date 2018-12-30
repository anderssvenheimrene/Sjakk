
package com.company;

public class Piece {

    final PieceType pieceType; // create the public enum variable pieceType
    Coord pos;
    final PieceColor pieceColor;

    Piece(PieceType pieceType, PieceColor pieceColor, Coord pos) {  //Constructor taking in a Coord
        this.pieceType = pieceType;
        this.pos = pos;
        this.pieceColor = pieceColor;
    }
    Piece(PieceType pieceType, PieceColor pieceColor, int row, int col) {  //Constructor taking in row, col
        this.pieceType = pieceType;
        this.pos = new Coord(row,col);
        /**
        char[]  newPosString = new char[2];
        newPosString[0] = (char)row; // row position = letter
        newPosString[1] = (char)col; // col position = number
        pos = new Coord(String.valueOf(newPosString));
         */
        this.pieceColor = pieceColor;
    }
    Piece(int row,int col) {
        this.pos = new Coord(row,col);
        pieceType = PieceType.X;
        pieceColor = PieceColor.NONE;
    }
    Piece(Coord pos){
        this.pos = pos;
        pieceType = PieceType.X;
        pieceColor = PieceColor.NONE;


    }
    void move(Board board, Coord pos){
        Coord oldSquare = this.pos;
        this.pos = pos;
        board.board[pos.row][pos.col] = this;
        board.board[oldSquare.row][oldSquare.col] = new Piece(oldSquare); //empty field on the board where the piece used to be
    }

    boolean isValidMove(Board board, Coord coord){
        switch(this.pieceType){
            case KNIGHT:
                return isValidKnightMove(board, coord);
            case PAWN:
                return isValidPawnMove(board,coord);
            case BISHOP:
                return isValidBishopMove(board,coord);
            default:
                return false;
        }
    }

    PieceColor getOppositeColor(){
        if(this.pieceColor ==PieceColor.BLACK)
            return pieceColor.WHITE;
        else if (this.pieceColor== PieceColor.WHITE)
            return pieceColor.BLACK;
        else return null;
    }

    private boolean isValidPawnMove(Board board, Coord desiredCoordinate){
        //if the move is 1 step ahead, or if it is 2 steps ahead and we havent moved yet, or if it is a capture and diagonal
        switch(this.pieceColor) {
            case WHITE:  // if the piece is white
                if (desiredCoordinate.col == this.pos.col) { //Same row i.e. frontal move
                    if (board.board[desiredCoordinate.row][desiredCoordinate.col].pieceType != PieceType.X) { //its not a legal move if theres a piece in the way
                        return false;
                    }
                    if (desiredCoordinate.row == this.pos.row - 1) { // -1 = in front of
                        return true;
                    } else if (this.pos.row == 6 && desiredCoordinate.row == this.pos.row - 2
                            && board.board[this.pos.row - 1][this.pos.col].pieceType == PieceType.X) { //there isnt anything in front){ //pawn hasnt moved yet, can move 2 spaces
                        return true;
                    } else {
                        return false;
                    }
                } //end of straight line moves
                //Diagonal attack moves:
                else if ((desiredCoordinate.col == this.pos.col - 1 || desiredCoordinate.col == this.pos.col + 1) // 1 step left or right of
                        && desiredCoordinate.row == this.pos.row - 1 //1 step in front of
                        && board.board[desiredCoordinate.row][desiredCoordinate.col].pieceColor == pieceColor.BLACK) { //there's a black piece there
                    return true;
                } else { // the desired coord is not in front of or adjacent to the piece
                    return false; // none of the above criterias match, therefore it isn't a legal move
                }
            case BLACK:
                if (desiredCoordinate.col == this.pos.col) { //Same row i.e. frontal move
                    if (board.board[desiredCoordinate.row][desiredCoordinate.col].pieceType != PieceType.X) { //its not a legal straight move if theres a piece in the way
                        return false;
                    }
                    if (desiredCoordinate.row == this.pos.row + 1) { // -1 = in front of
                        return true;
                    } else if (this.pos.row == 1 && desiredCoordinate.row == this.pos.row + 2 //pawn hasnt moved yet so it is at index 1 and can move 2 spaces,
                            && board.board[this.pos.row + 1][this.pos.col].pieceType == PieceType.X) { //there isnt anything in front
                        return true;
                    } else {
                        return false;
                    }
                } //end of straight line moves
                //Diagonal attack moves:
                else if ((desiredCoordinate.col == this.pos.col - 1 || desiredCoordinate.col == this.pos.col + 1) // 1 step left or right of
                        && desiredCoordinate.row == this.pos.row + 1 //1 step in front of
                        && board.board[desiredCoordinate.row][desiredCoordinate.col].pieceColor == pieceColor.WHITE) { //there's a white piece there
                    return true;
                } else { // the desired coord is not in front of or adjacent to the piece
                    return false; // none of the above criterias match, therefore it isn't a legal move
                }
        default:
            return false;
        }

    }

    private boolean isValidDiagonalMove(Board board, Coord desiredCoordinate){
        int newRow = this.pos.row;
        int newCol = this.pos.col;
        int counter = 0;
        while(true){
            if(counter > 0){
                if(desiredCoordinate.col == newCol && desiredCoordinate.row == newRow){ //return true if we reached the desired position
                    return true;
                }
                else if(board.board[newRow][newCol].pieceType != PieceType.X){ //return false if we are currently on top of a piece
                    return false;
                }
            }
           if(desiredCoordinate.row < newRow){
               if(desiredCoordinate.col < newCol){
                   newRow--;
                   newCol--;
               }
               else if(desiredCoordinate.col >= newCol){
                   newRow--;
                   newCol++;
               }
           }
           else if(desiredCoordinate.row >= newRow) {
               if (desiredCoordinate.col < newCol) {
                   newRow++;
                   newCol--;
               } else if (desiredCoordinate.col >= newCol) {
                   newRow++;
                   newCol++;
               }
           }
           counter++;
           if(newCol == 0 || newCol == 7 || newRow == 0 || newRow == 7){ //if we have reached a border, break and return false
               return false;
           }
        }
    }

    private boolean isValidBishopMove(Board board, Coord desiredCoordinate){
        return(isValidDiagonalMove(board,desiredCoordinate) && this.pieceColor != board.board[desiredCoordinate.row][desiredCoordinate.col].pieceColor);
        //its a valid move, and it is not occupied by a friendly piece
    }

    private boolean isValidKnightMove(Board board, Coord desiredCoordinate){
        if(board.board[desiredCoordinate.row][desiredCoordinate.col].pieceColor == this.pieceColor) { //cannot move there if we have a piece there
            return false;
        }
        if(desiredCoordinate.row < this.pos.row){
            if(desiredCoordinate.col < this.pos.col){ // the desired coordinate is below to the left of piece
                return((desiredCoordinate.row == this.pos.row -1 && desiredCoordinate.col == this.pos.col -2)
                    || desiredCoordinate.row == this.pos.row -2 && desiredCoordinate.col == this.pos.col -1);
            }
            else { // the desired coordinate is above to the right of the piece
                return((desiredCoordinate.row == this.pos.row -1 && desiredCoordinate.col == this.pos.col +2)
                        || desiredCoordinate.row == this.pos.row -2 && desiredCoordinate.col == this.pos.col +1);
            }
        }
        else if(desiredCoordinate.row >= this.pos.row) {
            if (desiredCoordinate.col < this.pos.col) { // the desired coordinate is above to the left of the piece
                return((desiredCoordinate.row == this.pos.row +1 && desiredCoordinate.col == this.pos.col -2)
                        || desiredCoordinate.row == this.pos.row +2 && desiredCoordinate.col == this.pos.col -1);
            } else { // the desired coordinate is above to the right of the piece
                return((desiredCoordinate.row == this.pos.row +1 && desiredCoordinate.col == this.pos.col +2)
                        || desiredCoordinate.row == this.pos.row +2 && desiredCoordinate.col == this.pos.col +1);
            }
        }
        return false;
    }

    private boolean isValidStraightMove(Board board, Coord desiredCoordinate){
        int newY = this.pos.row;
        int newX = this.pos.col;
        int direction = 0; //1 for col, 2 for row
        while(true){
            if(direction > 0){
                if(desiredCoordinate.col == newX && desiredCoordinate.row == newY){ //return true if we reached the desired position
                    return true;
                }
                else if(board.board[newY][newX].pieceType != PieceType.X){ //return false if we are currently on top of a piece
                    System.out.println("met a piece at the following location \n new X = " + newX);
                    System.out.println("new Y = " + newY);
                    return false;
                }
            }
            if(desiredCoordinate.row == this.pos.row){
                direction = 2;
                if(desiredCoordinate.col < newX){
                    newX--;
                }
                else if(desiredCoordinate.col >= newX){
                    newX++;
                }
            }
            else if(desiredCoordinate.col == this.pos.col) {
                direction = 1;
                if (desiredCoordinate.row < newY) {
                    newY--;
                } else if (desiredCoordinate.row >= newY) {
                    newY--;
                }
            }
            else{
                return false; //not row or col adjacent to desired position
            }
            if((direction == 1 && (newX == 0 || newX == 7 )) || //col-direction and have reached a border
                    (direction ==2 && ( newY == 0 || newY == 7))){
                //if we have reached a border, break and return false
                System.out.println("new X = " + newX);
                System.out.println("new Y = " + newY);
                return false;
            }
        }
    }

    boolean isValidRookMove(Board board, Coord desiredCoordinate){
        return (isValidStraightMove(board,desiredCoordinate) && this.pieceColor != board.board[desiredCoordinate.row][desiredCoordinate.col].pieceColor);
    }

    boolean isValidQueenMove(Board board, Coord desiredCoordinate){
        return ((isValidStraightMove(board, desiredCoordinate) || isValidDiagonalMove(board, desiredCoordinate)) // valid straight or diagonal move
                && this.pieceColor != board.board[desiredCoordinate.row][desiredCoordinate.col].pieceColor); // also not a friendly piece in desired location
    }

     boolean isValidKingMove(Board board, Coord desiredCoordinate){
        int xDiff = Math.abs(this.pos.col - desiredCoordinate.col);
        int yDiff = Math.abs(this.pos.row - desiredCoordinate.row);
        return (xDiff < 2 && yDiff < 2 && this.pieceColor != board.board[desiredCoordinate.row][desiredCoordinate.col].pieceColor);
    }

    public String toString(){
        return("" + this.pieceColor + " " + this.pieceType + " " +this.pos);
    }
//end of class
}