package Gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Client.Command;
import Client.Main;
import Controllers.LibrarianController;
import Entity.*;
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

public class SearchmemberController {

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
	private TableView<Member> Tableview;

	@FXML
	private TableColumn<Member, String> MemberID;

	@FXML
	private TableColumn<Member, String> FirstName;

	@FXML
	private TableColumn<Member, String> LastName;

	@FXML
	private TableColumn<Member, String> subscriptionMember;

	@FXML
	private TableColumn<Member, String> PhoneNumber;

	@FXML
	private GridPane GrindPane;

	@FXML
	private RadioButton byFirstNameBNT;

	@FXML
	private ToggleGroup Search;

	@FXML
	private RadioButton ByLastNameBNT;

	@FXML
	private RadioButton MemberIDBNT;

	@FXML
	private RadioButton bysubscriptionMemberBNT;

	@FXML
	private TextField ByMemberIDTextField;

	@FXML
	private TextField ByFirstNameNameTextField;

	@FXML
	private TextField bysubscriptionMemberTExtField;

	@FXML
	private TextField ByLastNameNameTextField;

	@FXML
	private Label SearchBookL;

	@FXML
	private Button Clearbnt;

	@FXML
	private Button Searchbnt;

	@FXML
	private Button ViewMemberbnt;

	@FXML
	private Button AddnewloanBNT;
	



	MainController mainController;
	ObservableList<Member> memberlist=FXCollections.observableArrayList();
	public static Stage searchmemberStage;
	LibrarianController librariancontroller;
	int caseBnt=0;
	public Main mainControl;
	public static Member lonerMember;
	
	
    @FXML
    void MessagesbntHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
    	
