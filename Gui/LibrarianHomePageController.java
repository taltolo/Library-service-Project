package Gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Client.*;
import Controllers.*;
import Entity.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

public class LibrarianHomePageController  {

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
    private Button Logoutbnt;

    @FXML
    private TableView<Book> Tableview;

    @FXML
    private TableColumn<Book, String> Title;

    @FXML
    private TableColumn<Book, String> Genre;

    @FXML
    private TableColumn<Book, String> Author;

    @FXML
    private TableColumn<Book, String> Description;


    @FXML
    private GridPane GrindPane;

    @FXML
    private RadioButton ByNameBNT;

    @FXML
    private ToggleGroup Search;

    @FXML
    private RadioButton ByAuthorBNT;

    @FXML
    private RadioButton ByGenreBNT;

    @FXML
    private RadioButton ByTextBNT;

    @FXML
    private TextField ByNameTextField;

    @FXML
    private TextField ByAuthorTextField;

    @FXML
    private TextField ByTextTExtField;

    @FXML
    private TextField ByGenreTextField;

    @FXML
    private Label SearchBookL;

    @FXML
    private Button Clearbnt;

    @FXML
    private Button Searchbnt;
    @FXML
    private Button viewBookbnt;
    @FXML
    private Button Messagesbnt;

    @FXML
    private ImageView ImgeMessages;


	public BooksearchResultsController booksearchResultsController;
    LibrarianController librariancontroller;
	ObservableList<Book> booklist=FXCollections.observableArrayList();
	private int caseBnt=0;
    public static Stage librarianHomePageStage;
    public static Book bookToView;
    public Book booknull;
    public Book viewBook=booknull;

	
    
