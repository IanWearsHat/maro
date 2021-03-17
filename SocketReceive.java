
import java.io.BufferedReader;
import java.io.*;
import java.net.*;

public class SocketReceive {
    public SocketReceive() {
        
     }

    public void serverTry() {
        try {
            int portNumber = 69;
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Established connection");
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
