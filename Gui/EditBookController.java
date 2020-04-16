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
public class EditBookController {

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
	private DatePicker PurchaseDateTEXT;

	@FXML
	private DatePicker ExecutionDateTEXT;

	@FXML
	private Label TableofContentsLabel;

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
	private TextField TaggedTextField;

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
	private Label LendingaBookLabel;

	@FXML
	private Button SaveBTN;

	@FXML
	private Button Cancelbnt;
	//Book Update_Book=new Book();
	//Update_Book
	@FXML
	private ComboBox<String> TaggedComboBox;

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
	protected static Stage editBookStage;
	//public static Stage editBookStage;
	@FXML
	void CancelbntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try
				{
					Main.client.editBookController.editBookStage.close();


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
	void SaveBntHandler(ActionEvent event) throws IOException {
		String BookID=BookIDTextField.getText();
		String BookName=BookNameTextField.getText();
		String BookNameToUpload=BookNameTextField.getText();
		BookNameToUpload.replaceAll(" ", "");
		String Genre=GenreTextField.getText();
		String Author=AuthorTextField.getText();
		String Tagged=TaggedComboBox.getValue();
		String Description=DescriptionTextField.getText();
		String EditionNumebr=EditionNumberTextField.getText();
		String BookLocation=BookLoactionTextField.getText();
		String Quantity=QuantityTextField.getText();
		//TableofContentsTextField.setText(BookNameToUpload);
		String CurrentlyLoan=CurrentlyonLoanTextField.getText();
		String TableOfContent=TableofContentsTextField.getText();
		String PurchaseDate;
		String Executiondate;
		int oldQuantity;

		if(Main.permission.equals(Command.permission1)) {
			PurchaseDate=Main.client.inventoryController.EditBook.getPurchasedate();
			Executiondate = Main.client.inventoryController.EditBook.getExecutiondate();
			oldQuantity=Main.client.inventoryController.EditBook.getQuantity();
		}
		else
		{
			PurchaseDate=Main.client.inventoryManagerController.EditBook.getPurchasedate();
			Executiondate = Main.client.inventoryManagerController.EditBook.getExecutiondate();
			oldQuantity=Main.client.inventoryManagerController.EditBook.getQuantity();
		}

		String errorMsg="The following fields are wrong:";
		boolean input=true;
		int isNewQuantityBigger = 0;


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

		if(input==true)
		{


			if(Main.permission.equals(Command.permission1)) {
				if(Integer.parseInt(QuantityTextField.getText())>Main.client.inventoryController.EditBook.getQuantity())
					isNewQuantityBigger=1;
				if(Integer.parseInt(QuantityTextField.getText())<Main.client.inventoryController.EditBook.getQuantity())
					isNewQuantityBigger=-1;
				Main.librariancontroller.EditBook(Main.client.inventoryController.EditBook.getIdbook(),BookIDTextField.getText(),BookNameTextField.getText(),
						GenreTextField.getText(),	AuthorTextField.getText(),TaggedComboBox.getValue(),
						DescriptionTextField.getText(),EditionNumberTextField.getText(),BookLoactionTextField.getText(),
						QuantityTextField.getText(),CurrentlyonLoanTextField.getText(),BookNameToUpload,
						PurchaseDate,Executiondate,isNewQuantityBigger,oldQuantity);}
			else {
				if(Integer.parseInt(QuantityTextField.getText())>Main.client.inventoryManagerController.EditBook.getQuantity())
					isNewQuantityBigger=1;
				if(Integer.parseInt(QuantityTextField.getText())<Main.client.inventoryManagerController.EditBook.getQuantity())
					isNewQuantityBigger=-1;
				Main.libraryManagerController.EditBook(Main.client.inventoryManagerController.EditBook.getIdbook(),BookIDTextField.getText(),BookNameTextField.getText(),
						GenreTextField.getText(),	AuthorTextField.getText(),TaggedComboBox.getValue(),
						DescriptionTextField.getText(),EditionNumberTextField.getText(),BookLoactionTextField.getText(),
						QuantityTextField.getText(),CurrentlyonLoanTextField.getText(),BookNameToUpload,
						PurchaseDate,Executiondate,isNewQuantityBigger,oldQuantity);
			}


		}
		else
			Main.client.inventoryController.displayAlert(AlertType.ERROR, "Error", "Add new book", errorMsg);




	}

	public void initialize() { 
		/*
		Main.client.editBookController=this;
		if(Main.permission.equals(Command.permission1)) {
		BookIDTextField.setText(Main.client.inventoryController.EditBook.getIdbook());
		BookNameTextField.setText(Main.client.inventoryController.EditBook.getBookname());
		GenreTextField.setText(Main.client.inventoryController.EditBook.getGenre());
		AuthorTextField.setText(Main.client.inventoryController.EditBook.getAuthor());
		TaggedTextField.setText(Main.client.inventoryController.EditBook.getTagged());
		DescriptionTextField.setText(Main.client.inventoryController.EditBook.getDescription());
		EditionNumberTextField.setText(String.valueOf(Main.client.inventoryController.EditBook.getEditionnumber()));
		BookLoactionTextField.setText(Main.client.inventoryController.EditBook.getBooklocation());
		QuantityTextField.setText(String.valueOf(Main.client.inventoryController.EditBook.getQuantity()));
		CurrentlyonLoanTextField.setText(String.valueOf(Main.client.inventoryController.EditBook.getCurrentlyonloan()));
		TableofContentsTextField.setText(Main.client.inventoryController.EditBook.getTableofcontents());
		PurchaseDateTEXT.setPromptText(Main.client.inventoryController.EditBook.getPurchasedate());
		ExecutionDateTEXT.setPromptText(Main.client.inventoryController.EditBook.getExecutiondate());}
		 */
		Main.client.editBookController=this;
		File_To_Upload=new ArrayList<>();
		File_To_Upload.add("*.PDF");
		File_To_Upload.add("*.pdf");
		TaggedComboBox.getItems().addAll("regular","wanted");
		if(Main.permission.equals(Command.permission1)) {
			BookIDTextField.setText(Main.client.inventoryController.EditBook.getIdbook());
			BookNameTextField.setText(Main.client.inventoryController.EditBook.getBookname());
			GenreTextField.setText(Main.client.inventoryController.EditBook.getGenre());
			AuthorTextField.setText(Main.client.inventoryController.EditBook.getAuthor());
			TaggedComboBox.setValue(Main.client.inventoryController.EditBook.getTagged());
			DescriptionTextField.setText(Main.client.inventoryController.EditBook.getDescription());
			EditionNumberTextField.setText(String.valueOf(Main.client.inventoryController.EditBook.getEditionnumber()));
			BookLoactionTextField.setText(Main.client.inventoryController.EditBook.getBooklocation());
			QuantityTextField.setText(String.valueOf(Main.client.inventoryController.EditBook.getQuantity()));
			CurrentlyonLoanTextField.setText(String.valueOf(Main.client.inventoryController.EditBook.getCurrentlyonloan()));
			TableofContentsTextField.setText(Main.client.inventoryController.EditBook.getTableofcontents());
			PurchaseDateTEXT.setPromptText(Main.client.inventoryController.EditBook.getPurchasedate());
			ExecutionDateTEXT.setPromptText(Main.client.inventoryController.EditBook.getExecutiondate());}
		else {
			BookIDTextField.setText(Main.client.inventoryManagerController.EditBook.getIdbook());
			BookNameTextField.setText(Main.client.inventoryManagerController.EditBook.getBookname());
			GenreTextField.setText(Main.client.inventoryManagerController.EditBook.getGenre());
			AuthorTextField.setText(Main.client.inventoryManagerController.EditBook.getAuthor());
			TaggedComboBox.setValue(Main.client.inventoryManagerController.EditBook.getTagged());
			DescriptionTextField.setText(Main.client.inventoryManagerController.EditBook.getDescription());
			EditionNumberTextField.setText(String.valueOf(Main.client.inventoryManagerController.EditBook.getEditionnumber()));
			BookLoactionTextField.setText(Main.client.inventoryManagerController.EditBook.getBooklocation());
			QuantityTextField.setText(String.valueOf(Main.client.inventoryManagerController.EditBook.getQuantity()));
			CurrentlyonLoanTextField.setText(String.valueOf(Main.client.inventoryManagerController.EditBook.getCurrentlyonloan()));
			TableofContentsTextField.setText(Main.client.inventoryManagerController.EditBook.getTableofcontents());
			PurchaseDateTEXT.setPromptText(Main.client.inventoryManagerController.EditBook.getPurchasedate());
			ExecutionDateTEXT.setPromptText(Main.client.inventoryManagerController.EditBook.getExecutiondate());

		}

	}
}

