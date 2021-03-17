public class Main {
    public static void main(String[] args) {
        System.out.println("maromaromaromaro");
        // new Game();
        if (args.length == 1) {
            if (args[0] == "server") {
                SocketReceive serv = new SocketReceive();
            }
            else if (args[0] == "client") {
                SocketTest clien = new SocketTest();
            }
            else { System.exit(1); }
        }
        else {
            System.out.println("Must use 2 arguments.");
            System.exit(1);
        }
    }
}