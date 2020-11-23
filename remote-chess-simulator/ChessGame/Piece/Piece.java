package ChessGame.Piece;

import ChessGame.ChessBoard;
import ChessGame.Position.Position;
import ChessGame.ColorEnum;
import java.util.ArrayList;

public abstract class Piece {
    protected ColorEnum color;
    protected PieceIDEnum id;
    protected Position position;
    protected boolean hasMoved = false;

    public Piece(ColorEnum color){
        this.color = color;
    }
    public void initPiece(Position position){
        this.position = position;
    }
    public abstract ArrayList<Move> getMoveSet(ChessBoard board, Position position);
    public ColorEnum getColor(){
        return color;
    }
    public PieceIDEnum getId(){
        return id;
    }
    public void setHasMoved (boolean value){
        hasMoved = value;
    }
}
