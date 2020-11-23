package ChessGame.Piece;

import ChessGame.ChessBoard;
import ChessGame.ColorEnum;
import ChessGame.Position.Position;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(ColorEnum color) {
        super(color);
        this.id = PieceIDEnum.BISHOP;
    }

    @Override
    public ArrayList<Move> getMoveSet(ChessBoard board, Position position) {
        Position[][] grid = board.getBoard();
        ArrayList<Move> moveSet = new ArrayList<Move>();

        int row = position.getX();
        int col = position.getY();
        Coord from = new Coord(row, col);
        int colIndex, rowIndex;

        //North-West Diagonal
        for (rowIndex = row - 1, colIndex = col - 1; colIndex >= 0 && rowIndex >= 0; colIndex--, rowIndex--){
            if(grid[rowIndex][colIndex].isEmpty()){
                moveSet.add(new Move(from, new Coord(rowIndex, colIndex)));
            } else if (grid[rowIndex][colIndex].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(rowIndex, colIndex)));
                break;
            } else {
                break;
            }
        }

        //North-East Diagonal
        for (rowIndex = row - 1, colIndex = col + 1; colIndex <= 7 && rowIndex >= 0; colIndex ++, rowIndex--){
            if(grid[rowIndex][colIndex].isEmpty()){
                moveSet.add(new Move(from, new Coord(rowIndex, colIndex)));
            } else if (grid[rowIndex][colIndex].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(rowIndex, colIndex)));
                break;
            } else {
                break;
            }
        }

        //South-west Diagonal
        for (rowIndex = row + 1, colIndex = col - 1; colIndex >= 0 && rowIndex <= 7; colIndex --, rowIndex++){
            if(grid[rowIndex][colIndex].isEmpty()){
                moveSet.add(new Move(from, new Coord(rowIndex, colIndex)));
            } else if (grid[rowIndex][colIndex].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(rowIndex, colIndex)));
                break;
            } else {
                break;
            }
        }
        //South-east Diagonal
        for (rowIndex = row + 1, colIndex = col + 1; colIndex <= 7 && rowIndex <= 7; colIndex ++, rowIndex++){
            if(grid[rowIndex][colIndex].isEmpty()){
                moveSet.add(new Move(from, new Coord(rowIndex, colIndex)));
            } else if (grid[rowIndex][colIndex].getPiece().getColor().getID() != color.getID()){
                moveSet.add(new Move(from, new Coord(rowIndex, colIndex)));
                break;
            } else {
                break;
            }
        }
        return moveSet;
    }
}
