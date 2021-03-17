public class Main {
    public static void main(String[] args) {
        System.out.println("maromaromaromaro");
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }
}