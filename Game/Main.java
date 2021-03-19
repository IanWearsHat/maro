package game;

import java.io.IOException;
import network.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("maromaromaromaro");
        // new Game();
        // ServerSide test2 = new ServerSide();
        // test2.startServer();

        ClientSide test1 = new ClientSide();
        test1.startClient();
        
    }
}