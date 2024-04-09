package Start;

import Controller.PopUpScene.SettingsController;
import Controller.SuperController;
import Thread.Music.Sound;
import Thread.Music.ThreadSound;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * Starting Point of the Application
 */
public class Main extends Application {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
    launch(args);
    }

    @Override
    public void start(Stage stage) {

        try {
            // Play Background music, loop endlessly
            Sound sound = new Sound("background");
            Thread tSound = new Thread(new ThreadSound(sound, true));
            tSound.start();

            // Create SettingsController for setting current Stylesheet from settings.txt.
            SettingsController controller = new SettingsController();
            controller.readFile();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainView.fxml")), SuperController.getBundle());
            stage.setTitle("Ludo");
            stage.setResizable(false);
            // Constants for Window Size.
            int WIDTH = 1280;
            int HEIGHT = 720;
            Scene scene = new Scene(root, WIDTH, HEIGHT);

            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(SettingsController.setCurrentStylesheet())).toExternalForm());

            stage.setScene(scene);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/default_icon.png"))));
            stage.show();
            logger.info("The application is running...");

        } catch (Exception e){

            logger.fatal("The application is unable to start: \n" + e.getMessage() + "\n");
            e.printStackTrace();

        }

    }
}
