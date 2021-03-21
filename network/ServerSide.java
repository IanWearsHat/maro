package network;

import java.net.*;
import java.util.ArrayList;

public class ServerSide implements Runnable {

    private final int PORT = 9696;
    public static ArrayList<Socket> clientList;
    public static ArrayList<ClientHandler> handlerList;
    public ClientHandler handler;

    public ServerSide() {
        clientList = new ArrayList<Socket>();
        handlerList = new ArrayList<ClientHandler>();
    }

    private void startServer() {
        try {
            //TODO: setup port forwarding so networks can connect instead of only local networks
            //edit: done :D

            //also, thread for networking has to be made so it starts in Game class

            /* Creates a ServerSocket bound to the port specified.
            Port is the same as the port specified in port forwarding for router. */
            ServerSocket serverSocket = new ServerSocket(PORT);

            /* Server waits for a client to attempt connection. When the client successfully connects,
            the socket from the client is stored and the code proceeds. */
            
            while (true) {
                System.out.println("Waiting for connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Established connection to client!");
                
                clientList.add(clientSocket);

                int playerNumber = clientList.size() - 1;
                handler = new ClientHandler(playerNumber);
                handlerList.add(handler);

                Thread handlerThread = new Thread(handler);
                handlerThread.start();
            }

        }
        catch (SocketException e) {
            
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    @Override
    public void run() {
        startServer();
    }

    public static void main(String[] args) {
        ServerSide server = new ServerSide();
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
    
}
