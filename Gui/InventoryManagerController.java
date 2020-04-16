package Gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Client.Command;
import Client.Main;
import Entity.Book;
import Entity.Msg;
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

public class InventoryManagerController {

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
    private Button SearchEmployeesbnt;

    @FXML
    private Button ReturnBookbnt;

    @FXML
    private Button Inventorybnt;

    @FXML
    private Button Reportsbnt;

    @FXML
    private ImageView ImageReport;

    @FXML
    private Button Logoutbnt;

	@FXML
	private TableView<Book> Tableview;

	@FXML
	private TableColumn<Book, String> BookID;

	@FXML
	private TableColumn<Book, String> Title;

	@FXML
	private TableColumn<Book, Integer> EditionNumber;

	@FXML
	private TableColumn<Book, Integer> Quantity;

	@FXML
	private TableColumn<Book, Integer> Currentlyonloan;

	@FXML
	private TableColumn<Book, String> Tagged;

    @FXML
    private Label InventoryLabel;

    @FXML
    private Button Searchbnt;

    @FXML
    private Button EditBookbnt;

    @FXML
    private Button AddnewBookBNT;

    @FXML
    private TextField BookIDTextField;

    @FXML
    private Label BookIDLabel;

    @FXML
    private Button RemoveBookbnt;
    
    
	ObservableList<Book> booklist=FXCollections.observableArrayList();
	public static Stage inventoryManagerStage;
	public static Book EditBook;


    	
    	
