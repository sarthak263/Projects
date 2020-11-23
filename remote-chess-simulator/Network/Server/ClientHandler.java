package Network.Server;

import ChessGame.ChessBoard;
import ChessGame.ColorEnum;
import ChessGame.Piece.Move;
import UI.Board;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ColorEnum player;

    public ClientHandler(Socket clientSocket) throws IOException, ClassNotFoundException {
        this.socketOut = new ObjectOutputStream(clientSocket.getOutputStream());
        this.socketIn = new ObjectInputStream(clientSocket.getInputStream());
        player = ColorEnum.BLUE;
        startGame();
    }

    private void startGame() throws IOException, ClassNotFoundException {
        Board board = new Board(player, socketIn, socketOut);
        Board.listen = 0;
        readWriteLoop(board);
    }
    private void readWriteLoop(Board board) throws ClassNotFoundException, IOException {
        while (true) {
            if (Board.listen == 1) {
                Move obj = (Move) socketIn.readObject();
                while (obj != null) {
                    if (obj.getPiece() != null){
                        board.makeMove(obj);
//                        board = board.getBoard();
                        promote(board, obj);
                        obj = null;
                        Board.listen = 0;
                    } else {
                        board.makeMove(obj);
                        obj = null;
                        Board.listen = 0;
                    }
                }
            } else {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }
    private void promote(Board board, Move move){
        ChessBoard tempBoard = board.getBoard();
        tempBoard.promotePiece(move, move.getPiece(), ColorEnum.BLACK);
        board.updateGUI(tempBoard);
}
    @Override
    public void run() {
        System.out.println("Received a connection!");
        player = ColorEnum.BLUE;
        try {
            startGame();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
