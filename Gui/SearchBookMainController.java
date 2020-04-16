package Gui;
import Entity.*;

import java.io.IOException;
import java.util.ArrayList;

import Client.Command;
import Client.Main;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.Stage;

public class SearchBookMainController {

    @FXML
    private AnchorPane AnchSerchBook;
    @FXML
    private ColumnConstraints GridPane;

    @FXML
    private ImageView Image;

    @FXML
    private Label SearchBookLabel;

    @FXML
    private RadioButton ByNameLabel;

    @FXML
    private ToggleGroup Search;

    @FXML
    private RadioButton ByAuthorLabel;

    @FXML
    private RadioButton ByGenreLabel;

    @FXML
    private RadioButton ByTextLabel;

    @FXML
    private TextField ByNameTextField;

    @FXML
    private TextField ByAuthorTextField;

    @FXML
    private TextField ByTextTExtField;

    @FXML
    private TextField ByGenreTextField;

    @FXML
    private Button Clearbnt;

    @FXML
    private Button Searchbnt;
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
    private Button viewBookbnt;

    @FXML
    private Button Backbnt;

    public static String Min_Return_Date;
    public static Book viewBook;
    public static Stage SearchBookMainControllerStage;
    MainController mainController;
    private int caseBnt=0;
    ObservableList<Book> booklist=FXCollections.observableArrayList();

    @FXML
    void BackbntHandler(ActionEvent event) {
    	Main.client.searchBookMainController.SearchBookMainControllerStage.close();
    	 Main.client.mainControl.MainController.mainStage.show();

    }
    

    @FXML
    void viewBookbntHandler(ActionEvent event) {
    	System.out.println("ENTER VIEW BOOK HANDLER FROM SEARCH BOOK MAIN COONT");
		Object object =  Tableview.getSelectionModel().selectedItemProperty().get();
		System.out.println( object.toString());
         viewBook=(Book)object;
	 try {
		Main.client.mainControl.clientMember.getBookMain(viewBook);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    }
    
    
	public void setBookViewMain(Book getBook) throws IOException {

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
    
    
    
    
    @FXML
    void ClearHandler(ActionEvent event) {
    	ByGenreTextField.clear();
    	ByTextTExtField.clear();
    	ByNameTextField.clear();
    	ByAuthorTextField.clear();
    	

    }

    @FXML
    void ByNameLabelHandler(ActionEvent event) {
        	ByGenreTextField.setDisable(true);
        	ByAuthorTextField.setDisable(true);
        	ByTextTExtField.setDisable(true);
        	ByNameTextField.setDisable(false);
        	caseBnt=1;
    	  
    }
    
    @FXML
    void ByAuthorLabelHandler(ActionEvent event) {
    	ByGenreTextField.setDisable(true);
    	ByAuthorTextField.setDisable(false);
    	ByTextTExtField.setDisable(true);
    	ByNameTextField.setDisable(true);
    	caseBnt=2;
    }

    @FXML
    void ByGenreLabelHandler(ActionEvent event) {
    	ByGenreTextField.setDisable(false);
    	ByAuthorTextField.setDisable(true);
    	ByTextTExtField.setDisable(true);
    	ByNameTextField.setDisable(true);
    	caseBnt=3;
    }


    @FXML
    void ByTextLabelHandler(ActionEvent event) {
    	ByGenreTextField.setDisable(true);
    	ByAuthorTextField.setDisable(true);
    	ByTextTExtField.setDisable(false);
    	ByNameTextField.setDisable(true);
    	caseBnt=4;
    }
    
    
    @FXML
    void SearchHandler(ActionEvent event) throws IOException {
    	
    	
    	    		String search=null,where=null;
    	    		
    	    
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
    	//PreparedStatement stm = c.prepareStatement("UPDATE user_table SET name=? WHERE id=?");
    	//stm.setString(1, "the name");
            Msg msg=new Msg("SELECT * FROM books WHERE " +where+ " LIKE " + search+ ";", Command.searchBook);
    	//Msg msg=new Msg("SELECT * FROM books WHERE "+where+" = ?;", Command.searchBook);
            msg.dataToServer.add(where);
            msg.dataToServer.add(search);
            where=null;
            search=null;
            System.out.println("SERACH"+Thread.currentThread().getId());
       
            Main.client.sendToServer(msg);
    	
 
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
    		
    		System.out.println(books.get(i).getBookname()+"handle Search result ");
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
    	Main.client.searchBookMainController=this;
    	booklist.add(null);
    	Tableview.setItems(booklist);
    
   
    	
    }
}
   
    


