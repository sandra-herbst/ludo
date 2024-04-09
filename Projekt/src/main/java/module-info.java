module SE2_Project_Game {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires jlayer;
    requires junit;
    requires javafx.graphics;
    requires javafx.base;

    opens Start to javafx.fxml;
    exports Start;

    opens Controller.GameScene to javafx.fxml;
    exports Controller.GameScene;

    opens Controller.MenuScene to javafx.fxml;
    exports Controller.MenuScene;

    opens Controller.PopUpScene to javafx.fxml;
    exports Controller.PopUpScene;

    opens Controller to javafx.fxml;
    exports Controller;
}