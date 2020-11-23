package ChessGame.Piece;

import ChessGame.ChessBoard;
import ChessGame.ColorEnum;
import ChessGame.Position.Position;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(ColorEnum color) {
        super(color);
        this.id = PieceIDEnum.ROOK;
        assertEquals(this.getId(), PieceIDEnum.ROOK);
    }

    @Override
    public ArrayList<Move> getMoveSet(ChessBoard board, Position position) {
        ArrayList<Move> moveSet = new ArrayList<>();
        Position[][] grid = board.getBoard();
        int row = position.getX();
        int col = position.getY();
        Coord from = new Coord(row, col);

        //checks left
        for (int colCount = col-1; colCount >= 0; colCount--) {
            if (grid[row][colCount].isEmpty())
                moveSet.add(new Move(from, new Coord(row, colCount)));
            else {
                if (grid[row][colCount].getPiece().getColor().getID() != color.getID())
                    moveSet.add(new Move(from, new Coord(row, colCount)));
                break; //encountered piece
            }
        }
        //checks right
        for (int colCount = col+1; colCount <= 7; colCount++) {
            if (grid[row][colCount].isEmpty())
                moveSet.add(new Move(from, new Coord(row, colCount)));
            else {
                if (grid[row][colCount].getPiece().getColor().getID() != color.getID())
                    moveSet.add(new Move(from, new Coord(row, colCount)));
                break; //encountered piece
            }
        }
        //checks up
        for (int rowCount = row-1; rowCount >= 0; rowCount--) {
            if (grid[rowCount][col].isEmpty())
                moveSet.add(new Move(from, new Coord(rowCount, col)));
            else {
                if (grid[rowCount][col].getPiece().getColor().getID() != color.getID())
                    moveSet.add(new Move(from, new Coord(rowCount, col)));
                break; //encountered piece
            }
        }
        //checks down
        for (int rowCount = row+1; rowCount <= 7; rowCount++) {
            if (grid[rowCount][col].isEmpty())
                moveSet.add(new Move(from, new Coord(rowCount, col)));
            else {
                if (grid[rowCount][col].getPiece().getColor().getID() != color.getID())
                    moveSet.add(new Move(from, new Coord(rowCount, col)));
                break; //encountered piece
            }
        }
        /*
        * To-Do Castling
        * */
        return moveSet;
    }

}

class RookTest {
    @Test
    public void CreateRookTest() {
        Rook rook1 = new Rook(ColorEnum.BLUE);
        Rook rook2 = new Rook(ColorEnum.BLACK);
        PieceIDEnum rook1id = rook1.getId();
        PieceIDEnum rook2id = rook1.getId();
        assertEquals(rook2id, PieceIDEnum.ROOK);
    }
}