    	@FXML
    	void AddnewBookBNTHandler(ActionEvent event) {
    		Platform.runLater(new Runnable() {
    		
    			@Override
    			public void run() {
    				try
    				{
    					

    					Stage primaryStage = new Stage();
    					Main.client.addNewBookController.addNewBook=primaryStage;
    					FXMLLoader loader = new FXMLLoader(getClass().getResource("AddNewBook.fxml"));
    					System.out.println(getClass().getResource("AddNewBook.fxml"));
    					AnchorPane pane =(AnchorPane) loader.load();
    					Scene scene = new Scene( pane );

    					// setting the stage
    					primaryStage.setScene( scene );
    					primaryStage.setTitle( "Add New Book " );
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
    void AddnewmemberbntHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
		Main.client.librarymanagerHomePageController.AddnewmemberbntHandler(event);
			}
		});

    }

	@FXML
	void EditBookbntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try
				{
					Object item = Title.getTableView().getItems();
					System.out.println("before Book Edit ");


					Object object =  Tableview.getSelectionModel().selectedItemProperty().get();
					int index = Tableview.getSelectionModel().selectedIndexProperty().get();
					EditBook=(Book)object;
					System.out.println("Make the Edit");

					if(EditBook!=null) {
						System.out.println("Enter if");
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Edit a Book");
						alert.setContentText("You want to edit the book: "+ EditBook.getBookname()+", click Yes to continue.");
						ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
						ButtonType noButton = new ButtonType("No", ButtonData.NO);

						alert.getButtonTypes().setAll(okButton, noButton);
						alert.showAndWait().ifPresent(type -> {
							if (type == okButton) {
								System.out.println("Edit the Book");

								try
								{
									//Main.client.inventoryController.inventoryStage.close();
									Stage primaryStage = new Stage();
									//Main.client.inventoryController.inventoryStage=primaryStage;
								//	Main.client.editBookController.editBookStage=primaryStage;
									//Main.client.editBookController.editBookStage=primaryStage;
									Main.client.editBookController.editBookStage=primaryStage;
									FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBook.fxml"));
									System.out.println(getClass().getResource("EditBook.fxml"));
									AnchorPane pane =(AnchorPane) loader.load();
									Scene scene = new Scene( pane );

									// setting the stage
									primaryStage.setScene( scene );
									primaryStage.setTitle( "Edit Book" );
									primaryStage.show();
								}
								catch (Exception e)
								{
									//displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
									e.printStackTrace();
								}
							}
						});
					}
					else
						displayAlert(AlertType.ERROR, "Error", "Exception", "Please select a book to edit");

				}
				catch (Exception e)
				{
					//displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
					e.printStackTrace();
				}
			}




		});
	}

    @FXML
    void InventorybntHandler(ActionEvent event) {
    	initialize();

    }

    @FXML
    void LogoutHandler(ActionEvent event) throws IOException {
     	System.out.println("BEFORE LOGOUT HANDLER");
    	Main.libraryManagerController.LogoutHandler(Main.client.librarymanagercontroller.curntlibrarymanager.getWorkerNumber());
    	
       	Alert alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Logout Window");
    		alert.setContentText("Are you Sure?");
    		ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
    		ButtonType noButton = new ButtonType("No", ButtonData.NO);

    		alert.getButtonTypes().setAll(okButton, noButton);
    		alert.showAndWait().ifPresent(type -> {
    			if (type == okButton) {
    				 Main.client.inventoryManagerController.inventoryManagerStage.close();
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
				Main.client.inventoryManagerController.inventoryManagerStage.close();
		Main.client.librarymanagerHomePageController.ProfileHandler(event);
			}
		});


    }

	@FXML
	void RemoveBookbntHandler(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try
				{
					Object item = Title.getTableView().getItems();
					System.out.println("before Book Removed ");


					Object object =  Tableview.getSelectionModel().selectedItemProperty().get();
					int index = Tableview.getSelectionModel().selectedIndexProperty().get();
					Book removebook=(Book)object;

					System.out.println("Make the Remove");
					if(removebook!=null) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Remove a Book");
						alert.setContentText("You want to remove the book: "+ removebook.getBookname()+", click Yes to remove.");
						ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
						ButtonType noButton = new ButtonType("No", ButtonData.NO);

						alert.getButtonTypes().setAll(okButton, noButton);
						alert.showAndWait().ifPresent(type -> {
							if (type == okButton) {
								System.out.println("Remove the Book");
								if(removebook.getCurrentlyonloan()>0) 
									displayAlert(AlertType.INFORMATION, "Remove a Book", "Remove a Book", "There still have a "+removebook.getCurrentlyonloan()+ " books in loan.\nPlease return them before removing.");

								else
									try {
										//Main.client.librarianController.RemoveBook(removebook);
										Main.libraryManagerController.RemoveBook(removebook);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
						});
					}
					else
						displayAlert(AlertType.ERROR, "Error", "Exception", "Please select a book to remove");
				}
				catch (Exception e)
				{
					//displayAlert(AlertType.ERROR, "Error", "Exception", e.getMessage());
					e.printStackTrace();
				}
			}
		});

	}

    @FXML
    void ReportsbntHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Main.client.inventoryManagerController.inventoryManagerStage.close();
		Main.client.librarymanagerHomePageController.ReportsbntHandler(event);
			}
		});

    }

    @FXML
    void ReturnBookbntHandler(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
		Main.client.librarymanagerHomePageController.ReturnBookbntHandler(event);
			}
		});

    }

    @FXML
    void SearchBookbntHandler(ActionEvent event) {
    	Main.client.inventoryManagerController.inventoryManagerStage.close();
    	Main.client.librarymanagerHomePageController.librarymanagerControllerStage.show();

    }

    @FXML
    void SearchEmployeesbntHandler(ActionEvent event) {
    	Main.client.inventoryManagerController.inventoryManagerStage.close();
    	Main.client.librarymanagerHomePageController.SearchEmployeesbntHandler(event);

    }

	@FXML
	void SearchHandler(ActionEvent event) {
		String idsearch=null;
		idsearch="'"+BookIDTextField.getText()+"%'";
		Msg msg=new Msg("SELECT * FROM books WHERE idbook LIKE "+idsearch +";", Command.searchBookFromInventory);
		msg.dataToServer.add(idsearch);
		idsearch=null;

		try {
			Main.client.sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @FXML
    void SearchmemberbntHandler(ActionEvent event) {
    	Main.client.inventoryManagerController.inventoryManagerStage.close();
    	Main.client.librarymanagerHomePageController.SearchmemberbntHandler(event);

    }
    
	public void handleSearchresult(ArrayList<Book> books) {
		// TODO Auto-generated method stub
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try
				{
					booklist.clear();

					for(int i=0 ;i<books.size();i++) {

						System.out.println(books.get(i).getBookname()+"handle Search result From Inventory");
						if(books.get(i).getBookname()==null)
							displayAlert(AlertType.ERROR, "Error", "Exception", "Book not found");
						else
							booklist.add(books.get(i));

					}  
					books.clear();
					BookID.setCellValueFactory(new PropertyValueFactory<>("idbook"));
					Title.setCellValueFactory(new PropertyValueFactory<>("bookname"));
					EditionNumber.setCellValueFactory(new PropertyValueFactory<>("Editionnumber"));
					Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
					Currentlyonloan.setCellValueFactory(new PropertyValueFactory<>("currentlyonloan"));
					Tagged.setCellValueFactory(new PropertyValueFactory<>("Tagged"));
					Tableview.setItems(booklist);


				}

				catch (Exception e) {
					System.out.println("ENTER HERE");
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

		Main.client.inventoryManagerController=this;
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		NameLabel.setText("Hello "+Main.client.librarymanagercontroller.curntlibrarymanager.getFirstname()+" "+Main.client.librarymanagercontroller.curntlibrarymanager.getLastname()+"!");
		DateL.setText(timeStamp);
		TimeL.setText(timeStampclock);
		booklist.add(null);
		Tableview.setItems(booklist);
	}
    

}
