import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;

// this should handle whatever is on the left side of the screen, or the options bar, or where b1 currently is in Main.java
// maybe it extends Jcomponent or sth
@SuppressWarnings("serial")
public class OptionsBar extends JPanel {
    public OptionsBar() {

    }

    public OptionsBar(GridLayout layout) {
        super(layout);
        add(new JButton("1"));
        add(new JButton("2"));
        add(new JButton("3"));
        add(new JButton("4"));
    }


}
