package Client;

import java.io.IOException;

import Gui.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientController {
	public static Stage stage=null;
	final public static int DEFAULT_PORT = 5555;
	@FXML
	private AnchorPane AnchorPaneServer;

	@FXML
	private ImageView HomeImage;

	@FXML
	private Label ServerInterfaceLabel;

	@FXML
	private Label ServerIPLabel;

	@FXML
	private TextField txtIP;

	@FXML
	private Button StartBnt;

	@FXML
	private Button ClearBnt;

	@FXML
	void ClearBntHandler(ActionEvent event) {
		txtIP.clear();
	}

	@FXML
	void StartBntHandler(ActionEvent event) {
		txtIP.getText();
		try {
			Main.client = new CommunicationController(txtIP.getText(), DEFAULT_PORT);
			MainController aFrame = new MainController();

			aFrame.start();
		} catch (IOException e) {
			e.printStackTrace();
			displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void displayAlert(AlertType type , String title , String header , String content)
	{

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = new Alert(type);
				alert.setTitle(title);
				alert.setHeaderText(header);
				alert.setContentText(content);
				alert.showAndWait();
			}
		});
	}
	
	public static void start() throws Exception {


		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try
				{
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Client.fxml"));
					System.out.println(getClass().getResource("Client.fxml"));
					AnchorPane pane =(AnchorPane) loader.load();
					Scene scene = new Scene( pane );

					// setting the stage
					primaryStage.setScene( scene );
					primaryStage.setTitle( "Intrface Client" );
					stage=primaryStage;
					primaryStage.show();

				}
				catch (Exception e)
				{
					//	displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
					e.printStackTrace();
				}
			}
		});

	}
}
