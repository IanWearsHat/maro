package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private int clientID;

    public ClientHandler(int id) {
        clientID = id;
    }
    public void startClient(Socket sock) {

    }

    public void run() {
        try {
            Socket clientSocket = ServerSide.clientList.get(clientID);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                System.out.println(inputLine);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
