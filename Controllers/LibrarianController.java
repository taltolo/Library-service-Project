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
/***
 * 
 * Controller class to perform Librarian actions
 *
 */

public class LibrarianController implements Serializable {

	public static Employees curntlibrarian; 
	public  MainController mainController;
	public BooksearchResultsController booksearchResultsController;
	public LibrarianHomePageController librarianHomePageController;
	public LendingaBookController lendingaBookController;
	public ViewMemberController viewMemberControllerFROMLibrarianController;
	static final String AB = "01ABCDEFGHuvIJKLMSTcU5678VWstwXYZabdefghijklmn2349opqrNOPQRxyz";
	static SecureRandom rnd = new SecureRandom();
	
	/**
	 * handle login Checks for if the member exist and logs in to the system
	 * @param userName - geting from the MainController
	 * @param pass - geting from the MainController
	 * @throws IOException
	 */
	public void handlelogin(String userName,String pass  ) throws IOException {

		Msg msg=new Msg("SELECT * FROM employe WHERE WorkerNumber ="+ userName+ " and Password =" + pass +";", Command.getEmployeByWorkerNumberWorkerNumber);
		msg.dataToServer.add(userName);
		//msg.dataToServer.add(pass);
		System.out.println("from Librarian Controller handlelogin");
		Main.client.sendToServer(msg);
	}
	/**
	 * LogoutHandler - update his "isLogged" status to 0; 
	 * @param empolyeNumber  logout the employ.
	 * @throws IOException
	 */
	 public void LogoutHandler(String empolyeNumber) throws IOException {
		 System.out.println("ENTER LOGOUT HANDLER");
	  		Msg msg=new Msg("UPDATE employe SET isLogged = false WHERE WorkerNumber = "+"'"+empolyeNumber+"';", Command.logout);
	      	msg.dataToServer.add(empolyeNumber);
	      	
	      	Main.client.sendToServer(msg);
	  		
	  	}
	/**
	 * Create a Librarian 
	 * 
	 * @param dataFromServer
	 * @return new Librarian 
	 * @throws Exception 
	 */

		public void createLibrarian(Msg msg) throws Exception {
			if(msg.result) {
				Main.librariancontroller.curntlibrarian=new Employees(msg.dataFromServer.get(0).toString(),
						msg.dataFromServer.get(1).toString(),msg.dataFromServer.get(2).toString(),
						msg.dataFromServer.get(3).toString(),msg.dataFromServer.get(4).toString(),
						msg.dataFromServer.get(5).toString(),Boolean.valueOf(msg.dataFromServer.get(6).toString()),
						Boolean.valueOf(msg.dataFromServer.get(7).toString()),msg.dataFromServer.get(8).toString(),msg.dataFromServer.get(9).toString());
				System.out.println(Main.librariancontroller.curntlibrarian.toString());
				//	Main.client.librarianhomepagecontroller.start();
				//Main.client.librarianhomepagecontroller.start( );
			}

			else {
				Main.client.mainController.displayAlert(AlertType.ERROR, "Error", "Error Message ", "Sorry, You do not exist in the system, or one of your details was entered incorrectly");

			}
		}
	/**
	 * searchForMember - Prepare a search query for a member in DB
	 * @param where
	 * @param search
	 */

