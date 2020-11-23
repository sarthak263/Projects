package ChessGame.Piece;

import java.io.Serializable;

public class Move implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Coord from, to;
    private PieceIDEnum id;
    public Move(Coord from, Coord to){
        this.from = from;
        this.to = to;
    }
    public Coord getFrom(){
        return from;
    }
    public Coord getTo(){
        return to;
    }
    public void setPiece(PieceIDEnum id){
        this.id = id;
    }
    public PieceIDEnum getPiece(){
        return id;
    }
}
