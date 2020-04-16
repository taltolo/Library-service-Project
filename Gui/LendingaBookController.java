package Gui;
import java.sql.Date;
import java.time.LocalDate;
import Entity.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import Client.*;
import Controllers.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LendingaBookController {

    @FXML
    private AnchorPane AnchSerchBook;

    @FXML
    private Label LendingaBookLabel;

    @FXML
    private Label CopyBookIDLabel;

    @FXML
    private Label IDLabel;

    @FXML
    private TextField CopyBookIDField;

    @FXML
    private TextField IDfiels;

    @FXML
    private Label issueDataLabel;

    @FXML
    private Label returnDataLabel;

    @FXML
    private Button okBnt;

    @FXML
    private Button Cancelbnt;

    @FXML
    private DatePicker issuData;

    @FXML
    private DatePicker dateR;

	LibrarianController librariancontroller;
    public static Stage lendingaBookStage;

    @FXML
    void CancelbntHandler(ActionEvent event) {
    	
    	Main.client.lendingaBookController.lendingaBookStage.close();

    }

    @FXML
    void okBntHandler(ActionEvent event) {

		Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	String copyBookId=CopyBookIDField.getText();
    	String idMember=IDfiels.getText();
    	java.sql.Date issueDate1 = java.sql.Date.valueOf(issuData.getValue());
    	java.sql.Date returnDate = java.sql.Date.valueOf(dateR.getValue());
    	//Date issueDate = new Date(issuData.getValue().get);
    	
    	/*LocalDate issueDate = issuData.getValue();
    	Calendar c =  Calendar.getInstance();
    	c.set(issueDate.getYear(), issueDate.getMonthValue(), issueDate.getDayOfMonth());
    	Date issueDate1 = c.getTime();*/
    	
    	
    	//Date returnDate = new Date(dateR.getValue().toEpochDay());
    	//Date issueDate=issuData.getValue();
    	//Date returnDate=dateR.getValue();
    	System.out.println(issueDate1.toString()+" "+returnDate);
    	
    	if(copyBookId.isEmpty() || issueDate1.equals(null) ||returnDate.equals(null) ) {
    		displayAlert(AlertType.ERROR, "Error", "Loaning a Book Failed", "Copy Book ID or one of the Date's are missing!");
    		return;
    		
    	}
    	else {

            if(Main.permission.equals(Command.permission1)) {
	    	Main.librariancontroller.loanaBookHandler(copyBookId,idMember,(Date) issueDate1,returnDate,Main.client.searchmemberController.lonerMember);
	    	}
            else {
            	
            	Main.libraryManagerController.loanaBookHandler(copyBookId,idMember,(Date) issueDate1,returnDate,Main.client.searchmemberManagerController.lonerMember);
            }
    	     }
    	
    	    }
    	});
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
    	String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    	if(Main.permission.equals(Command.permission1)) {
    	IDfiels.setText(Main.client.searchmemberController.lonerMember.getMemberID());}
    	else {
    		IDfiels.setText(Main.client.searchmemberManagerController.lonerMember.getMemberID());
    	}
    	issuData.setPromptText(timeStamp);
   
    }
    
    

}
