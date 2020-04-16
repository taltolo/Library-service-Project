package Client;

import Entity.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import Controllers.*;
import Gui.*;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import ocsf.client.AbstractClient;
public class CommunicationController extends AbstractClient {
	
	public Main mainControl;
	public  MainController mainController;
	public  MemberController memberController;
	public LibrarianHomePageController librarianHomePageController;
	public LibrarianController librarianController;
	public SearchBookController searchbookcontroller;
	public ForgotpasswordController forgotpasswordController;
	public SearchBookMainController searchBookMainController;
	public ProfileController profileController;
	public MyBookController mybookController;
	public HomepageController HomePageController;
	public LibrarianHomePageController librarianhomepagecontroller;
	public SearchmemberController searchmemberController;
	public LendingaBookController lendingaBookController;
	public ReturnBookController returnBookController;
	public AddNewMemberController addNewMemberController;
	public ViewMemberController viewMemberController;
	public InventoryController inventoryController;
	public EditBookController editBookController;
	public AddNewBookController addNewBookController;
	public BooksearchResultsController booksearchResultsController;
	public MessageController messageController;
	public ActivityLogController activityLogController;
	public ProfileEmployeesController profileEmployeesController;
	public LibrarymanagerController librarymanagercontroller;
	public LibrarymanagerHomePageController librarymanagerHomePageController;
	public ReportsController reportsController;
	public ProfileManagerController profileManagercontroller;
	public InventoryManagerController inventoryManagerController;
	public SearchmemberManagerController searchmemberManagerController;
	public ViewMemberManagerController viewMemberManagerController;
	public SearchEmployeesController searchemployeesController;
	public ViewEmployeeController viewEmployeeController;
	public ActivityLogmemberController activityLogmemberController;
	public ActivityLognewController activityLognewController;
	
	
	public CommunicationController(String host, int port) {
		super(host, port);
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected  synchronized void handleMessageFromServer(Object msg)   {
		Msg newmsg = (Msg)msg;	
		System.out.println(newmsg.dataFromServer.toString()+"from comm");

		switch(newmsg.getFuncToRun()) {

		case getMemberByIdAndPass:
			try {
				if(newmsg.result==false)
					Main.client.HomePageController.displayAlert(AlertType.INFORMATION, "Error", "Could not login as member", "Invalid username or password combination\nPlease try again.");
				else
					if((int)newmsg.dataFromServer.get(12)==1)
						Main.client.HomePageController.displayAlert(AlertType.INFORMATION, "Error", "Could not login as member", "You allready logged in to the system.");

					else if(newmsg.result==true) {
					System.out.println("newmsg.result = "+newmsg.result);
					//mainControl.clientMember.curntllyLogIn.setLogged(true);
					mainControl.clientMember.curntllyLogIn=mainControl.clientMember.creatNewMember(newmsg.dataFromServer);
					Main.clientMember.checkStatusLogin(Main.clientMember.curntllyLogIn);

				}
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block we need to add Alert

				e.printStackTrace();
			}
			break;

		case getMemberByIdAndGraduation:
			try {
				if((newmsg.getDataFromServer().isEmpty())){
					System.out.println(newmsg.dataFromServer.toString()+"from comm");
					forgotpasswordController.displayAlert(AlertType.INFORMATION, "Error", "Member does not exist", "Member does not exist in the system");
				}
				else {
					forgotpasswordController.handleforgotpasswordresult(newmsg.getDataFromServer().get(5).toString());
				}
			}

			catch (Exception e) {
				// TODO Auto-generated catch block we need to add Alert

				e.printStackTrace();
			}
			break;	 
		case searchBook:{
			Book book=(Book) newmsg.dataFromServer.get(0);
			System.out.println(book.getBookname());
		
			if(!(newmsg.getDataFromServer().isEmpty())){
				for(int i = 0 ; i<newmsg.dataFromServer.size();i++) {
					Book.books.add((Book) newmsg.dataFromServer.get(i));
					System.out.println(Book.books.get(i).getBookname());
				}
				
				searchBookMainController.handleSearchresult(Book.books);

	 }
	
			break;
		       }
		
		case updataMemberPhoneANDEmail:
		   {System.out.println("up data Member Phone AND Email");
			   mainControl.clientMember.checkIfTheUpDataSucceeded(newmsg);
			
			   break;
			
		    }
		   
		   
		case searchBookFromController:
		   {
				Book book=(Book) newmsg.dataFromServer.get(0);
				System.out.println(book.getBookname());
			
				if(!(newmsg.getDataFromServer().isEmpty())){
					for(int i = 0 ; i<newmsg.dataFromServer.size();i++) {
						Book.books.add((Book) newmsg.dataFromServer.get(i));
						System.out.println(Book.books.get(i).getBookname());
					}
					Main.client.HomePageController.handleSearchresult(Book.books);
					break;
				}
		   }
		case searchBookFromControllerLibrarian:
		   {
				Book book=(Book) newmsg.dataFromServer.get(0);
				System.out.println(book.getBookname());
			
				if(!(newmsg.getDataFromServer().isEmpty())){
					for(int i = 0 ; i<newmsg.dataFromServer.size();i++) {
						Book.books.add((Book) newmsg.dataFromServer.get(i));
						System.out.println(Book.books.get(i).getBookname());
					}
					if(Main.permission.equals(Command.permission1)) {
					Main.client.librarianhomepagecontroller.handleSearchresult(Book.books);}
					else {
						Main.client.librarymanagerHomePageController.handleSearchresult(Book.books);
						
					}
				//	Main.client.HomePageController.handleSearchresult(Book.books);
					break;
				}
		   }
		   
		case MakeOrderBook:
		{
			System.out.println("Make Order Book from Comm");
			 mainControl.clientMember.resultOrderBook(newmsg);
			
			break;
	     	}
		case getEmployeByWorkerNumberWorkerNumber:
		{

			try
			{
				if(newmsg.result==false)
					Main.client.HomePageController.displayAlert(AlertType.INFORMATION, "Error", "Could not login as staff manegment", "Invalid username or password combination\nPlease try again.");
				else
					if((int)newmsg.dataFromServer.get(10)==1)
						Main.client.HomePageController.displayAlert(AlertType.INFORMATION, "Error", "Could not login as staff manegment", "You allready logged in to the system.");

					else if(newmsg.result==true) {
					System.out.println("newmsg.result = "+newmsg.result);
					//mainControl.clientMember.curntllyLogIn.setLogged(true);
					if(Main.permission.equals(Command.permission1)) {
						mainControl.librariancontroller.createLibrarian(newmsg);
						Main.client.mainController.start1( );}
					else {

						mainControl.libraryManagerController.createLibrarymanagerController(newmsg);
						Main.client.mainController.handleloginresultLibrarymanager();}

				}
			} 
			/*
			{
				if(newmsg.result==false)
				{
					Main.client.HomePageController.displayAlert(AlertType.INFORMATION, "Error", "Could not login as staff managment", "Invalid username or password combination\nPlease try again.");

				}


				else
				{
					System.out.println("msg.result = "+newmsg.result);
					if(Main.permission.equals(Command.permission1)) {
						mainControl.librariancontroller.createLibrarian(newmsg);
						Main.client.mainController.start( );}
					else {

						mainControl.libraryManagerController.createLibrarymanagerController(newmsg);
						Main.client.mainController.handleloginresultLibrarymanager();}
				}
			}
			*/
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case getMembers:
		{
			System.out.println("get Members from comm ");
			if(Main.permission.equals(Command.permission1)) {
			mainControl.librariancontroller.checkTheResults(newmsg);}
			else {
				
				mainControl.libraryManagerController.checkTheResults(newmsg);
			}
		//	 mainControl.client.librarianController.checkTheResults(newmsg);
			
			
			break;
			
		}
		case loanaBook1:
		{
			System.out.println("loan a Book from comm ");
			if(Main.permission.equals(Command.permission1)) {
			mainControl.librariancontroller.checkloanaBookStatus(newmsg);}
			else {
				mainControl.libraryManagerController.checkloanaBookStatus(newmsg);
				
			}
			break;
		}
		
		case ReturnBook:{
			
			mainControl.librariancontroller.checkReturnBookStatus(newmsg);

			break;
			
		}
		case getAllActivitiesMember:
		{					System.out.println(newmsg.dataFromServer.size());
		if(Main.permission.equals(Command.permission1)) {
			mainControl.librariancontroller.handlerAllActivitiesMember(newmsg);}
		else {
			mainControl.libraryManagerController.handlerAllActivitiesMember(newmsg);
			
		}
			break;
		}
		case extendloan:
		{
			if(Main.permission.equals(Command.permission1))
			mainControl.librariancontroller.checkextendloan(newmsg);
			else
				mainControl.libraryManagerController.checkextendloan(newmsg);
			break;
			
		}
	case addNewMember:{
			if(Main.permission.equals(Command.permission1)) {
			mainControl.librariancontroller.checkAddNewMemberStatus(newmsg);}
			else {
				mainControl.libraryManagerController.checkAddNewMemberStatus(newmsg);
			}

			break;
			
		}
	case searchBookFromInventory:
	{
		Book book=(Book) newmsg.dataFromServer.get(0);
		System.out.println(book.getBookname());

		if(!(newmsg.getDataFromServer().isEmpty())){

			for(int i = 0 ; i<newmsg.dataFromServer.size();i++) {
				Book.books.add((Book) newmsg.dataFromServer.get(i));
				System.out.println(Book.books.get(i).getBookname());
			}
            if(Main.permission.equals(Command.permission1)) {
			Main.client.inventoryController.handleSearchresult(book.books);}
            else {
            	
            	Main.client.inventoryManagerController.handleSearchresult(book.books);
            }

		}
		else {
			if(Main.permission.equals(Command.permission1)) {
			InventoryController.displayAlert(AlertType.INFORMATION, "search a Book", "Search a Book", "The book not found");
			}
			else {
				
				Main.client.inventoryManagerController.displayAlert(AlertType.INFORMATION, "search a Book", "Search a Book", "The book not found");
			}
		}

		break;

	}
	case RemoveBook:
	{
		Book book=(Book) newmsg.dataFromServer.get(0);
		int succeed =(int) newmsg.dataFromServer.get(1);
		if(succeed!=0)
			InventoryController.displayAlert(AlertType.INFORMATION, "Remove a Book", "Remove a Book", "The book "+book.getBookname()+ " has been removed.");
		else
			InventoryController.displayAlert(AlertType.ERROR, "Remove a Book", "Remove a Book", "There was a problem removing the book, Please try again later");

		break;

	}
	case EditBook:
	{
		String BookName=(String) newmsg.dataFromServer.get(0);
		int succeed =(int) newmsg.dataFromServer.get(1);
		System.out.println("RETURN FROM SERVER");
		if(newmsg.result) 
			InventoryController.displayAlert(AlertType.INFORMATION, "Edit a Book", "Edit a Book", "The book "+BookName+ " has been changed.");
		else 
			InventoryController.displayAlert(AlertType.ERROR, "Edit a Book", "Edit a Book", "There was a problem editing the book, Please try again later");
		break;

	}
	case AddNewBook:
	{
		System.out.println("ARRIVED add new book comm");
		
		
		InventoryController.displayAlert(AlertType.INFORMATION, "Add new Book", "Add new Book", (String)newmsg.dataFromServer.get(0));
	
		System.out.println("AFTER DISPLAY");
		System.out.println(newmsg.result);
		/*
		if(newmsg.result)
		{
			Main.client.addNewBookController.addNewBook.close();
			System.out.println("AFTER IF");
		}
		
		/*
		if(newmsg.result) 
		{
			System.out.println("RESULT=TRUE");
			InventoryController.displayAlert(AlertType.INFORMATION, "Add new Book", "Add new Book", "The book "+BookName+ " has been added.");
		}
		else 
			InventoryController.displayAlert(AlertType.ERROR, "Add a Book", "Add a Book", "There was a problem adding the book, Please try again later");
		break;

	}

	}
		 */
		break;
	}
	case setNewValueForMember:{
		if(Main.permission.equals(Command.permission1))
		mainControl.librariancontroller.checksetNewValueForMember(newmsg);
		else
			mainControl.libraryManagerController.checksetNewValueForMember(newmsg);
		
		break;
		
	}
	case getLoans:
	{
		
		Main.clientMember.insertLoanToMyBook(newmsg);
		break;
		
	}
	case getBook:{
		
		Main.clientMember.setViewBook(newmsg);
		break;
		
		
	}
	case extendLoanAuto:{
		
		Main.clientMember.checkTheExtendLoan(newmsg);
		break;
		
	}
	
	case getMessageToLibrarian:{
		
		System.out.println(((Message)newmsg.getDataFromServer().get(0)).getMessage()+"asdfgfaSD");
		if(!(newmsg.getDataFromServer().isEmpty())){
			for(int i = 0 ; i<newmsg.dataFromServer.size();i++) {
				messageController.MSG.add((Message)newmsg.dataFromServer.get(i));
			}

			messageController.handleSearchresult();

		}


		break;
	}
	
	case UpDataLibrarian:{
		mainControl.librariancontroller.checkTheUpDataLibrarian(newmsg);
		break; 
	}
	case ActivityLog:{
		
		System.out.println("handle_All_Returns_Result before");
		//Main.clientMember.handle_All_Returns_Result(newmsg);
		Main.client.mainControl.clientMember.handle_All_Returns_Result(newmsg);
		// mainControl.clientMember.handle_All_Returns_Result(newmsg);
		
     	 
		break; 
	}
	case getEmployees:{
		mainControl.libraryManagerController.setTheEmployeesList(newmsg);
		break; 
	}
	case UpDataLibrarianInfo:{
		System.out.println("UpDataLibrarianInfo form CON");
		mainControl.libraryManagerController.checkUpDataLibrarianInfo(newmsg);
		break; 
	}
	case getReportType2:{
		Platform.runLater(()->{
			(new Type2report(newmsg)).start(new Stage());
		});
		break; 
	}
	
	case getReportType3:{
		Platform.runLater(()->{
			(new Type3report(newmsg)).start(new Stage());
		});
		break; 
	}
	case getReportType3ByBook:{
		Platform.runLater(()->{
			(new Type3ByBookReport(newmsg)).start(new Stage());
		});
		break; 
		
	}	
	case getReportType1 :{
		Platform.runLater(()->{
			(new Type1report(newmsg)).start(new Stage());
		});
		break; 
		
	}
	case getReportType1ByDate:{
		Platform.runLater(()->{
			(new Type1report(newmsg)).start(new Stage());
		});
		break; 
		
	}
	case getActivityReport:{
	
		for (int i = 0; i < newmsg.dataFromServer.size(); i++) {
			ReportsController.activityList.add((java.sql.Date) newmsg.dataFromServer.get(i));	
		}
		System.out.println("ARRAYLIST FROM COMM = "+ReportsController.activityList.toString());
		break; 
		
	}
	case getRturnsReport:{
		
		for (int i = 0; i < newmsg.dataFromServer.size(); i++) {
			ReportsController.returnList.add((String) newmsg.dataFromServer.get(i));	
		}
		System.out.println("ARRAYLIST FROM COMM = "+ReportsController.returnList);
		break; 
		
	}
	case cancelOrder:{
		Main.clientMember.handlerCancelOrder(newmsg);
		break; 
	}
		
		
		}
	
		
		
		}

	 	

















	public void handleMessageFromClientUI(Object arr) {
		System.out.println(arr);
		try
		{
			sendToServer(arr);		 
		}
		catch(IOException e)
		{
			System.out.println("Could not send message to server.  Terminating client." + e);
		}
	}

}

