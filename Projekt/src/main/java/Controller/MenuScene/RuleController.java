package Controller.MenuScene;

import Controller.SuperController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Rule Window (Can be opened by the 'Rules' option in the Main Window)
 */
public class RuleController extends SuperController implements Initializable {

    @FXML private AnchorPane pane;
    @FXML private ImageView bgImage;

    /**
     * Method for changing the Rules Scene to the Main Scene
     */
    public void changeScreentoMenu() {
        createSoundThread("click6");
        loadNextScene(getMainFXML() , pane);
    }

    public void setBgImageLanguage(ResourceBundle resources){
        System.out.println("basebundlename:" + resources.getBaseBundleName());
        if (resources.getBaseBundleName() == null || resources.getBaseBundleName().equals("languages.Language_en")) {
            bgImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/LudoRules_en.png"))));
        } else {
            bgImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/LudoRules_de.png"))));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBgImageLanguage(resourceBundle);
        pane.setOpacity(0);
        makeFadeInTransition(pane);
    }
}
