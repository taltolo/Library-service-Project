package Gui;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

public class ViewEmployeeController {

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
    private Label FirstNameLabel;

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
    private TextField FirstNameField;

    @FXML
    private TextField WorkerIDField;

    @FXML
    private TextField roleField;

    @FXML
    private TextField WorkerNumberField;

    @FXML
    private TextField departmentField;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private TextField phonenumberFeild;
    
    @FXML
    private Label LastNameLabel;

    @FXML
    private TextField LastNameField;
    @FXML
    private Label locitonLabel;

    @FXML
    private Button backbnt;
    
    public static Stage viewEmployeeStage;
    
    public static Employees employeesUPDATA; 
    

    @FXML
    void AddnewmemberbntHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				
		    	Main.client.librarymanagerHomePageController.AddnewmemberbntHandler(event);

			}
		});

    }
    
    @FXML
    void backbntHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
    	Main.client.viewEmployeeController.viewEmployeeStage.close();
    	Main.client.searchemployeesController.searchEmployeesControllerStage.show();
    	
	}
});
    }

    @FXML
    void BntUpdataHandler(ActionEvent event) {
    	boolean input = false;
    	String errorMsg = "The following fields are wrong:";
    	String firstName =FirstNameField.getText();
    	String lastName= LastNameField.getText();
    	String ID=WorkerIDField.getText();
    	String WorkerNumber=WorkerNumberField.getText();
    	String email=EmailFeild.getText();
    	String phone=phonenumberFeild.getText();
    	String role=roleField.getText();
    	String department=departmentField.getText();
    	
    	if(firstName.isEmpty()|| lastName.isEmpty() || ID.isEmpty() || WorkerNumber.isEmpty() || email.isEmpty() || phone.isEmpty() || role.isEmpty() || department.isEmpty() ) {
    		
    		displayAlert(AlertType.ERROR, "Error", "Up Data Failed", "One of the Faileds  is missing!");
    		return;
    	}
    	else {
    		
    		
    		if (!validateName(firstName))
    		{
    			errorMsg=errorMsg+"\nFirst name ";
    			input=true;
    		}
    		if (!validateName(lastName))
    		{
    			errorMsg=errorMsg+"\nlast name";
    			input=true;
    		}
    		if (!phone.matches("^[0][5][0-9]{8}"))
    		{
    			if(!phone.matches("^[0][2,3,4,8,9][0-9]{7}"))
    				errorMsg=errorMsg+"\nphone number";
    			input=true;

    		}
    		if (!ID.matches("[0-9]{7,9}"))
    		{
    			errorMsg=errorMsg+"\nid number";
    			input=true;
    		}
    		if(!email.matches("^[a-zA-Z0-9.!#$%&'*+=?^_`\\{|\\}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"))
    		{
    			errorMsg=errorMsg+"\nemail";
    			input=true;
    		}
    		System.out.println("worker number ="+ WorkerNumber);
    		System.out.println(WorkerNumber.substring(0, 1).equals("2"));
    		System.out.println( WorkerNumber.substring(0, 1));
    		System.out.println(WorkerNumber.substring(0, 1).equals("1"));

    		if(!((WorkerNumber.substring(0, 1)).equals("1")|| WorkerNumber.substring(0, 1).equals("2"))) {
    			
    			errorMsg=errorMsg+"\nWorker Number";
    			input=true;
    		}
    		System.out.println("department ="+department);
    		System.out.println(department.equals("Library"));
    		System.out.println(department.equals("Management Library"));
    		if(!(department.equals("Library") || department.equals("Management Library"))) {
    			errorMsg=errorMsg+"\ndepartment";
    			input=true;
    			
    		}
    	
    		
    			if(input){
    				errorMsg=errorMsg+".";
    				displayAlert(Alert.AlertType.ERROR,"Add new member", "ERROR Message ", errorMsg);
    			}
    			else {
    			
    			   
    				Main.libraryManagerController.UpDataLibrarianInfo(firstName,lastName,phone,ID,email,WorkerNumber,department);

    			}
    		
    		
    		
    	}

    }
    
	public static boolean validateName( String name )
	{
		return name.matches( "^[A-Z]{1}[a-z]+$" );
	} 

    @FXML
    void ClearHandler(ActionEvent event) {
    	
    	FirstNameField.clear();
    	LastNameField.clear();
		WorkerIDField.clear();
		WorkerNumberField.clear();
		EmailFeild.clear();
		phonenumberFeild.clear();
		roleField.clear();
		departmentField.clear();
    	

    }

    @FXML
    void InventorybntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		
				Main.client.viewEmployeeController.viewEmployeeStage.close();
		 
		 Main.client.librarymanagerHomePageController.InventorybntHandler(event);
	
	}
});

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
    				 Main.client.viewEmployeeController.viewEmployeeStage.close();
    				Main.client.mainController.mainStage.show();
    				Main.client.mainController.TextFiledUsername.clear();
    				Main.client.mainController.TextFiledPassword.clear();

    			}
    		});

    }

    @FXML
    void ProfileHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		
				Main.client.viewEmployeeController.viewEmployeeStage.close();
		 
		 Main.client.librarymanagerHomePageController.ProfileHandler(event);
	
	}
});


    }

    @FXML
    void ReportsbntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		
				Main.client.viewEmployeeController.viewEmployeeStage.close();
		 
		 Main.client.librarymanagerHomePageController.ReportsbntHandler(event);
	
	}
});


    }

    @FXML
    void ReturnBookbntHandler(ActionEvent event) {
      	 Main.client.librarymanagerHomePageController.ReturnBookbntHandler(event);
    }

    @FXML
    void SearchBookbntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		
				Main.client.viewEmployeeController.viewEmployeeStage.close();
		 
		 Main.client.librarymanagerHomePageController.librarymanagerControllerStage.show();
	
	}
});


    }

    @FXML
    void SearchEmployeesbntHandler(ActionEvent event) {
    	initialize();
    	

    }

    @FXML
    void SearchmemberbntHandler(ActionEvent event) {
       	Platform.runLater(new Runnable() {
    			@Override
    			public void run() {
        	Main.client.viewEmployeeController.viewEmployeeStage.close();
        	Main.client.searchmemberController.searchmemberStage.show();
        	
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

    	Main.client.viewEmployeeController=this;
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		//NameLabel.setText("Hello "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname()+"!");
		DateL.setText(timeStamp);
		TimeL.setText(timeStampclock);
		NameLabel.setText("Hello "+Main.client.librarymanagercontroller.curntlibrarymanager.getFirstname()+" "+Main.client.librarymanagercontroller.curntlibrarymanager.getLastname()+"!");
		WelcomeLabel.setText(Main.client.searchemployeesController.employeesView.getFirstname()+" "+Main.client.searchemployeesController.employeesView.getLastname()+" View :");
		FirstNameField.setText(Main.client.searchemployeesController.employeesView.getFirstname());
		LastNameField.setText(Main.client.searchemployeesController.employeesView.getLastname());
		WorkerIDField.setText(Main.client.searchemployeesController.employeesView.getID());
		WorkerNumberField.setText(Main.client.searchemployeesController.employeesView.getWorkerNumber());
		EmailFeild.setText(Main.client.searchemployeesController.employeesView.getEmail());
		phonenumberFeild.setText(Main.client.searchemployeesController.employeesView.getPhoneNumber());
		
		if(Main.client.searchemployeesController.employeesView.getDepartment().equals("Library")) {
		roleField.setText("Librarian");}
		else {
			
			roleField.setText("Library manager");
		}
		departmentField.setText(Main.client.searchemployeesController.employeesView.getDepartment());
		}
    
    
    
  

}
