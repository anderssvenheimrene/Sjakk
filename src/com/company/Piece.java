package com.company;

public class Piece {

    public PieceType pieceType; // create the public enum variable pieceType
    public Coord pos;
    final PieceColor pieceColor;

    public Piece(PieceType pieceType, PieceColor pieceColor, Coord pos) {  //Constructor taking in a Coord
        this.pieceType = pieceType;
        this.pos = pos;
        this.pieceColor = pieceColor;
    }
    public Piece(PieceType pieceType, PieceColor pieceColor, int xPos, int yPos) {  //Constructor taking in y, x
        this.pieceType = pieceType;
        this.pos = new Coord(xPos,yPos);
        /**
        char[]  newPosString = new char[2];
        newPosString[0] = (char)yPos; // y position = letter
        newPosString[1] = (char)xPos; // x position = number
        pos = new Coord(String.valueOf(newPosString));
         */
        this.pieceColor = pieceColor;
    }
    public Piece(int xPos, int yPos) {

        char[]  newPosString = new char[2];
        newPosString[0] = (char)yPos; // y position = letter
        newPosString[1] = (char)xPos; // x position = number
        pos = new Coord(xPos,yPos);
        pieceType = PieceType.X;
        pieceColor = PieceColor.NONE;
    }
    public Piece(){
        pieceType = PieceType.X;
        pieceColor = PieceColor.NONE;
    }

    void move(Board board, Coord c){
        this.pos = c;
        board.board[c.x][c.y] = this;
    }

