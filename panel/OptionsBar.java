package panel;

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
        add(new JButton("5"));
        add(new JButton("6"));
        add(new JButton("7"));
        add(new JButton("8"));
        add(new JButton("9"));
        add(new JButton("10"));
        add(new JButton("11"));
        add(new JButton("12"));
        add(new JButton("13"));
        add(new JButton("14"));
        add(new JButton("15"));
        add(new JButton("16"));
        add(new JButton("17"));
        add(new JButton("18"));
        add(new JButton("19"));
        add(new JButton("20"));
        add(new JButton("21"));
    }


}