    @FXML
    void MessagesbntHandler(ActionEvent event) {
    	
      	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	try
    	    	{
    	    		
    	    		Main.client.librarianhomepagecontroller.librarianHomePageStage.close();

    	        	Stage primaryStage = new Stage();
    	        	Main.client.messageController.MessageControllerStage=primaryStage;
    	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("Message.fxml"));
    	            System.out.println(getClass().getResource("Message.fxml"));
    	            AnchorPane pane =(AnchorPane) loader.load();
    	            Scene scene = new Scene( pane );
    	            Main.librariancontroller.createaMessageRequest();
    	            // setting the stage
    	            primaryStage.setScene( scene );
    	            primaryStage.setTitle( "Message" );
    	            primaryStage.show();
    	       
    	    	}
    	    	catch (Exception e)
    	    	{
    	    		displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
    	    	}
    	    }
    	});
    	
    	
    	
    	
    	

    }
    
    
    @FXML
    void viewBookbntHandler(ActionEvent event) {
    	
    	
		Platform.runLater(new Runnable() {
			@Override
		
			public void run() {
		
		Object object =  Tableview.getSelectionModel().selectedItemProperty().get();
		System.out.println( object.toString());
		 viewBook=(Book)object;
		
		bookToView=viewBook;
		System.out.println(viewBook.toString() + bookToView.toString());
     
    //    Main.client.booksearchResultsController.setStageToviewBook(bookToView);
        
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
        	
        	
        //	Main.client.booksearchResultsController.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	
	
        
			}
		});
    	
    	

    }
    
    
    
    @FXML
    void AddnewmemberbntHandler(ActionEvent event) {
    	
      	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	try
    	    	{
    	    		
    	    		System.out.println("start from Main");

    	        	Stage primaryStage = new Stage();
    	        	Main.client.addNewMemberController.AddNewMemberStage=primaryStage;
    	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddNewMember.fxml"));
    	            System.out.println(getClass().getResource("AddNewMember.fxml"));
    	            AnchorPane pane =(AnchorPane) loader.load();
    	            Scene scene = new Scene( pane );
    	            
    	            // setting the stage
    	            primaryStage.setScene( scene );
    	            primaryStage.setTitle( "Add New Member" );
    	            primaryStage.show();
    	       
    	    	}
    	    	catch (Exception e)
    	    	{
    	    		displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
    	    	}
    	    }
    	});
    	
    	
    	

    }

  

	@FXML
	void ByAuthorBNTHandler(ActionEvent event) {
		ByGenreTextField.setDisable(true);
		ByAuthorTextField.setDisable(false);
		ByTextTExtField.setDisable(true);
		ByNameTextField.setDisable(true);
		caseBnt=2;

	}

	@FXML
	void ByGenreBNTHandler(ActionEvent event) {
		ByGenreTextField.setDisable(false);
		ByAuthorTextField.setDisable(true);
		ByTextTExtField.setDisable(true);
		ByNameTextField.setDisable(true);
		caseBnt=3;

	}

	@FXML
	void ByNameBNTHandler(ActionEvent event) {
		ByGenreTextField.setDisable(true);
		ByAuthorTextField.setDisable(true);
		ByTextTExtField.setDisable(true);
		ByNameTextField.setDisable(false);
		caseBnt=1;

	}

	@FXML
	void ByTextBNTHandler(ActionEvent event) {
		ByGenreTextField.setDisable(true);
		ByAuthorTextField.setDisable(true);
		ByTextTExtField.setDisable(false);
		ByNameTextField.setDisable(true);
		caseBnt=4;

	}

    @FXML
    void ClearHandler(ActionEvent event) {
    	ByGenreTextField.clear();
		ByAuthorTextField.clear();
		ByTextTExtField.clear();
		ByNameTextField.clear();
    }

    @FXML
    void InventorybntHandler(ActionEvent event) {
    	
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	try
    	    	{
    	    		Main.client.librarianhomepagecontroller.librarianHomePageStage.close();
    	    		System.out.println("start from Main");

    	        	Stage primaryStage = new Stage();
    	        	Main.client.inventoryController.inventoryStage=primaryStage;
    	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("Inventory.fxml"));
    	            System.out.println(getClass().getResource("Inventory.fxml"));
    	            AnchorPane pane =(AnchorPane) loader.load();
    	            Scene scene = new Scene( pane );
    	            
    	            // setting the stage
    	            primaryStage.setScene( scene );
    	            primaryStage.setTitle( "Inventory" );
    	            primaryStage.show();
    	       
    	    	}
    	    	catch (Exception e)
    	    	{
    	    		displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
    	    	}
    	    }
    	});
    	
    	
    	

    }

    @FXML
    void LogoutHandler(ActionEvent event) throws IOException {
    	System.out.println("BEFORE LOGOUT HANDLER");
    	//Main.client.librarianController.LogoutHandler(librariancontroller.curntlibrarian.getWorkerNumber());;
    	//librariancontroller.LogoutHandler(librariancontroller.curntlibrarian.getWorkerNumber());
    	Main.librariancontroller.LogoutHandler(librariancontroller.curntlibrarian.getWorkerNumber());
    	
    	Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Logout Window");
		alert.setContentText("Are you Sure?");
		ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonData.NO);

		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
			if (type == okButton) {
				 Main.client.librarianhomepagecontroller.librarianHomePageStage.close();
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
    	    	try
    	    	{
    	    		
    	    		System.out.println("start from Main");
    	    		Main.client.librarianhomepagecontroller.librarianHomePageStage.close();

    	        	Stage primaryStage = new Stage();
    	        	Main.client.profileEmployeesController.profileEmployeesControllerStage=primaryStage;
    	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileEmployees.fxml"));
    	            System.out.println(getClass().getResource("ProfileEmployees.fxml"));
    	            AnchorPane pane =(AnchorPane) loader.load();
    	            Scene scene = new Scene( pane );
    	            
    	            // setting the stage
    	            primaryStage.setScene( scene );
    	            primaryStage.setTitle( "Profile" );
    	            primaryStage.show();
    	       
    	    	}
    	    	catch (Exception e)
    	    	{
    	    		displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
    	    	}
    	    }
    	});

    }

    @FXML
    void ReturnBookbntHandler(ActionEvent event) {
    	
     	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	try
    	    	{
    	    		
    	    		System.out.println("start from Main");

    	        	Stage primaryStage = new Stage();
    	        	Main.client.returnBookController.returnBookStage=primaryStage;
    	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturnBook.fxml"));
    	            System.out.println(getClass().getResource("ReturnBook.fxml"));
    	            AnchorPane pane =(AnchorPane) loader.load();
    	            Scene scene = new Scene( pane );
    	            
    	            // setting the stage
    	            primaryStage.setScene( scene );
    	            primaryStage.setTitle( "Return Book" );
    	            primaryStage.show();
    	       
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
    	Main.client.librarianhomepagecontroller.initialize();

    }

	@FXML
	void SearchHandler(ActionEvent event) throws IOException {
		String search=null,where=null;
		System.out.println("Search Book bnt Handler from Home page");

		switch(caseBnt) {

		case 1:
			search="'%"+ByNameTextField.getText()+"%'";
			where="bookname";
			break;
		case 2:
			search="'%"+ByAuthorTextField.getText()+"%'";
			where="Author";
			break;
		case 3:
			search="'%"+ByGenreTextField.getText()+"%'";
			where="Genre";
			break;
		case 4:
			search="'%"+ByTextTExtField.getText()+"%'";
			where="Description";
			break;
		}
		System.out.println("Search Book bnt Handler");
		Main.librariancontroller.createaBookSearchRequest(where, search);


	}
	
	public void handleSearchresult(ArrayList<Book> books)
	{
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try
				{

					booklist.clear();

					for(int i=0 ;i<books.size();i++) {

						System.out.println(books.get(i).getBookname()+" handle Search result ");
						booklist.add(books.get(i));


					}  

					if(booklist.size()==0) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("An unsuccessful search");
						alert.setHeaderText(" Book is missing ");
						alert.setContentText("We're sorry we don't have this book " );
						ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
						alert.getButtonTypes().setAll(okButton);
						alert.showAndWait();
					}
					else {
						books.clear();
						Title.setCellValueFactory(new PropertyValueFactory<>("bookname"));
						Genre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
						Author.setCellValueFactory(new PropertyValueFactory<>("Author"));
						Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
						Tableview.setItems(booklist);

					}



				}

				catch (Exception e) {
					displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
				}
			}
		});

	}

    @FXML
  public void SearchmemberbntHandler(ActionEvent event) {
    	
     	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	try
    	    	{
    	    		Main.client.librarianhomepagecontroller.librarianHomePageStage.close();
    	    		System.out.println("start from Main");

    	        	Stage primaryStage = new Stage();
    	        	Main.client.searchmemberController.searchmemberStage=primaryStage;
    	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("Searchmember.fxml"));
    	            System.out.println(getClass().getResource("Searchmember.fxml"));
    	            AnchorPane pane =(AnchorPane) loader.load();
    	            Scene scene = new Scene( pane );
    	            
    	            // setting the stage
    	            primaryStage.setScene( scene );
    	            primaryStage.setTitle( "Search Member" );
    	            primaryStage.show();
    	       
    	    	}
    	    	catch (Exception e)
    	    	{
    	    		displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
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
	
	public void initialize() {

		Main.client.librarianhomepagecontroller=this;
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		NameLabel.setText("Hello "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname()+"!");
		DateL.setText(timeStamp);
		
		TimeL.setText(timeStampclock);
		booklist.add(null);
		Tableview.setItems(booklist);
	}

}
