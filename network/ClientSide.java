package network;

import java.io.*;
import java.net.*;

public class ClientSide implements Runnable {

    volatile boolean kill = false;
    public ClientSide() {
        
    }

    private String userPrompt(BufferedReader in) {
        try {
            System.out.print("You: ");
            String input = in.readLine();
            return input;
        }
        catch (Exception e) {}
        return null;
    }

    public void startClient() {

        try {
            //TODO: setup port forwarding so networks can connect instead of only local networks
            //also, thread for networking has to be made so it starts in Game class

            /* Local address is just the address of the client
            remote address is the open ip of the server that the client connects to */
            InetAddress localAddress = InetAddress.getLocalHost();
            System.out.print("Enter IP: ");
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String remoteAddress = stdIn.readLine();

            /* Creates a new socket and binds it to the client's address with an open port */
            Socket echoSocket = new Socket();
            echoSocket.bind(new InetSocketAddress(localAddress, 0));

            /* Attempts to connect to the remote address at the port specified.
            Port is the same as the port specified in port forwarding for router. */
            System.out.println("Attempting Connection...");
            echoSocket.connect(new InetSocketAddress(remoteAddress, 9696), 10 * 1000);
            System.out.println("Connected to server!");

            /* Creates input and output streams for the socket and creates an input stream from the keyboard so the user can provide input */
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            
            //this thread has to run so that the client can wait for a message from the server
            //while also waiting for an input from the user.
            new Thread(() -> {
                while (!kill) {
                    try {
                        System.out.println(in.readLine());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                }
            }).start();

            /* first thing you need to do is type in your name*/
            String userInput;
            userInput = stdIn.readLine();
            out.println(userInput);

            /* Waits for the user to input something in the terminal. 
            When the user hits the return key, the input is sent to the server through the out stream (out.println(userInput)). */
            while ((userInput = userPrompt(stdIn)) != null) {
                out.println(userInput);
            }

            kill = true;

        }
        catch (ConnectException | SocketTimeoutException e) {
            e.printStackTrace();
            System.out.println("No server found. Exiting...");
            System.exit(1);
        }
        catch (SocketException e) {
            System.out.println("Invalid IP");
            System.exit(1);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        startClient();
    }
    
}