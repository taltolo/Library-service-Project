
package Gui;


import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Client.Command;
import Client.Main;
import Entity.Msg;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReportsController {
	 @FXML
	    private AnchorPane AnchorHome;

	    @FXML
	    private ImageView Image;

	    @FXML
	    private Label ReportsL;

	    @FXML
	    private Button showReportBnt;

	    @FXML
	    private ColumnConstraints GridPane;

	    @FXML
	    private RadioButton oldactivityReportRadiobtn;

	    @FXML
	    private ToggleGroup Search;

	    @FXML
	    private RadioButton borrowReportRadiobtn;

	    @FXML
	    private RadioButton returnsReportRadiobtn;

	    @FXML
	    private ComboBox<Date> ActivitypickDatecombobox;

	    @FXML
	    private ComboBox<String> ReturnpickDatecombobox;

	    @FXML
	    private RadioButton activityReportRadiobtn;

	    @FXML
	    private ToggleGroup Search1;

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
	    private Label LabelEx;

	  
	
	public static Stage reportsControllerstage;
	private int caseBnt=0;
	String ReportByBook;
	static public ArrayList<Date> activityList =new ArrayList<Date>(); 
	static public ArrayList<String> returnList =new ArrayList<String>(); 
	
	
	

    @FXML
    void InventorybntHandler(ActionEvent event) {
    	
 Main.client.reportsController.reportsControllerstage.close();
 Main.client.librarymanagerHomePageController.InventorybntHandler(event);
    }


    
    @FXML
    void LogoutHandler(ActionEvent event) throws IOException  {
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
    				 Main.client.reportsController.reportsControllerstage.close();
    				Main.client.reportsController.reportsControllerstage.close();
    				Main.client.mainController.mainStage.show();
    				Main.client.mainController.TextFiledUsername.clear();
    				Main.client.mainController.TextFiledPassword.clear();

    			}
    		});

    }
    @FXML
    void AddnewmemberbntHandler(ActionEvent event) {
  
    	 Main.client.librarymanagerHomePageController.AddnewmemberbntHandler(event);

    }

	  @FXML
	    void ActivityReportHandler(ActionEvent event) {
		  LabelEx.setText("*Get a periodic report that display comprehensive information");
		  caseBnt=0;

	    }

	    @FXML
	    void ProfileHandler(ActionEvent event) {
	    	
	    	 Main.client.reportsController.reportsControllerstage.close();
	    	 Main.client.librarymanagerHomePageController.ProfileHandler(event);
	    }

	    @FXML
	    void ReportsbntHandler(ActionEvent event) {
	    	initialize();
	    }

	    @FXML
	    void ReturnBookbntHandler(ActionEvent event) {
	    	
	    	
	    	 Main.client.librarymanagerHomePageController.ReturnBookbntHandler(event);

	    }

	    @FXML
	    void SearchBookbntHandler(ActionEvent event) {
	    	
	    	 Main.client.reportsController.reportsControllerstage.close();
	    	 Main.client.librarymanagerHomePageController.librarymanagerControllerStage.show();
	    }

	    @FXML
	    void SearchEmployeesbntHandler(ActionEvent event)  {
	    	
	    	 Main.client.reportsController.reportsControllerstage.close();
	    	 Main.client.librarymanagerHomePageController.SearchEmployeesbntHandler(event);

	    }

	    @FXML
	    void SearchmemberbntHandler(ActionEvent event) {
	    	
	    	 Main.client.reportsController.reportsControllerstage.close();
	    	 Main.client.librarymanagerHomePageController.SearchmemberbntHandler(event);
	    	
	    	

	    }


	 

	@FXML
	void OldactivityReportHandler(ActionEvent event) {
		LabelEx.setText("*Re-examination periodic reports");
		caseBnt=1;
		
	}
	@FXML
	void BorrowReportHandler(ActionEvent event) {
		LabelEx.setText("*Get a report of duration of wanted and regular loan books");
		caseBnt=2;
	}

	@FXML
	void ReturnReportHandler(ActionEvent event) {
		LabelEx.setText("*Get a report of books that were returned late");

		caseBnt=3;
	}



	@FXML
	void ShowReportbntHandler(ActionEvent event) throws IOException {   	    	    
		Msg msg=null;

		switch(caseBnt) {
		case 0:
		{
			msg=new Msg("",Command.getReportType1);
			msg.dataToServer.add((Object)("select count(MemberID) from member where Status = 'Active';")); 
			msg.dataToServer.add((Object)("select count(MemberID) from member where Status = 'Frozen';")); 
			msg.dataToServer.add((Object)("select count(MemberID) from member where Status = 'Block'")); 
			msg.dataToServer.add((Object)("select count(MemberID) from member;")); 
			msg.dataToServer.add((Object)("select count(idloan) from loans;")); 
			msg.dataToServer.add((Object)("select count(idloan) from loans where rimenderSent != 0;")); 
			Main.client.sendToServer(msg);
			break;
		}


		case 1:
		{

			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String text = df.format(ActivitypickDatecombobox.getValue());

			msg=new Msg("SELECT Active,Frozen,Block,totalNumOfMember,activLoans,late FROM obl.reporttype1 where reportCreationDate ='"+ActivitypickDatecombobox.getValue().toString()+ "';",Command.getReportType1ByDate);

			Main.client.sendToServer(msg);
			break;

		}
		case 2:
			msg=new Msg("",Command.getReportType2);
			msg.dataToServer.add((Object)("SELECT DATEDIFF(actualReturnDate,issueDate) FROM returns inner join  books on returns.idbook=books.idbook where books.Tagged = 'Wanted';"));
			msg.dataToServer.add((Object)("SELECT DATEDIFF(actualReturnDate,issueDate) FROM returns inner join  books on returns.idbook=books.idbook where books.Tagged = 'Regular';"));
			Main.client.sendToServer(msg);
			break;
		case 3:
			if(ReturnpickDatecombobox.getValue().equals("General"))
			{
				msg=new Msg("SELECT DATEDIFF(actualReturnDate,returnDate) FROM returns where DATEDIFF(actualReturnDate,returnDate) > 0;",Command.getReportType3);
				Main.client.sendToServer(msg);
			}
			else				
			{
				msg=new Msg("SELECT DATEDIFF(actualReturnDate,returnDate) FROM returns inner join books on books.idbook = returns.idbook where DATEDIFF(actualReturnDate,returnDate) > 0 and books.bookname = '"+ReturnpickDatecombobox.getValue()+"';",Command.getReportType3ByBook);//הכנסת BOOKID
				Main.client.sendToServer(msg);
			}
			break; 
		}

	}


	public void initialize() {

		Main.client.reportsController=this;
		System.out.println("ARRAYLIST = "+activityList.toString());
		for (int i = 0; i < activityList.size(); i++) {
			ActivitypickDatecombobox.getItems().add(activityList.get(i));	
		}

	
			System.out.println("ARRAYLIST return = "+returnList.toString());
		for (int i = 0; i < returnList.size(); i++) {
			ReturnpickDatecombobox.getItems().add(returnList.get(i));
		}
		ReturnpickDatecombobox.getItems().add("General");
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		String timeStampclock = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
		//NameLabel.setText("Hello "+librariancontroller.curntlibrarian.getFirstname()+" "+librariancontroller.curntlibrarian.getLastname()+"!");
		DateL.setText(timeStamp);
		TimeL.setText(timeStampclock);
		NameLabel.setText("Hello "+Main.client.librarymanagercontroller.curntlibrarymanager.getFirstname()+" "+Main.client.librarymanagercontroller.curntlibrarymanager.getLastname()+"!");
	

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