	public void searchForMember(String where,String search) {

		Msg msg=new Msg("SELECT * FROM member WHERE " +where+ " LIKE " + search+ ";", Command.getMembers);
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
	 * checkTheResults - member Friend Search Results Restore from DB
	 * @param newmsg
	 */

	public void checkTheResults(Msg newmsg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (newmsg.result) {

					System.out.println(newmsg.dataFromServer.get(0).toString());
					for(int i=0;i<newmsg.dataFromServer.size();i++) {
						Member.members.add((Member)newmsg.dataFromServer.get(i));
						System.out.println(Member.members.get(i).toString());
					}
					if(Main.permission.equals(Command.permission1)) {
					Main.client.searchmemberController.handleSearchResultMember(Member.members);}
					else {
						
						Main.client.searchmemberManagerController.handleSearchResultMember(Member.members);
					}
				}

				else {
					if(Main.permission.equals(Command.permission1)) {

					Main.client.searchmemberController.displayAlert(AlertType.ERROR, "Error", "Exception", "The member does not exist in the system, or one of the details was entered incorrectly ");}
					else {
						Main.client.searchmemberManagerController.displayAlert(AlertType.ERROR, "Error", "Exception", "The member does not exist in the system, or one of the details was entered incorrectly ");
						
					}
					
				}
			}
		});

	}
	/**
	 * loanaBookHandler - Prepare a loan query for a loan a book in DB
	 * @param copyBookId
	 * @param idMember
	 * @param issueDate
	 * @param returnDate
	 * @param lonerMember 
	 */


	public void loanaBookHandler(String copyBookId, String idMember, Date issueDate, Date returnDate, Member lonerMember) {		
		if(lonerMember.getStatus().equals("Active")) {
			System.out.println("loanaBookHandler from LibrarianController");
			String idbook=copyBookId.substring(0,5);
			String rday= returnDate.toString();
			String iday=issueDate.toString();
			int returnDay=Integer.parseInt(rday.substring(8, 10));
			int iDay=Integer.parseInt(iday.substring(8, 10));
			Loan newloan=new Loan(idMember,idbook,copyBookId,issueDate,returnDate);
			System.out.println(returnDay);
			Msg msg=new Msg( "select Tagged from books where idbook='"+idbook+"';",Command.loanaBook1);
			//Msg msg=new Msg("INSERT INTO loan (MemberID,idbook,copyid,IssueDate,ReturnDate) VALUES(" +"'"+lonerMember.getMemberID()+"'"+","+"'"+idbook+"',"+"'"+copyBookId+"'"+",'"+issueDate.toString() +"',"+"'"+returnDate.toString()+"');",Command.loanaBook1);
			try {
				msg.dataToServer.add(newloan);
				//msg.dataToServer.add(idbook);
				//	msg.dataToServer.add(copyBookId);
				msg.dataToServer.add(Math.abs(returnDay-iDay));
				System.out.println(msg.getQuery());
				Main.client.sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	/**
	 * checkloanaBookStatus - check if the borrowing has been successful, display a message accordingly
	 * @param newmsg
	 */
	public void checkloanaBookStatus(Msg newmsg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				System.out.println("checkloanaBookStatus from LibrarianController");
				int date=Integer.parseInt(newmsg.dataToServer.get(1).toString());
				System.out.println(date);
				if(newmsg.result) {
					String tagged=newmsg.dataFromServer.get(0).toString();

					if(tagged.equals("wanted")& date>3) {
						Main.client.lendingaBookController.displayAlert(Alert.AlertType.WARNING, "WARNING", "Unsuccessfulness loan", "This book is tagged as a wanted book, Therefore it can be loan only for 3 days.");
						Main.client.lendingaBookController.lendingaBookStage.close();}
					else {
						Main.client.lendingaBookController.displayAlert(Alert.AlertType.CONFIRMATION,"Loan a Book ", "Successfulness ", " The loan was successful!");
						Main.client.lendingaBookController.lendingaBookStage.close();}
				}
				else {


					Main.client.lendingaBookController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Something went wrong,\n Please try again later");
					Main.client.lendingaBookController.lendingaBookStage.close();

				}


			}
		});
	}
	/**
	 * handlerReturnBook - Prepare a loan query for a Return a Book in DB
	 * @param copyID
	 * @param memberID
	 */
	public void handlerReturnBook(String copyID, String memberID) {

		String idbook=copyID.substring(0,5);
		String timeStamp = new SimpleDateFormat("yyy-MM-dd").format(Calendar.getInstance().getTime());
		System.out.println(timeStamp);
		Msg msg=new Msg("SELECT * FROM loans where MemberID='"+memberID+"' and idbook='"+idbook+"' and copyid='"+copyID+"';", Command.ReturnBook);
		msg.dataToServer.add(copyID);
		msg.dataToServer.add(memberID);
		msg.dataToServer.add(idbook);
		msg.dataToServer.add(timeStamp);

		try {
			System.out.println("Before server");
			Main.client.sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	/**
	 * checkReturnBookStatus - check if the Return has been successful, display a message accordingly
	 * @param newmsg
	 */

	public void checkReturnBookStatus(Msg newmsg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				System.out.println("check Return Book Status");
				if(newmsg.result) {

					if(newmsg.dataFromServer.get(0).equals("Block")) {
						Main.client.returnBookController.displayAlert(Alert.AlertType.WARNING,"WARNING ", "Member blocked ", " After three delays in returning a book, the member was blocked.");
						Main.client.returnBookController.returnBookStage.close();

					}
					else {
						Main.client.returnBookController.displayAlert(Alert.AlertType.CONFIRMATION,"Return a Book ", "Successfulness ", " Book returned successfully!");
						Main.client.returnBookController.returnBookStage.close();}
				}

				else {

					Main.client.returnBookController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Something went wrong,\n Please try again later");
					Main.client.returnBookController.returnBookStage.close();
				}
			}

		});
	}

	/**
	 * getAllActivitiesMember - get member's activities.
	 * @param addLoan
	 */
	public void getAllActivitiesMember(Member addLoan) {

		Msg msg=new Msg("select  loans.idloan,loans.idbook,loans.copyid, loans.IssueDate, loans.ReturnDate , books.bookname , books.Tagged FROM loans INNER JOIN books on loans.idbook = books.idbook where loans.MemberID='"+addLoan.getMemberID() +"';",Command.getAllActivitiesMember);
		//Msg msg=new Msg("SELECT * FROM loans where MemberID='"+addLoan.getMemberID() +"';",Command.getAllActivitiesMember);
		msg.dataToServer.add(addLoan.getMemberID());
		msg.dataToServer.add(addLoan);
		
		try {
			System.out.println("Before server");
			Main.client.sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
/**
 * handlerAllActivitiesMember - open stage with the relevant activities.
 * @param newmsg
 */
	public void handlerAllActivitiesMember(Msg newmsg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(newmsg.result) {
					int i=0;
					System.out.println(newmsg.dataFromServer.size());
					int counterLoand=(int) newmsg.dataFromServer.get( newmsg.dataFromServer.size()-2) ;
					int counterReturn=(int) newmsg.dataFromServer.get(newmsg.dataFromServer.size()-1) ;
					System.out.println(counterReturn +" " + counterLoand);
					for ( ;i<counterLoand;i++) {
						//viewMemberControllerFROMLibrarianController.loanBooks.add((Loan) newmsg.dataFromServer.get(i));
						if(Main.permission.equals(Command.permission1)) {
						Main.client.viewMemberController.loanBooks.add((Loan) newmsg.dataFromServer.get(i));
						System.out.println(Main.client.viewMemberController.loanBooks.get(i).getIdbook());}
						else {
							Main.client.viewMemberManagerController.loanBooks.add((Loan) newmsg.dataFromServer.get(i));
							
						}
					}
					System.out.println(i);
					for ( i=counterLoand;i<newmsg.dataFromServer.size()-2;i++) {
						//viewMemberControllerFROMLibrarianController.returnBooks.add((Return) newmsg.dataFromServer.get(i));
						System.out.println("FOR RETURN");
						if(Main.permission.equals(Command.permission1)) {
						Main.client.viewMemberController.returnBooks.add((Return) newmsg.dataFromServer.get(i));}
						else {
							
							Main.client.viewMemberManagerController.returnBooks.add((Return) newmsg.dataFromServer.get(i));
						}

					}
					if(Main.permission.equals(Command.permission1)) {
					Main.client.searchmemberController.openViewStage();}
					else {
						Main.client.searchmemberManagerController.openViewStage();
					}
		
					
					
				
					}
				else {
					if(Main.permission.equals(Command.permission1))
					Main.client.viewMemberController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Something went wrong,\n Please try again later");
					else
						Main.client.viewMemberManagerController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Something went wrong,\n Please try again later");
				}
				
				
				
				
			}
			});
			

	}
	/**
	 * extendloan - Checking whether there is a queue on the book, and accordingly dealing with extension of the loan
	 * @param extendLoanMember
	 * @param memberID
	 */

	public void extendloan(Loan extendLoanMember,String memberID) {
		
		Msg msg=new Msg("SELECT max(positionInLine) FROM obl.orders where idbook='"+extendLoanMember.getIdbook() +"';",Command.extendloan);
		msg.dataToServer.add(extendLoanMember);
		msg.dataToServer.add(memberID);
		try {
			System.out.println("Before server");
			Main.client.sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
	
		
	}	
}
/**
 * checkextendloan - check if extend available.
 * @param newmsg
 */
	public void checkextendloan(Msg newmsg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(newmsg.result) {
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Extend Loan Successful");
					alert.setContentText("The new RETURN DATE IS : "+newmsg.dataFromServer.get(1).toString() +".");
					ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
					//ButtonType noButton = new ButtonType("No", ButtonData.NO);

					alert.getButtonTypes().setAll(okButton);
					alert.showAndWait().ifPresent(type -> {
						if (type == okButton) {
							if(Main.permission.equals(Command.permission1)) {
							Main.client.searchmemberController.searchmemberStage.show();
							
							//Main.librariancontroller.getAllActivitiesMember(Main.client.searchmemberController.lonerMember);
							
							Main.client.viewMemberController.viewMemberController.close();}
							else 
							{Main.client.searchmemberManagerController.searchmemberManagerControllerStage.show();
							Main.client.viewMemberManagerController.viewMemberManagerControllerStage.close();
							}

						}
						
					});
					//viewMemberControllerFROMLibrarianController.viewMemberController.close();
					//Main.librariancontroller.getAllActivitiesMember(Main.client.searchmemberController.lonerMember);
				//	Main.client.viewMemberController.displayAlert(Alert.AlertType.INFORMATION, "Extend Loan", "The extension was successful", "The new RETURN DATE IS : "+newmsg.dataFromServer.get(1).toString() +" ");
					}
				else {
					if(Main.permission.equals(Command.permission1))
					Main.client.viewMemberController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", newmsg.dataFromServer.get(0).toString());
					else
						Main.client.viewMemberManagerController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", newmsg.dataFromServer.get(0).toString());
				}
		
		
	
			}
		});
	}
	
	
	/**
	 * generate random password from A-Z a-z 0-9
	 * @param len
	 * @return
	 */
		String randomPassword( int len ){
		   StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   return sb.toString();
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
			String password=randomPassword(6);
			Member memberToServer=new Member(Firstname,Lastname,MemberID,subscriptionMember,Email,Phonenumber,Graduationyear);
			Msg msg=new Msg("insert into member values ("+MemberID+",'"+Lastname +"','"+Firstname+"',"+"105"+","+MemberID+","+"'"+password+"'"+","+Phonenumber+",'"+Email+"',"+Graduationyear+",'Active',0,0);", Command.addNewMember);
			msg.dataToServer.add(password);
			System.out.println(msg);
			try {
				Main.client.sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}




		}
		/**
		 * checkAddNewMemberStatus - check if the adding has been successful, display a message accordingly
		 * @param newmsg
		 */
		public void checkAddNewMemberStatus(Msg newmsg) {


			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if (!newmsg.result) 
					{ 				
						Main.client.addNewMemberController.displayAlert(AlertType.ERROR, "Error", "Error Message ", "The member is already in register");
					}
					else
					{
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Add new member Successfulness ");
						alert.setContentText("The member is successfully added\n Is initial password is "+newmsg.dataFromServer.get(0));
						ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);

						alert.getButtonTypes().setAll(okButton);
						alert.showAndWait().ifPresent(type -> {
							if (type == okButton) {	
								Main.client.addNewMemberController.AddNewMemberStage.close();
							}
						});
						
			
					}
				}
			});
		}
	
	/**
	 * RemoveBook - remove the book from DB 
	 * @param removeBook
	 * @throws IOException
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
 * AddNewBook - add new book to DB 
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
 * setNewValueForMember - edit member details.
 * @param memberID
 * @param email
 * @param phonnumber
 * @param status
 */
