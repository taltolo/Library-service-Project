package Gui;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Client.Main;
import Controllers.MemberController;
import Entity.Loan;
import Entity.Return;
import Entity.orders;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ActivityLognewController  implements Serializable {

    @FXML
    private AnchorPane AnchorHome;

    @FXML
    private ImageView Image;

    @FXML
    private TabPane tableView;

    @FXML
    private Tab Tab1;

    @FXML
    private TableView<Return> TableView;

    @FXML
    private TableColumn<Return,String> Title;

    @FXML
    private TableColumn<Return,Date> issueDate;

    @FXML
    private TableColumn<Return,Date> actualReturnDate;

    //@FXML
   // private TableColumn<Return,String> statusReturn;

    @FXML
    private Tab Tab2;

    @FXML
    private TableView<orders> TableView2;

    @FXML
    private TableColumn<orders,String> Title2;
    @FXML
    private TableColumn<orders,String> statusOrder;

    @FXML
    private TableColumn<orders, String> Option;


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
    private Button Cancelbnt;

    @FXML
    private Label labelONE;

    @FXML
    private Label labelTWO;

    
    
    ObservableList<Return> bookreturnlist=FXCollections.observableArrayList();
    public static ArrayList< Return> returnBooks=new ArrayList< Return>();
    public static ArrayList< Return> keepreturnBooks=new ArrayList< Return>();
    ObservableList<orders> bookorderslist=FXCollections.observableArrayList();
    public static ArrayList< orders> orderBooks=new ArrayList< orders>();
    public static ArrayList< orders> keeporderBooks=new ArrayList< orders>();
     public static Stage activityLognewControllerStage;
    MemberController membercontroller;

    

    @FXML
    void CancelbntHandler(ActionEvent event) {
    	
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
    	
    	Object object = TableView2.getSelectionModel().selectedItemProperty().get();
    	 if ((object instanceof orders)) {
    			System.out.println(object.toString());
    	    	orders cancelOrderMember=(orders)object;
    	    	System.out.println(cancelOrderMember.getMemberID());
    	    	TableView2.getSelectionModel().clearSelection();
    	    	  try {
					Main.client.mainControl.clientMember.cancelOrder(cancelOrderMember);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		 
    	 }
    
    	
    	else {
    		TableView.getSelectionModel().clearSelection();
    		 displayAlert(AlertType.ERROR , "ERROR" , " " , "Please Choose From Order Table ");
    	}
			}
		});

    }
    
    @FXML
    void ActivityLogbntHandler(ActionEvent event) {
    	initialize();

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
				Main.client.activityLognewController.activityLognewControllerStage.close();
				//Main.client.mainControl.clientMember.curntllyLogIn.setLogged(false);
				Main.client.HomePageController.HomeStage.close();
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});
		

    }

    @FXML
    void MyBooksbnHandler(ActionEvent event) throws IOException {
    	Main.client.activityLognewController.activityLognewControllerStage.close();
    	Main.client.HomePageController.MyBooksbnHandler(event);

    }

    @FXML
    void ProfileHandler(ActionEvent event) {
    	Main.client.activityLognewController.activityLognewControllerStage.close();
    	Main.client.HomePageController.ProfileHandler(event);

    }

    @FXML
    void SearchBookbntHandler(ActionEvent event) {
    	Main.client.activityLognewController.activityLognewControllerStage.close();
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
 	System.out.println((returnBooks.size()));
 	
 	keepreturnBooks.clear();
 	bookreturnlist.clear();
	if(returnBooks.size()!=0) {
	for(int i=0;i<returnBooks.size();i++) {
		bookreturnlist.add(returnBooks.get(i));
		keepreturnBooks.add(returnBooks.get(i));
	System.out.println(bookreturnlist.get(i).getstatusReturn());
	}
	
	returnBooks.clear();
	Title.setCellValueFactory(new PropertyValueFactory<>("bookname"));
	issueDate.setCellValueFactory(new PropertyValueFactory<Return,Date>("issueDate"));
	actualReturnDate.setCellValueFactory(new PropertyValueFactory<Return,Date>("actualReturnDate"));
	///statusReturn.setCellValueFactory(new PropertyValueFactory<>("statusReturn"));
	TableView.setItems(bookreturnlist);
	}

	else {
		bookreturnlist.add(null);
		TableView.setItems(bookreturnlist);
	
	}
	System.out.println(orderBooks.size());
	bookorderslist.clear();
	keeporderBooks.clear();
	if(orderBooks.size()!=0) {
		for(int i=0;i<orderBooks.size();i++) {
			bookorderslist.add(orderBooks.get(i));
			keeporderBooks.add(orderBooks.get(i));
		System.out.println(bookorderslist.get(i).getBookname());
		}
		System.out.println(bookorderslist.get(0).getBookname());
		
		orderBooks.clear();

		Title2.setCellValueFactory(new PropertyValueFactory<>("bookname"));
		statusOrder.setCellValueFactory(new PropertyValueFactory<>("statusOrder"));
		Option.setCellValueFactory(new PropertyValueFactory<>("Option"));
	
		TableView2.setItems(bookorderslist);
		}

	else {
		bookorderslist.add(null);
		TableView2.setItems(bookorderslist);
	}
	}
 	   
 	   
    
    
    public void initialize() {
    	
    	
        Main.client.activityLognewController=this;
        setStageViewTable();
    	String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    	String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    	NameLabel.setText("Hello "+membercontroller.curntllyLogIn.getFirstname()+" "+membercontroller.curntllyLogIn.getLastname()+"!");
    	DateL.setText(timeStamp);
    	TimeL.setText(timeStampclock);
    	
    }

}

