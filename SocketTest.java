import java.io.*;
import java.net.Socket;

public class SocketTest {

    public SocketTest() {

    }

    public void doSomething() {

        try {
            String hostName = "ianpc";
            Socket echoSocket = new Socket(hostName, 69);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
            
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        }
        catch(Exception e) {
            System.exit(1);
        }
    }
    
}
