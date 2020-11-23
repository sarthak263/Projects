package ChessGame.Piece;

import ChessGame.ChessBoard;
import ChessGame.ColorEnum;
import ChessGame.Position.Position;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(ColorEnum color) {
        super(color);
        this.id = PieceIDEnum.KNIGHT;
    }


    @Override
    public ArrayList<Move> getMoveSet(ChessBoard board, Position position) {
        ArrayList<Move> moveSet = new ArrayList<>();
        Position[][] grid = board.getBoard();
        int row = position.getX();
        int col = position.getY();
        Coord from = new Coord(row, col);

        if (board.inBound(row - 1, col - 2)){
            if (grid[row - 1][col - 2].isEmpty()){
                moveSet.add(new Move(from, new Coord(row - 1, col - 2)));
            } else if (grid[row - 1][col - 2].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(row - 1, col - 2)));
            }
        }

        if (board.inBound(row - 1, col + 2)){
            if (grid[row - 1][col + 2].isEmpty()){
                moveSet.add(new Move(from, new Coord(row - 1, col + 2)));
            } else if (grid[row - 1][col + 2].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(row - 1, col + 2)));
            }
        }

        if (board.inBound(row + 1, col - 2)){
            if (grid[row + 1][col - 2].isEmpty()){
                moveSet.add(new Move(from, new Coord(row + 1, col - 2)));
            } else if (grid[row + 1][col - 2].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(row + 1, col - 2)));
            }
        }

        if (board.inBound(row + 1, col + 2)){
            if (grid[row + 1][col + 2].isEmpty()){
                moveSet.add(new Move(from, new Coord(row + 1, col + 2)));
            } else if (grid[row + 1][col + 2].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(row + 1, col + 2)));
            }
        }

        if (board.inBound(row - 2, col + 1)){
            if (grid[row - 2][col + 1].isEmpty()){
                moveSet.add(new Move(from, new Coord(row - 2, col + 1)));
            } else if (grid[row - 2][col + 1].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(row - 2, col + 1)));
            }
        }

        if (board.inBound(row - 2, col - 1)){
            if (grid[row - 2][col - 1].isEmpty()){
                moveSet.add(new Move(from, new Coord(row - 2, col - 1)));
            } else if (grid[row - 2][col - 1].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(row - 2, col - 1)));
            }
        }

        if (board.inBound(row + 2, col + 1)){
            if (grid[row + 2][col + 1].isEmpty()){
                moveSet.add(new Move(from, new Coord(row + 2, col + 1)));
            } else if (grid[row + 2][col + 1].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(row + 2, col + 1)));
            }
        }

        if (board.inBound(row + 2, col - 1)){
            if (grid[row + 2][col - 1].isEmpty()){
                moveSet.add(new Move(from, new Coord(row + 2, col - 1)));
            } else if (grid[row + 2][col - 1].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(row + 2, col - 1)));
            }
        }
        return moveSet;
    }

}
