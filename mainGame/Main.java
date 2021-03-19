package mainGame;

import java.io.IOException;
import network.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("maromaromaromaro");
        // new Game();

        ClientSide client = new ClientSide();
        Thread clientThread = new Thread(client);
        clientThread.start();
        
    }
}