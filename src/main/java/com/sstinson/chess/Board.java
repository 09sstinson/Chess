package com.sstinson.chess;

import java.util.ArrayList;

public class Board{

    public static final int size = 8;
    //public Piece[] pieces; // contains all the pieces currently on the board
    public ArrayList<Piece> pieces = new ArrayList<>();
    public Piece lastMovedPiece; // stores the last moved piece for the checkEnPassant method

    Board(){
        initialiseBoard();
    }

    // Returns true if the piece can move to the position (does not include taking the position)
    public boolean tryMovePiece(Piece piece, Position position){

        return position.isInGrid(size) &&
                (piece.isValidMove(position) || checkEnPassant(piece, position) || checkCastle(piece, position)) &&
                !isPositionFilled(position) && !isMoveObstructed(piece, position) &&
                !willFriendlyKingBeChecked(piece,position);
    }

    // Returns true if the piece can move or take the position
    public boolean tryTakeTurn(Piece piece, Position position){
        return tryMovePiece(piece, position) || tryTakePiece(piece, position);
    }

    // Returns true if the piece can take the position
    public boolean tryTakePiece(Piece piece, Position position){
        return canTakePosition(piece, position) && hasEnemyPiece(piece, position);
    }

    // Returns true if the piece could take take the position,
    // assuming the position is filled with an enemy piece.
    // Note: this method does not check if the position can be taken by using an en passant move.
    //       This is because it could not take any piece at that position, only a pawn that has
    //       moved by 2 spaces last turn. Thus the en passant is classed as a move and not a take.
    public boolean canTakePosition(Piece piece, Position position){
        return position.isInGrid(size) && piece.isValidTake(position) &&
                !isMoveObstructed(piece, position) &&
                !willFriendlyKingBeChecked(piece,position);
    }

    // Returns true if the piece can be promoted
    // i.e if it is a Pawn and it is at the correct y value for promotion
    public boolean checkPromotion(Piece piece){
        if(piece.type == PieceType.PAWN){
            Pawn pawn = (Pawn) piece;
            int promoteYValue = pawn.getPromotionYValue();
            return pawn.position.y == promoteYValue;
        }
        return false;
    }

    // When a king performs a castle move, this method moves the castle into the correct spot
    public void resolveCastle(Piece piece, Position position){
        if(checkCastle(piece, position)){
            Piece castle = getPieceAtPosition(position.closestCorner(Board.size));
            Position[] positions = piece.position.getPositionsInBetween(position);
            castle.position = positions[0];
            castle.moveCounter++;
        }
    }

