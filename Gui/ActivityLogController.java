package Gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.time.LocalDate;
import Client.Main;
import Controllers.LibrarianController;
import Controllers.MemberController;
import Entity.Loan;
import Entity.Return;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ActivityLogController {

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
    private TabPane TabPane;

    @FXML
    private Tab Historyofloaning;

    @FXML
    private TableView<Return> TableHistory;

    @FXML
    private TableColumn<Return, String> TitleHistory;

    @FXML
    private TableColumn<Return, Date> IssueDateHistory;

    @FXML
    private TableColumn<Return, Date> ReturnDateHistory;

    @FXML
    private TableColumn<Return, String> ReturnStausHistory;

    @FXML
    private Tab MyOrders;

    @FXML
    private GridPane GridPaneMyOrders;

    @FXML
    private Label TitleOrder;

    @FXML
    private Label StausOrder;

    @FXML
    private Label Option;
    
   
    ObservableList<Return> bookreturnlist=FXCollections.observableArrayList();
    public static ArrayList< Return> returnBook=new ArrayList< Return>();
     public static Stage activityLogControllerStage;
    MemberController membercontroller;

    @FXML
    void ActivityLogbntHandler(ActionEvent event) {
    	initialize();

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
				Main.client.activityLogController.activityLogControllerStage.close();
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});

    }

    @FXML
    void MyBooksbnHandler(ActionEvent event) throws IOException {
    	Main.client.activityLogController.activityLogControllerStage.close();
    	Main.client.HomePageController.MyBooksbnHandler(event);

    }

    @FXML
    void ProfileHandler(ActionEvent event) {
    	Main.client.activityLogController.activityLogControllerStage.close();
    	Main.client.HomePageController.ProfileHandler(event);

    }

    @FXML
    void SearchBookbntHandler(ActionEvent event) {
    	Main.client.activityLogController.activityLogControllerStage.close();
    	Main.client.HomePageController.HomeStage.show();

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
 	
	
    public void setStageViewTable( ) {
 	   
 	   System.out.println(" set Stage View Table");
 	

 	   bookreturnlist.clear();
 		if(returnBook.size()!=0) {
 		for(int i=0;i<returnBook.size();i++) {
 			//bookreturnlist.add(Return.ReturnBook.get(i));
 		
 			bookreturnlist.add(returnBook.get(i));
 			//System.out.println(bookreturnlist.get(i).getBookname()+bookreturnlist.get(i).getRimenderSent());
 		}
 		returnBook.clear();
 		TitleHistory.setCellValueFactory(new PropertyValueFactory<Return,String>("bookname"));
 		IssueDateHistory.setCellValueFactory(new PropertyValueFactory<Return,Date>("issueDate"));
 		ReturnDateHistory.setCellValueFactory(new PropertyValueFactory<Return,Date>("actualReturnDate"));
 		ReturnStausHistory.setCellValueFactory(new PropertyValueFactory<Return,String>("rimenderSent"));
 		TableHistory.setItems(bookreturnlist);}
 		else {
 			bookreturnlist.add(null);
 			TableHistory.setItems(bookreturnlist);
 			
 		}
 	   
 	   
    }
 	
 	
 	
    public void initialize() {
    	setStageViewTable();
    	
        Main.client.activityLogController=this;
    	String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    	String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    	NameLabel.setText("Hello "+membercontroller.curntllyLogIn.getFirstname()+" "+membercontroller.curntllyLogIn.getLastname()+"!");
    	DateL.setText(timeStamp);
    	TimeL.setText(timeStampclock);
    	
    }

}
