
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTest {

    public SocketTest() {

    }

    public void doSomething() {

        try {
            InetAddress address;
            address = InetAddress.getLocalHost();
            Socket echoSocket = new Socket(address, 69);
            System.out.println("Connected");
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            
            out.println("bruh");

            System.out.println("echo: " + in.readLine());


        }
        catch(Exception e) {
            System.out.println("break");
            System.exit(1);
        }
    }
    
}