    // Returns true if the piece can perform a castle by moving to the position
    // TODO: make this less messy
    public boolean checkCastle(Piece piece, Position position) {
        if(piece.type == PieceType.KING){
            King king = (King) piece;
            Piece newPiece = getPieceAtPosition(position.closestCorner(Board.size));
            if(newPiece == null){
                return false;
            }
            if(newPiece.type == PieceType.CASTLE &&
              newPiece.moveCounter == 0 && king.moveCounter == 0 &&
              king.isValidCastle(position) && !isFriendlyKingChecked(king.colour)){
                Position[] positions = king.position.getPositionsInBetween(position);
                for(Position p: positions){
                    if(isPositionControlled(p, king.getEnemyColour())){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }


    // When a pawn performs a en passant move, this method removes the piece directly behind the pawn
    public void resolveEnPassant(Piece piece, Position position){
        if(checkEnPassant(piece, position)){
            Pawn pawn = (Pawn) piece;
            pieces.remove(getPieceAtPosition(position.minus(new Position(0, pawn.direction))));
        }
    }

    // Returns true if the piece can perform an en passant move by moving to the position
    // TODO: make this less messy
    public boolean checkEnPassant(Piece piece, Position position){
        if(piece.type == PieceType.PAWN){
            Pawn pawn = (Pawn) piece;
            Piece newPiece = getPieceAtPosition(position.minus(new Position(0, pawn.direction)));
            if(newPiece == null){
                return false;
            }else if(newPiece.type != PieceType.PAWN){
                return false;
            }

            if(pawn.isValidTake(position) && position.minus(newPiece.position).equals(0, pawn.direction) &&
                    newPiece == lastMovedPiece && !isPositionFilled(position)){
                return true;
            }
            return false;
        }
        return false;
    }

    // When a piece is promoted, this method replaces it with a new piece of the given type
    // at the same position and with the same colour as the original piece
    public void resolvePromotion(Piece piece, PieceType pieceType){
        int index = pieces.indexOf(piece);
        Piece p = createPieceByPieceType(pieceType, piece.colour, piece.position);
        pieces.set(index, p);
    }

    // Returns a piece of the given type at the given position with the given colour
    public Piece createPieceByPieceType(PieceType type, Colour colour, Position position){
        switch(type){
            case KING: return new King(colour, position.x, position.y);
            case QUEEN: return new Queen(colour, position.x, position.y);
            case BISHOP: return new Bishop(colour, position.x, position.y);
            case KNIGHT: return new Knight(colour, position.x, position.y);
            case CASTLE: return new Castle(colour, position.x, position.y);
            case PAWN: return new Pawn(colour, position.x, position.y);
            default: System.out.println("Error no piece of that type");return null;
        }
    }

    // Returns true if the given position is controlled by the given colour
    // i.e if any piece of the given colour could take a piece at that position
    public boolean isPositionControlled(Position position, Colour colour){
        for(Piece piece: pieces){
            if(piece.colour == colour){
                if(canTakePosition(piece, position)){
                    return true;
                }
            }
        }
        return false;
    }

        // Returns BoardState.CHECKMATE if the king of the given colour is in checkmate.
        // Otherwise returns BoardState.ACTIVE
        public BoardState checkCheckMate(Colour colour){
        if(isFriendlyKingChecked(colour)){
            if(!canFriendlyPieceTakeTurn(colour)){
                return BoardState.CHECKMATE;
            }
        }
        return BoardState.ACTIVE;
    }

    // Returns BoardState.STALEMATE if the king of the given colour is not in check
    // but no other pieces of that colour can take a turn.
    // Otherwise returns BoardState.ACTIVE
    public BoardState checkStaleMate(Colour colour){
        if(!isFriendlyKingChecked(colour)){
            if(!canFriendlyPieceTakeTurn(colour)){
                return BoardState.STALEMATE;
            }
        }
       return BoardState.ACTIVE;
    }

    // Returns true if any piece of the given colour can take a turn
    public boolean canFriendlyPieceTakeTurn(Colour colour){
        for(Piece piece: pieces){
            //Java uses short circuit evaluation so canTakeTurn only checked if piece.colour == colour
            if(piece.colour == colour && canTakeTurn(piece)){
                return true;
            }
        }
        return false;
    }

    // Returns true if the given piece can take a turn
    public boolean canTakeTurn(Piece piece) {
        Position[] positions = getAllPositionsOnBoard();
        for (Position position : positions) {
            if (tryTakeTurn(piece, position)) {
                return true;
            }
        }
        return false;
    }

    // Returns true if the king of the given colour is checked
    public boolean isFriendlyKingChecked(Colour colour){
        Piece king = getKing(colour);
        if(colour == colour.YELLOW) {
            return isPositionControlled(king.position, colour.RED);
        }else{
            return isPositionControlled(king.position, colour.YELLOW);
        }
    }

    // Returns the king of the given colour
    // Note: this method assumes there is only one king of each colour
    // so will need changing if implementing a version of chess with more than one king per colour
    public Piece getKing(Colour colour){
        for(Piece piece: pieces){
            if (piece.colour == colour && piece.type == PieceType.KING) {
                return piece;
            }
        }
        System.out.println("Error cant find a king of that colour");
        return null;
    }

    // Returns true if moving the given piece to the given position will cause the
    // king of the piece to be in check
    // Fixed Bug: does not check if en passant will remove king from check
    // Note: castling can't remove king from check
    // TODO: make this less messy
    public boolean willFriendlyKingBeChecked(Piece piece, Position position){
            Piece king = getKing(piece.colour);
            Position original = piece.position;

            Piece newPiece;
            if(checkEnPassant(piece, position)){
                Pawn pawn = (Pawn) piece;
                newPiece = getPieceAtPosition(position.minus(new Position(0, pawn.direction)));
            }else {
                newPiece = getPieceAtPosition(position);
            }

            if(newPiece != null && newPiece.colour == piece.getEnemyColour()){
                pieces.remove(newPiece);
            }
            piece.position = position;
            boolean bool = isPositionControlled(king.position, king.getEnemyColour());
            piece.position = original;
            if(newPiece != null && newPiece.colour == piece.getEnemyColour()){
                pieces.add(newPiece);
            }
            return bool;
    }

    // Returns true if there are any pieces blocking the path from
    // the given piece's current position to the given position
    public boolean isMoveObstructed(Piece piece, Position position){
        Position[] positions = position.getPositionsInBetween(piece.position);
        if(positions == null){
            return false;
        }
        for(Position p : positions){
            if(isPositionFilled(p)){
                return true;
            }
        }
        return false;
    }


    // Returns true if the given position contains a piece
    // of the opposite colour to the given piece
    public boolean hasEnemyPiece(Piece piece, Position position){
        return isPositionFilled(position) && !hasFriendlyPiece(piece, position);
    }

    // Performs the necessary operations for the given piece to take its turn to the given position
    // Resolves any special moves, removes taken piece if necessary, changes the position,
    // increments the movecounter and updates the last moved piece
    public void resolveTurn(Piece piece, Position position){
        resolveEnPassant(piece, position);
        resolveCastle(piece, position);
        if(isPositionFilled(position)){
            Piece newPiece = getPieceAtPosition(position);
            pieces.remove(newPiece);
        }
        piece.position = position;
        piece.moveCounter++;
        lastMovedPiece = piece;
    }

    // Returns true if the given position contains a piece of the given colour
    public boolean hasFriendlyPiece(Colour colour, Position position){
        Piece newPiece = getPieceAtPosition(position);
        if(newPiece == null){
            return false;
        } else{
            return colour == newPiece.colour;
        }
    }
    // Same as above but using the given piece's colour as the colour
    public boolean hasFriendlyPiece(Piece piece, Position position){
        return hasFriendlyPiece(piece.colour, position);
    }




    // Returns true if a there is a piece at the given x and y coordinates
    public boolean isPositionFilled(int x, int y){

        for(Piece piece : pieces){
            if(piece.position.equals(x,y)){
                return true;
            }
        }
        return false;
    }

    // Returns true if a there is a piece at the given position
    public boolean isPositionFilled(Position position){
        return isPositionFilled(position.x, position.y);
    }

    // Returns the piece at the given position
    // If there is no piece at the position then returns null
    public Piece getPieceAtPosition(Position position){
        for(Piece piece: pieces){
            if(piece.position.equals(position)){
                return piece;
            }
        }
        return null;
    }

    // Initialises the front and back row of pieces for both colours
    public void initialiseBoard(){
        initialisePawns();
        initialiseBackRow();
    }

    // Adds the front row of pawns to the board for both players
    public void initialisePawns(){
        for(int i = 0; i<size; i++){
            pieces.add(new Pawn(Colour.RED, i, 1 ));
            pieces.add(new Pawn(Colour.YELLOW, i, 6));
        }
    }

    // Adds the back row of pieces to the board for both players
    public void initialiseBackRow(){

        PieceType[] types;
        types = new PieceType[] {PieceType.CASTLE, PieceType.KNIGHT, PieceType.BISHOP,
                PieceType.QUEEN, PieceType.KING, PieceType.BISHOP,
                PieceType.KNIGHT, PieceType.CASTLE};
        initialiseRow(types, 0, Colour.RED);
        initialiseRow(types, 7, Colour.YELLOW);
    }


    // Adds a full row of pieces of the given types and the given colour
    // and in the given order to the board
    // Currently the PieceType[] must be the same length as the board size
    public void initialiseRow(PieceType[] types, int row, Colour colour){
        // be given some names of pieces and initialise them; e,g (pawn, pawn, pawn, castle, castle,... king)
        if(types.length != size){
            System.out.println("ERROR too few types");
            return;
        }
            int i = 0;
            for (PieceType type : types) {
                switch (type) {
                    case PAWN: pieces.add(new Pawn(colour, i++, row )); break;
                    case CASTLE: pieces.add(new Castle(colour, i++, row)); break;
                    case KNIGHT: pieces.add(new Knight(colour, i++, row)); break;
                    case BISHOP: pieces.add(new Bishop(colour, i++, row)); break;
                    case QUEEN: pieces.add(new Queen(colour, i++, row)); break;
                    case KING: pieces.add(new King(colour, i++, row)); break;
                }
            }

    }

    // Prints the board to system output
    public void printBoard(){
        for(int j = size - 1 ; j >= 0 ; j--){
            printRow(j);
        }
        printLowerIndex();
    }

    // Prints the string representation of each board position in the given row of the board
    public void printRow(int rowIndex){
        String row = "";
        Position position;
        for(int i = 0; i < size; i++){
            position = new Position(i,rowIndex);
            row = row + getBoardPositionString(position);
        }
        System.out.println(row + (char)27 + "[30m" + (rowIndex+1));
    }

    // Returns the string representation of the given board position
    public String getBoardPositionString(Position position){
        String string = "";
        String red = (char)27 + "[31m";
        String yellow = (char)27 + "[33m";
        String blue = (char)27 + "[34m";
        String tileColour;
        if((position.x + position.y)%2 == 0){
            tileColour = blue;
        }else{
            tileColour = yellow;
        }
        Piece piece = getPieceAtPosition(position);
        if(piece == null ){
            return string = tileColour +  "[ ] ";
        }else{
            switch(piece.colour) {
                case YELLOW:
                    string = tileColour + "[" + yellow + piece.getType() + tileColour + "] ";
                    break;
                case RED:
                    string = tileColour + "[" + red
                            + piece.getType() + tileColour + "] ";
                    break;
            }
        }
        return string;
    }

    // Prints the coordinates of the board with the proper spacing
    // to line up with the string representation of each position
    public void printLowerIndex(){
        String row = " ";
        for(int i = 1; i <= size; i++){
            row = row + i + "   " ;
        }
        System.out.println(row);
    }

    // Returns a position array of all the possible position in the board
    public Position[] getAllPositionsOnBoard(){
        Position[] positions = new Position[size*size];
        for(int i = 0; i < size; i++){
            for(int j =0; j < size; j++){
                positions[ size * i + j] = new Position(i,j);
            }
        }
        return positions;
    }
}
