package Gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Client.Main;
import Entity.Employees;
import Entity.Member;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchEmployeesController {

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
    private TableView<Employees> Tableview;

    @FXML
    private TableColumn<Employees,String> EmployeeID;

    @FXML
    private TableColumn<Employees,String> FirstName;

    @FXML
    private TableColumn<Employees,String> LastName;

    @FXML
    private TableColumn<Employees,String> WorkerNumber;

    @FXML
    private TableColumn<Employees,String> Email;

    @FXML
    private GridPane GrindPane;

    @FXML
    private RadioButton byFirstNameBNT;

    @FXML
    private ToggleGroup Search;

    @FXML
    private RadioButton ByLastNameBNT;

    @FXML
    private RadioButton EmployeesIDBNT;

    @FXML
    private RadioButton WorkerNumberBNT;

    @FXML
    private TextField ByEmployeesTextField;

    @FXML
    private TextField ByFirstNameNameTextField;

    @FXML
    private TextField bysWorkerNumberTExtField;

    @FXML
    private TextField ByLastNameNameTextField;

    @FXML
    private Label SearchEmployeesL;

    @FXML
    private Button Clearbnt;

    @FXML
    private Button Searchbnt;

    @FXML
    private Button ViewEmployeebnt;
    
    public static ArrayList<Employees>employees=new ArrayList<Employees>();
   public ObservableList<Employees> employeeslist=FXCollections.observableArrayList();
	int caseBnt=0;
	
	public static Employees employeesView;
    public static Stage searchEmployeesControllerStage;

    @FXML
    void AddnewmemberbntHandler(ActionEvent event) {
    	Main.client.librarymanagerHomePageController.AddnewmemberbntHandler(event);


    }

    @FXML
    void ByLastNameBNTHandler(ActionEvent event) {
    	ByFirstNameNameTextField.setDisable(true);
    	ByEmployeesTextField.setDisable(true);
		ByLastNameNameTextField.setDisable(false);
		bysWorkerNumberTExtField.setDisable(true);
		caseBnt=3;

    }

    @FXML
    void ClearHandler(ActionEvent event) {
    	ByFirstNameNameTextField.clear();
    	ByEmployeesTextField.clear();
		ByLastNameNameTextField.clear();
		bysWorkerNumberTExtField.clear();
	

    }

    @FXML
    void EmployeesIDBNT(ActionEvent event) {
    	ByFirstNameNameTextField.setDisable(true);
    	ByEmployeesTextField.setDisable(false);
		ByLastNameNameTextField.setDisable(true);
		bysWorkerNumberTExtField.setDisable(true);
		caseBnt=1;

    }

    @FXML
    void InventorybntHandler(ActionEvent event) {
    	
    	Main.client.searchemployeesController.searchEmployeesControllerStage.close();
    	
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
			
				Main.client.searchemployeesController.searchEmployeesControllerStage.close();
			
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});

    	
    	
    	

    }

    @FXML
    void ProfileHandler(ActionEvent event) {
Main.client.searchemployeesController.searchEmployeesControllerStage.close();
    	
    	Main.client.librarymanagerHomePageController.ProfileHandler(event);

    }

    @FXML
    void ReportsbntHandler(ActionEvent event) {
Main.client.searchemployeesController.searchEmployeesControllerStage.close();
    	
    	Main.client.librarymanagerHomePageController.ReportsbntHandler(event);

    }

    @FXML
    void ReturnBookbntHandler(ActionEvent event) {
    	Main.client.librarymanagerHomePageController.ReturnBookbntHandler(event);

    }

    @FXML
    void SearchBookbntHandler(ActionEvent event) {
Main.client.searchemployeesController.searchEmployeesControllerStage.close();
    	
    	Main.client.librarymanagerHomePageController.librarymanagerControllerStage.show();

    }

    @FXML
    void SearchEmployeesbntHandler(ActionEvent event) {
    	initialize();

    }


	@FXML
	void SearchHandler(ActionEvent event) {


		String search=null,where=null;


		switch(caseBnt) {

		case 1:
			search="'%"+ByEmployeesTextField.getText()+"%'";
			where="ID";
			break;
		case 2:
			search="'%"+ByFirstNameNameTextField.getText()+"%'";
			where="FirstName";
			break;
		case 3:
			search="'%"+ByLastNameNameTextField.getText()+"%'";
			where="LastName";
			break;
		case 4:
			search="'%"+bysWorkerNumberTExtField.getText()+"%'";
			where="WorkerNumber";
			break;

		}

		Main.libraryManagerController.searchForEmployees(where,search);




	}

    @FXML
    void SearchmemberbntHandler(ActionEvent event) {
Main.client.searchemployeesController.searchEmployeesControllerStage.close();
    	
    	Main.client.librarymanagerHomePageController.SearchmemberbntHandler(event);
    	

    }

    @FXML
    void ViewEmployeebntHandler(ActionEvent event) {
    	
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		Object object =  Tableview.getSelectionModel().selectedItemProperty().get();

		employeesView=(Employees)object;
		
		Main.client.searchemployeesController.searchEmployeesControllerStage.close();
		try {
		Stage primaryStage = new Stage();
		Main.client.viewEmployeeController.viewEmployeeStage=primaryStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewEmployee.fxml"));
		System.out.println(getClass().getResource("ViewEmployee.fxml"));
		AnchorPane pane;
		
			pane = (AnchorPane) loader.load();
			Scene scene = new Scene( pane );

			// setting the stage
			primaryStage.setScene( scene );
			primaryStage.setTitle( "View Employee" );
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		

	
			}
		});
    	

    }

    @FXML
    void byFirstNameBNTHandler(ActionEvent event) {
    	ByFirstNameNameTextField.setDisable(false);
    	ByEmployeesTextField.setDisable(true);
		ByLastNameNameTextField.setDisable(true);
		bysWorkerNumberTExtField.setDisable(true);
		caseBnt=2;

    }

    @FXML
    void byWorkerNumberBNTHandler(ActionEvent event) {
    	ByFirstNameNameTextField.setDisable(true);
    	ByEmployeesTextField.setDisable(true);
		ByLastNameNameTextField.setDisable(true);
		bysWorkerNumberTExtField.setDisable(false);
		caseBnt=4;


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

		Main.client.searchemployeesController=this;
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		NameLabel.setText("Hello "+Main.client.librarymanagercontroller.curntlibrarymanager.getFirstname()+" "+Main.client.librarymanagercontroller.curntlibrarymanager.getLastname()+"!");
		DateL.setText(timeStamp);
		employeeslist.add(null);
		Tableview.setItems(employeeslist);
		
	
	}

	public void handleSearchResultEmployees() {
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try
				{
					

					employeeslist.clear();

					for(int i=0 ;i<employees.size();i++) {

						System.out.println(employees.get(i).getFirstname()+" handle Search Result Member ");
						employeeslist.add(employees.get(i));


					}  


					if(employeeslist.size()==0) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("An unsuccessful search");
						alert.setHeaderText(" Member is not existing ");
						alert.setContentText("Try again " );
						ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
						alert.getButtonTypes().setAll(okButton);
						alert.showAndWait();
					}
					else {
						employees.clear();
						EmployeeID.setCellValueFactory(new PropertyValueFactory<>("ID"));
						FirstName.setCellValueFactory(new PropertyValueFactory<>("Firstname"));
						LastName.setCellValueFactory(new PropertyValueFactory<>("Lastname"));
						WorkerNumber.setCellValueFactory(new PropertyValueFactory<>("WorkerNumber"));
						Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
						Tableview.setItems(employeeslist);

					}



				}

				catch (Exception e) {
					displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
				}
			}
		});
		
		
	}

}
