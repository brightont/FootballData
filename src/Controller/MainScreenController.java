package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MainScreenController {

    @FXML
    private void handleCloseMenu() {
        System.exit(0);

    }

    @FXML
    private void handleAboutMenu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Football Data Analyzer");
        alert.setHeaderText("About");
        alert.setContentText("Which team will win?");

        alert.showAndWait();
    }
}
