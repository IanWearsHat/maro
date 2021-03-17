import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler {

    //Buffered Image is an image with a buffer
    private BufferedImage view;

    //This is for the raster or a rectangular array of pixels
    private int[] pixels;

    //Be able to edit an image with a buffer.
    //Edit all pictures in the buffer

    //width = width of screen i.e. buffer.
    //height = height of screen/buffer.
    public RenderHandler(int width, int height){

        //Creates a Buffered Image that will represent our view.
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //Creates an array of pixels
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

    }

    public void render(Graphics graphics){
        for (int index = 0; index < pixels.length; index++){
            pixels[index] = (int) (Math.random() * 0xFFFF);
        }

        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }
    
}
