
import java.util.logging.Logger;

import panel.FileHandler;
import panel.Window;

public class Main {
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
    public static void main(String[] args) {
        // TODO: needs a button that allows the user to toggle between "zoom mode" and "tile select mode", and maybe hotkeys to toggle between the two modes
        System.out.println("maromaromaromaro");
        Window window = new Window();
        new FileHandler(window);
    }
}