    	 Main.client.searchmemberController.searchmemberStage.close();
    	 Main.client.librarianhomepagecontroller.MessagesbntHandler(event);
	}
});
    }


	@FXML
	void AddnewloanBNTHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try
				{

					Object object =  Tableview.getSelectionModel().selectedItemProperty().get();

					Member addLoan=(Member)object;

					lonerMember=addLoan;


					if(addLoan!=null) {

						switch(addLoan.getStatus()) {

						case "Block":{

							displayAlert(Alert.AlertType.WARNING, "Block", "Member is Blocked", "The member can not lend a book due to his status as a block member");

							break;

						}
						case "Frozen":{
							displayAlert(Alert.AlertType.WARNING, "Frozen", "Member is frozenen", "The member can not lend a book due to his status as a frozen member");

							break;

						}
						case "Active":{
							Stage primaryStage = new Stage();
							Main.client.lendingaBookController.lendingaBookStage=primaryStage;
							FXMLLoader loader = new FXMLLoader(getClass().getResource("LendingaBook.fxml"));
							System.out.println(getClass().getResource("LendingaBook.fxml"));
							AnchorPane pane =(AnchorPane) loader.load();
							Scene scene = new Scene( pane );


							// setting the stage
							primaryStage.setScene( scene );
							primaryStage.setTitle( "Lending a Book" );
							primaryStage.show();

							break;
						}


						}


					}

				}
				catch (Exception e)
				{

					displayAlert(AlertType.ERROR, "Error", "The loan did not made", "Please be sure you press on a member");
				}

			}
		});

	}

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
	void ByLastNameBNTHandler(ActionEvent event) {
		ByFirstNameNameTextField.setDisable(true);
		bysubscriptionMemberTExtField.setDisable(true);
		ByLastNameNameTextField.setDisable(false);
		ByMemberIDTextField.setDisable(true);
		caseBnt=3;
	}

	@FXML
	void ClearHandler(ActionEvent event) {
		ByFirstNameNameTextField.clear();
		bysubscriptionMemberTExtField.clear();
		ByLastNameNameTextField.clear();
		ByMemberIDTextField.clear();
	}

	@FXML
	void InventorybntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		
		 Main.client.searchmemberController.searchmemberStage.close();
		 
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
				Main.client.searchmemberController.searchmemberStage.close();
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});

	}

	@FXML
	void MemberIDBNTHandler(ActionEvent event) {
		ByFirstNameNameTextField.setDisable(true);
		bysubscriptionMemberTExtField.setDisable(true);
		ByLastNameNameTextField.setDisable(true);
		ByMemberIDTextField.setDisable(false);
		caseBnt=1;

	}

	@FXML
	void ProfileHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		 Main.client.searchmemberController.searchmemberStage.close();
		 Main.client.librarianhomepagecontroller.ProfileHandler(event);
			}
		});
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
		 Main.client.searchmemberController.searchmemberStage.close();
    	 Main.client.librarianHomePageController.librarianHomePageStage.show();
			}
		});


	}

	public void handleSearchResultMember(ArrayList <Member>members) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try
				{


					memberlist.clear();

					for(int i=0 ;i<members.size();i++) {

						System.out.println(members.get(i).getFirstname()+" handle Search Result Member ");
						memberlist.add(members.get(i));


					}  

					if(memberlist.size()==0) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("An unsuccessful search");
						alert.setHeaderText(" Member is not existing ");
						alert.setContentText("Try again " );
						ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
						alert.getButtonTypes().setAll(okButton);
						alert.showAndWait();
					}
					else {
						members.clear();
						MemberID.setCellValueFactory(new PropertyValueFactory<>("MemberID"));
						FirstName.setCellValueFactory(new PropertyValueFactory<>("Firstname"));
						LastName.setCellValueFactory(new PropertyValueFactory<>("Lastname"));
						subscriptionMember.setCellValueFactory(new PropertyValueFactory<>("subscriptionMember"));
						PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("Phonenumber"));
						Tableview.setItems(memberlist);

					}



				}

				catch (Exception e) {
					displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
				}
			}
		});

	}






	@FXML
	void SearchHandler(ActionEvent event) {


		String search=null,where=null;


		switch(caseBnt) {

		case 1:
			search="'%"+ByMemberIDTextField.getText()+"%'";
			where="MemberID";
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
			search="'%"+bysubscriptionMemberTExtField.getText()+"%'";
			where="subscriptionMember";
			break;

		}

		Main.librariancontroller.searchForMember(where,search);




	}

	@FXML
	void SearchmemberbntHandler(ActionEvent event) {
		Main.client.searchmemberController.initialize();

	}

	@FXML
	void ViewMemberbntHandler(ActionEvent event) throws IOException {
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		Object object =  Tableview.getSelectionModel().selectedItemProperty().get();

		Member addLoan=(Member)object;
		lonerMember=addLoan;
		Main.librariancontroller.getAllActivitiesMember(addLoan);
		/*Main.client.searchmemberController.searchmemberStage.close();
		Stage primaryStage = new Stage();
		Main.client.viewMemberController.viewMemberController=primaryStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewMember.fxml"));
		System.out.println(getClass().getResource("ViewMember.fxml"));
		AnchorPane pane;
		try {
			pane = (AnchorPane) loader.load();
			Scene scene = new Scene( pane );
			// setting the stage
			primaryStage.setScene( scene );
			primaryStage.setTitle( "View Member" );
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
			}
		});

	}

	@FXML
	void byFirstNameBNTHandler(ActionEvent event) {
		ByFirstNameNameTextField.setDisable(false);
		bysubscriptionMemberTExtField.setDisable(true);
		ByLastNameNameTextField.setDisable(true);
		ByMemberIDTextField.setDisable(true);
		caseBnt=2;
	}

	@FXML
	void bysubscriptionMemberBNTHandler(ActionEvent event) {
		ByFirstNameNameTextField.setDisable(true);
		bysubscriptionMemberTExtField.setDisable(false);
		ByLastNameNameTextField.setDisable(true);
		ByMemberIDTextField.setDisable(true);
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

		Main.client.searchmemberController=this;
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		NameLabel.setText("Hello "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname()+"!");
		DateL.setText(timeStamp);
		TimeL.setText(timeStampclock);
		memberlist.add(null);
		Tableview.setItems(memberlist);
	}

	public void openViewStage() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				
				Main.client.searchmemberController.searchmemberStage.close();
				Stage primaryStage = new Stage();
				Main.client.viewMemberController.viewMemberController=primaryStage;
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewMember.fxml"));
				System.out.println(getClass().getResource("ViewMember.fxml"));
				AnchorPane pane;
				try {
					pane = (AnchorPane) loader.load();
					Scene scene = new Scene( pane );
					// setting the stage
					primaryStage.setScene( scene );
					primaryStage.setTitle( "View Member" );
					primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
		
	}
});
	}

}
