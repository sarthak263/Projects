package ChessGame.Position;

import ChessGame.Piece.Coord;
import ChessGame.Piece.Piece;

public class Position {
    Coord coord;
    Piece piece = null;
    boolean empty = true;
    public Position(Coord coord){
        this.coord = coord;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
        empty = false;
    }
    public boolean isEmpty(){
        if (empty == true){
            return true;
        } else
            return false;
    }
    public int getX(){
        return coord.getX();
    }
    public int getY(){
        return coord.getY();
    }

    public Coord getCoord() {
        return coord;
    }

    public void setEmpty(){
        empty = true;
        piece = null;
    }

    public Piece getPiece(){
        if (piece != null){
            return piece;
        } else {
            return null;
        }
    }
}
