package Controller.PopUpScene;

import Controller.SuperController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsController extends SuperController implements Initializable {

    private static final Logger logger = LogManager.getLogger(SettingsController.class);
    private static boolean darkTheme;
    private boolean languageEN;

    @FXML private AnchorPane pane;
    @FXML private RadioButton darkThemeButton;
    @FXML private RadioButton lightThemeButton;
    @FXML private ToggleButton englishButton;
    @FXML private ToggleButton deutschButton;
    private ToggleGroup langGRP;
    private Stage stage;
    private File file;
    private String language;

    // English resourcebundle
    private static final Locale localeEN = new Locale ("en" , "UK");
    private static final ResourceBundle bundleEN = ResourceBundle.getBundle("languages.Language_en",localeEN);

    // German resourcebundle
    private static final Locale localeDE = new Locale ("de" , "DE");
    private static final ResourceBundle bundleDE = ResourceBundle.getBundle("languages.Language_de",localeDE);


    /**
     * Method for closing the screen when cancel button has been pushed.
     * No changed will be made.
     */
    public void cancelSettings(){
        createSoundThread("click6");
        this.stage = (Stage) pane.getScene().getWindow();
        stage.close();

        unblurBG((AnchorPane) stage.getOwner().getScene().getRoot());
    }

    /**
     * Method for closing the screen when confirmation button has been pushed.
     * Settings will be changed accordingly.
     */
    public void confirmSettings() {
        createSoundThread("click6");
        // File will be written to 'true' or 'false' depending on selection.
        darkTheme = darkThemeButton.isSelected();
        languageEN = englishButton.isSelected();
        writeFile();

        this.stage = (Stage) pane.getScene().getWindow();
        stage.close();

        stage.getOwner().getScene().getStylesheets().clear();
        stage.getOwner().getScene().getStylesheets().add(setCurrentStylesheet());

        unblurBG((AnchorPane) stage.getOwner().getScene().getRoot());
    }


    /**
     * Method for checking current Stylesheet.
     * @return current Stylesheet url.
     */
    public static String setCurrentStylesheet(){
        if (darkTheme){
            return "/css/Dark-Theme.css";
        } else {
            return "/css/Light-Theme.css";
        }
    }

    /**
     * Method for setting value of the radiobutton when settings are opened again.
     */
    private void prepareSettings(){
        darkThemeButton.setSelected(darkTheme);
        englishButton.setSelected(languageEN);
        deutschButton.setSelected(!languageEN);
    }

    /**
     * Method for reading settings.txt whether or not dark mode is true/false & language.
     * Goal: persistent data.
     */
    public void readFile() {
        try{
            file = new File("settings.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            darkTheme = Boolean.parseBoolean(reader.readLine());
            language = reader.readLine();

            switch (language){
                case "en":
                    setBundle(bundleEN);
                    languageEN = true;
                    break;
                case "de":
                    setBundle(bundleDE);
                    languageEN = false;
                    break;
                default: logger.info("Reading language failed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Overwriting file to new boolean(darkTheme) if confirmation button has been pushed and
     * language settings.
     */
    private void writeFile() {
        try {
            // Write file to true or false, depending on confirmation
            Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            w.write(darkTheme + "\n");
            // Only if there is a selected Toggle, change the settings after confirmation.
            if (langGRP.getSelectedToggle() != null){
                if (languageEN){
                    w.write("en");
                    setBundle(bundleEN);
                } else {
                    w.write("de");
                    setBundle(bundleDE);
                }
            } else {
                // If no Toggle has been selected, change the settings to before.
                w.write(language);
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        langGRP = new ToggleGroup();
        langGRP.getToggles().addAll(deutschButton,englishButton);
        // Read and prepare user settings once window has been opened
        readFile();
        // Set RadioButton checked/unchecked
        prepareSettings();

        pane.setOpacity(0);
        makeFadeInTransition(pane);
    }
}
