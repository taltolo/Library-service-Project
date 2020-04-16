package Gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import Client.Main;
import Controllers.MemberController;
import Entity.Book;
import Entity.Loan;
import Entity.Return;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyBookController {
	
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
    private Label MyBooksLabel;

    @FXML
    private TableView<Loan> TableviewLoan;

    @FXML
    private TableColumn<Loan, String> Titel;

    @FXML
    private TableColumn<Loan, String> Tagged;

    @FXML
    private TableColumn<Loan,Date> IssueDate;

    @FXML
    private TableColumn<Loan,Date> ReturnDate;

    @FXML
    private Button extendloanbnt;

    @FXML
    private Button viewBookbnt;
    
    ObservableList<Loan> bookloanlist=FXCollections.observableArrayList();
    public static ArrayList <Loan>loanBooks=new  ArrayList <Loan>();
    MainController mainController;
    MemberController membercontroller;
     static Stage MyBooksStage;
     public Loan viewBook;
     public static Book book;
     

     @FXML
     void extendloanbntHandler(ActionEvent event) {
    	 
 		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(!(membercontroller.curntllyLogIn.getStatus().equals("Frozen"))) {
    	
    	Object object = TableviewLoan.getSelectionModel().selectedItemProperty().get();
    	System.out.println(object.toString());
    	Loan extenLoan=(Loan)object;
    	System.out.println(extenLoan.getIdloan());
    	String idMember=membercontroller.curntllyLogIn.getMemberID();
    	System.out.println(idMember);
    	Calendar today = Calendar.getInstance();
    	Calendar returndate = Calendar.getInstance();
		returndate.setTime(extenLoan.getReturnDate());
		today.add(Calendar.DATE,7);
		System.out.println(today.getTime());
		boolean after =today.after(returndate);
		System.out.println(after);
		
		if(extenLoan.getTagged().equals("wanted")) {
    		displayAlert(AlertType.INFORMATION , "Extend loan" , "Unsucceed extending loan " , extenLoan.getBookname()+" is a WANTED book ,\nTherefore, his loan can not be extended.");
    	
    	}
		else {
		
	  if(after) {
		  returndate.add(Calendar.DATE,7);
		  SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		 
		  String newreturn = sdf1.format(returndate.getTime());
		  System.out.println(newreturn);
		 //java.sql.Date date = new java.sql.Date(returndate.getTimeInMillis());
		  Date date = new java.sql.Date(returndate.getTimeInMillis());
		  extenLoan.setReturnDate(date);

		  Main.client.mainControl.clientMember.extendLoanAuto(extenLoan,sdf1.format(date));
   		
      	}
	  
	  else {
		  
		  displayAlert(AlertType.INFORMATION , "Extend loan" , "Unsucceed extending loan " , "Sorry, but the loan of a book can not be extended.\n when you got more than a week left for its return.");
	  }
		}
    
    	
    		//Main.librariancontroller.extendloan(extenLoan,idMember);
				}
				else {
					 displayAlert(AlertType.INFORMATION , "Extend loan" , "Unsucceed extending loan " , "Sorry, Because your subscription is frozen, you can not extend the loan.");
					
				}
			}
		});
    	 

     }

     @FXML
     void viewBookbntHandler(ActionEvent event) {
    	 
    		Object object =  TableviewLoan.getSelectionModel().selectedItemProperty().get();
    		System.out.println( object.toString());
             viewBook=(Loan)object;
    	 try {
			Main.client.mainControl.clientMember.getBook(viewBook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

     }
     

    

    @FXML
    void ActivityLogbntHandler(ActionEvent event) {
    	
    	
    	    		Main.client.mybookController.MyBooksStage.close();
    	    		Main.client.HomePageController.ActivityLogbntHandler(event);

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
			Main.client.mybookController.MyBooksStage.close();
				Main.client.HomePageController.HomeStage.close();
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});

    }

    @FXML
    void MyBooksbnHandler(ActionEvent event) {
    	
    	initialize();
    }

    @FXML
    void ProfileHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	try
    	    	{
    	    		Main.client.mybookController.MyBooksStage.close();
    	    		Main.client.HomePageController.ProfileHandler(event);
    	    		
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
    	Main.client.mybookController.MyBooksStage.close();
		Main.client.HomePageController.HomeStage.show();

    }

    public void initialize() {
    	setStageViewTable();
        Main.client.mybookController=this;
    	String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    	String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    	NameLabel.setText("Hello "+membercontroller.curntllyLogIn.getFirstname()+" "+membercontroller.curntllyLogIn.getLastname()+"!");
    	DateL.setText(timeStamp);
    	TimeL.setText(timeStampclock);
    	
    }
    
    public void setStageViewTable() {
 	   
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

	public void setBookView(Book getBook) throws IOException {

		Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
		
		
		 
			//Main.client.booksearchResultsController.start();
			   	Stage primaryStage = new Stage();
		    	Main.client.booksearchResultsController.booksearchResultsController=primaryStage;
		    
		    	
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("BookView.fxml"));
		        System.out.println(getClass().getResource("BookView.fxml"));
		        AnchorPane pane;
				try {
					pane = (AnchorPane) loader.load();
				
		        Scene scene = new Scene( pane );
		        
		        // setting the stage
		        primaryStage.setScene( scene );
		        primaryStage.setTitle( "Book View" );
		        primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
    }
});
	}
	
    
}
