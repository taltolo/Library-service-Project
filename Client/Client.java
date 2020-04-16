package Client;

import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {

	
	
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * create GUI
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		ClientController.start();
		
	}

	


}
