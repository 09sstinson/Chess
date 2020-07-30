package com.sstinson.chess;

// add checkEnPassant function, will need moveHistory or lastMovedPiece
// add checkCheckMate function, will need to check if a friendly move puts the king in check,
//      will also need to check if a friendly piece can obstruct a piece to remove check
// add checkKingCastle function to tryMovePiece function
// add promotePawn method
public class Board {

    public static final int size = 8;
    public Piece[] pieces;
    public Piece lastMovedPiece;

    Board(){
        initialiseBoard();
    }

    // dont need isInBoard
    public boolean tryMovePiece(Piece piece, Position position){

        return position.isInBoard() &&
                (piece.isValidMove(position) || checkEnPassant(piece, position) || checkCastle(piece, position)) &&
                !isPositionFilled(position) && !isMoveObstructed(piece, position) &&
                !willFriendlyKingBeChecked(piece,position);
    }

    public boolean tryTakeTurn(Piece piece, Position position){
        return tryMovePiece(piece, position) || tryTakePiece(piece, position);
    }

    public boolean tryTakePiece(Piece piece, Position position){
        return canTakePosition(piece, position) && hasEnemyPiece(piece, position);
    }

    // Assuming the position is filled with an enemy piece could the piece take it
    public boolean canTakePosition(Piece piece, Position position){
        return position.isInBoard() && piece.isValidTake(position) &&
                !isMoveObstructed(piece, position) &&
                !willFriendlyKingBeChecked(piece,position);
    }

    public boolean checkPromotion(Piece piece){
        if(piece.type == PieceType.PAWN){
            Pawn pawn = (Pawn) piece;
            int promoteYValue = pawn.getPromotionYValue();
            return pawn.position.y == promoteYValue;
        }
        return false;
    }

    public void resolveCastle(Piece piece, Position position){
        if(checkCastle(piece, position)){
            Piece castle = getPieceAtPosition(position.closestCorner());
            Position[] positions = piece.position.getPositionsInBetween(position);
            castle.position = positions[0];
            castle.moveCounter++;
        }
    }

    public boolean checkCastle(Piece piece, Position position) {
        if(piece.type == PieceType.KING){
            King king = (King) piece;
            Piece newPiece = getPieceAtPosition(position.closestCorner());
            if(newPiece == null){
                return false;
            }
            if(newPiece.type == PieceType.CASTLE &&
              newPiece.moveCounter == 0 && king.moveCounter == 0 &&
              king.isValidCastle(position)){
                //check if position is controlled
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


    public void resolveEnPassant(Piece piece, Position position){
        if(checkEnPassant(piece, position)){
            Pawn pawn = (Pawn) piece;
            removePiece(getPieceAtPosition(position.minus(new Position(0, pawn.direction))));
        }
    }

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
                    newPiece == lastMovedPiece){
                return true;
            }
            return false;
        }
        return false;
    }

    public void resolvePromotion(Piece piece, PieceType pieceType){
        int index = getIndexOfPiece(piece);
        pieces[index] = createPieceByPieceType(pieceType, piece.colour, piece.position);
    }

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
    public BoardState checkCheckMate(Colour colour){
        if(isFriendlyKingChecked(colour)){
            if(!canFriendlyPieceTakeTurn(colour)){
                return BoardState.CHECKMATE;
            }
        }
        return BoardState.ACTIVE;
    }

    public BoardState checkStaleMate(Colour colour){
        if(!isFriendlyKingChecked(colour)){
            if(!canFriendlyPieceTakeTurn(colour)){
                return BoardState.STALEMATE;
            }
        }
       return BoardState.ACTIVE;
    }

