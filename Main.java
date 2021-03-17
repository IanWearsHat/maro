
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("maromaromaromaro");
        // new Game();
        int id = Integer.parseInt(args[0]);
        if (id == 1) {
            SocketTest test = new SocketTest();
            test.doSomething();
        }
        else if (id == 2) {
            SocketReceive test = new SocketReceive();
            test.serverTry();
        }
        
    }
}