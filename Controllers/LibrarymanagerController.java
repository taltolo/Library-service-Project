package Controllers;

import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import Client.*;
import Entity.*;
import Gui.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LibrarymanagerController extends LibrarianController implements Serializable  {
	
	
	public  MainController mainController;
	public static Employees curntlibrarymanager; 

	
		/**
		 * handle login Checks for if the member exist and logs in to the system
		 * @param userName - geting from the MainController
		 * @param pass - geting from the MainController
		 * @throws IOException
		 */
		public void handlelogin(String userName,String pass ) throws IOException {

			Msg msg=new Msg("SELECT * FROM employe WHERE WorkerNumber ="+ userName+ " and Password =" + pass +";", Command.getEmployeByWorkerNumberWorkerNumber);
			msg.dataToServer.add(userName);
		
			Main.client.sendToServer(msg);
		}
		
		/**
		 * Create a LibrarymanagerController 
		 * 
		 * @param dataFromServer
		 * @return new Librarian 
		 * @throws Exception 
		 */
		public void LogoutHandler(String workerNumber) throws IOException {
			 System.out.println("ENTER LOGOUT HANDLER");
		  		Msg msg=new Msg("UPDATE employe SET isLogged = false WHERE WorkerNumber = "+"'"+workerNumber+"';", Command.logout);
		      	msg.dataToServer.add(workerNumber);
		      	
		      	Main.client.sendToServer(msg);
		}
		/**
		 * Create a LibrarymanagerController 
		 * 
		 * @param dataFromServer
		 * @return new Librarian 
		 * @throws Exception 
		 */
		public void createLibrarymanagerController(Msg msg) throws Exception {
			if(msg.result) {
				Main.libraryManagerController.curntlibrarymanager=new Employees(msg.dataFromServer.get(0).toString(),
						msg.dataFromServer.get(1).toString(),msg.dataFromServer.get(2).toString(),
						msg.dataFromServer.get(3).toString(),msg.dataFromServer.get(4).toString(),
						msg.dataFromServer.get(5).toString(),Boolean.valueOf(msg.dataFromServer.get(6).toString()),
						Boolean.valueOf(msg.dataFromServer.get(7).toString()),msg.dataFromServer.get(8).toString(),msg.dataFromServer.get(9).toString());
				
			}

			else {
				Main.client.mainController.displayAlert(AlertType.ERROR, "Error", "Error Message ", "Sorry, You do not exist in the system, or one of your details was entered incorrectly");

			}
		}
	
		public void createaBookSearchRequest(String where, String search) {
			
			super.createaBookSearchRequest(where, search);
			
      
		
}
		
			/**
			 * addNewMember - Prepare a new member query and send it to the DB
			 * @param Firstname
			 * @param Lastname
			 * @param MemberID
			 * @param subscriptionMember
			 * @param Email
			 * @param Phonenumber
			 * @param Graduationyear
			 */
			public void addNewMember(String Firstname , String Lastname,String MemberID,String subscriptionMember,String Email,String Phonenumber,int Graduationyear)
			{
				
				super.addNewMember(Firstname, Lastname, MemberID, subscriptionMember, Email, Phonenumber, Graduationyear);

			}
			/**
			 * checkAddNewMemberStatus - check if the adding has been successful, display a message accordingly
			 * @param newmsg
			 */
			public void checkAddNewMemberStatus(Msg newmsg) {
				
				super.checkAddNewMemberStatus(newmsg);

			}
/**
 * search in DB for employ
 * @param where
 * @param search
 */
			public void searchForEmployees(String where, String search) {

				Msg msg=new Msg("SELECT * FROM employe WHERE " +where+ " LIKE " + search+ ";", Command.getEmployees);
				msg.dataToServer.add(where);
				msg.dataToServer.add(search);
				where=null;
				search=null;

				try {
					Main.client.sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
/**
 * display employ details
 * @param newmsg
 */
			public void setTheEmployeesList(Msg newmsg) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (newmsg.result) {

							System.out.println(newmsg.dataFromServer.get(0).toString());
							for(int i=0;i<newmsg.dataFromServer.size();i++) {
								Main.client.searchemployeesController.employees.add((Employees)newmsg.dataFromServer.get(i));
								System.out.println(Main.client.searchemployeesController.employees.get(i).getFirstname());
								
							}
						
								Main.client.searchemployeesController.handleSearchResultEmployees();
							
						}

						else {
						

							Main.client.searchemployeesController.displayAlert(AlertType.ERROR, "Error", "Exception", "The Employees does not exist in the system, or one of the details was entered incorrectly ");
							
							
						}
					}
				});
				
			}
			
			/**
			 * remove book from DB
			 */
			public void RemoveBook(Book removeBook) throws IOException {
				//System.out.println("Error HERE");
				Msg msg=new Msg("DELETE FROM books WHERE idbook ="+"'"+removeBook.getIdbook()+"'"+";",Command.RemoveBook);
				msg.dataToServer.add(removeBook);
				Main.client.sendToServer(msg);


			}


			/*
			public void EditBook(String originalBookID,String newBookID, String newBookName, String newGenre, String newAuthor, String newTagged, String newDescription,
					String newEditionNumber, String newBookLoaction, String newQuantity, String newCurrentlyonLoan,
					String newTableofContents, String newPurchaseDate, String newExecutionDate) throws IOException {
				// TODO Auto-generated method stub
				Msg msg=new Msg("UPDATE books SET idbook="+"'"+newBookID+"'"+" , bookname="+"'"+newBookName+"'"+ " , Quantity="+"'"+newQuantity+"'"+ 
						 " , booklocation="+"'"+newBookLoaction+"'"+ " ,Editionnumber="+"'"+newEditionNumber+"'"+ " , Genre="+"'"+newGenre+ "'"+
						 ",Author="+"'"+newAuthor+"'"+ ",Description="+"'"+newDescription+"'"+ ",tableofcontents="+"'"+newTableofContents+"'"+ 
						 ",Tagged="+"'"+newTagged+"'"+ ",currentlyonloan="+"'"+newCurrentlyonLoan+"'"+ ",Purchasedate="+"'"+newPurchaseDate+"'"+ ",Executiondate="+"'"+newExecutionDate+"'"
						 + "WHERE idbook ="+"'"+originalBookID+"'"+";",Command.EditBook);
				msg.dataToServer.add(newBookName);
				Main.client.sendToServer(msg);
				//UPDATE member SET Phonenumber="+ newPhoneNumber + " , Email="+ newEmail + " WHERE MemberID =" +curntllyLogIn.getMemberID()+ ";",Command.updataMemberPhoneANDEmail)
				
				
			}
			*/
			/**
			 * 
			 * @param originalBookID
			 * @param newBookID
			 * @param newBookName
			 * @param newGenre
			 * @param newAuthor
			 * @param newTagged
			 * @param newDescription
			 * @param newEditionNumber
			 * @param newBookLoaction
			 * @param newQuantity
			 * @param newCurrentlyonLoan
			 * @param newTableofContents
			 * @param newPurchaseDate
			 * @param newExecutionDate
			 * @throws IOException
			 */
			public void EditBook(String originalBookID,String newBookID, String newBookName, String newGenre, String newAuthor, String newTagged, String newDescription,
					String newEditionNumber, String newBookLoaction, String newQuantity, String newCurrentlyonLoan,
					String newTableofContents, String purchaseDate,
					String executiondate,int isNewQuantityBigger,int oldQuantity)  {
				// TODO Auto-generated method stub

				try {
					int current=Integer.parseInt(newCurrentlyonLoan);
					int quant=Integer.parseInt(newQuantity);
					int edition=Integer.parseInt(newEditionNumber);
					System.out.println(edition +" "+ quant+" "+current);
					System.out.println(purchaseDate.toString()+ executiondate.toString());
					
					Msg msg=new Msg("UPDATE books SET idbook="+"'"+newBookID+"'"+" , bookname="+"'"+newBookName+"'"+ " , Quantity="+quant+ 
							 " , booklocation="+"'"+newBookLoaction+"'"+ " ,Editionnumber="+edition+ " , Genre="+"'"+newGenre+ "'"+
							 ",Author="+"'"+newAuthor+"'"+ ",Description="+"'"+newDescription+"'"+ ",tableofcontents="+"'"+newTableofContents+"'"+ 
							 ",Tagged="+"'"+newTagged+"'"+ ",currentlyonloan="+current+ ",Purchasedate="+"'"+purchaseDate.toString()+"'"+ ",Executiondate="+"'"+executiondate.toString()+"'"
							 + " WHERE idbook ="+"'"+originalBookID+"'"+";",Command.EditBook);
					System.out.println(msg.getQuery());
					msg.dataToServer.add(newBookName);
					msg.dataToServer.add(isNewQuantityBigger);
					msg.dataToServer.add(oldQuantity);
					msg.dataToServer.add(quant);
					msg.dataToServer.add(newBookID);
					Main.client.sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					mainController.displayAlert(AlertType.ERROR, "Error", "Exception", "INSERT");
				}
			}
			/**
			 * add new book in DB
			 */
			public void AddNewBook(String newBookID, String newBookName, String newGenre, String newAuthor, String newTagged, String newDescription,
					String newEditionNumber, String newBookLoaction, String newQuantity, String newCurrentlyonLoan,
					String newTableofContents, LocalDate newPurchaseDate,
					LocalDate newExecutionDate,byte[] file) throws IOException {
				
				// TODO Auto-generated method stub
				int current=Integer.parseInt(newCurrentlyonLoan);
				int quant=Integer.parseInt(newQuantity);
				int edition=Integer.parseInt(newEditionNumber);
				System.out.println(edition +" "+ quant+" "+current);
				System.out.println(newPurchaseDate.toString()+ newExecutionDate.toString());
				/*
				Msg msg=new Msg("UPDATE books SET idbook="+"'"+newBookID+"'"+" , bookname="+"'"+newBookName+"'"+ " , Quantity="+quant+ 
						 " , booklocation="+"'"+newBookLoaction+"'"+ " ,Editionnumber="+edition+ " , Genre="+"'"+newGenre+ "'"+
						 ",Author="+"'"+newAuthor+"'"+ ",Description="+"'"+newDescription+"'"+ ",tableofcontents="+"'"+newTableofContents+"'"+ 
						 ",Tagged="+"'"+newTagged+"'"+ ",currentlyonloan="+current+ ",Purchasedate="+"'"+newPurchaseDate.toString()+"'"+ ",Executiondate="+"'"+newExecutionDate.toString()+"'"
						 + "WHERE idbook ="+"'"+originalBookID+"'"+";",Command.EditBook);
					*/	 
				Msg msg=new Msg("INSERT INTO books (idbook,bookname, Quantity,booklocation,Editionnumber,Genre,Author,Description,tableofcontents,Tagged,currentlyonloan,Purchasedate,Executiondate)"
						+ "VALUES ("+"'"+newBookID+"',"+"'"+newBookName+"',"+quant+",'"+newBookLoaction+"',"+edition+","+"'"+newGenre+"',"+"'"+newAuthor+"',"+"'"+newDescription+"',"+"'"+newTableofContents+"',"+"'"+newTagged+"',"+current+",'"+newPurchaseDate.toString()+"',"+"'"+newExecutionDate.toString()+"'"+");",Command.AddNewBook);
				System.out.println(msg.getQuery());
				msg.dataToServer.add(newBookName);
				msg.dataToServer.add(newBookID);
				msg.dataToServer.add(newEditionNumber);
				msg.dataToServer.add(newQuantity);
				msg.dataToServer.add(file);
				Main.client.sendToServer(msg);
			}
			
			/**
			 * edit librarian details
			 * @param firstName
			 * @param lastName
			 * @param phone
			 * @param iD
			 * @param email
			 * @param workerNumber
			 * @param department
			 */
			public void UpDataLibrarianInfo(String firstName, String lastName, String phone, String iD, String email,
					String workerNumber, String department) {
				
				Msg msg=new Msg("UPDATE employe SET ID = '"+iD +"', LastName = '"+lastName +"', FirstName='"+firstName +"',WorkerNumber='"+workerNumber+"',Email='"+email+"',department='"+department+"',phoneNumber='"+phone+"' WHERE ID='"+ iD+"' ;", Command.UpDataLibrarianInfo);
				
				  Main.client.searchemployeesController.employeesView.setFirstname(firstName);
 			     Main.client.searchemployeesController.employeesView.setLastname(lastName);
 			     Main.client.searchemployeesController.employeesView.setPhoneNumber(phone);
 			     Main.client.searchemployeesController.employeesView.setID(iD);
 			     Main.client.searchemployeesController.employeesView.setEmail(email);
 			     Main.client.searchemployeesController.employeesView.setWorkerNumber(workerNumber);
 			     Main.client.searchemployeesController.employeesView.setDepartment(department);

				try {
					Main.client.sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
/**
 * check if edit librarian success.
 * @param newmsg
 */
			public void checkUpDataLibrarianInfo(Msg newmsg) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
				
				if(newmsg.result) {
					//Main.client.viewMemberController.displayAlert(Alert.AlertType.INFORMATION,"Edit Member ", "Successfulness ", "Member details have been successfully updated ");
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Edit Employee Successfulness");
					alert.setContentText("Employee details have been successfully updated");
					ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);

					alert.getButtonTypes().setAll(okButton);
					alert.showAndWait().ifPresent(type -> {
						if (type == okButton) {
								Main.client.viewEmployeeController.initialize();
						

						}
					});
					
				}
				else {
					
						Main.client.viewEmployeeController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Something went wrong,\n Please try again later");
						
				}
					}
				});
				
			}
			/**
			 * get activity report details from DB
			 */
			public void getActivityReport() {
				Msg msg=new Msg("SELECT reportCreationDate FROM reporttype1;", Command.getActivityReport);
				try {
					Main.client.sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			/**
			 * get late in return book details from DB 
			 */
			public void getRturnsReport() {
				Msg msg=new Msg("SELECT DISTINCT books.bookname FROM returns inner join books on books.idbook = returns.idbook where DATEDIFF(actualReturnDate,returnDate)>0;", Command.getRturnsReport);
				try {
					Main.client.sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

	
	

}
