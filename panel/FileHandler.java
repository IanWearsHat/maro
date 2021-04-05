package panel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileHandler {
    private static final Logger LOGGER = Logger.getLogger( FileHandler.class.getName() );
    private static TileDrawer drawer;
    private static OptionsBar optionsBar;
    private static Window window;
    private static JFileChooser chooser;

    public FileHandler(TileDrawer drawer, OptionsBar optionsBar, Window window) {
        FileHandler.drawer = drawer;
        FileHandler.optionsBar = optionsBar;
        FileHandler.window = window;
        chooser = new JFileChooser();
    }

    public static void importTileSet() {
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Tile Sheet (.gif)", "gif");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                
                drawer.loadTiles(file);
                optionsBar.makeOptions();
                window.pack();
            }
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unable to import tile sheet.", e);
        }
    }

    public static void importBG() {
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Tile Sheet (.gif)", "gif");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                
                drawer.loadBackground(file);
            }
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unable to import background.", e);
        }
    }

    public static void importMapFromDirectory() {
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Map file (*.map)", "map");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                Path path = file.toPath();
                
                importMap(path);
            }
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unable to import file.", e);
        }
    }

    public static void importMap(Path path) {
        try {
            BufferedReader br = Files.newBufferedReader(path);
            int colCount = Integer.parseInt(br.readLine());
            int rowCount = Integer.parseInt(br.readLine());
            int[][] map = new int[rowCount][colCount];

            String delims = "\\s+";

            for (int row = 0; row < rowCount; row++){
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for(int col = 0; col < colCount; col++){
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
            drawer.importMap(colCount, rowCount, map);
        }
        catch(Exception e) {
            LOGGER.log(Level.WARNING, "Could not import .map file.", e);
        }
    }

    /**
     * This method exports a .map file with a specified name to a specified directory.
     * <p>
     * This is called by the Export button in the dropdown menu for the File Button in the JToolBar at the top.
     * <p>
     * If the file already exists in the directory, the user will be asked if they want to overwrite it or not.
     * <p>
     * If they overwrite it, the file getes deleted and the new file gets created. Else, it'll just stop running.
     * 
     * @param filePath The directory to export to.
     * @param fileName The name of the file.
     */
    public static void exportFile() {
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Map file (*.map)", "map");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filePath = chooser.getCurrentDirectory().getPath();
                String fileName = chooser.getSelectedFile().getName();
                Path inputPath = Paths.get(filePath + File.separator + fileName + ".map");
                if (Files.exists(inputPath)) {
                    int option = JOptionPane.showConfirmDialog(
                        null, "File already exists. Would you like to overwrite it?", "Overwrite", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    
                    if (option == JOptionPane.YES_OPTION) { Files.delete(inputPath); }
                    else { throw new Exception("stop"); }
                }
                createMapFile(fileName);
    
                /*  Workaround for not being able to create the file directly in the directory. The file is created here in the working directory.
                    This then moves the file from this directory to the intended specified directory. */
                
                Path filePathDest = Paths.get(fileName + ".map");
                Files.move(filePathDest, inputPath);
                JOptionPane.showMessageDialog(null, "File " + fileName + " sucessfully exported to " + filePath + " !");
            }

            
        }
        catch(Exception e) {
            LOGGER.log(Level.WARNING, "Could not export file.", e);
        }
    }

    public static void createMapFile(String fileName) {
        try {
            File file = new File(fileName + ".map");
            file.deleteOnExit();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String toWrite = String.valueOf(drawer.getColCount()) + "\n" + String.valueOf(drawer.getRowCount()) + "\n";
            writer.write(toWrite);
            
            /*  This writes every single grid box's tile ID to the .map file. It goes row by row until it hits the last box. 
                It adds a space after every tile ID except at the end of a row. When it hits the end of a row, it adds a new line. */
            ArrayList<ArrayList<GridBox>> boxList = drawer.getBoxList();
            for (int row = 0, rowCount = drawer.getRowCount(); row < rowCount; row++) {
                for (int col = 0, colCount = drawer.getColCount(); col < colCount; col++) {
                    toWrite = String.valueOf(boxList.get(row).get(col).getTileIndex());
                    if (col + 1 < colCount) { toWrite += " "; }
                    writer.write(toWrite);
                }
                if (row + 1 < rowCount) { writer.write("\n"); }
            }
            writer.close();
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not create map file.", e);
        }
    }

}
