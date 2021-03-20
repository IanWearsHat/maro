package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private int clientID;
    private String name;
    Socket clientSocket;
    
    public ClientHandler(int id) {
        clientID = id;
    }

    //all this does is print a message to the client that the clienthandler is handling.
    //Could be expanded to fit any message from the server, but for now it's just for what
    //another player is saying.
    private void printMessageToClient(String inputName, String message) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(inputName + " says: " + message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //says a message to every client that's not the broadcaster
    private void broadcast(String message) {
        for (int i = 0; i < ServerSide.handlerList.size(); i++) {
            if (i != clientID) {
                ServerSide.handlerList.get(i).printMessageToClient(name, message);
            }
        }
    }
    
    public void run() {
        try {
            clientSocket = ServerSide.clientList.get(clientID);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
            out.println("Please enter your name: "); // first thing the handler does is ask for a name, which will identify who has said what later on
            name = in.readLine();

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                broadcast(inputLine); //broadcasts whatever has been inputted to every other client that isn't the broadcaster
                System.out.println(name + " says: " + inputLine);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //tell every client that a certain client has left. This only runs when the try block finishes, meaning the client leaves. 
        broadcast(name + " has left the game.");
        System.out.println(name + " has lost connection. Handler " + clientID + " closing."); // server message to indicate that a client has lost connection. 
    }

    public String getName() {
        return name;
    }
    public int getClientID() {
        return clientID;
    }
}
