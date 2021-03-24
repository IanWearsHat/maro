package Game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VideoTest extends Application {

    private String VIDEO_URL = VideoTest.class.getResource("Jason's Secret Stuff" +"\\"+ "video.mp4").toString();
    private String imageURL = VideoTest.class.getResource("Jason's Secret Stuff" +"\\"+ "BestGirl4.png").toString();

    // public static void main(String[] args) {
    //     launch();
    // }

    public static void begin(){
        launch();
    }

    @Override
    public void start(Stage palco) throws Exception {

        // palco.setTitle("Hello World");        

        // Group root = new Group();
        // Scene mainScene = new Scene(root);
        // palco.setScene(mainScene);

        // Canvas canvas = new Canvas(1000,600);
        // root.getChildren().add(canvas);

        // GraphicsContext gc = canvas.getGraphicsContext2D();

        // Image cool = new Image(imageURL);
        // gc.drawImage(cool, 0, 0);
        // gc.setFill(Color.RED);
        // gc.setStroke(Color.BLACK);
        // gc.setLineWidth(2);
        // Font coolFont = Font.font("Times New Roman", FontWeight.BOLD,48);
        // gc.setFont(coolFont);
        // gc.fillText("Hello World", 60, 50);
        // gc.strokeText("Hello World",60,50);

        // palco.show();

        Media media = new Media(VIDEO_URL); // 1
        MediaPlayer mediaPlayer = new MediaPlayer(media); // 2
        MediaView mediaView = new MediaView(mediaPlayer); // 3

        Pane raiz = new Pane();
        raiz.getChildren().add(mediaView); // 4
        Scene cena = new Scene(raiz, 600, 400);
        palco.setTitle("Tocando Video em JavaFX");
        palco.setScene(cena);

        mediaPlayer.play(); // 4
        palco.show();
    }
}
