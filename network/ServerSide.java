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

            int portNumber = 9696;
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Attempting to receive connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Established connection to client");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        }
        catch (Exception e) {
            System.exit(1);
        }
        
    }
    
}
