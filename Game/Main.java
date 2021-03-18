package game;

import java.io.IOException;
import network.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("maromaromaromaro");
        // new Game();
        // ClientSide test1 = new ClientSide();
        // test1.startClient();

        if (args.length == 1) {
            int id = Integer.parseInt(args[0]);
            if (id == 1) {
                ClientSide test = new ClientSide();
                test.startClient();
            }
            else if (id == 2) {
                ServerSide test = new ServerSide();
                test.startServer();
            }
        }
        
    }
}