public void setNewValueForMember(String memberID, String email, String phonnumber, String status) {
	
	Msg msg=new Msg("UPDATE member SET MemberID = '"+ memberID+"', Email = '"+email +"', Phonenumber='"+ phonnumber+"', Status='"+status+"'  WHERE MemberID='"+ memberID+"';",Command.setNewValueForMember);
	try {
		if(Main.permission.equals(Command.permission1)) {
		Main.client.searchmemberController.lonerMember.setMemberID(memberID);
		Main.client.searchmemberController.lonerMember.setEmail(email);
		Main.client.searchmemberController.lonerMember.setPhonenumber(phonnumber);
		Main.client.searchmemberController.lonerMember.setStatus(status);}
		else {
			Main.client.searchmemberManagerController.lonerMember.setMemberID(memberID);
			Main.client.searchmemberManagerController.lonerMember.setEmail(email);
			Main.client.searchmemberManagerController.lonerMember.setPhonenumber(phonnumber);
			Main.client.searchmemberManagerController.lonerMember.setStatus(status);
			
		}
		Main.client.sendToServer(msg);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

/**
 * setNewValueForMember - check if details edit success
 * @param newmsg
 */
public void checksetNewValueForMember(Msg newmsg) {
	Platform.runLater(new Runnable() {
		@Override
		public void run() {
	
	if(newmsg.result) {
		//Main.client.viewMemberController.displayAlert(Alert.AlertType.INFORMATION,"Edit Member ", "Successfulness ", "Member details have been successfully updated ");
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Edit Member Successfulness");
		alert.setContentText("Member details have been successfully updated");
		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);

		alert.getButtonTypes().setAll(okButton);
		alert.showAndWait().ifPresent(type -> {
			if (type == okButton) {
				if(Main.permission.equals(Command.permission1))
				Main.client.viewMemberController.initialize();
				else
					Main.client.viewMemberManagerController.initialize();
				//Main.librariancontroller.getAllActivitiesMember(Main.client.searchmemberController.lonerMember);
			//	Main.client.viewMemberController.viewMemberController.close();

			}
		});
		
	}
	else {
		if(Main.permission.equals(Command.permission1))
		Main.client.viewMemberController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Something went wrong,\n Please try again later");
		else
			Main.client.viewMemberManagerController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Something went wrong,\n Please try again later");
			
	}
		}
	});
	
}
/**
 * create a Book Search Request for server 
 * @param where
 * @param search
 * @throws IOException
 */

public void createaBookSearchRequest(String where, String search) {
	

	        	Platform.runLater(new Runnable() {
	        	    @Override
	        	    public void run() {
	        			System.out.println("create a Book Search Request");
	        			   Msg msg=new Msg("SELECT * FROM books WHERE " +where+ " LIKE " + search+ ";", Command.searchBookFromControllerLibrarian);
	        		            msg.dataToServer.add(where);
	        		            msg.dataToServer.add(search);
	        		            System.out.println("SERACH"+Thread.currentThread().getId());
	        		            try {
									Main.client.sendToServer(msg);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	        	    }
	        	});
	             	
	        
			
	}


/**
 * create a message Search Request for server 
 * @throws IOException
 */

public void createaMessageRequest() {
	        	Platform.runLater(new Runnable() {
	        	    @Override
	        	    public void run() {
	        			   Msg msg=new Msg("SELECT senderID, member.FirstName,member.LastName,message FROM message INNER JOIN member ON member.memberID = message.senderID where messageRecipient = 'Librarian';", Command.getMessageToLibrarian);
	        		       System.out.println(msg.getQuery());
	        			   try {
									Main.client.sendToServer(msg);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	        	    }
	        	});			
	}

/**
 * UpDataLibrarian - edit employ details.
 * @param newEmail
 */
public void UpDataLibrarian(String newEmail) {
	System.out.println("Up Data newEmail");
	 Msg msg=new Msg();
	if(Main.permission.equals(Command.permission1)) {
	    msg=new Msg("UPDATE employe SET Email="+ newEmail + " WHERE ID ="+curntlibrarian.getID()+";", Command.UpDataLibrarian);}
	else {
		
		 msg=new Msg("UPDATE employe SET Email="+ newEmail + " WHERE ID ="+Main.client.librarymanagercontroller.curntlibrarymanager.getID()+";", Command.UpDataLibrarian);
	}
	   
         msg.dataToServer.add(newEmail);

         try {
				Main.client.sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
}
/**
 * checkTheUpDataLibrarian - check if librarian details edit success.
 * @param newmsg
 */
public void checkTheUpDataLibrarian(Msg newmsg) {
	Platform.runLater(new Runnable() {
		@Override
		public void run() {
	
	if(newmsg.result) {
		
		
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Success Message");
		alert.setContentText("Your information has been successfully updated, thank you for updating us");
		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);

		alert.getButtonTypes().setAll(okButton);
		alert.showAndWait().ifPresent(type -> {
			if (type == okButton) {
				String SetEmail=(String)newmsg.dataToServer.get(0);
				if(Main.permission.equals(Command.permission1)) {
				curntlibrarian.setEmail(SetEmail.substring(1, (SetEmail.length()-1)));
				
				Main.client.profileEmployeesController.initialize();}
				else {
					
					Main.client.librarymanagercontroller.curntlibrarymanager.setEmail(SetEmail.substring(1, (SetEmail.length()-1)));
					Main.client.profileManagercontroller.initialize();
				}
		

			}
		});
		
	}
	else {
		
		Main.client.profileEmployeesController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Something went wrong,\n Please try again later");
		
	}
		}
	});
	
	
	
}
	
}



