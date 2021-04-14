package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSide implements Runnable {
    private static final Logger LOGGER = Logger.getLogger( ServerSide.class.getName() );

    private final int PORT = 9696;
    volatile boolean run = true;

    public static ArrayList<Socket> clientList;
    public static ArrayList<ClientHandler> handlerList;
    public ClientHandler handler;

    public ServerSide() {
        clientList = new ArrayList<Socket>();
        handlerList = new ArrayList<ClientHandler>();
    }

    private void runServer() {
        try {
            //also, thread for networking has to be made so it starts in Game class

            /* Creates a ServerSocket bound to the port specified.
            Port is the same as the port specified in port forwarding for router. */
            ServerSocket serverSocket = new ServerSocket(PORT);

            // this allows someone to issue server commands through the server terminal
            new Thread(() -> {
                try {
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                    String userInput = "";
                    while (userInput != null) {
                        userInput = stdIn.readLine();
                        if (userInput.equals("stop")) {
                            run = false;
                            serverSocket.close();
                        }
                        else if (userInput.equals("printList")) {
                            for (Socket socket : clientList) {
                                System.out.println(socket);
                            }
                            System.out.println("");
                            for (ClientHandler handler : handlerList) {
                                System.out.println(handler);
                            }
                        }
                    }
                }
                catch (Exception e) {}
            }).start();

            /* All this class does is wait for a client to attempt connection. When the client successfully connects,
            a socket is created on the server's end that connects to the client socket. The server
            socket (called "clientSocket" here for readability) is stored and the code proceeds. */
            while (run) {
                LOGGER.log(Level.INFO, "Listening for connection...");
                Socket clientSocket = serverSocket.accept();
                LOGGER.log(Level.INFO, "Established connection to client from " + clientSocket.getInetAddress());
                
                clientList.add(clientSocket);

                int playerNumber = clientList.size() - 1;
                handler = new ClientHandler(playerNumber, clientSocket);
                handlerList.add(handler);

                LOGGER.log(Level.INFO, "Starting client handler thread " + playerNumber);
                new Thread(handler).start();
            }
            
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not connect to client. Stopping...");
            System.exit(1);
        }
    }

    private void cleanUp() {
        for (Socket socket : clientList) {
            try {
                socket.close();
                clientList.remove(socket);
                LOGGER.info("closing socket");
            }
            catch (Exception e) {

            }
        }
        for (ClientHandler handler : handlerList) {
            LOGGER.info("stopping handler");
            handler.stop();
            handlerList.remove(handler);
        }
    }

    @Override
    public void run() {
        runServer();
        cleanUp();
    }

    public static void main(String[] args) {
        new Thread( new ServerSide() ).start();
    }
    
}
