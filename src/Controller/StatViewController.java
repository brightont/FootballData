package Controller;

import MainApplication.MainApplication;
import javafx.stage.Stage;

public class StatViewController {

	private Stage dialogStage;
	
	private MainApplication mainApplication;
	
	private boolean clicked = false;
	
	public void setDialogStage(Stage theDialogStage) {
        dialogStage = theDialogStage;
    }
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void setMainApp(MainApplication main) {
        mainApplication = main;
    }
}
