package panel;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

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

    private JButton createButton(String name, int tileIndex) {
        JButton button = new JButton(name);
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
        for (int i = 0; i < 26; i++) {
            add(createButton(String.valueOf(i), i));
        }

    }


}
