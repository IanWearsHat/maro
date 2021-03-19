package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private int clientID;
    Socket clientSocket;
    
    public ClientHandler(int id) {
        clientID = id;
    }

    public void printMessage(int ID, String message) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Client " + ID + " says: " + message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            clientSocket = ServerSide.clientList.get(clientID);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                for (int i = 0; i < ServerSide.handlerList.size(); i++) {
                    if (i != clientID) {
                        ServerSide.handlerList.get(i).printMessage(getClientID(), inputLine);
                    }
                }
                System.out.println(inputLine);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getClientID() {
        return clientID;
    }
}
