package Gui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Client.Command;
import Client.Main;
import Entity.Book;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BooksearchResultsController {

	@FXML
	private AnchorPane AnchSerchBook;

	@FXML
	private Label BooknameLabel;

	@FXML
	private Button TableofContentsbnt;

	@FXML
	private Button Closebnt;

	@FXML
	private Label AuthorLabel;

	@FXML
	private Label GenreLabel;

	@FXML
	private Label DescriptionLabel;

	@FXML
	private Label BookLoactionLabel;

	@FXML
	private TextArea DescriptionTextAreaField;
	
    @FXML
    private Label minDateReturnLabel;
    
	public static Stage booksearchResultsController;

	@FXML
	void ClosebntHandler(ActionEvent event) {
		Main.client.booksearchResultsController.booksearchResultsController.close();

	}

	@FXML
	void TableofContentsbntHandler(ActionEvent event) {
		try{
			if(Main.permission.equals(Command.permission0)) {
				System.out.println(System.getProperty("user.dir") );
				File temp = new File(System.getProperty("user.dir") +"\\"+ Main.client.librarymanagerHomePageController.viewBook.getBookname()+".pdf");
				FileOutputStream fos = new FileOutputStream(temp);
				fos.write(Main.client.librarymanagerHomePageController.viewBook.pdf);
				Desktop.getDesktop().open(temp);
				fos.close();
				
			}
			
			if(Main.permission.equals(Command.permission1)) {
				System.out.println(System.getProperty("user.dir") );
				File temp = new File(System.getProperty("user.dir") +"\\"+ Main.client.librarianhomepagecontroller.viewBook.getBookname()+".pdf");
				FileOutputStream fos = new FileOutputStream(temp);
				fos.write(Main.client.librarianhomepagecontroller.viewBook.pdf);
				Desktop.getDesktop().open(temp);
				fos.close();
			}

			if(Main.permission.equals(Command.permission2)) {
				System.out.println(System.getProperty("user.dir") );
	            if(Main.clientMember.curntllyLogIn.getCurrentPage().equals("HomePage")) {
	            	
	            	File temp = new File(System.getProperty("user.dir") +"\\"+ Main.client.HomePageController.bookToView.getBookname()+".pdf");
					FileOutputStream fos = new FileOutputStream(temp);
					System.out.println(Main.client.HomePageController.bookToView.getBookname());
					fos.write(Main.client.HomePageController.bookToView.pdf);
					Desktop.getDesktop().open(temp);
					fos.close();
	            }
	            else {
				File temp = new File(System.getProperty("user.dir") +"\\"+ Main.client.mybookController.viewBook.getBookname()+".pdf");
				FileOutputStream fos = new FileOutputStream(temp);
				System.out.println(Main.client.mybookController.viewBook.getBookname());
				fos.write(Main.client.mybookController.book.pdf);
				Desktop.getDesktop().open(temp);
				fos.close();}
			}
			
			if(Main.permission.equals(Command.permission3)) {
				
				File temp = new File(System.getProperty("user.dir") +"\\"+ Main.client.searchBookMainController.viewBook.getBookname()+".pdf");
				FileOutputStream fos = new FileOutputStream(temp);
				System.out.println(Main.client.searchBookMainController.viewBook.getBookname());
				fos.write(Main.client.searchBookMainController.viewBook.pdf);
				Desktop.getDesktop().open(temp);
				fos.close();}
			

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}








	public void initialize() {

		Main.client.booksearchResultsController=this;
		//setStageToviewBook(Main.client.mybookController.book);
		switch(Main.permission) {
		
		case permission0:{
			setStageToviewBook(Main.client.librarymanagerHomePageController.viewBook);
			break;
			
		}
		case permission1:{
			
			BooknameLabel.setText(Main.client.librarianhomepagecontroller.viewBook.getBookname()+" book : ");
			AuthorLabel.setText("Author : "+Main.client.librarianhomepagecontroller.viewBook.getAuthor());
			GenreLabel.setText("Genre : "+Main.client.librarianhomepagecontroller.viewBook.getGenre());
			DescriptionTextAreaField.setText(Main.client.librarianhomepagecontroller.viewBook.getDescription());
			BookLoactionLabel.setText("Book Loaction "+Main.client.librarianhomepagecontroller.viewBook.getBooklocation());
			
			break;
			
		}
		case permission2:{
			 if(Main.clientMember.curntllyLogIn.getCurrentPage().equals("HomePage")) {
			setStageToviewBook(Main.client.HomePageController.bookToView);
			}
			else {
			setStageToviewBook(Main.client.mybookController.book);}
			break;
		}
		
		case permission3:{
			setStageToviewBook(Main.client.searchBookMainController.viewBook);
			break;
			
		}
		
		
		}


	}

	public void setStageToviewBook(Book bookview) {

		BooknameLabel.setText(bookview.getBookname()+": ");
		AuthorLabel.setText("Author : "+bookview.getAuthor());
		GenreLabel.setText("Genre : "+bookview.getGenre());
		DescriptionTextAreaField.setText(bookview.getDescription());
		//BookLoactionLabel.setText("Book Loaction "+bookview.getBooklocation());
		if(Main.permission.equals(Command.permission2)||Main.permission.equals(Command.permission3)) {
		if((int)bookview.getQuantity()-(int)bookview.getCurrentlyonloan()!=0)
			BookLoactionLabel.setText("Book Loaction "+bookview.getBooklocation());
		else
		{
			
			System.out.println((int)bookview.getQuantity()-(int)bookview.getCurrentlyonloan());
			if(Main.clientMember.curntllyLogIn.getCurrentPage().equals("HomePage")) {
				minDateReturnLabel.setText("The book will be available on "+Main.client.HomePageController.Min_Return_Date);
			}
			else {
			minDateReturnLabel.setText("The book will be available on "+Main.client.searchBookMainController.Min_Return_Date);
		}
			}

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

	public void start() throws IOException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
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
