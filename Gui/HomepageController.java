package Gui;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import Entity.*;
import Controllers.*;
import Client.Main;
import Gui.MainController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomepageController {

	@FXML
	private AnchorPane AnchorHome; 

	@FXML
	private ImageView Image;

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
	private Button Clearbnt;

	@FXML
	private Button Searchbnt;

	@FXML
	private ImageView Imagebnt;

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
	private TableColumn<Book, String> tableofcontents;
	@FXML
	private Button OrderaBookbnt;

	@FXML
	private Button ViewBook;

	SearchBookController searchsookcontroller;
	MainController mainController;
	MemberController membercontroller;
	static Stage HomeStage;
	ObservableList<Book> booklist=FXCollections.observableArrayList();
	private int caseBnt=0;
	public static Book bookToView;
	public Book booknull;
	public Book viewBook=booknull;
	 public static String Min_Return_Date;

	@FXML
	void ViewBookbntHandler(ActionEvent event){


		Platform.runLater(new Runnable() {
			@Override

			public void run() {

				Object object =  Tableview.getSelectionModel().selectedItemProperty().get();
				System.out.println( object.toString());
				viewBook=(Book)object;

				bookToView=viewBook;
				 try {
						Main.client.mainControl.clientMember.getBookMain(bookToView);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				System.out.println(viewBook.toString() + bookToView.toString());

				//    Main.client.booksearchResultsController.setStageToviewBook(bookToView);

			/*	Stage primaryStage = new Stage();
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
				}*/




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
	void OrderaBookbntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try
				{
					if(membercontroller.curntllyLogIn.getStatus().equals("Frozen"))
					{
						displayAlert(AlertType.ERROR, "Member frozen", "Unable to make an order", "Your card's status is frozen.\nPlease contact our stuff managment");

					}
					else
					{
						Object item = Title.getTableView().getItems();
						System.out.println("before OrderaBookbntHandler ");


						Object object =  Tableview.getSelectionModel().selectedItemProperty().get();
						int index = Tableview.getSelectionModel().selectedIndexProperty().get();
						Book orderBook=(Book)object;

						System.out.println("Make the order");
						if(orderBook!=null) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Order a Book");
							alert.setContentText("You want to order the book: "+ orderBook.getBookname()+"\n, click Yes to place the order?");
							ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
							ButtonType noButton = new ButtonType("No", ButtonData.NO);

							alert.getButtonTypes().setAll(okButton, noButton);
							alert.showAndWait().ifPresent(type -> {
								if (type == okButton) {
									System.out.println("Make the order");
									try {
										Main.clientMember.makeOrder(orderBook);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
						}
					}
				}
				catch (Exception e)
				{
					displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
				}
			}
		});

	}




	@FXML
	void ActivityLogbntHandler(ActionEvent event) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try
				{

					System.out.println("ActivityLogbntbnHandler in Home page");

					Main.clientMember.getAllReturnd(Main.clientMember.curntllyLogIn);
					/*
					Main.client.HomePageController.HomeStage.close();

					Stage primaryStage = new Stage();
					Main.client.activityLogController.activityLogControllerStage=primaryStage;
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ActivityLog.fxml"));
					System.out.println(getClass().getResource("ActivityLog.fxml"));
					AnchorPane pane =(AnchorPane) loader.load();
					Scene scene = new Scene( pane );

					// setting the stage
					primaryStage.setScene( scene );
					primaryStage.setTitle( "Activity Log" );
					primaryStage.show();*/


				}
				catch (Exception e)
				{
					displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
				}
			}
		});



	}

	public void startActivityLog() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try
				{

					System.out.println("ActivityLogbntbnHandler in Home page");


					Main.client.HomePageController.HomeStage.close();

					Stage primaryStage = new Stage();
					Main.client.activityLognewController.activityLognewControllerStage=primaryStage;

					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ActivityLognew.fxml"));
					System.out.println(getClass().getResource("ActivityLognew.fxml"));
					AnchorPane pane =(AnchorPane) loader.load();
					Scene scene = new Scene( pane );
					

					// setting the stage
					primaryStage.setScene( scene );
					primaryStage.setTitle( "Activity Log" );
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
	void ClearHandler(ActionEvent event) {
		ByGenreTextField.clear();
		ByTextTExtField.clear();
		ByNameTextField.clear();
		ByAuthorTextField.clear();

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
				//Main.client.mainControl.clientMember.curntllyLogIn.setLogged(false);
				Main.client.HomePageController.HomeStage.close();
				Main.client.mainController.mainStage.show();
				Main.client.mainController.TextFiledUsername.clear();
				Main.client.mainController.TextFiledPassword.clear();

			}
		});



	}
	@FXML
	public void MyBooksbnHandler(ActionEvent event) throws IOException {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try
				{

					System.out.println("MyBooksbnHandler in Home page");
					Main.client.mainControl.clientMember.getLoans(membercontroller.curntllyLogIn);

					Main.client.HomePageController.HomeStage.close();

					Stage primaryStage = new Stage();
					Main.client.mybookController.MyBooksStage=primaryStage;
					FXMLLoader loader = new FXMLLoader(getClass().getResource("MyBook.fxml"));
					System.out.println(getClass().getResource("MyBook.fxml"));
					AnchorPane pane =(AnchorPane) loader.load();
					Scene scene = new Scene( pane );

					// setting the stage
					primaryStage.setScene( scene );
					primaryStage.setTitle( "My Books" );
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
	void ProfileHandler(ActionEvent event) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try
				{
					Main.client.HomePageController.HomeStage.close();

					Stage primaryStage = new Stage();
					Main.client.profileController.ProfileStage=primaryStage;
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
					System.out.println(getClass().getResource("Profile.fxml"));
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
	void SearchBookbntHandler(ActionEvent event) throws IOException {

		initialize();

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
						tableofcontents.setCellValueFactory(new PropertyValueFactory<>("tableofcontents"));
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
		Main.client.mainControl.clientMember.createaBookSearchRequest(where, search);


	}



	public void initialize() {

		Main.client.HomePageController=this;
		membercontroller.curntllyLogIn.setCurrentPage("HomePage");
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		NameLabel.setText("Hello "+membercontroller.curntllyLogIn.getFirstname()+" "+membercontroller.curntllyLogIn.getLastname()+"!");
		DateL.setText(timeStamp);
		TimeL.setText(timeStampclock);
		booklist.add(null);
		Tableview.setItems(booklist);
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


}
