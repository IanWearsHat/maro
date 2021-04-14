package mainGame;

import java.awt.Dimension;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import network.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("maromaromaromaro");
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(1600, 900));
        Background bg = new Background();
        frame.add(bg);
        Thread bgThread = new Thread(bg);
        bgThread.start();

        ClientSide client = new ClientSide(bg.getChat());
        Thread clientThread = new Thread(client);
        clientThread.start();
        
        frame.pack();
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                bgThread.interrupt();
                clientThread.interrupt();
                System.exit(0); 
            }
            public void windowActivated(WindowEvent e) { }
            public void windowDeactivated(WindowEvent e) { }
        });
    }
}