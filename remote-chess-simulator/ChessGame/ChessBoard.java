package ChessGame;

import ChessGame.Piece.*;
import ChessGame.Position.Position;

import java.util.ArrayList;

public class ChessBoard {
    private final PieceFactory pieceFactory = new PieceFactory();
    private final static int BOARD_ROWS = 8;
    private final static int BOARD_COLS = 8;
    private final Position[][] grid;

    private final ArrayList<Piece> bluePieces = new ArrayList<>();
    private final ArrayList<Piece> blackPieces = new ArrayList<>();
    private ColorEnum playerTurn; // 1 = blue, //2 = black

    private Move lastMove = null;
    private boolean begin = false;

    public ChessBoard() {
        grid = new Position[BOARD_ROWS][BOARD_COLS];
        initBoard();
        initBlack(ColorEnum.BLACK);
        initBlue(ColorEnum.BLUE);
        playerTurn = ColorEnum.BLUE;
    }

    public void initBoard() {
        for (int i = 0; i < BOARD_COLS; i++) {
            for (int j = 0; j < BOARD_ROWS; j++) {
                grid[i][j] = new Position(new Coord(i, j));
            }
        }
    }

    public void initBlack(final ColorEnum color) { // top of board
        final int row = 1;
        for (int i = 0; i < 8; i++) {
            grid[row][i].setPiece(pieceFactory.makePawn(color));
            blackPieces.add(pieceFactory.makePawn(color));
        }
        grid[0][0].setPiece(pieceFactory.makeRook(color));
        grid[0][7].setPiece(pieceFactory.makeRook(color));
        blackPieces.add(pieceFactory.makeRook(color));
        blackPieces.add(pieceFactory.makeRook(color));

        grid[0][1].setPiece(pieceFactory.makeKnight(color));
        grid[0][6].setPiece(pieceFactory.makeKnight(color));
        blackPieces.add(pieceFactory.makeKnight(color));
        blackPieces.add(pieceFactory.makeKnight(color));

        grid[0][2].setPiece(pieceFactory.makeBishop(color));
        grid[0][5].setPiece(pieceFactory.makeBishop(color));
        blackPieces.add(pieceFactory.makeBishop(color));
        blackPieces.add(pieceFactory.makeBishop(color));

        grid[0][3].setPiece(pieceFactory.makeQueen(color));
        blackPieces.add(pieceFactory.makeQueen(color));

        grid[0][4].setPiece(pieceFactory.makeKing(color));
        blackPieces.add(pieceFactory.makeKing(color));
    }

    public void initBlue(final ColorEnum color) { // bottom of board
        final int row = 6;
        for (int i = 0; i < 8; i++) {
            grid[row][i].setPiece(pieceFactory.makePawn(color));
            bluePieces.add(pieceFactory.makePawn(color));
        }
        grid[7][0].setPiece(pieceFactory.makeRook(color));
        grid[7][7].setPiece(pieceFactory.makeRook(color));
        bluePieces.add(pieceFactory.makeRook(color));
        bluePieces.add(pieceFactory.makeRook(color));

        grid[7][1].setPiece(pieceFactory.makeKnight(color));
        grid[7][6].setPiece(pieceFactory.makeKnight(color));
        bluePieces.add(pieceFactory.makeKnight(color));
        bluePieces.add(pieceFactory.makeKnight(color));

        grid[7][2].setPiece(pieceFactory.makeBishop(color));
        grid[7][5].setPiece(pieceFactory.makeBishop(color));
        bluePieces.add(pieceFactory.makeBishop(color));
        bluePieces.add(pieceFactory.makeBishop(color));

        grid[7][3].setPiece(pieceFactory.makeQueen(color));
        bluePieces.add(pieceFactory.makeQueen(color));

        grid[7][4].setPiece(pieceFactory.makeKing(color));
        bluePieces.add(pieceFactory.makeKing(color));
    }

    public Position[][] getBoard() {
        return grid;
    }

    public ChessBoard getChessBoard() {
        return this;
    }

    public Position getPosition(final int x, final int y) {
        return grid[x][y];
    }

    public boolean inBound(final int x, final int y) {
        if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
            return true;
        } else {
            return false;
        }
    }

    public void print() {
        for (int rows = 0; rows < BOARD_ROWS; rows++) {
            for (int cols = 0; cols < BOARD_COLS; cols++) {
                if (!(grid[rows][cols].isEmpty())) {
                    final PieceIDEnum id = grid[rows][cols].getPiece().getId();
                    System.out.print(id.getID() + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }

    }

    public void makeMove(final Move move) {
        final Coord from = move.getFrom();
        final Coord to = move.getTo();

        final int fromX = from.getX();
        final int fromY = from.getY();
        final int toX = to.getX();
        final int toY = to.getY();

        final Piece selectedPiece = grid[fromX][fromY].getPiece();
        selectedPiece.setHasMoved(true);
        grid[fromX][fromY].setEmpty();

        if (!(grid[toX][toY].isEmpty())) {
            final Piece removedPiece = grid[toX][toY].getPiece();
            removePiece(removedPiece);
        }

        grid[toX][toY].setPiece(selectedPiece);
        lastMove = move;
        if (begin == false)
            begin = true;
    }

    private void removePiece(final Piece piece) {
        final ColorEnum id = piece.getColor();
        piece.getId();
        if (id == ColorEnum.BLUE) {
            for (final Piece x : bluePieces) {
                if (x.getId() == piece.getId()) {
                    bluePieces.remove(x);
                    break;
                }
            }
        } else {
            for (final Piece x : blackPieces) {
                if (x.getId() == piece.getId()) {
                    blackPieces.remove(x);
                    break;
                }
            }
        }
    }

    public boolean checkBlueLife() {
        for (final Piece x : bluePieces) {
            if (x.getId() == PieceIDEnum.KING) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBlackLife() {
        for (final Piece x : blackPieces) {
            if (x.getId() == PieceIDEnum.KING) {
                return true;
            }
        }
        return false;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public boolean begin() {
        return begin;
    }

    public void switchTurn() {
        if (playerTurn.getID() == ColorEnum.BLUE.getID())
            playerTurn = ColorEnum.BLACK;
        else
            playerTurn = ColorEnum.BLUE;
    }

    public void promotePiece(final Move move, final PieceIDEnum pieceIDEnum, final ColorEnum colorPromote) {
        final Coord to = move.getTo();
        final int toX = to.getX();
        final int toY = to.getY();
        Piece promotedPiece = null;
        final Position position = grid[toX][toY];
        switch(pieceIDEnum.getID()){
            case "rook":
                promotedPiece = new Rook(colorPromote);
                break;
            case "queen":
                promotedPiece = new Queen(colorPromote);
                break;
            case "knight":
                promotedPiece = new Knight(colorPromote);
                break;
            case "bishop":
                promotedPiece = new Bishop(colorPromote);
                break;
        }
        removePiece(position.getPiece());
        grid[toX][toY].setPiece(promotedPiece);

    }

    public ColorEnum getPlayerTurn(){
        return playerTurn;
    }

}
