
import java.io.*;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSide {
    

    public ClientSide() {
        
    }

    public void startClient() {

        try {
            //TODO: setup port forwarding so networks can connect instead of only local networks
            //also, thread for networking has to be made so it starts in Game class
            
            InetAddress address;
            address = InetAddress.getLocalHost();

            Socket echoSocket = new Socket(address, 9696);
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
            System.out.println("No server found.");
            System.exit(1);
        }
        catch (IOException e) {
            System.exit(1);
        }
    }
    
}
