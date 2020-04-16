package Gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Client.Command;
import Client.Main;
import Controllers.LibrarianController;
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

public class ProfileEmployeesController {

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
    private Button ReturnBookbnt;

    @FXML
    private Button Inventorybnt;

    @FXML
    private Button Messagesbnt;

    @FXML
    private ImageView ImgeMessages;

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


   
    
    public static Stage profileEmployeesControllerStage;
    LibrarianController librariancontroller;

    @FXML
    void AddnewmemberbntHandler(ActionEvent event) {
    	
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				
		Main.client.librarianhomepagecontroller.AddnewmemberbntHandler(event);
			}
		});
	}
		
    

    @FXML
    void BntUpdataHandler(ActionEvent event) throws IOException {
    
    	String newEmail=EmailFeild.getText();

    	System.out.println(newEmail);
    	if ( newEmail.isEmpty())
    	{
    		displayAlert(AlertType.ERROR, "Error", "Up Data Failed", "Phone Number or Email are missing!");
    		return;
    	}
    	
    	System.out.println("Bnt Up data Handlerr");
    	newEmail="'"+EmailFeild.getText()+"'";
    	Main.librariancontroller.UpDataLibrarian( newEmail);

    }

    @FXML
    void ClearHandler(ActionEvent event) {
    	EmailFeild.clear();

    }

    @FXML
    void InventorybntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		
		 Main.client.profileEmployeesController.profileEmployeesControllerStage.close();
		 
		 Main.client.librarianhomepagecontroller.InventorybntHandler(event);
	
	}
});

    }

    @FXML
    void LogoutHandler(ActionEvent event) {
    	Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Logout Window");
		alert.setContentText("Are you Sure?");
		ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonData.NO);

		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
			if (type == okButton) {
			
				Main.client.profileEmployeesController.profileEmployeesControllerStage.close();
			
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});

    }

    @FXML
    void MessagesbntHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
    	
    	 Main.client.profileEmployeesController.profileEmployeesControllerStage.close();
    	 Main.client.librarianhomepagecontroller.MessagesbntHandler(event);
	}
});

    }

    @FXML
    void ProfileHandler(ActionEvent event) {
    	initialize();

    }

    @FXML
    void ReturnBookbntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		Main.client.librarianhomepagecontroller.ReturnBookbntHandler(event);
	}
});

    }

    @FXML
    void SearchBookbntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		 Main.client.profileEmployeesController.profileEmployeesControllerStage.close();
		 
    	 Main.client.librarianHomePageController.librarianHomePageStage.show();
	
			}
		});


    }

    @FXML
    void SearchmemberbntHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
		 Main.client.profileEmployeesController.profileEmployeesControllerStage.close();
	    	 Main.client.librarianhomepagecontroller.SearchmemberbntHandler(event);
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

		Main.client.profileEmployeesController=this;
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		//NameLabel.setText("Hello "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname()+"!");
		DateL.setText(timeStamp);
		TimeL.setText(timeStampclock);
		if(Main.permission.equals(Command.permission1)) {
			NameLabel.setText("Hello "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname()+"!");
		WelcomeLabel.setText("Welcome,  "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname()+" :");
		FullNameLabel.setText("Full Name : "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname());
		IDLabel.setText("Worker ID : " +librariancontroller.curntlibrarian.getID() );
		WorkerNumberLabel.setText("Worker Number : "+librariancontroller.curntlibrarian.getWorkerNumber());
		EmailFeild.setText(librariancontroller.curntlibrarian.getEmail());
		departmentLabel.setText("department : "+librariancontroller.curntlibrarian.getDepartment());
		
		roleLabel.setText(" role : Librarian");}
		else {
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

}

