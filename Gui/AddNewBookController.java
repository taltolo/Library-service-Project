package Gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import Client.*;
import Controllers.*;
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
import javafx.scene.control.ComboBox;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AddNewBookController {

	   @FXML
	    private AnchorPane AnchSerchBook;

	    @FXML
	    private Label BookIDLabel;

	    @FXML
	    private Label BookNameLabel;

	    @FXML
	    private Label AuthorLabel;

	    @FXML
	    private Label GenreLabel;

	    @FXML
	    private Label TaggedLabel;

	    @FXML
	    private Label DescriptionLabel;

	    @FXML
	    private Label EditionNumberLabel;

	    @FXML
	    private Label BookLoactionLabel;

	    @FXML
	    private Label QuantityLabel;

	    @FXML
	    private Label CurrentlyonLoanLabel;

	    @FXML
	    private Label ExecutionDateLabel;

	    @FXML
	    private Label PurchaseDateLabel;

	    @FXML
	    private Label TableofContentsLabel;

	    @FXML
	    private DatePicker PurchaseDateTEXT;

	    @FXML
	    private DatePicker ExecutionDateTEXT;

	    @FXML
	    private TextField TableofContentsTextField;

	    @FXML
	    private TextField BookIDTextField;

	    @FXML
	    private TextField BookNameTextField;

	    @FXML
	    private TextField GenreTextField;

	    @FXML
	    private TextField AuthorTextField;


	    @FXML
	    private ComboBox<String> TaggedComboBox;

	    @FXML
	    private TextField DescriptionTextField;

	    @FXML
	    private TextField EditionNumberTextField;

	    @FXML
	    private TextField BookLoactionTextField;

	    @FXML
	    private TextField QuantityTextField;

	    @FXML
	    private TextField CurrentlyonLoanTextField;

	    @FXML
	    private Label addNewBookLabel;

	    @FXML
	    private Button SaveBTN;

	    @FXML
	    private Button Cancelbnt;

	    @FXML
	    private Button pdfBTN;
	    
	    @FXML
	    void UploadFileHandler(ActionEvent event) {
	    	FileChooser fc=new FileChooser();
	    	fc.getExtensionFilters().add(new ExtensionFilter("PDF File", File_To_Upload));
	    	File f = fc.showOpenDialog(null);
	    	if(f!=null)
	    		TableofContentsTextField.setText(f.getAbsolutePath());

	    }
    
    LibrarianController librariancontroller;
	ObservableList<Book> booklist=FXCollections.observableArrayList();
	List <String> File_To_Upload;
	protected static Stage addNewBook;
	//public static Stage addNewBook;
	
    @FXML
    void CancelbntHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			try
    			{
    				Main.client.addNewBookController.addNewBook.close();
    		

    			}
    			catch (Exception e)
    			{
    				if(Main.permission.equals(Command.permission1)) {
    				Main.client.inventoryController.displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());}
    				else {
    					Main.client.inventoryManagerController.displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
    					
    				}
    			}
    		}
    	});
    }

    @FXML
    void SaveBntHandler(ActionEvent event) {
	try {
    	String BookID=BookIDTextField.getText();
    	String BookName=BookNameTextField.getText();
    	String Genre=GenreTextField.getText();
    	String Author=AuthorTextField.getText();
    	String Tagged=TaggedComboBox.getValue();
    	String Description=DescriptionTextField.getText();
    	String EditionNumebr=EditionNumberTextField.getText();
    	String BookLocation=BookLoactionTextField.getText();
    	String Quantity=QuantityTextField.getText();
    	String CurrentlyLoan=CurrentlyonLoanTextField.getText();
    	String TableOfContent=BookName;
    	String errorMsg="The following fields are wrong:";
    	boolean input=true;
    	//System.out.println(BookID+BookName+Genre+Author+Tagged+Description+EditionNumebr+BookLocation+Quantity+CurrentlyLoan+TableOfContent);
    	
    	if(!BookID.matches("^[A][0-9]{4}$"))
		{
			errorMsg=errorMsg+"\nBook ID (Example: A0000)";
			input=false;
		}
			
			
		if(BookName.isEmpty())
		{
			errorMsg=errorMsg+"\nBook Name";
			input=false;
		}
		if(Genre.isEmpty())
		{
			errorMsg=errorMsg+"\nGenre";
			input=false;
		}
		if(Author.isEmpty())
		{
			errorMsg=errorMsg+"\nAuthor";
			input=false;
		}
		if(Tagged==null)
		{
			errorMsg=errorMsg+"\nTagged";
			input=false;
		}
		if(Description.isEmpty())
		{
			errorMsg=errorMsg+"\nDescription";
			input=false;
		}
		if(!EditionNumebr.matches("^[0-9]{1,2}$"))
		{
			errorMsg=errorMsg+"\nEdition Number (Between 0-99)";
			input=false;
		}
		if(!BookLocation.matches("^[A-Z][0-9]$"))
		{
			errorMsg=errorMsg+"\nBook Location (Example: X9)";
			input=false;
		}
		if(!Quantity.matches("^[0-9]{1,2}$"))
		{
			errorMsg=errorMsg+"\nQuantity (between 0-99)";
			input=false;
		}
		if(!CurrentlyLoan.matches("^[0-9]{1,2}$"))
		{
			errorMsg=errorMsg+"\nCurrently on Loan (between 0-99)";
			input=false;
		}
		if(TableOfContent.isEmpty())
		{
			errorMsg=errorMsg+"\nTable of Content";
			input=false;
		}
		if(PurchaseDateTEXT.getValue()==null)
		{
			errorMsg=errorMsg+"\nPurchase Date";
			input=false;
		}
		if(ExecutionDateTEXT.getValue()==null)
		{
			errorMsg=errorMsg+"\nExecution Date";
			input=false;
		}
		TableOfContent=BookName.replaceAll(" ", "");
		System.out.println("TABLE = "+TableOfContent);
		File file =new File(TableofContentsTextField.getText());
		byte[] temp = null;
		temp=Files.readAllBytes(file.toPath());
		if(input==true)
		{
			if(Main.permission.equals(Command.permission1)) {
			Main.librariancontroller.AddNewBook(BookIDTextField.getText(),BookNameTextField.getText(),
					GenreTextField.getText(),	AuthorTextField.getText(),Tagged,
					DescriptionTextField.getText(),EditionNumberTextField.getText(),BookLoactionTextField.getText(),
					QuantityTextField.getText(),CurrentlyonLoanTextField.getText(),TableOfContent,
					PurchaseDateTEXT.getValue(),ExecutionDateTEXT.getValue(),temp);}
			else {
				
				Main.libraryManagerController.AddNewBook(BookIDTextField.getText(),BookNameTextField.getText(),
						GenreTextField.getText(),	AuthorTextField.getText(),Tagged,
						DescriptionTextField.getText(),EditionNumberTextField.getText(),BookLoactionTextField.getText(),
						QuantityTextField.getText(),CurrentlyonLoanTextField.getText(),TableOfContent,
						PurchaseDateTEXT.getValue(),ExecutionDateTEXT.getValue(),temp);
			}
		}
		else
		{
			if(Main.permission.equals(Command.permission1)) {
			Main.client.inventoryController.displayAlert(AlertType.ERROR, "Error", "Add new book", errorMsg);}
			else {
				Main.client.inventoryManagerController.displayAlert(AlertType.ERROR, "Error", "Add new book", errorMsg);
				
			}
			
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println("PROBLEM HERE");
			
			e.printStackTrace();
		}

    }
    
    public void initialize() {

		Main.client.addNewBookController=this;
		CurrentlyonLoanTextField.setText("0");
		TaggedComboBox.getItems().addAll("regular","wanted");
		File_To_Upload=new ArrayList<>();
		File_To_Upload.add("*.PDF");
		File_To_Upload.add("*.pdf");
	
    }

}
