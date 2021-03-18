package network;

import java.io.BufferedReader;
import java.io.*;
import java.net.*;

public class ServerSide {

    public ServerSide() {
        
     }

    public void startServer() {
        try {
            //TODO: setup port forwarding so networks can connect instead of only local networks
            //also, thread for networking has to be made so it starts in Game class

            /* Creates a ServerSocket bound to the port specified.
            Port is the same as the port specified in port forwarding for router. */
            int portNumber = 9696;
            ServerSocket serverSocket = new ServerSocket(portNumber);

            /* Server waits for a client to attempt connection. When the client successfully connects,
            the socket from the client is stored and the code proceeds. */
            System.out.println("Attempting to receive connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Established connection to client!");

            /* Creates input and output streams for the socket */
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            /* When the user hits the return key, the input from the user is received through in.readLine(). 
            The input is then sent back through the out stream (out.println(inputLine)).
            The input is also printed to the terminal of the server. */
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                System.out.println(inputLine);
            }
        }
        catch (Exception e) {
            System.exit(1);
        }
        
    }
    
}
