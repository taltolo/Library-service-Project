package Gui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Client.Main;
import Controllers.MemberController;
import Entity.Return;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ActivityLogmemberController {

	 @FXML
	    private TabPane tableView;

	    @FXML
	    private Tab Tab1;

	    @FXML
	    private Tab Tab2;
	
    @FXML
    private AnchorPane AnchorHome;

    @FXML
    private ImageView Image;
    @FXML
    private TableView<Return> TableView;

    @FXML
    private TableColumn<Return,String> Title;

    @FXML
    private TableColumn<Return,Date> issueDate;

    @FXML
    private TableColumn<Return,Date> actualReturnDate;

    @FXML
    private TableColumn<Return,String> statusReturn;
    
    /*
    @FXML
    private TableView<Return> TableView;

    @FXML
    private TableColumn<Return,String> Title;

    @FXML
    private TableColumn<Return,Date> issueDate;

    @FXML
    private TableColumn<Return,Date> actualReturnDate;

    @FXML
    private TableColumn<Return, String> statusReturn;
    

   /* @FXML
    private TableView<Return> TableHistory;

    @FXML
    private TableColumn<Return, String> TitleHistory;

    @FXML
    private TableColumn<Return, Date> IssueDateHistory;

    @FXML
    private TableColumn<Return, Date> ReturnDateHistory;

    @FXML
    private TableColumn<Return, String> ReturnStausHistory;*/

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
    
    
    
    ObservableList<Return> bookreturnlist=FXCollections.observableArrayList();
    public static ArrayList< Return> returnBooks=new ArrayList< Return>();
     public static Stage activityLogmemberControllerStage;
    MemberController membercontroller;

    @FXML
    void ActivityLogbntHandler(ActionEvent event) {

    }

    @FXML
    void LogoutHandler(ActionEvent event) {

    }

    @FXML
    void MyBooksbnHandler(ActionEvent event) {

    }

    @FXML
    void ProfileHandler(ActionEvent event) {

    }

    @FXML
    void SearchBookbntHandler(ActionEvent event) {

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
 	System.out.println((returnBooks.size()));

 	bookreturnlist.clear();
	if(returnBooks.size()!=0) {
	for(int i=0;i<returnBooks.size();i++) {
		bookreturnlist.add(returnBooks.get(i));
	System.out.println(bookreturnlist.get(i).getstatusReturn());
	}
	returnBooks.clear();
	Title.setCellValueFactory(new PropertyValueFactory<Return,String>("bookname"));
	issueDate.setCellValueFactory(new PropertyValueFactory<Return,Date>("issueDate"));
	actualReturnDate.setCellValueFactory(new PropertyValueFactory<Return,Date>("actualReturnDate"));
	statusReturn.setCellValueFactory(new PropertyValueFactory<Return,String>("statusReturn"));
	TableView.setItems(bookreturnlist);
	}
	/*
	TitleHistory.setCellValueFactory(new PropertyValueFactory<Return,String>("bookname"));
	IssueDateHistory.setCellValueFactory(new PropertyValueFactory<Return,Date>("issueDate"));
	ReturnDateHistory.setCellValueFactory(new PropertyValueFactory<Return,Date>("returnDate"));
	ReturnStausHistory.setCellValueFactory(new PropertyValueFactory<Return,String>("statusReturn"));
	TableHistory.setItems(bookreturnlist);}*/
	else {
		bookreturnlist.add(null);
		TableView.setItems(bookreturnlist);
		//TableHistory.setItems(bookreturnlist);
		
	}
 	   
 	   
    }
    
    public void initialize() {
    	
    	
        Main.client.activityLogmemberController=this;
        setStageViewTable();
    	String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    	String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    	NameLabel.setText("Hello "+membercontroller.curntllyLogIn.getFirstname()+" "+membercontroller.curntllyLogIn.getLastname()+"!");
    	DateL.setText(timeStamp);
    	TimeL.setText(timeStampclock);
    	
    }

}
