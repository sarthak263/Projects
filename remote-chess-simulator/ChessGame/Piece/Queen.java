package ChessGame.Piece;

import ChessGame.ChessBoard;
import ChessGame.ColorEnum;
import ChessGame.Position.Position;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(ColorEnum color) {
        super(color);
        this.id = PieceIDEnum.QUEEN;
    }

    @Override
    public ArrayList<Move> getMoveSet(ChessBoard board, Position position) {
        ArrayList<Move> moveSet = new ArrayList<>();
        ColorEnum clonedColor = position.getPiece().getColor();

        Bishop clonedBishop = new Bishop(clonedColor);
        Rook clonedRook = new Rook(clonedColor);

        moveSet = clonedBishop.getMoveSet(board, position);
        moveSet.addAll(clonedRook.getMoveSet(board, position));

        return moveSet;
    }

}
