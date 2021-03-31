package panel;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

// this should handle whatever is on the left side of the screen, or the options bar, or where b1 currently is in Main.java
// maybe it extends Jcomponent or sth
@SuppressWarnings("serial")
public class OptionsBar extends JPanel {
    private TileEditor editor;
    private BufferedImage[][] tiles;

    private ArrayList<JButton> buttonList;

    public OptionsBar(TileEditor editor, BufferedImage[][] tiles, GridLayout layout) {
        super(layout);
        this.editor = editor;
        this.tiles = tiles;

        buttonList = new ArrayList<JButton>();

        makeOptions();
        setFocusable(false);
    }

    private JButton createButton(int tileIndex) {
        JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                editor.setSelectedTile(tileIndex);
            }
        });

        int r = tileIndex / tiles[0].length;
        int c = tileIndex % tiles[0].length;
        button.setIcon(new ImageIcon(tiles[r][c]));
        return button;
    }

    private void makeOptions() {
        JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                editor.setSelectedTile(26);
            }
        });

        button.setIcon(new ImageIcon(tiles[0][0]));
        add(button);

        for (int i = 1; i <= 25; i++) {
            add(createButton(i));
        }

    }


}