    public boolean isValidMove(Board board, Coord coord){
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

    public PieceColor getOppositeColor(){
        if(this.pieceColor ==PieceColor.BLACK)
            return pieceColor.WHITE;
        else if (this.pieceColor== PieceColor.WHITE)
            return pieceColor.BLACK;
        else return null;
    }

    public boolean isValidPawnMove(Board board, Coord desiredCoordinate){
        //if the move is 1 step ahead, or if it is 2 steps ahead and we havent moved yet, or if it is a capture and diagonal
        switch(this.pieceColor) {
            case WHITE:  // if the piece is white
                if (desiredCoordinate.x == this.pos.x) { //Same row i.e. frontal move
                    if (board.board[desiredCoordinate.y][desiredCoordinate.x].pieceType != PieceType.X) { //its not a legal move if theres a piece in the way
                        return false;
                    }
                    if (desiredCoordinate.y == this.pos.y - 1) { // -1 = in front of
                        return true;
                    } else if (this.pos.y == 6 && desiredCoordinate.y == this.pos.y - 2
                            && board.board[this.pos.y - 1][this.pos.x].pieceType == PieceType.X) { //there isnt anything in front){ //pawn hasnt moved yet, can move 2 spaces
                        return true;
                    } else {
                        return false;
                    }
                } //end of straight line moves
                //Diagonal attack moves:
                else if ((desiredCoordinate.x == this.pos.x - 1 || desiredCoordinate.x == this.pos.x + 1) // 1 step left or right of
                        && desiredCoordinate.y == this.pos.y - 1 //1 step in front of
                        && board.board[desiredCoordinate.y][desiredCoordinate.x].pieceColor == pieceColor.BLACK) { //there's a black piece there
                    return true;
                } else { // the desired coord is not in front of or adjacent to the piece
                    return false; // none of the above criterias match, therefore it isn't a legal move
                }
            case BLACK:
                if (desiredCoordinate.x == this.pos.x) { //Same row i.e. frontal move
                    if (board.board[desiredCoordinate.y][desiredCoordinate.x].pieceType != PieceType.X) { //its not a legal straight move if theres a piece in the way
                        return false;
                    }
                    if (desiredCoordinate.y == this.pos.y + 1) { // -1 = in front of
                        return true;
                    } else if (this.pos.y == 1 && desiredCoordinate.y == this.pos.y + 2 //pawn hasnt moved yet so it is at index 1 and can move 2 spaces,
                            && board.board[this.pos.y + 1][this.pos.x].pieceType == PieceType.X) { //there isnt anything in front
                        return true;
                    } else {
                        return false;
                    }
                } //end of straight line moves
                //Diagonal attack moves:
                else if ((desiredCoordinate.x == this.pos.x - 1 || desiredCoordinate.x == this.pos.x + 1) // 1 step left or right of
                        && desiredCoordinate.y == this.pos.y + 1 //1 step in front of
                        && board.board[desiredCoordinate.y][desiredCoordinate.x].pieceColor == pieceColor.WHITE) { //there's a white piece there
                    return true;
                } else { // the desired coord is not in front of or adjacent to the piece
                    return false; // none of the above criterias match, therefore it isn't a legal move
                }
        default:
            return false;
        }

    }

    public boolean isValidDiagonalMove(Board board, Coord desiredCoordinate){
        int newY = this.pos.y;
        int newX = this.pos.x;
        int counter = 0;
        while(true){
            if(counter > 0){
                if(desiredCoordinate.x == newX && desiredCoordinate.y == newY){ //return true if we reached the desired position
                    return true;
                }
                else if(board.board[newY][newX].pieceType != PieceType.X){ //return false if we are currently on top of a piece
                    System.out.println("met a piece at the following location \n new X = " + newX);
                    System.out.println("new Y = " + newY);
                    return false;
                }
            }
           if(desiredCoordinate.y < newY){
               if(desiredCoordinate.x < newX){
                   newY--;
                   newX--;
               }
               else if(desiredCoordinate.x >= newX){
                   newY--;
                   newX++;
               }
           }
           else if(desiredCoordinate.y >= newY) {
               if (desiredCoordinate.x < newX) {
                   newY++;
                   newX--;
               } else if (desiredCoordinate.x >= newX) {
                   newY++;
                   newX++;
               }
           }
           counter++;
           if(newX == 0 || newX == 7 || newY == 0 || newY == 7){ //if we have reached a border, break and return false
               System.out.println("new X = " + newX);
               System.out.println("new Y = " + newY);
               return false;
           }
        }
    }

    public boolean isValidBishopMove(Board board, Coord desiredCoordinate){
        return(isValidDiagonalMove(board,desiredCoordinate) && this.pieceColor != board.board[desiredCoordinate.y][desiredCoordinate.x].pieceColor);
        //its a valid move, and it is not occupied by a friendly piece
    }

    public boolean isValidKnightMove(Board board, Coord desiredCoordinate){
        if(board.board[desiredCoordinate.y][desiredCoordinate.x].pieceColor == this.pieceColor) { //cannot move there if we have a piece there
            return false;
        }
        if(desiredCoordinate.y < this.pos.y){
            if(desiredCoordinate.x < this.pos.x){ // the desired coordinate is below to the left of piece
                return((desiredCoordinate.y == this.pos.y-1 && desiredCoordinate.x == this.pos.x -2)
                    || desiredCoordinate.y == this.pos.y-2 && desiredCoordinate.x == this.pos.x -1);
            }
            else { // the desired coordinate is above to the right of the piece
                return((desiredCoordinate.y == this.pos.y-1 && desiredCoordinate.x == this.pos.x +2)
                        || desiredCoordinate.y == this.pos.y-2 && desiredCoordinate.x == this.pos.x +1);
            }
        }
        else if(desiredCoordinate.y >= this.pos.y) {
            if (desiredCoordinate.x < this.pos.x) { // the desired coordinate is above to the left of the piece
                return((desiredCoordinate.y == this.pos.y+1 && desiredCoordinate.x == this.pos.x -2)
                        || desiredCoordinate.y == this.pos.y+2 && desiredCoordinate.x == this.pos.x -1);
            } else { // the desired coordinate is above to the right of the piece
                return((desiredCoordinate.y == this.pos.y+1 && desiredCoordinate.x == this.pos.x +2)
                        || desiredCoordinate.y == this.pos.y+2 && desiredCoordinate.x == this.pos.x +1);
            }
        }
        return false;
    }

    public boolean isValidStraightMove(Board board, Coord desiredCoordinate){
        int newY = this.pos.y;
        int newX = this.pos.x;
        int direction = 0; //1 for x, 2 for y
        while(true){
            if(direction > 0){
                if(desiredCoordinate.x == newX && desiredCoordinate.y == newY){ //return true if we reached the desired position
                    return true;
                }
                else if(board.board[newY][newX].pieceType != PieceType.X){ //return false if we are currently on top of a piece
                    System.out.println("met a piece at the following location \n new X = " + newX);
                    System.out.println("new Y = " + newY);
                    return false;
                }
            }
            if(desiredCoordinate.y == this.pos.y){
                direction = 2;
                if(desiredCoordinate.x < newX){
                    newX--;
                }
                else if(desiredCoordinate.x >= newX){
                    newX++;
                }
            }
            else if(desiredCoordinate.x == this.pos.x) {
                direction = 1;
                if (desiredCoordinate.y < newY) {
                    newY--;
                } else if (desiredCoordinate.y >= newY) {
                    newY--;
                }
            }
            else{
                return false; //not row or col adjacent to desired position
            }
            if((direction == 1 && (newX == 0 || newX == 7 )) || //x-direction and have reached a border
                    (direction ==2 && ( newY == 0 || newY == 7))){
                //if we have reached a border, break and return false
                System.out.println("new X = " + newX);
                System.out.println("new Y = " + newY);
                return false;
            }
        }
    }

    public boolean isValidRookMove(Board board, Coord desiredCoordinate){
        return (isValidStraightMove(board,desiredCoordinate) && this.pieceColor != board.board[desiredCoordinate.y][desiredCoordinate.x].pieceColor);
    }

    boolean isValidQueenMove(Board board, Coord desiredCoordinate){
        return ((isValidStraightMove(board, desiredCoordinate) || isValidDiagonalMove(board, desiredCoordinate)) // valid straight or diagonal move
                && this.pieceColor != board.board[desiredCoordinate.y][desiredCoordinate.x].pieceColor); // also not a friendly piece in desired location
    }

    boolean isValidKingMove(Board board, Coord desiredCoordinate){
        int xDiff = Math.abs(this.pos.x - desiredCoordinate.x);
        int yDiff = Math.abs(this.pos.y - desiredCoordinate.y);
        return (xDiff < 2 && yDiff < 2 && this.pieceColor != board.board[desiredCoordinate.y][desiredCoordinate.x].pieceColor);
    }

    public String toString(){
        return("" + this.pieceColor + " " + this.pieceType + " " +this.pos);
    }
//end of class
}