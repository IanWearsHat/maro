package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private void printMessageToClient(boolean serverMessage, String inputName, String message) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream()); 
            if (serverMessage) {
                out.writeObject(new Packet(0, message));
            }
            else{
                out.writeObject(new Packet(0, inputName + " says: " + message));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Broadcasts a message to every client that isn't the client broadcasting.
     * @param serverMessage If true, sends a message from the server. if false, the client is saying something that's shared with everyone
     * @param message The message being sent
     */
    private void broadcast(boolean serverMessage, String message) {
        for (int i = 0; i < ServerSide.handlerList.size(); i++) {
            if (i == clientID) {
                ServerSide.handlerList.get(i).printMessageToClient(serverMessage, name, message);
            }
        }
    }
    
    public void run() {
        try {
            clientSocket = ServerSide.clientList.get(clientID);

            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream()); 
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            
            out.writeObject(new Packet(0, "Please enter your name: ")); // first thing the handler does is ask for a name, which will identify who has said what later on
            Packet received = (Packet) in.readObject();
            name = received.message;
            System.out.println("Name: " + name);
    
            
            String inputLine;
            boolean running = true;
            while (running) {
                received = (Packet) in.readObject();
                inputLine = received.message;

                if (inputLine != null ) {
                    broadcast(false, inputLine);
                    System.out.println(name + " says: " + inputLine);
                }
                else {
                    running = false;
                }
            }

            // while ((inputLine = (Packet) in.readObject()) != null) {
            //     broadcast(false, inputLine); //broadcasts whatever has been inputted to every other client that isn't the broadcaster
            //     System.out.println(name + " says: " + inputLine);
            // }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //tell every client that a certain client has left. This only runs when the try block finishes, meaning the client leaves. 
        broadcast(true, name + " has left the game.");
        System.out.println(name + " has lost connection. Handler " + clientID + " closing."); // server message to indicate that a client has lost connection. 
    }

    public String getName() {
        return name;
    }
    public int getClientID() {
        return clientID;
    }
}
