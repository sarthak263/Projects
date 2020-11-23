package ChessGame.Piece;

import ChessGame.ChessBoard;
import ChessGame.ColorEnum;
import ChessGame.Position.Position;

import java.util.ArrayList;

public class King extends Piece {
    public King(ColorEnum color) {
        super(color);
        this.id = PieceIDEnum.KING;
    }

    @Override
    public ArrayList<Move> getMoveSet(ChessBoard board, Position position) {
        ArrayList<Move> moveSet = new ArrayList<>();
        Position[][] grid = board.getBoard();

        int row = position.getX();
        int col = position.getY();
        Coord from = new Coord(row, col);

        if (board.inBound(row - 1, col)){
            if (grid[row - 1][col].isEmpty()) {
                moveSet.add(new Move(from, new Coord(row - 1, col)));
            } else if (grid[row - 1][col].getPiece().getColor().getID() != color.getID()) {
                moveSet.add(new Move(from, new Coord(row - 1, col)));
            }
        }

        if (board.inBound(row + 1, col)){
            if (grid[row + 1][col].isEmpty()) {
                moveSet.add(new Move(from, new Coord(row + 1, col)));
            } else if (grid[row + 1][col].getPiece().getColor().getID() != color.getID()) {
                moveSet.add(new Move(from, new Coord(row + 1, col)));
            }
        }

        if (board.inBound(row, col - 1)){
            if (grid[row][col - 1].isEmpty()) {
                moveSet.add(new Move(from, new Coord(row, col - 1)));
            } else if (grid[row][col - 1].getPiece().getColor().getID() != color.getID()) {
                moveSet.add(new Move(from, new Coord(row, col - 1)));
            }
        }

        if (board.inBound(row, col + 1)){
            if (grid[row][col + 1].isEmpty()) {
                moveSet.add(new Move(from, new Coord(row, col + 1)));
            } else if (grid[row][col + 1].getPiece().getColor().getID() != color.getID()) {
                moveSet.add(new Move(from, new Coord(row, col + 1)));
            }
        }


        if (board.inBound(row - 1, col - 1)){
            if (grid[row - 1][col - 1].isEmpty()) {
                moveSet.add(new Move(from, new Coord(row - 1, col - 1)));
            } else if (grid[row - 1][col - 1].getPiece().getColor().getID() != color.getID()) {
                moveSet.add(new Move(from, new Coord(row - 1, col - 1)));
            }
        }

        if (board.inBound(row - 1, col + 1)){
            if (grid[row - 1][col + 1].isEmpty()) {
                moveSet.add(new Move(from, new Coord(row - 1, col + 1)));
            } else if (grid[row - 1][col + 1].getPiece().getColor().getID() != color.getID()) {
                moveSet.add(new Move(from, new Coord(row - 1, col + 1)));
            }
        }


        if (board.inBound(row + 1, col + 1)){
            if (grid[row + 1][col + 1].isEmpty()) {
                moveSet.add(new Move(from, new Coord(row + 1, col + 1)));
            } else if (grid[row + 1][col + 1].getPiece().getColor().getID() != color.getID()) {
                moveSet.add(new Move(from, new Coord(row + 1, col + 1)));
            }
        }

        if (board.inBound(row + 1, col - 1)){
            if (grid[row + 1][col - 1].isEmpty()) {
                moveSet.add(new Move(from, new Coord(row + 1, col - 1)));
            } else if (grid[row + 1][col - 1].getPiece().getColor().getID() != color.getID()) {
                moveSet.add(new Move(from, new Coord(row + 1, col - 1)));
            }
        }
        /*
        * To Do Castling
        * */
        return moveSet;
    }
}
