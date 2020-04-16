package Client;

import javafx.application.Application;
import javafx.application.Application.Parameters;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

import Controllers.*;
import Gui.*;

public class Main extends Application {
	final public static int DEFAULT_PORT = 5555;
	private static String args0, args1;
	public static CommunicationController client;
	public static MainController MainController;
	public  static MemberController clientMember  = new MemberController() ;
	public  static LibrarianController librariancontroller= new LibrarianController();
	public static LibrarymanagerController libraryManagerController=new LibrarymanagerController();
	public  static Command permission;
	
	
	public static  void start() {
	launch();
		}



	



	public static void startApp() throws Exception {
		MainController aFrame = new MainController();

		aFrame.start();

	}
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}


}