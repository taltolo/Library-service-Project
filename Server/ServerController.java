package Server;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Client.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerController {
	static Stage stage = null;

	@FXML
	private AnchorPane AnchorPaneServer;

	@FXML
	private ImageView HomeImage;

	@FXML
	private Label ServerInterfaceLabel;

	@FXML
	private TextField txtPort;

	@FXML
	private Label PortLabel;

	@FXML
	private TextField txtDb;

	@FXML
	private Label DataBaseLabel;

	@FXML
	private Label PasswordLabel;

	@FXML
	private PasswordField txtPass;

	@FXML
	private Label UserLabel;

	@FXML
	private TextField txtUser;

	@FXML
	private Label ServerIPLabel;

	@FXML
	private TextField txtIP;

	@FXML
	private Button StartBnt;

	@FXML
	private Button ClearBnt;
	int port;
	@FXML
	void ClearBntHandler(ActionEvent event) {
		txtIP.clear();
		txtUser.clear();
		txtPass.clear();
		txtDb.clear();
		txtPort.clear();

	}

	@FXML
	void StartBntHandler(ActionEvent event) {
		EchoServer.dataBaseName = txtDb.getText();
		EchoServer.dataBasePassword = txtPass.getText();
		EchoServer.dataBaseUser = txtUser.getText();
		EchoServer ser = new EchoServer(5555);
		ser.startServer(ser);

		stage.close();

	}

	public void initialize() {

		try {
			txtIP.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtPort.setText(Integer.toString(EchoServer.DEFAULT_PORT));
	}


	/**
	 * Show the scene view of the server *
	 * 
	 *            current stage to build
	 * @throws Exception
	 *             if failed to display
	 *             
	 **/          

	public static void start() throws Exception {


		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try
				{
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Server.fxml"));
					System.out.println(getClass().getResource("Server.fxml"));
					AnchorPane pane =(AnchorPane) loader.load();
					Scene scene = new Scene( pane );

					// setting the stage
					primaryStage.setScene( scene );
					primaryStage.setTitle( "Server UI" );
					stage=primaryStage;
					primaryStage.show();

				}
				catch (Exception e)
				{
					//	displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
				}
			}
		});

	}


}
