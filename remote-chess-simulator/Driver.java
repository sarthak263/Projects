import Network.Server.Client;
import java.io.IOException;

public class Driver {
    public static void main(String[] args){
        try {
            new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
