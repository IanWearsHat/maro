package panel;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Graphics2D;
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

    public OptionsBar(TileEditor editor, BufferedImage[][] tiles, GridLayout layout) {
        super(layout);
        this.editor = editor;
        this.tiles = tiles;
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

        BufferedImage resizedImg = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.drawImage(tiles[r][c], 0, 0, 60, 60, null);
        g2.dispose();

        button.setIcon(new ImageIcon(resizedImg));
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
