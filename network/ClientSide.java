package network;

import java.io.*;
import java.net.*;

public class ClientSide implements Runnable {
    volatile boolean kill = false;
    public ClientSide() {
        
    }

    public void startClient() {

        try {
            //TODO: setup port forwarding so networks can connect instead of only local networks
            //also, thread for networking has to be made so it starts in Game class

            /* Local address is just the address of the client
            remote address is the open ip of the server that the client connects to */
            InetAddress localAddress = InetAddress.getLocalHost();
            String remoteAddress = "45.51.187.16";

            /* Creates a new socket and binds it to the client's address with an open port */
            Socket echoSocket = new Socket();
            echoSocket.bind(new InetSocketAddress(localAddress, 0));

            /* Attempts to connect to the remote address at the port specified.
            Port is the same as the port specified in port forwarding for router. */
            System.out.println("Attempting Connection...");
            echoSocket.connect(new InetSocketAddress("192.168.1.6", 9696));
            System.out.println("Connected to server!");

            /* Creates input and output streams for the socket and creates an input stream from the keyboard so the user can provide input */
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            /* Waits for the user to input something in the terminal. 
            When the user hits the return key, the input is sent to the server through the out stream (out.println(userInput)). 
            All the server does is return that input to the client through the input stream. (in.readLine()) */
            new Thread(() -> {
                while (!kill) {
                    try {
                        System.out.println(in.readLine());
                    }
                    catch (Exception e) {

                    }
                    
                }
            }).start();

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                // System.out.println(in.readLine());
            }
            kill = true;

        }
        catch (ConnectException e) {
            e.printStackTrace();
            System.out.println("No server found. Exiting...");
            System.exit(1);
        }
        catch (IOException e) {
            System.exit(1);
        }
    }

    @Override
    public void run() {
        startClient();
    }
    
}