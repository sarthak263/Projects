package UI;

import ChessGame.ChessBoard;
import ChessGame.ColorEnum;
import ChessGame.Piece.*;
import ChessGame.Position.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Board extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final int WIDTH = 640;
    private final int HEIGHT = 640;
    private final int ROWS = 8;
    private final int COLS = 8;
    private final int TILE_WIDTH = WIDTH/8;
    private final int TILE_HEIGHT = HEIGHT/ 8;
    private final Dimension OUTER_FRAME_DIMENSION = new Dimension(WIDTH, HEIGHT);
    private final Dimension BOARD_PANEL_DIMENSON = new Dimension(WIDTH, HEIGHT);
    private final Dimension TILE_PANEL_DIMENSION = new Dimension(TILE_WIDTH, TILE_HEIGHT);

    private final Color WHITE = new Color(255, 255,255);
    private final Color BLUE = new Color(203, 241, 245);
    private Position sourcePosition = null;
    private Position destinationPosition = null;
    private Piece selectedPiece = null;
    private int selectedPieceX = -1;
    private int selectedPieceY = -1;


    private ChessBoard board;
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private boolean gameOver = false;
    private boolean promotePanel = false;
    private Move movePromote = null;
    private ColorEnum colorPromote = null;

    private ColorEnum player;
    private ObjectOutputStream socketOut;
    private ColorEnum playerTurn;

    public static int listen = 0;

    public Board(ColorEnum player, ObjectInputStream socketIn, ObjectOutputStream socketOut) {
        this.player = player;
        this.socketOut = socketOut;

        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.board = new ChessBoard();
        playerTurn = board.getPlayerTurn();
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        repaint();
        this.gameFrame.setVisible(true);
    }

    public void updateGUI(ChessBoard board) {
        this.board = board;
        boardPanel.drawBoard(board);
    }

    public void makeMove(Move move) {
        board.makeMove(move);
        board = board.getChessBoard();
        board.switchTurn();
        playerTurn = board.getPlayerTurn();
        boardPanel.drawBoard(board);
    }

    public ChessBoard getBoard() {
        return board;
    }

    private class BoardPanel extends JPanel {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        SquarePanel[][] squares;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.squares = new SquarePanel[ROWS][COLS];
            for (int rows = 0; rows < ROWS; rows++) {
                for (int cols = 0; cols < COLS; cols++) {
                    final SquarePanel squarePanel = new SquarePanel(rows, cols);
                    squares[rows][cols] = squarePanel;
                    add(squarePanel);
                }
            }
            setPreferredSize(BOARD_PANEL_DIMENSON);
            validate();
        }

        public void drawBoard(final ChessBoard board) {
            if (gameOver == false) {
                if (promotePanel == true) {
                    removeAll();
                    for (int num = 0; num < 4; num++) {
                        final PromotionPanel promotionPanel;
                        try {
                            promotionPanel = new PromotionPanel(num);
                            add(promotionPanel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    validate();
                    repaint();
                } else {
                    removeAll();
                    for (int rows = 0; rows < ROWS; rows++) {
                        for (int cols = 0; cols < COLS; cols++) {
                            final SquarePanel squarePanel = squares[rows][cols];
                            squarePanel.drawSquarePanel(board);
                            add(squarePanel);
                        }
                    }
                    validate();
                    repaint();
                }
            } else {
                removeAll();
            }
        }

        private void resetPromote() {
            movePromote = null;
            promotePanel = false;
            colorPromote = null;
        }
    }

    private class SquarePanel extends JPanel {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private final int row;
        private final int col;

        SquarePanel(final int row, final int col) {
            super(new GridBagLayout());
            this.row = row;
            this.col = col;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(board);
            assignListener();
            validate();
        }

        private void assignListener() {
            try {
                MouseListener listener = this.getMouseListeners()[0];
                this.removeMouseListener(listener);
            } catch (Exception e) {
            }
            if (playerTurn.getID() == player.getID()) {
                this.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (isRightMouseButton(e)) {
                            resetClickedPositionAndPieces();
                        } else if (isLeftMouseButton(e)) {
                            if (sourcePosition == null) {
                                sourcePosition = board.getPosition(row, col);
                                selectedPiece = sourcePosition.getPiece();
                                if (selectedPiece == null) {
                                    sourcePosition = null;
                                } else {
                                    if (selectedPiece.getColor().getID() == player.getID()) {
                                        selectedPieceX = row;
                                        selectedPieceY = col;
                                    } else {
                                        selectedPiece = null;
                                        sourcePosition = null;
                                    }
                                }
                            } else {
                                destinationPosition = board.getPosition(row, col);
                                Coord from = sourcePosition.getCoord();
                                Coord to = destinationPosition.getCoord();
                                ArrayList<Move> moveSet = selectedPiece.getMoveSet(board, sourcePosition);

                                for (Move x : moveSet) {
                                    Coord checkFrom = x.getFrom();
                                    Coord checkTo = x.getTo();
                                    if (Coord.checkEquality(from, checkFrom) && Coord.checkEquality(to, checkTo)) {
                                        boolean proceed = false;
                                        if (selectedPiece.getId() == PieceIDEnum.PAWN) {
                                            ColorEnum checkPromoteColor = selectedPiece.getColor();
                                            int promoteXCoord = checkTo.getX();
                                            int promoteYCoord = checkTo.getY();

                                            if (checkPromoteColor.getID() == ColorEnum.BLUE.getID()) {
                                                if (promoteXCoord == 0) {
                                                    for (int i = 0; i < 8; i++) {
                                                        if (promoteYCoord == i) {
                                                            colorPromote = ColorEnum.BLUE;
                                                            proceed = true;
                                                        }
                                                    }
                                                }
                                            } else if (checkPromoteColor.getID() == ColorEnum.BLACK.getID()) {
                                                if (promoteXCoord == 7) {
                                                    for (int i = 0; i < 8; i++) {
                                                        if (promoteYCoord == i) {
                                                            colorPromote = ColorEnum.BLACK;
                                                            proceed = true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (proceed) {
                                            movePromote = x;
                                            promotePanel = true;
                                        } else {
                                            board.makeMove(x);
                                            board.switchTurn();
                                            playerTurn = board.getPlayerTurn();
                                            try {
                                                socketOut.writeObject(x);
                                                socketOut.flush();
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                        break;
                                    }
                                    board = board.getChessBoard();
                                }
                                listen = 0;
                                resetClickedPositionAndPieces();
                            }
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (board.checkBlueLife() == false) {
                                        boardPanel.drawBoard(board);
                                        gameOver = true;
                                        System.out.println("Game Over, Winner = BLACK");
                                    } else if (board.checkBlackLife() == false) {
                                        boardPanel.drawBoard(board);
                                            gameOver = true;
                                            System.out.println("Game Over, Winner = BLUE");
                                        } else {
                                            boardPanel.drawBoard(board);
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                } else {
                    listen = 1;
                }
            }
            private void highlightLegalMoves(final ChessBoard board){
                if (selectedPiece != null) {
                    if (selectedPiece.getColor().getID() == player.getID()) {
                        for (final Move move : selectedPiece.getMoveSet(board, sourcePosition)) {
                            Coord to = move.getTo();
                            int x = to.getX();
                            int y = to.getY();
                            if ((x == row) && (y == col)) {
                                try {
                                    final BufferedImage image = ImageIO.read(getClass().getResource("/icons/misc/green_dot.png"));
                                    add(new JLabel(new ImageIcon(image)));
                                } catch (final IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

            private void highlightTile(final ChessBoard board) {
                if(selectedPiece != null){
                    if (row == selectedPieceX && col == selectedPieceY) {
                        if (player.getID() == ColorEnum.BLUE.getID()) {
                            setBorder(BorderFactory.createLineBorder(Color.cyan, 3));
                        } else
                            setBorder(BorderFactory.createLineBorder(Color.black, 3));
                    } else {
                        setBorder(null);
                    }
                }
            }

            private void resetClickedPositionAndPieces(){
                sourcePosition = null;
                destinationPosition = null;
                selectedPiece = null;
            }
            private void assignTileColor(){
                setBackground(row%2 == col%2 ? WHITE : BLUE);
            }

            private void assignTilePieceIcon(ChessBoard board){
                this.removeAll();
                try{
                    Position[][] grid = board.getBoard();
                    if(!(grid[row][col].isEmpty())){
                        Piece piece = board.getBoard()[row][col].getPiece();
                        ColorEnum color = piece.getColor();
                        String colorFolder;
                        if (color.getID() == 1)
                            colorFolder = "blue";
                        else
                            colorFolder = "black";

                        PieceIDEnum name = board.getBoard()[row][col].getPiece().getId();

                        String path = "/icons/" + colorFolder + "/" + name.getID() + ".gif";
                        final BufferedImage image = ImageIO.read(getClass().getResource(path));
                        add(new JLabel(new ImageIcon(image)));
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            private void highLightLastMove(final ChessBoard board){
                if (board.begin() == true) {
                    Move move = board.getLastMove();
                    int lastX = move.getFrom().getX();
                    int lastY = move.getFrom().getY();
                    if (row == lastX && col == lastY) {
                        setBorder(BorderFactory.createLineBorder(Color.gray, 3));
                    } else {
                        setBorder(null);
                    }
                }
            }

            public void drawSquarePanel(ChessBoard board){
                assignTileColor();
                assignTilePieceIcon(board);
                assignListener();
                highLightLastMove(board);
                highlightLegalMoves(board);
                highlightTile(board);
                validate();
                repaint();
            }
    }

    private class PromotionPanel extends JPanel{
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        PromotionPanel(final int num) throws IOException {
            super(new GridBagLayout());
            int id = num;
            setPreferredSize(TILE_PANEL_DIMENSION);

            String colorFolder;
            if (colorPromote.getID() == 1)
                colorFolder = "blue";
            else
                colorFolder = "black";

            String path = "/icons/" + colorFolder + "/";
            switch (num){
                case 0:
                    path += "rook.gif";
                    break;
                case 1:
                    path += "bishop.gif";
                    break;
                case 2:
                    path += "queen.gif";
                    break;
                case 3:
                    path += "knight.gif";
                    break;
            }
            final BufferedImage image = ImageIO.read(getClass().getResource(path));
            add(new JLabel(new ImageIcon(image)));
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (isLeftMouseButton(e)) {
                        board.makeMove(movePromote);
                        board.switchTurn();
                        playerTurn = board.getPlayerTurn();
                        switch (id){
                            case 0:
                                board.promotePiece(movePromote, PieceIDEnum.ROOK, colorPromote);
                                movePromote.setPiece(PieceIDEnum.ROOK);
                                break;
                            case 1:
                                board.promotePiece(movePromote, PieceIDEnum.BISHOP, colorPromote);
                                movePromote.setPiece(PieceIDEnum.BISHOP);
                                break;
                            case 2:
                                board.promotePiece(movePromote, PieceIDEnum.QUEEN, colorPromote);
                                movePromote.setPiece(PieceIDEnum.QUEEN);
                                break;
                            case 3:
                                board.promotePiece(movePromote, PieceIDEnum.KNIGHT, colorPromote);
                                movePromote.setPiece(PieceIDEnum.KNIGHT);
                                break;
                        }
                        try {
                            socketOut.writeObject(movePromote);
                            socketOut.flush();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    board = board.getChessBoard();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boardPanel.resetPromote();
                            boardPanel.drawBoard(board);
                        }
                    });
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }

}
