package Controllers;
import Entity.*;
import Gui.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Client.Command;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.Serializable;
import Client.Main;

/***
 * 
 * Controller class to perform subscription actions
 *
 */

public class MemberController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  MainController mainController;
	
	public static Member curntllyLogIn; 
	public static SearchBookController searchbookcontroller;
	public MemberController() {
		super();
	}
	/**
	 * handle login Checks for if the member exist and logs in to the system
	 * @param userName - geting from the MainController
	 * @param pass - geting from the MainController
	 * @throws IOException
	 */
	public void handlelogin(String userName,String pass  ) throws IOException {
			
    	Msg msg=new Msg("SELECT * FROM Member WHERE MemberID ="+ userName+ " and Password =" + pass +";", Command.getMemberByIdAndPass);
    	msg.dataToServer.add(userName);
        //msg.dataToServer.add(pass);
    	System.out.println("from Member Controller handlelogin");
    	Main.client.sendToServer(msg);
	}
	/**
	 * update his "isLogged" status to false;
	 * @param memberID
	 * @throws IOException
	 */
	 public void LogoutHandler(String memberID) throws IOException {
	  		Msg msg=new Msg("UPDATE member SET isLogged = false WHERE MemberID = "+"'"+memberID+"';", Command.logout);
	      	msg.dataToServer.add(memberID);
	      	
	      	Main.client.sendToServer(msg);
	  		
	  	}
	
	
	
	/**
	 * Create a new member 
	 * 
	 * @param dataFromServer
	 * @return new Member 
	 */
	
	public Member creatNewMember(ArrayList<Object> dataFromServer) {
	
		Member member=new Member (Boolean.valueOf(dataFromServer.get(10).toString()),dataFromServer.get(0).toString(),
				dataFromServer.get(1).toString(),dataFromServer.get(2).toString(),
				dataFromServer.get(3).toString(),dataFromServer.get(4).toString(),
				dataFromServer.get(5).toString(),dataFromServer.get(6).toString(),
				dataFromServer.get(7).toString(),Integer.valueOf(dataFromServer.get(8).toString()),
				dataFromServer.get(9).toString(),Integer.valueOf(dataFromServer.get(10).toString()));
	
		System.out.println(member.toString());
		return member;
		
		
	}
	/**
	 * handle Forgot password help the member to login by identification by ID and year of graduation to sent the password
	 * @param ID - geting from the ForgotpasswordController
	 * @param graduation - geting from the ForgotpasswordController
	 * @throws IOException
	 */
	public void handlelForgotpassword(String ID , String graduation) throws IOException {
		Msg msg=new Msg("SELECT * FROM Member WHERE MemberID ="+ ID+ " and Graduationyear =" + graduation +";", Command.getMemberByIdAndGraduation);
    	msg.dataToServer.add(ID);
    	msg.dataToServer.add(graduation);
    	Main.client.sendToServer(msg);
		
	}
	/**
	 *  check the Status Member if he could login to the system
	 * @param curntllyLogIn
	 * @throws Exception
	 */
	public void checkStatusLogin(Member curntllyLogIn) throws Exception {
		//Platform.runLater(new Runnable() {
    	 //   @Override
    	//    public void run() {
		System.out.println("checkStatusLogin" );
		if(curntllyLogIn.getStatus().equals("Active")||curntllyLogIn.getStatus().equals("Frozen")) {
			try {
				Main.client.mainController.handleloginresult();
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			
			Main.client.mainController.displayAlert(AlertType.INFORMATION, "Blocked", "User Blocked", "Your user has been blocked. Contact our stuff managment");

			}
    	    }
	//	});
			
		
	     
	//}
	
	/**
	 *  Up Data Member- updating his personal information
	 * @param newPhoneNumber
	 * @param newEmail
	 * @throws IOException 
	 */
	
	public void UpDataMember(String newPhoneNumber,String newEmail) throws IOException {
		System.out.println("Up Data Member");
		
		if(!(curntllyLogIn.getPhonenumber().equals(newPhoneNumber)) && !(curntllyLogIn.getEmail().equals(newEmail))) {
			                 
			Msg msg=new Msg("UPDATE member SET Phonenumber="+ newPhoneNumber + " , Email="+ newEmail + " WHERE MemberID =" +curntllyLogIn.getMemberID()+ ";",Command.updataMemberPhoneANDEmail);
			msg.dataToServer.add(newPhoneNumber);
			msg.dataToServer.add(newEmail);
			Main.client.sendToServer(msg);
			return;
		}
		
		else{if(!(curntllyLogIn.getPhonenumber().equals(newPhoneNumber))) {
		Msg msg=new Msg("UPDATE member SET Phonenumber="+ newPhoneNumber + " WHERE MemberID ="+curntllyLogIn.getMemberID()+";",Command.updataMemberPhoneANDEmail);
		msg.dataToServer.add(newPhoneNumber);
		msg.dataToServer.add(curntllyLogIn.getEmail());
		Main.client.sendToServer(msg);
		return;
		}
		
		if(!(curntllyLogIn.getEmail().equals(newEmail))) {
			Msg msg=new Msg("UPDATE member SET Email="+ newEmail + " WHERE MemberID ="+curntllyLogIn.getMemberID()+";",Command.updataMemberPhoneANDEmail);
			msg.dataToServer.add(curntllyLogIn.getPhonenumber());
			msg.dataToServer.add(newEmail);
			Main.client.sendToServer(msg);
			return;
			}
		
		}
		
		Main.client.profileController.displayAlert(AlertType.INFORMATION, "No change was made", "Unchanged Message ", "You have not entered any new information, so no change was made");
		
		
	}
	
	/**
	 *  check If The UpData data was a success and print a message in accordance
	 * @param newmsg
	 */
	public void  checkIfTheUpDataSucceeded(Msg newmsg){
		
		
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	if (newmsg.result) { 
    	    		System.out.println("check If The Up Data Succeeded");
    	    		String SetEmail=(String)newmsg.dataFromServer.get(1);
    	    		curntllyLogIn.setPhonenumber((String)newmsg.dataFromServer.get(0));
    	    		curntllyLogIn.setEmail(SetEmail.substring(1, (SetEmail.length()-1)));
    	    		System.out.println(curntllyLogIn.getPhonenumber().toString()+" "+curntllyLogIn.getEmail().toString());
    	    		Main.client.profileController.initialize();
    				Main.client.profileController.displayAlert(AlertType.INFORMATION, "Success", "Success Message ", "Your information has been successfully updated, thank you for updating us");
    				
    			}
    			else {
    				
    				Main.client.profileController.displayAlert(AlertType.ERROR, "Error", "Error Message ", "Sorry, the update did not pass successfully, please try again later");
    				
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
	public void createaBookSearchRequest(String where , String search) throws IOException {
		

	        	Platform.runLater(new Runnable() {
	        	    @Override
	        	    public void run() {
	        			System.out.println("create a Book Search Request");
	        			   Msg msg=new Msg("SELECT * FROM books WHERE " +where+ " LIKE " + search+ ";", Command.searchBookFromController);
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
	 * make Order - Create a request from the server to order a book
	 * @param orderBook - the book the member choose
	 * @throws IOException 
	 */
	
	public void makeOrder(Book orderBook) throws IOException {
		if((orderBook.getQuantity()-orderBook.getCurrentlyonloan())>0) {
			Main.client.HomePageController.displayAlert(AlertType.INFORMATION, "Order a Book", "The Book is available ", "The order has not been made,\n there are "+(orderBook.getQuantity()-orderBook.getCurrentlyonloan())+" copies available in the library so you are welcome to grab it before someone else does.\nYou can find it on shelf "+orderBook.getBooklocation() );
		}
		else {
		//Msg msg=new Msg("SELECT COUNT(*) FROM orders WHERE idbook= "+"'"+orderBook.getIdbook()+"'"+";",Command.MakeOrderBook);
		Msg msg=new Msg("SELECT memberID,idbook FROM orders where idbook ="+"'"+orderBook.getIdbook()+"'"+";",Command.MakeOrderBook);
	    msg.dataToServer.add(orderBook);
		msg.dataToServer.add(curntllyLogIn.getMemberID());
		Main.client.sendToServer(msg);
		}
	}

	/**
	 * resultOrderBook - according to the result of the DB displaying the appropriate message
	 * @param newmsg
	 */
	public void resultOrderBook(Msg newmsg ) {
		Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
		System.out.println("from controller");
		if(newmsg.result) {
			
			
			Main.client.HomePageController.displayAlert(AlertType.INFORMATION, "Order a Book", "The Order has been placed ", 
					"The order has been made,\n Your place in the queue is "+newmsg.dataFromServer.get(0) +". ");

			
		}
		else {
			
				String alert=(String)newmsg.dataFromServer.get(0);
				Main.client.HomePageController.displayAlert(AlertType.INFORMATION, "Order a Book", "The Book is not available ",alert  );
				
				}
		
    	    } });
	}
	/**
	 * get member loans from DB
	 * @param curntllyLogIn2
	 * @throws IOException
	 */
	public void getLoans(Member curntllyLogIn2) throws IOException {
		   Msg msg=new Msg("select  loans.idloan,loans.idbook,loans.copyid, loans.IssueDate, loans.ReturnDate , books.bookname , books.Tagged FROM loans INNER JOIN books on loans.idbook = books.idbook where loans.MemberID='"+curntllyLogIn2.getMemberID() +"';", Command.getLoans);
		   System.out.println(msg.getQuery());
		   msg.dataToServer.add(curntllyLogIn);
		   Main.client.sendToServer(msg);
	}
	/**
	 * insert all loans to "my book" table.
	 * @param newmsg
	 */
	public void insertLoanToMyBook(Msg newmsg) {
		
		Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	if(newmsg.result) {
    	    		for (int i=0;i<newmsg.dataFromServer.size();i++) {
    	    			Main.client.mybookController.loanBooks.add((Loan)newmsg.dataFromServer.get(i));
    	    			System.out.println(Main.client.mybookController.loanBooks.get(i).toString());
    	    			
    	    		}
    	    		Main.client.mybookController.setStageViewTable();
    	    		
    	    	}
    	    	
    	    	else {
    	    		Main.client.mybookController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Something went wrong,\n Please try again later");
    	    		
    	    	}
    	    	
    	    	
    	    }
    	});
    	    }
	/**
	 * get a specific book from DB via ID of book
	 * @param viewBook
	 * @throws IOException
	 */
	public void getBook(Loan viewBook) throws IOException {
		   Msg msg=new Msg("select  * FROM books where books.idbook='"+viewBook.getIdbook() +"';", Command.getBook);
		   Main.client.sendToServer(msg);
		
	}
	public void setViewBook(Msg newmsg) {
		
		Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	if(newmsg.result) {
    	    		if(Main.permission.equals(Command.permission2)) {
    	    			if(Main.clientMember.curntllyLogIn.getCurrentPage().equals("HomePage")) {
    	    				Main.client.HomePageController.bookToView=(Book)newmsg.dataFromServer.get(0);
    	    				Main.client.HomePageController.Min_Return_Date=(String)newmsg.dataFromServer.get(1);
    	    				try {
    							//Main.client.mybookController.book.getPdf().toString();
    	    					Main.client.HomePageController.setBookView(	Main.client.HomePageController.bookToView);
    						} catch (IOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
    	    				
    	    			}
    	    			else {
    	    		Main.client.mybookController.book=(Book)newmsg.dataFromServer.get(0);
					try {
						//Main.client.mybookController.book.getPdf().toString();
						Main.client.mybookController.setBookView(Main.client.mybookController.book);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
    	    	}
    	    		else {
        	    		
        	    		Main.client.searchBookMainController.viewBook=(Book)newmsg.dataFromServer.get(0);
        	    		System.out.println("min return = "+(String)newmsg.dataFromServer.get(1));
						Main.client.searchBookMainController.Min_Return_Date=(String)newmsg.dataFromServer.get(1);
        	    		try {
    						Main.client.searchBookMainController.setBookViewMain(Main.client.searchBookMainController.viewBook);
    					} catch (IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
        	    		
        	    	}
    	    		
    	    	}
    	    	else {
    	    		Main.client.mybookController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Something went wrong,\n Please try again later");
    	    		
    	    	}
    	    		
    	    	
    	    	
    	    	
    	    }
    	});
		
		
	}
	/**
	 * extend his loan 
	 * @param extenLoan
	 * @param newreturn
	 */
	public void extendLoanAuto(Loan extenLoan, String newreturn) {
		
		  Msg msg=new Msg("SELECT max(positionInLine) FROM obl.orders where idbook='"+extenLoan.getIdbook() +"'; ", Command.extendLoanAuto);
		  msg.dataToServer.add(newreturn);
		  msg.dataToServer.add(extenLoan);
		  msg.dataToServer.add(curntllyLogIn);
	      try {
				Main.client.sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  
	}
	/**
	 * check if extend success
	 * @param newmsg
	 */
	public void checkTheExtendLoan(Msg newmsg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(newmsg.result) {
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Extend Loan Successful");
					alert.setContentText("yayy , I'm yours for anoter week! ");
					ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
					//ButtonType noButton = new ButtonType("No", ButtonData.NO);

					alert.getButtonTypes().setAll(okButton);
					alert.showAndWait().ifPresent(type -> {
						if (type == okButton) {
							
							try {
								Main.client.mainControl.clientMember.getLoans(curntllyLogIn);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}


						}
						
					});
					
					//Main.librariancontroller.getAllActivitiesMember(Main.client.searchmemberController.lonerMember);
				//	Main.client.viewMemberController.displayAlert(Alert.AlertType.INFORMATION, "Extend Loan", "The extension was successful", "The new RETURN DATE IS : "+newmsg.dataFromServer.get(1).toString() +" ");
					}
				else {
					
					Main.client.mybookController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", newmsg.dataFromServer.get(0).toString());
				}
		
		
	
			}
		});
		
		
	}
	/**
	 * get details from DB for activity log
	 * @param curntllyLogIn2
	 * @throws IOException
	 */
	public void getAllReturnd(Member curntllyLogIn2) throws IOException 	
	{
		
		Msg msg=new Msg("SELECT books.bookname,returns.IssueDate,returns.actualReturnDate,loans.rimenderSent FROM returns INNER JOIN books ON returns.idbook=books.idbook INNER JOIN loans ON returns.MemberID=loans.MemberID WHERE returns.MemberID='"+curntllyLogIn2.getMemberID()+"';",Command.ActivityLog);
		msg.dataToServer.add(curntllyLogIn2);
//SELECT returns.MemberID,returns.idbook,books.bookname,returns.copyid,returns.IssueDate,returns.actualReturnDate,loans.rimenderSent FROM returns INNER JOIN books ON returns.idbook=books.idbook INNER JOIN loans ON returns.MemberID=loans.MemberID where returns.MemberID='204677645'              
		// SELECT returns.MemberID,returns.idbook,returns.copyid,returns.IssueDate,returns.actualReturnDate,loans.rimenderSent FROM returns INNER JOIN books ON returns.idbook=books.idbook INNER JOIN loans ON returns.MemberID=loans.MemberID where returns.MemberID='204677645'
		Main.client.sendToServer(msg);
	}
	
	/**
	 * get details from DB for activity log
	 * @param newmsg
	 */
	public void handle_All_Returns_Result(Msg newmsg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				System.out.println(newmsg.result);
				if(newmsg.result){
					System.out.println(newmsg.result);
					int i=0;
					int counterReturn=(int) newmsg.dataFromServer.get( newmsg.dataFromServer.size()-2) ;
					int counterOrder=(int) newmsg.dataFromServer.get(newmsg.dataFromServer.size()-1) ;
					System.out.println(counterOrder);
					for(;i<counterReturn;i++) {
						Main.client.activityLognewController.returnBooks.add((Return)newmsg.dataFromServer.get(i));
						
					}
					for ( i=counterReturn;i<newmsg.dataFromServer.size()-2;i++) {
						System.out.println((orders)newmsg.dataFromServer.get(i));
						Main.client.activityLognewController.orderBooks.add((orders)newmsg.dataFromServer.get(i));
						
					}
					Main.client.HomePageController.startActivityLog();
					
				}
				else {
					Main.client.HomePageController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Somting whent worng");
					
				}
				
		
		/*if(!(newmsg.getDataFromServer().isEmpty())){
			System.out.println("FROM COMM - ADD TO ARRAY");
			Main.client.activityLogController.returnBook.add((Return)newmsg.dataFromServer.get(0));
			System.out.println(Main.client.activityLogController.returnBook.get(0).getBookname());
			System.out.println();
			for(int i = 0 ; i<newmsg.dataFromServer.size();i++) {
				//System.out.println( Return.ReturnBook.get(i).getBookname());
				 //  Return.ReturnBook.add((Return)newmsg.dataFromServer.get(i));
				Main.client.activityLognewController.returnBooks.add((Return)newmsg.dataFromServer.get(i));
				System.out.println((Return)newmsg.dataFromServer.get(i));
				
				System.out.println(Main.client.activityLognewController.returnBooks.get(i).getBookname());
			}
			//Main.client.activityLogController.setStageViewTable();
			//Main.client.activityLogmemberController.setStageViewTable();
			Main.client.HomePageController.startActivityLog();
			
	}*/
	}
});
	}
	/**
	 * get book by ID from DB
	 * @param viewBook
	 * @throws IOException
	 */
	public void getBookMain(Book viewBook) throws IOException {
		
		System.out.println("getBookMain");
		Msg msg=new Msg("select  * FROM books where books.idbook='"+viewBook.getIdbook() +"';", Command.getBook);
		   Main.client.sendToServer(msg);
		
	}
	/**
	 * cancelOrder - member can cancel his order
	 * @param cancelOrderMember
	 * @throws IOException
	 */
	public void cancelOrder(orders cancelOrderMember) throws IOException {
		
		Msg msg=new Msg("DELETE FROM obl.orders WHERE memberID='"+cancelOrderMember.getMemberID() +"' and idbook='"+cancelOrderMember.getIdbook()+"';", Command.cancelOrder);
		System.out.println(msg.getQuery());
		msg.dataToServer.add(cancelOrderMember); 
		Main.client.sendToServer(msg);
	}
	/**
	 * handlerCancelOrder - Check whether the cancellation is successful and update the arrays to the table
	 * @param newmsg
	 */
	public void handlerCancelOrder(Msg newmsg) {
		Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	if(newmsg.result) {
    	    		orders orderToCancel=(orders)newmsg.dataFromServer.get(0);
    	    		
    	    		//Main.client.activityLognewController.displayAlert(Alert.AlertType.CONFIRMATION, "successful ", "Cancel Order Book", "The requested order was successfully canceled!");
    	    		Main.client.activityLognewController.keeporderBooks.remove(orderToCancel);
    	    		for(int i=0;i<Main.client.activityLognewController.keeporderBooks.size();i++) {
    	    			Main.client.activityLognewController.orderBooks.add(Main.client.activityLognewController.keeporderBooks.get(i));
    	    			
    	    		}
    	    		for (int i=0;i<Main.client.activityLognewController.keepreturnBooks.size();i++) {
    	    			Main.client.activityLognewController.returnBooks.add(Main.client.activityLognewController.keepreturnBooks.get(i));
    	    			
    	    		}
    	    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Cancel Order Book Successful");
					alert.setContentText("The requested order was successfully canceled!");
					ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
					
					alert.getButtonTypes().setAll(okButton);
					alert.showAndWait().ifPresent(type -> {
						if (type == okButton) {
							
								Main.client.activityLognewController.initialize();
								
					
						}
						
					});
    	    		
    	    		
    	    		
    	    }
    	    	else {
    	    		
    	    		Main.client.activityLognewController.displayAlert(Alert.AlertType.ERROR, "ERROR", "ERROR Message", "Somting whent worng");
    	    	}
    	    }
     	});
	}
    
}
			



	