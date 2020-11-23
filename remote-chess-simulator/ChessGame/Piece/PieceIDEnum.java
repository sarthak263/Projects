package ChessGame.Piece;

public enum PieceIDEnum {
        PAWN("pawn"), ROOK("rook"), KNIGHT("knight"), BISHOP("bishop"), QUEEN("queen"), KING("king");
    private String id;
    private PieceIDEnum(String id){
        this.id = id;
    }
    public String getID(){
        return id;
    }
}
