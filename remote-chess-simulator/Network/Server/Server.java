package Network.Server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 9222;
    private static Thread connection = null;
    private static ServerSocket socket;

    public static void main(final String args[]) {
        try {
            socket = new ServerSocket(PORT);
            System.out.println("Server Started on \"LocalHost, Port: " + PORT + "\"");
        }
        catch (final IOException e) {
            System.out.println("Couldn't create socket on port " + PORT);
            return;
        }

        while (connection == null) {
            Socket clientSocket;
            try {
                clientSocket = socket.accept();
                connection = new Thread(new ClientHandler(clientSocket));
                connection.start();
                while(true){
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error accepting connection on socket.");
                return;
            }
        }
    }
}
