package Gui;

import Client.Command;
import Client.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReturnBookController {

    @FXML
    private AnchorPane AnchSerchBook;

    @FXML
    private Label ReturnaBookLabel;

    @FXML
    private Label CopyBookIDLabel;

    @FXML
    private Label IDLabel;

    @FXML
    private TextField CopyBookIDField;

    @FXML
    private TextField IDfiels;

    @FXML
    private Button okBnt;

    @FXML
    private Button Cancelbnt;
    public static Stage returnBookStage;

    @FXML
    void CancelbntHandler(ActionEvent event) {
    	Main.client.returnBookController.returnBookStage.close();

    }

    @FXML
    void okBntHandler(ActionEvent event) {
    	
    	String copyID=CopyBookIDField.getText();
    	String MemberID=IDfiels.getText();
    	
    	if(copyID.isEmpty() & MemberID.isEmpty()) {
    		
    		displayAlert(AlertType.ERROR, "Error", "Return a Book Failed", "Copy Book ID or Member ID are missing!");
    		
    	}
    	if(Main.permission.equals(Command.permission1)) {
    	Main.librariancontroller.handlerReturnBook(copyID,MemberID);}
    	else {
    		
    		Main.libraryManagerController.handlerReturnBook(copyID,MemberID);
    	}

    	
    	
    }
    
	/**
	 * Show an Alert dialog with custom info
	 * @param type type alert
	 * @param title title window
	 * @param header header of the message
	 * @param content message
	 */
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
    
    
    
    public void initialize() {
    	Main.client.returnBookController=this;
    	
    
   
    	
    }

}
