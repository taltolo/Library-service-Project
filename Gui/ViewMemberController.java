package Gui;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import Client.Main;
import Controllers.LibrarianController;
import Entity.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewMemberController {

    @FXML
    private AnchorPane AnchorHome;

    @FXML
    private ImageView Image;

    @FXML
    private TableView<Loan> TableviewLoan;

    @FXML
    private TableColumn<Loan,String> Titel;

    @FXML
    private TableColumn<Loan,String> Tagged;

    @FXML
    private TableColumn<Loan,Date> IssueDate;

    @FXML
    private TableColumn<Loan,Date> ReturnDate;

    @FXML
    private Label ViewMemberL;

    @FXML
    private Button extendloanbnt;

    @FXML
    private TextField MemberIDTextField;

    @FXML
    private Label IDLabel;

    @FXML
    private Label EmailLabel;

    @FXML
    private TextField EmailTextField;

    @FXML
    private Label StatusLabel;

    @FXML
    private TextField PhonnumberTextField;

    @FXML
    private Label PhonnumberLabel;

    @FXML
    private ComboBox<String> StatusComboBox;

    @FXML
    private TableView<Return> TableviewReturn;

    @FXML
    private TableColumn<Return,String> TitelReturn;

    @FXML
    private TableColumn<Return,Date> IssueDateReturn;

    @FXML
    private TableColumn<Return,Date> ReturnDateReturn;
    @FXML
    private TableColumn<Return,Date> actualReturnDate;

    @FXML
    private Label RecentlyreturnedLabel;

    @FXML
    private Label CurrentlyloansLabel;

    @FXML
    private Button SAVEEDITbnt;

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
    
    public static Stage viewMemberController;
    ObservableList<Loan> bookloanlist=FXCollections.observableArrayList();
    ObservableList<Return> bookreturnlist=FXCollections.observableArrayList();
    LibrarianController librariancontroller;
    public static ArrayList <Loan>loanBooks=new  ArrayList <Loan>();
    public static ArrayList< Return> returnBooks=new ArrayList< Return>();
    Member extendloan;

    @FXML
    void MessagesbntHandler(ActionEvent event) {
      	 Main.client.viewMemberController.viewMemberController.close();
   		 Main.client.librarianhomepagecontroller.MessagesbntHandler(event);


    }
    
    
    @FXML
    void AddnewmemberbntHandler(ActionEvent event) {
      	
   		 Main.client.librarianhomepagecontroller.AddnewmemberbntHandler(event);


    }

    @FXML
    void InventorybntHandler(ActionEvent event) {
    	 Main.client.viewMemberController.viewMemberController.close();
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
				Main.client.viewMemberController.viewMemberController.close();
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});

    }

    @FXML
    void ProfileHandler(ActionEvent event) {
      	 Main.client.viewMemberController.viewMemberController.close();
   		 Main.client.librarianhomepagecontroller.ProfileHandler(event);


    }

    @FXML
    void ReturnBookbntHandler(ActionEvent event) {

   		 Main.client.librarianhomepagecontroller.ReturnBookbntHandler(event);


    }

    @FXML
    void SAVEEDITbntHandler(ActionEvent event) {
    	boolean input = false;
		String errorMsg = "The following fields are wrong:";
    	String MemberID=MemberIDTextField.getText();
    	String Email=EmailTextField.getText();
    	String Phonnumber=PhonnumberTextField.getText();
    	String Status=StatusComboBox.getValue();
		if (!Phonnumber.matches("^[0][5][0-9]{8}"))
		{
			if(!Phonnumber.matches("^[0][2,3,4,8,9][0-9]{7}"))
				errorMsg=errorMsg+"\nphone number";
			input=true;

		}
		if (!MemberID.matches("[0-9]{7,9}"))
		{
			errorMsg=errorMsg+"\nid number";
			input=true;
		}
		if(!Email.matches("^[a-zA-Z0-9.!#$%&'*+=?^_`\\{|\\}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"))
		{
			errorMsg=errorMsg+"\nemail";
			input=true;
		}
		
		if(input==true)
		{
			displayAlert(AlertType.ERROR, "Error", "SAVE EDIT", errorMsg);
		}
		else {

			Main.librariancontroller.setNewValueForMember(MemberID,Email,Phonnumber,Status);
			
			}
		}
		
    	
    	

    

    @FXML
    void SearchBookbntHandler(ActionEvent event) {
      	 Main.client.viewMemberController.viewMemberController.close();
   		 Main.client.librarianhomepagecontroller.librarianHomePageStage.show();


    }

    @FXML
    void SearchmemberbntHandler(ActionEvent event) {
      	 Main.client.viewMemberController.viewMemberController.close();
   		 Main.client.librarianhomepagecontroller.SearchmemberbntHandler(event);


    }

    @FXML
    void extendloanbntHandler(ActionEvent event) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
    	
    	Object object = TableviewLoan.getSelectionModel().selectedItemProperty().get();
    	System.out.println(object.toString());
    	Loan extenLoanMember=(Loan)object;
    	System.out.println(extenLoanMember.getIdloan());
    	String idMember=MemberIDTextField.getText();
    	System.out.println(idMember);
    	if(extenLoanMember.getTagged().equals("wanted")) {
    		displayAlert(AlertType.INFORMATION , "Extend loan" , "Unsucceed extending loan " , extenLoanMember.getBookname()+" is a WANTED book ,\nTherefore, his loan can not be extended.");
    	
    	}
    	else {
    		Main.librariancontroller.extendloan(extenLoanMember,idMember);
    	}
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
	
   void setStageViewTable() {
	   
	   bookloanlist.clear();
	   System.out.println(" set Stage View Table");
	   System.out.println(loanBooks.toString());
	   if(loanBooks.size()!=0) {
		for(int i=0;i<loanBooks.size();i++) {
			bookloanlist.add(loanBooks.get(i));
			System.out.println(loanBooks.get(i).toString());
		}
		loanBooks.clear();
		Titel.setCellValueFactory(new PropertyValueFactory<Loan,String>("bookname"));
		Tagged.setCellValueFactory(new PropertyValueFactory<Loan,String>("Tagged"));
		IssueDate.setCellValueFactory(new PropertyValueFactory<Loan,Date>("IssueDate"));
		ReturnDate.setCellValueFactory(new PropertyValueFactory<Loan,Date>("ReturnDate"));
		TableviewLoan.setItems(bookloanlist);}
	   else {
		   bookloanlist.add(null);
		   TableviewLoan.setItems(bookloanlist);
		   
	   }
	   bookreturnlist.clear();
		if(returnBooks.size()!=0) {
		for(int i=0;i<returnBooks.size();i++) {
			bookreturnlist.add(returnBooks.get(i));
			System.out.println(returnBooks.toString());
		}
		returnBooks.clear();
		TitelReturn.setCellValueFactory(new PropertyValueFactory<Return,String>("bookname"));
		IssueDateReturn.setCellValueFactory(new PropertyValueFactory<Return,Date>("issueDate"));
		ReturnDateReturn.setCellValueFactory(new PropertyValueFactory<Return,Date>("returnDate"));
		actualReturnDate.setCellValueFactory(new PropertyValueFactory<Return,Date>("actualReturnDate"));
		TableviewReturn.setItems(bookreturnlist);}
		else {
			bookreturnlist.add(null);
			TableviewReturn.setItems(bookreturnlist);
			
		}
	   
	   
   }
    
    
	public void initialize() {
		setStageViewTable();
		Main.client.viewMemberController=this;
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		NameLabel.setText("Hello "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname()+"!");
		DateL.setText(timeStamp);
		TimeL.setText(timeStampclock);
		ViewMemberL.setText(Main.client.searchmemberController.lonerMember.getFirstname()+" "+Main.client.searchmemberController.lonerMember.getLastname()+" view :");
		MemberIDTextField.setText(Main.client.searchmemberController.lonerMember.getMemberID());
		PhonnumberTextField.setText(Main.client.searchmemberController.lonerMember.getPhonenumber());
		EmailTextField.setText(Main.client.searchmemberController.lonerMember.getEmail());
		StatusComboBox.getItems().addAll("Active","Frozen","Block");
		StatusComboBox.setValue(Main.client.searchmemberController.lonerMember.getStatus());
		
		//bookreturnlist.add(null);
		//bookloanlist.add(null);
		
		
		//bookloanlist.clear();
		//bookreturnlist.clear();
		
	/*	for(int i=0;i<loanBooks.size()-1;i++) {
			bookloanlist.add(loanBooks.get(i));
			System.out.println(loanBooks.get(i).toString());
		}
		
		Titel.setCellValueFactory(new PropertyValueFactory<>("copyid"));
		Tagged.setCellValueFactory(new PropertyValueFactory<>("idbook"));
		IssueDate.setCellValueFactory(new PropertyValueFactory<>("IssueDate"));
		ReturnDate.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
		TableviewLoan.setItems(bookloanlist);
		
		for(int i=0;i<returnBooks.size()-1;i++) {
			bookreturnlist.add(returnBooks.get(i));
		}
		
		TitelReturn.setCellValueFactory(new PropertyValueFactory<>("copyid"));
		IssueDateReturn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
		ReturnDateReturn.setCellValueFactory(new PropertyValueFactory<>("actualReturnDate"));
		
		TableviewReturn.setItems(bookreturnlist);*/
			
		
		
	}
    
    

}
