package Gui;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Client.Main;
import Controllers.MemberController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

public class ProfileController {

    @FXML
    private AnchorPane AnchorHome;

    @FXML
    private ImageView Image;

    @FXML
    private Label WelcomeLabel;

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
    private Button MyBooksbn;

    @FXML
    private Button SearchBookbnt;

    @FXML
    private ImageView SearchImage;

    @FXML
    private Button ActivityLogbnt;

    @FXML
    private Button Logoutbnt;

    @FXML
    private Label FullNameLabel;

    @FXML
    private Label IDLabel;

    @FXML
    private Label CardNumberLabel;

    @FXML
    private TextField FullNameField;

    @FXML
    private Label StatusLabel;

    @FXML
    private TextField IDfiels;

    @FXML
    private TextField CardNumberField;

    @FXML
    private TextField StatusFeild;

    @FXML
    private Label PhoneNumberLabel;

    @FXML
    private Label EmailLabel;

    @FXML
    private Button ClearBnt;

    @FXML
    private TextField PhoneNumberFeild;

    @FXML
    private TextField EmailFeild;

    @FXML
    private Button BntUpdata;

    @FXML
    private ImageView BntImage;
    
    MainController mainController;
    MemberController membercontroller;
      static Stage ProfileStage;

    @FXML
    void ActivityLogbntHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				
				
    	
    	Main.client.profileController.ProfileStage.close();
    	Main.client.HomePageController.ActivityLogbntHandler(event);
			}
	    	});
    	

    }

    @FXML
    void BntUpdataHandler(ActionEvent event) throws IOException {
    	String newPhoneNumber=PhoneNumberFeild.getText();
    	String newEmail="'"+EmailFeild.getText()+"'";
    	
    	
       	
    	if (newPhoneNumber.isEmpty() || newEmail.isEmpty())
    	{
    		displayAlert(AlertType.ERROR, "Error", "Up Data Failed", "Phone Number or Email are missing!");
    		return;
    	}
    	
    	System.out.println("Bnt Up data Handlerr");
    	Main.client.mainControl.clientMember.UpDataMember(newPhoneNumber, newEmail);
    	
    	
    	

    }
    

    
  


    @FXML
    void ClearBntHandler(ActionEvent event) {
      	PhoneNumberFeild.clear();
    	EmailFeild.clear();

    }

    @FXML
void LogoutHandler(ActionEvent event) throws IOException {
		
		Main.clientMember.LogoutHandler(membercontroller.curntllyLogIn.getMemberID());
		
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Logout Window");
		alert.setContentText("Are you Sure?");
		ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonData.NO);

		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
			if (type == okButton) {
				//Main.client.mainControl.clientMember.curntllyLogIn.setLogged(false);
				Main.client.profileController.ProfileStage.close();
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});
		
		

	}

    @FXML
    void MyBooksbnHandler(ActionEvent event) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try
				{

					System.out.println("MyBooksbnHandler in Home page");
					Main.client.profileController.ProfileStage.close();
					Main.client.HomePageController.MyBooksbnHandler(event);
			
				}
				catch (Exception e)
				{
					displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
				}
			}
		});


	}


    @FXML
    void ProfileHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	try
    	    	{
    	System.out.println("Profile Handler in Profile");
    	
    	 initialize();
    	    	}
    	    	catch (Exception e)
    	    	{
    	    		displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
    	    	}
    	    }
    	});

    }

    @FXML
    void SearchBookbntHandler(ActionEvent event) {
    	Main.client.profileController.ProfileStage.close();
    	Main.client.HomePageController.HomeStage.show();

    }
    
    
    public void initialize() {
        
    	Main.client.profileController=this;
    	String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    	String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    	NameLabel.setText("Hello "+membercontroller.curntllyLogIn.getFirstname()+" "+membercontroller.curntllyLogIn.getLastname()+"!");
    	DateL.setText(timeStamp);
    	TimeL.setText(timeStampclock);
    	FullNameField.setText(membercontroller.curntllyLogIn.getFirstname()+" "+membercontroller.curntllyLogIn.getLastname());
    	WelcomeLabel.setText("Welcome, "+membercontroller.curntllyLogIn.getFirstname()+" "+membercontroller.curntllyLogIn.getLastname()+":");
    	IDfiels.setText(membercontroller.curntllyLogIn.getMemberID());
    	CardNumberField.setText(membercontroller.curntllyLogIn.getSubscriptionMember());
    	StatusFeild.setText(membercontroller.curntllyLogIn.getStatus());
    	PhoneNumberFeild.setText(membercontroller.curntllyLogIn.getPhonenumber());
    	EmailFeild.setText(membercontroller.curntllyLogIn.getEmail());
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
	

}
