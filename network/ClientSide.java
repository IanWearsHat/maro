package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.packet.*;

public class ClientSide implements Runnable {
    private static final Logger LOGGER = Logger.getLogger( ClientSide.class.getName() );

    private volatile boolean kill = false;

    public ClientSide() {}

    public void startClient() {

        try {
            //TODO: the userinput while loop should be a thread that can be interrupted by the waiting for message from server thread
            // creates an input stream from the keyboard so the user can provide input
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter IP: ");
            String remoteAddress = stdIn.readLine();

            /* Creates a new socket and binds it to the client's address with an open port */
            Socket clientSocket = new Socket();

            /* Local address is just the address of the client
            remote address is the open ip of the server that the client connects to */
            InetAddress localAddress = InetAddress.getLocalHost();
            clientSocket.bind(new InetSocketAddress(localAddress, 0));

            /* Attempts to connect to the remote address at the port specified.
            Port is the same as the port specified in port forwarding for router. */
            LOGGER.log(Level.INFO, "Attempting connection to server...");
            clientSocket.connect(new InetSocketAddress(remoteAddress, 9696), 10 * 1000);
            LOGGER.log(Level.INFO, "Connected to server!");

            /* Creates input and output streams for the socket */
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream()); 
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            
            /* Waits for the user to input something in the terminal. 
            When the user hits the return key, the input is sent to the server through the out stream (out.println(userInput)). */
            Thread userInputThread = new Thread(() -> {
                boolean isRunning = true;
                while (!Thread.interrupted() && isRunning) {
                    try {
                        if (!stdIn.ready()) { //https://8thlight.com/blog/francesca-sadikin/2019/08/01/handling-blocking-threads-in-java.html
                            try {
                                Thread.sleep(1);
                                continue;
                            }
                            catch (InterruptedException e) {
                                return;
                            }
                        }

                        System.out.print("You: ");
                        String userInput = stdIn.readLine();
                        if (!userInput.equals(null)) {
                            out.writeObject(new MessagePacket(userInput));
                            // out.reset();
                        }
                        else {
                            isRunning = false;
                        }
                    }
                    catch (Exception e) {

                    }
                }
            });
            userInputThread.start();

            //this thread has to run so that the client can wait for a message from the server
            //while also waiting for an input from the user.
            while (!kill) {
                try {
                    Packet receivedPacket = (Packet) in.readObject();
                    if (receivedPacket instanceof MessagePacket) {
                        System.out.println(((MessagePacket) receivedPacket).message);
                    }
                    else if (receivedPacket instanceof PlayerPacket) {
                        System.out.println(((PlayerPacket) receivedPacket).x);
                        // this is how the client would update other players' positions on the client's screen
                        // it would receive other clients' positions from the server and render them accordingly on its screen.
                    }
                }
                catch (ClassNotFoundException e) {
                    LOGGER.log(Level.SEVERE, "Packet wasn't a Packet object, killing input reader thread.", e);
                    kill = true;
                    userInputThread.interrupt();
                } 
                catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Could not read packet, killing input reader thread.", e);
                    kill = true;
                    userInputThread.interrupt();
                }
            }
        }
        catch (ConnectException | SocketTimeoutException e) {
            LOGGER.log(Level.SEVERE, "No server found. Exiting...", e);
        }
        catch (SocketException e) {
            LOGGER.log(Level.SEVERE, "Invalid IP.", e);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        startClient();
    }
    
}