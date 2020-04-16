package Gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Client.Main;
import Controllers.LibrarianController;
import Entity.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MessageController {

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
    private TableView<Message> Tableview;

    @FXML
    private TableColumn<Message, String> MemberID;

    @FXML
    private TableColumn<Message, String> FullName;

    @FXML
    private TableColumn<Message, String> Message;

    @FXML
    private Label MessageL;

    @FXML
    private Button ViewMessagebnt;
	LibrarianController librariancontroller;
	ObservableList<Message> msglist=FXCollections.observableArrayList();
    public static Stage MessageControllerStage;
    public ArrayList<Message> MSG = new ArrayList<Message>();

    @FXML
    void AddnewmemberbntHandler(ActionEvent event) {
    	 Main.client.librarianhomepagecontroller.AddnewmemberbntHandler(event);

    }

    @FXML
    void InventorybntHandler(ActionEvent event) {
    	
    	Main.client.messageController.MessageControllerStage.close();
    Main.client.librarianhomepagecontroller.InventorybntHandler(event);

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
				Main.client.messageController.MessageControllerStage.close();
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});

    }

    @FXML
    void MessagesbntHandler(ActionEvent event) {
    	initialize();

    }

    @FXML
    void ProfileHandler(ActionEvent event) {
    	Main.client.messageController.MessageControllerStage.close();
        Main.client.librarianhomepagecontroller.ProfileHandler(event);

    }

    @FXML
    void ReturnBookbntHandler(ActionEvent event) {
    	 Main.client.librarianhomepagecontroller.ReturnBookbntHandler(event);

    }

    @FXML
    void SearchBookbntHandler(ActionEvent event) {
    	Main.client.messageController.MessageControllerStage.close();
        Main.client.librarianhomepagecontroller.librarianHomePageStage.show();

    }

    @FXML
    void SearchmemberbntHandler(ActionEvent event) {
    	Main.client.messageController.MessageControllerStage.close();
        Main.client.librarianhomepagecontroller.SearchmemberbntHandler(event);

    }

    @FXML
    void ViewMessagebntHandler(ActionEvent event) {
   	 
 		Platform.runLater(new Runnable() {
			@Override
			public void run() {
    	
    	Object object = Tableview.getSelectionModel().selectedItemProperty().get();
    	Message msg=(Message)object;
    	
    	displayAlert(AlertType.INFORMATION , "View Message " , "Message From: "+msg.getFullName() +" " , msg.getMessage());
    	
    	
    	
			}
		});
    	

    }
    
	public void handleSearchresult()
	{
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try
				{



					msglist.clear();

					for(int i=0 ;i<MSG.size();i++) {

						System.out.println(MSG.get(i).getMessage()+"handle Search result ");
						msglist.add(MSG.get(i));
						System.out.println(MSG.get(0).getMessage());

					}  

					if(msglist.size()==0) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("An unsuccessful search");
						alert.setHeaderText(" Book is missing ");
						alert.setContentText("We're sorry we don't have this book " );
						ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
						alert.getButtonTypes().setAll(okButton);
						alert.showAndWait();
					}
					else {
						System.out.println(MSG.get(0).getMessage());
						MSG.clear();
						MemberID.setCellValueFactory(new PropertyValueFactory<>("memberID"));
						FullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
						Message.setCellValueFactory(new PropertyValueFactory<>("Message"));

						Tableview.setItems(msglist);

					}


//		delete from message where test = CURDATE(); למחיקה אוטומטית מהDB כשאר באותו יום
	//	String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
					
				}

				catch (Exception e) {
					displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
				}
			}
		});

	}
	
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

		Main.client.messageController=this;
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		NameLabel.setText("Hello "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname()+"!");
		DateL.setText(timeStamp);
		TimeL.setText(timeStampclock);
		msglist.add(null);
		Tableview.setItems(msglist);
	}

}
