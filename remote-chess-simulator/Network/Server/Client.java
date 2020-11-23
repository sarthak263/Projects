package Network.Server;

import ChessGame.ChessBoard;
import ChessGame.ColorEnum;
import ChessGame.Piece.Move;
import UI.Board;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final int PORT = 9222;
    private static final String URL = "127.0.0.1";

    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ColorEnum player;
    public Client() throws IOException {
        try {
            connect(URL);
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());
            System.out.println("Received A Connection");
            player = ColorEnum.BLACK;
            startGame();
        } catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
    private void readWriteLoop(Board board) throws ClassNotFoundException, IOException {
        while (true) {
            if (Board.listen == 1) {
                Move obj = (Move) socketIn.readObject();
                while (obj != null) {
                    if (obj.getPiece() != null){
                        board.makeMove(obj);
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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }

    private void promote(Board board, Move move){
        ChessBoard tempBoard = board.getBoard();
        tempBoard.promotePiece(move, move.getPiece(), ColorEnum.BLUE);
        board.updateGUI(tempBoard);
    }
    private void startGame() throws IOException, ClassNotFoundException {
        Board board = new Board(player, socketIn, socketOut);
        readWriteLoop(board);
    }
    private boolean connect(String host) {
        try {
            socket = new Socket(host, PORT);
            System.out.println("Connection to LocalHost on Port: " + PORT);
        }
        catch (IOException e) {
            return false;
        }

        return true;
    }
    public void send(Move move) throws IOException {
        socketOut.writeObject(move);
        socketOut.flush();
    }
    public void send (int id) throws IOException {
        socketOut.writeObject(id);
        socketOut.flush();
    }

}
