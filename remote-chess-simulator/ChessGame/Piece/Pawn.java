package ChessGame.Piece;

import ChessGame.ChessBoard;
import ChessGame.ColorEnum;
import ChessGame.Position.Position;

import java.util.ArrayList;

public class Pawn extends Piece {
    private int offset;

    public Pawn(ColorEnum color) {
        super(color);
        this.id = PieceIDEnum.PAWN;
        if (color.getID() == 1){
            offset = -1;
        } else if (color.getID() == 2){
            offset = 1;
        }
    }
    @Override
    public ArrayList<Move> getMoveSet(ChessBoard board, Position position) {
        ArrayList<Move> moveSet = new ArrayList<>();
        Position[][] grid = board.getBoard();
        int row = position.getX();
        int col = position.getY();
        Coord from = new Coord(row, col);
        int left = -1;
        int right = 1;
        if (hasMoved == false){
            Position oneAhead = grid[row + offset][col];
            Position twoAhead = grid [row + offset*2][col];
            if (oneAhead.isEmpty()) {
                moveSet.add(new Move(from, new Coord(row + offset, col)));
                if (twoAhead.isEmpty()) {
                    moveSet.add(new Move(from, new Coord(row + offset * 2, col)));
                }
            }
        }
        if(hasMoved == true){
            Position oneAhead = grid[row + offset][col];
            if (oneAhead.isEmpty()) {
                moveSet.add(new Move(from, new Coord(row + offset, col)));
            }
        }
        if (board.inBound(row + offset, col + left)){
            Position aheadAndLeft = grid[row + offset][col+left];
            if (!(aheadAndLeft.isEmpty()) && aheadAndLeft.getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(row + offset, col + left)));
            }
        }
        if(board.inBound(row + offset, col + right)){
            Position aheadAndRight = grid[row + offset][col + right];
            if (!(aheadAndRight.isEmpty()) && aheadAndRight.getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(row + offset, col + right)));
            }
        }
        /* To Do
        * En Passant
        * */
        return moveSet;
    }
}