    public boolean canFriendlyPieceTakeTurn(Colour colour){
        for(Piece piece: pieces){
            //Java uses short circuit evaluation so canTakeTurn only checked if piece.colour == colour
            if(piece.colour == colour && canTakeTurn(piece)){
                return true;
            }
        }
        return false;
    }
    public boolean canTakeTurn(Piece piece) {
        Position[] positions = getAllPositionsOnBoard();
        for (Position position : positions) {
            if (tryTakeTurn(piece, position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFriendlyKingChecked(Piece piece){
        Piece king = getKing(piece.colour);
        return isPositionControlled(king.position, piece.getEnemyColour());
    }

    public boolean isFriendlyKingChecked(Colour colour){
        Piece king = getKing(colour);
        if(colour == colour.YELLOW) {
            return isPositionControlled(king.position, colour.BLUE);
        }else{
            return isPositionControlled(king.position, colour.YELLOW);
        }
    }

    public Piece getKing(Colour colour){
        for(Piece piece: pieces){
            if (piece.colour == colour && piece.type == PieceType.KING) {
                return piece;
            }
        }
        System.out.println("Error cant find a king of that colour");
        return null;
    }

    // Returns true if moving the piece to the position will cause the king to be in check
    public boolean willFriendlyKingBeChecked(Piece piece, Position position){

            if(containsEnemyKing(piece, position)){
                //Friendly king will not be checked in this case because the opposing
                //king will be captured, ending the game
                return false;
            }
            Piece king = getKing(piece.colour);
            Position original = piece.position;
            Piece newPiece = getPieceAtPosition(position);


            if(newPiece != null && newPiece.colour == piece.getEnemyColour()){
                removePiece(newPiece);
            }
            piece.position = position;
            boolean bool = isPositionControlled(king.position, king.getEnemyColour());
            piece.position = original;
            if(newPiece != null && newPiece.colour == piece.getEnemyColour()){
                appendPiece(newPiece);
            }
            return bool;
    }

    public boolean containsEnemyKing(Piece piece, Position position){
        Piece king = getKing(piece.getEnemyColour());
        return position.equals(king.position);
    }

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


    public boolean hasEnemyPiece(Piece piece, Position position){
        return isPositionFilled(position) && !hasFriendlyPiece(piece, position);
    }

    public void resolveMove(Piece piece, Position position){
        resolveEnPassant(piece, position);
        resolveCastle(piece, position);
        if(isPositionFilled(position)){
            Piece newPiece = getPieceAtPosition(position);
            removePiece(newPiece);
        }
        piece.position = position;
        piece.moveCounter++;
        lastMovedPiece = piece;
    }



    public boolean hasFriendlyPiece(Piece piece, Position position){
            Piece newPiece = getPieceAtPosition(position);
            if(newPiece == null){
                return false;
            } else{
                return piece.colour == newPiece.colour;
            }
    }

    public boolean hasFriendlyPiece(Colour colour, Position position){
        Piece newPiece = getPieceAtPosition(position);
        if(newPiece == null){
            return false;
        } else{
            return colour == newPiece.colour;
        }
    }

    public boolean isPositionFilled(Position position){
        return isPositionFilled(position.x, position.y);
    }

    public boolean isPositionFilled(int x, int y){

        for(Piece piece : pieces){
            if(piece.position.equals(x,y)){
                return true;
            }
        }
        return false;
    }

    public Piece getPieceAtPosition(Position position){
        for(Piece piece: pieces){
            if(piece.position.equals(position)){
                return piece;
            }
        }
        return null;
    }

    public Piece getPieceAtPosition(int x, int y){
        Position position = new Position(x,y);
        return getPieceAtPosition(position);
    }

    public void initialiseBoard(){
        initialisePawns();
        initialiseBackRow();
    }

    public void initialisePawns(){
        for(int i = 0; i<size; i++){
            appendPiece(new Pawn(Colour.BLUE, i, 1 ), new Pawn(Colour.YELLOW, i, 6));
        }
    }

    public int getIndexOfPiece(Piece piece){
        for(int i = 0; i < pieces.length; i++){
            if(piece == pieces[i]){
                return i;
            }
        }
        System.out.println("Error piece not found");
        return -1;
    }

    public void initialiseBackRow(){

        PieceType[] types;
        types = new PieceType[] {PieceType.CASTLE, PieceType.KNIGHT, PieceType.BISHOP,
                                    PieceType.QUEEN, PieceType.KING, PieceType.BISHOP,
                                                    PieceType.KNIGHT, PieceType.CASTLE};
        initialiseRow(types, 0, Colour.BLUE);
        initialiseRow(types, 7, Colour.YELLOW);
    }

    public void initialiseRow(PieceType[] types, int row, Colour colour){
        // be given some names of pieces and initialise them; e,g (pawn, pawn, pawn, castle, castle,... king)
        if(types.length != size){
            System.out.println("ERROR too few types");
            return;
        }
            int i = 0;
            for (PieceType type : types) {
                switch (type) {
                    case PAWN: appendPiece(new Pawn(colour, i++, row )); break;
                    case CASTLE: appendPiece(new Castle(colour, i++, row)); break;
                    case KNIGHT: appendPiece(new Knight(colour, i++, row)); break;
                    case BISHOP: appendPiece(new Bishop(colour, i++, row)); break;
                    case QUEEN: appendPiece(new Queen(colour, i++, row)); break;
                    case KING: appendPiece(new King(colour, i++, row)); break;
                }
            }

    }




    public void removePiece(Piece piece1){
        int i=0;
        for(Piece piece : pieces){
            if(piece.equals(piece1)){
                removePieceAtIndex(i);
            }
            i++;
        }
    }

    public void removePieceAtIndex(int index){

        if(pieces == null || index < 0 || index >= pieces.length){
            System.out.println("array is empty or index out of bounds");
            return;
        }
        Piece[] newPieces = new Piece[pieces.length - 1];

        for(int i = 0, k = 0; i < pieces.length ; i++){
            if(i == index){
                continue;
            }
            newPieces[k++] = pieces[i];
        }
        pieces = newPieces;
    }

    //Make work with multiple piece arguments to clean up initialisePawn method (ellipse)
    public void appendPiece(Piece piece){

        if(pieces == null){
            pieces = new Piece[] {piece};
            return;
        }

        Piece[] newPieces = new Piece[pieces.length + 1];
        for(int i = 0; i < pieces.length ; i++){
            newPieces[i] = pieces[i];
        }
        newPieces[pieces.length] = piece;
        pieces = newPieces;
    }

    public void appendPiece(Piece... newPieces){
        for(Piece piece : newPieces){
            appendPiece(piece);
        }
    }
    public void printPieces(){
        for(Piece piece : pieces){
            System.out.println(piece.getType() + " position " + piece.position.x + " " + piece.position.y);
        }
    }

    public void printBoard(){
        for(int j = size - 1 ; j >= 0 ; j--){
            printRow(j);
        }
        printLowerIndex();
    }

//    public void printRow(int rowIndex){
//        String row = "";
//        Piece piece;
//        for(int i = 0; i < size; i++){
//            piece = getPieceAtPosition(i,rowIndex);
//            row = row + getPieceString(piece);
//        }
//        System.out.println(row + (rowIndex+1));
//    }

    public void printRow(int rowIndex){
        String row = "";
        Position position;
        for(int i = 0; i < size; i++){
            position = new Position(i,rowIndex);
            row = row + getBoardPositionString(position);
        }
        System.out.println(row + (char)27 + "[30m" + (rowIndex+1));
    }

    public String getPieceString(Piece piece){
        String string = "";
        if(piece == null ){
            string =  "[ ] ";
        }else{
            switch(piece.colour) {
                case YELLOW:
                    string = (char)27 + "[30m" + "[" + piece.getType() + "] ";
                    break;
                case BLUE:
                    string = (char)27 + "[30m" + "[" + (char)27 + "[31m"
                            + piece.getType() + (char)27 + "[30m" + "] ";
                    break;
            }
        }
        return string;
    }

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
                case BLUE:
                    string = tileColour + "[" + red
                            + piece.getType() + tileColour + "] ";
                    break;
            }
        }
        return string;
    }

    public void printLowerIndex(){
        String row = " ";
        for(int i = 1; i <= size; i++){
            row = row + i + "   " ;
        }
        System.out.println(row);
    }

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
