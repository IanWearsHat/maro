package network;

import java.io.*;
import java.net.*;

public class ClientSide {
    

    public ClientSide() {
        
    }

    public void startClient() {

        try {
            //TODO: setup port forwarding so networks can connect instead of only local networks
            //also, thread for networking has to be made so it starts in Game class
            //45.51.187.16
            //remoteAddress, 9696, localAddress, 0

            InetAddress localAddress = InetAddress.getLocalHost();
            // String remoteAddress = InetAddress.getLocalHost().getHostAddress();

            Socket echoSocket = new Socket();
            echoSocket.bind(new InetSocketAddress(localAddress, 5124));
            echoSocket.connect(new InetSocketAddress("45.51.187.16", 9696));

            System.out.println("Connected to server.");
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }

        }
        catch (ConnectException e) {
            System.out.println("No server found. Exiting...");
            System.exit(1);
        }
        catch (IOException e) {
            System.exit(1);
        }
    }
    
}
