package Gui;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Client.Command;
import Client.Main;
import Entity.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileManagerController {

    @FXML
    private AnchorPane AnchorHome;

    @FXML
    private ImageView Image;

    @FXML
    private VBox Vbox;

    @FXML
    private Label TimeL;

    @FXML
    private Label DateL;

    @FXML
    private Label NameLabel;

    @FXML
    private Button Profilebnt;

    @FXML
    private Button Addnewmemberbnt;

    @FXML
    private Button SearchBookbnt;

    @FXML
    private ImageView SearchImage;

    @FXML
    private Button Searchmemberbnt;

    @FXML
    private Button SearchEmployeesbnt;

    @FXML
    private Button ReturnBookbnt;

    @FXML
    private Button Inventorybnt;

    @FXML
    private Button Reportsbnt;

    @FXML
    private ImageView ImageReport;

    @FXML
    private Button Logoutbnt;

    @FXML
    private Button Clearbnt;

    @FXML
    private Label WelcomeLabel;

    @FXML
    private Label FullNameLabel;

    @FXML
    private Label IDLabel;

    @FXML
    private Label WorkerNumberLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label EmailLabel;

    @FXML
    private TextField EmailFeild;

    @FXML
    private Button BntUpdata;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label phoneNumberLabel;

    
    public static Stage profileManagerControllerStage;

    @FXML
    void AddnewmemberbntHandler(ActionEvent event) {
    	
    	Main.client.librarymanagerHomePageController.AddnewmemberbntHandler(event);

    }

    @FXML
    void BntUpdataHandler(ActionEvent event) throws IOException {
    	String newEmail=EmailFeild.getText();
    	System.out.println(newEmail);
    	if (newEmail.isEmpty())
    	{
    		
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Up Data Failed");
    		alert.setContentText("Failed Email is missing!");
    		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);

    		alert.getButtonTypes().setAll(okButton);
    		alert.showAndWait().ifPresent(type -> {
    			if (type == okButton) {
    				initialize();
    	    		return;

    			}
    		});
    	
    	}
    	else {
    	System.out.println("Bnt Up data Handlerr");
    	newEmail="'"+EmailFeild.getText()+"'";
    	if ( newEmail.isEmpty())
    	{
    		displayAlert(AlertType.ERROR, "Error", "Up Data Failed", "Phone Number or Email are missing!");
    		return;
    	}
    	
    	Main.libraryManagerController.UpDataLibrarian( newEmail);
    	}
    	

    }

    @FXML
    void ClearHandler(ActionEvent event) {
    	EmailFeild.clear();
    	

    }

    @FXML
    void InventorybntHandler(ActionEvent event) {
    	Main.client.profileManagercontroller.profileManagerControllerStage.close();
    	Main.client.librarymanagerHomePageController.InventorybntHandler(event);

    }

    @FXML
    void LogoutHandler(ActionEvent event) throws IOException {
    	Main.libraryManagerController.LogoutHandler(Main.client.librarymanagercontroller.curntlibrarymanager.getWorkerNumber());

    	Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Logout Window");
		alert.setContentText("Are you Sure?");
		ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonData.NO);

		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
			if (type == okButton) {
			
				Main.client.profileManagercontroller.profileManagerControllerStage.close();
			
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});

    }

    @FXML
    void ProfileHandler(ActionEvent event) {
    	initialize();

    }

    @FXML
    void ReportsbntHandler(ActionEvent event) {
    	Main.client.profileManagercontroller.profileManagerControllerStage.close();
    	Main.client.librarymanagerHomePageController.ReportsbntHandler(event);
    	
    	

    }

    @FXML
    void ReturnBookbntHandler(ActionEvent event) {
    	
    	Main.client.librarymanagerHomePageController.ReturnBookbntHandler(event);

    }

    @FXML
    void SearchBookbntHandler(ActionEvent event) {
    	Main.client.profileManagercontroller.profileManagerControllerStage.close();
    	Main.client.librarymanagerHomePageController.librarymanagerControllerStage.show();

    }

    @FXML
    void SearchEmployeesbntHandler(ActionEvent event) {
    	Main.client.profileManagercontroller.profileManagerControllerStage.close();
    	Main.client.librarymanagerHomePageController.SearchEmployeesbntHandler(event);
    }

    @FXML
    void SearchmemberbntHandler(ActionEvent event) {
    	Main.client.profileManagercontroller.profileManagerControllerStage.close();
    	Main.client.librarymanagerHomePageController.SearchmemberbntHandler(event);

    	

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

		Main.client.profileManagercontroller=this;
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		//NameLabel.setText("Hello "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname()+"!");
		DateL.setText(timeStamp);
		TimeL.setText(timeStampclock);
		NameLabel.setText("Hello "+Main.client.librarymanagercontroller.curntlibrarymanager.getFirstname()+" "+Main.client.librarymanagercontroller.curntlibrarymanager.getLastname()+"!");
		WelcomeLabel.setText("Welcome,  "+Main.client.librarymanagercontroller.curntlibrarymanager.getFirstname()+" "+Main.client.librarymanagercontroller.curntlibrarymanager.getLastname()+" :");
		FullNameLabel.setText("Full Name : "+Main.client.librarymanagercontroller.curntlibrarymanager.getFirstname()+" "+Main.client.librarymanagercontroller.curntlibrarymanager.getLastname());
		IDLabel.setText("Worker ID : " +Main.client.librarymanagercontroller.curntlibrarymanager.getID() );
		WorkerNumberLabel.setText("Worker Number : "+Main.client.librarymanagercontroller.curntlibrarymanager.getWorkerNumber());
		EmailFeild.setText(Main.client.librarymanagercontroller.curntlibrarymanager.getEmail());
		departmentLabel.setText("department : "+Main.client.librarymanagercontroller.curntlibrarymanager.getDepartment());
		roleLabel.setText(" role : Library Manager");
	   
    }


}
