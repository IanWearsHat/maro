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
    ObjectOutputStream outStream;
    
    public ClientHandler(int id) {
        clientID = id;
    }

    /**
     * Prints a message to the client that the client handler is handling, whether that be 
     * from the server or from another client. 
     * 
     * @param outStream The client's output stream, so we keep using only one output stream
     * @param serverMessage True prints a message as it is, false is for when another client is chatting 
     * @param inputName The name of the client that sent the message if serverMessage is false. Should usually just be name (ex. printMessageToClient(out, false, name, "play val"))
     * @param message The message that is sent, whether from server or otherwise
     */
    private void printMessageToClient(ObjectOutputStream outStream, boolean serverMessage, String inputName, String message) {
        try {
            if (serverMessage) {
                outStream.writeObject(new Packet(0, message));
            }
            else{
                outStream.writeObject(new Packet(0, inputName + " says: " + message));
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
            if (i != clientID) {
                ServerSide.handlerList.get(i).printMessageToClient(ServerSide.handlerList.get(i).getOutStream(), serverMessage, name, message);
            }
        }
    }
    
    public void run() {
        try {
            clientSocket = ServerSide.clientList.get(clientID);

            outStream = new ObjectOutputStream(clientSocket.getOutputStream()); 
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            
            outStream.writeObject(new Packet(0, "Please enter your name: ")); // first thing the handler does is ask for a name, which will identify who has said what later on
            Packet received = (Packet) in.readObject();
            name = received.message;
            System.out.println("Name: " + name);
            
            outStream.reset(); //so the client doesn't throw a StreamCorruptedException
    
            
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //tell every client that a certain client has left. This only runs when the try block finishes, meaning the client leaves. 
        // broadcast(outStream, true, name + " has left the game.");
        System.out.println(name + " has lost connection. Handler " + clientID + " closing."); // server message to indicate that a client has lost connection. 
    }

    public String getName() {
        return name;
    }

    public int getClientID() {
        return clientID;
    }

    public ObjectOutputStream getOutStream() {
        return outStream;
    }

}
