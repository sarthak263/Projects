package ChessGame.Piece;

import ChessGame.ColorEnum;

public class PieceFactory {
    public Piece makePawn(ColorEnum color){
        return new Pawn(color);
    }
    public Piece makeRook(ColorEnum color){
        return new Rook(color);
    }
    public Piece makeKnight(ColorEnum color){
        return new Knight(color);
    }
    public Piece makeQueen(ColorEnum color){
        return new Queen(color);
    }
    public Piece makeKing(ColorEnum color){
        return new King(color);
    }
    public Piece makeBishop(ColorEnum color){
        return new Bishop(color);
    }
}
