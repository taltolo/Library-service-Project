package Server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;

//This file contains material supporting section 3.7 of the textbook:
//"Object Oriented Software Engineering" and is issued under the open-source
//license found at www.lloseng.com 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Client.CommunicationController;
import Entity.Book;
import Entity.Employees;
import Entity.Loan;
import Entity.Member;
import Entity.Msg;
import Entity.Return;
import Entity.orders;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
	//Class variables *************************************************
	static public String dataBaseName;
	static public String dataBaseUser;
	static public String dataBasePassword;
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	public static Connection conn = null;

	//Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) 
	{
		super(port);
	}

	private Connection connectToDB() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			/* handle the error */
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/"+dataBaseName, dataBaseUser, dataBasePassword);} catch (SQLException ex) {/* handle any errors */
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		return conn;
	}


	public ArrayList<String> getQuery(String query) throws Exception {
		Statement stmt;
		Connection con = connectToDB();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<String> objectArr = new ArrayList<>();
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			while(rs.next()) {
				for(int i=1;i<=rsmd.getColumnCount();i++) {
					objectArr.add(rs.getString(i));
					System.out.println(rs.getString(i));}
			}
			this.sendToAllClients(objectArr);
			rs.close();
			return objectArr;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}




	private void saveUserToDB(Connection con,Statement s) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			// String query = String.format("INSERT INTO obl.member VALUES('%s','%s','%s','%s','%s')",s.getID(),s.getName(),s.getStatusMembership(),s.getOperation(),Integer.parseInt(s.isFreeze()));
			// stmt.execute(query);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	//private Statement parsingTheData(ArrayList<String> arr) {
	//	return new Statement(arr.get(1), arr.get(0), arr.get(2), arr.get(3), arr.get(4));
	//}



	//Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		//ArrayList<Object> objectArr = null;
		Connection con = connectToDB();
		//Statement stmt;
		Msg newMsg = (Msg) msg;
		switch(newMsg.getFuncToRun())
		{
		case getMemberByIdAndPass:
		{
			newMsg = getMemberByIdAndPass(newMsg, con);
			break;
		}

		case getMemberByIdAndGraduation:
		{
			newMsg=getMemberByIdAndGraduation(newMsg,con);
			break;
		}
		case searchBook:
		{
			newMsg=searchBook(newMsg,con);
			break;
		}
		case updataMemberPhoneANDEmail:
		{
			newMsg=updataMemberPhoneANDEmail(newMsg,con);
			break;
		}
		case searchBookFromController:
		{

			newMsg=searchBookFromController(newMsg,con);
			break;
		}
		case MakeOrderBook:
		{
			newMsg=MakeOrderBook(newMsg,con);
			break;
		}
		case getEmployeByWorkerNumberWorkerNumber:
		{

			newMsg=getEmployeByWorkerNumberWorkerNumber(newMsg,con);
			break;

		}
		case getMembers:{

			newMsg=getMembers(newMsg,con);
			break;
		}
		case loanaBook1:{

			try {
				newMsg=loanaBook1(newMsg,con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		case ReturnBook:
		{


			newMsg=ReturnBook(newMsg,con);

			break;
		}
		case getAllActivitiesMember:{
			newMsg=getAllActivitiesMember(newMsg,con);
			break;
		}
		case extendloan:{


			newMsg=extendloan(newMsg,con);
			break;
		}
		case upadteMemberStatus:
		{
			upadteMemberStatus(newMsg, con);
			break;
		}
		case addNewMember:
		{
			addNewMember(newMsg, con);
			break;
		}

		case automatedRun:{
			sendingReminder(newMsg,con);
			break;
		}
		case searchBookFromInventory:{
			newMsg=searchBookFromController(newMsg,con);
			break;
		}
		case RemoveBook:
		{
			newMsg=RemoveBook(newMsg,con);
			break;
		}
		case EditBook:
		{
			newMsg=EditBook(newMsg,con);
			break;
		}
		case AddNewBook:
		{
			newMsg=AddNewBook(newMsg,con);
			break;
		}
		case setNewValueForMember:{
			newMsg=setNewValueForMember(newMsg,con);
			break;
		}
		case searchBookFromControllerLibrarian:{

			newMsg=searchBookFromController(newMsg,con);
			break;

		}
		case getLoans:
		{

			newMsg=getLoans(newMsg,con);
			break;
		}
		case getBook:{
			newMsg=getBook(newMsg,con);
			break;

		}
		case extendLoanAuto:{
			newMsg=extendLoanAuto(newMsg,con);
			break;

		}
		case getMessageToLibrarian:{
			newMsg=getMessageToLibrarian(newMsg,con);
			break;
		}
		case UpDataLibrarian:{
			newMsg=UpDataLibrarian(newMsg,con);
			break;

		}
		case ActivityLog:{
			newMsg=setActivityLog1( newMsg,  con);
			break;
		}
		case getEmployees:{
			newMsg=getEmployees( newMsg,  con);
			break;

		}
		case logout:{
			newMsg=logout_From_System(newMsg,con);
			break;
		}
		case UpDataLibrarianInfo:{
			newMsg=UpDataLibrarianInfo( newMsg,  con);
			break;
		}


		case getReportType2:{
			newMsg=getReportType2(newMsg,con);
			break;

		}

		case getReportType3:{
			newMsg=getReportType3(newMsg,con);
			break;

		}
		case getReportType3ByBook:{
			newMsg=getReportType3ByBook(newMsg,con);
			break;

		}
		case getReportType1:{
			newMsg=getReportType1(newMsg, con);
			break;

		}
		case getReportType1ByDate:{
			newMsg=getReportType1ByDate(newMsg, con);
			break;

		}

		case getActivityReport:{
			newMsg=getActivityReport(newMsg, con);
			break;

		}
		case getRturnsReport:{
			newMsg=getRturnsReport(newMsg, con);
			break;
		}
		case cancelOrder:{
			newMsg=cancelOrder(newMsg, con);
			break;

		}
		}




		System.out.println(newMsg.dataFromServer.toString());
		System.out.println(newMsg.getFuncToRun());
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ;
	}




	/**
	 * createObject_ordersForActivityLog= create Object of order for the ActivityLog of a member
	 * @param rs
	 * @return
	 * @throws SQLException
	 */


	public Object createObject_ordersForActivityLog(ResultSet rs) throws SQLException {

		String memberID= rs.getString(1) ;
		String idbook=rs.getString(2);
		int positionInLine=rs.getInt(3);
		String bookname=rs.getString(4);
		String statusOrder;
		String Option;

		if(positionInLine!=0) {
			statusOrder="Waiting";
			Option="Cancel";
		}
		else {
			statusOrder="available";
			Option="Cancel";
		}

		return new orders(memberID,idbook,positionInLine,bookname,statusOrder,Option) ;
	}







	/**
	 * Parse the result set in to a Loan object
	 * 
	 * @param rs - the data from the DB 
	 * @return new object to add to MSG array 
	 * @throws SQLException
	 */


	public Object createObjecLoan(ResultSet rs) throws SQLException {

		int idloan=rs.getInt(1);
		String MemberID= rs.getString(2);
		String idbook=rs.getString(3);
		String copyid=rs.getString(4);
		Date IssueDate=rs.getDate(5);
		Date ReturnDate=rs.getDate(6);
		boolean isremined = rs.getBoolean(7);
		return new Loan(idloan,MemberID,idbook,copyid,IssueDate,ReturnDate,isremined);
	}

	/**
	 * createObjectMessage - to send to data as Object to the client 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */

	public Object createObjectMessage(ResultSet rs) throws SQLException {
		String memberID = rs.getString(1);
		String firstname = rs.getString(2);
		String lastname = rs.getString(3);
		String message = rs.getString(4);

		return new Entity.Message(memberID, lastname, firstname, message);
	}

	/**
	 * Parse the result set in to a Loan object
	 * 
	 * @param rs - the data from the DB 
	 * @return new object to add to MSG array 
	 * @throws SQLException
	 */

	public Object createObjecLoanForActivitieLoan(ResultSet rs) throws SQLException {

		// loans.idloan,loans.idbook,loans.copyid,

		int idloan=rs.getInt(1);
		String idbook=rs.getString(2);
		String copyid=rs.getString(3);
		Date IssueDate=rs.getDate(4);
		Date ReturnDate=rs.getDate(5);
		String bookname= rs.getString(6);
		String Tagged=rs.getString(7);
		return new Loan(idloan,idbook,copyid,bookname,Tagged,IssueDate,ReturnDate);
	}




	/**
	 * Parse the result set in to a Return object
	 * 
	 * @param rs - the data from the DB 
	 * @return new object to add to MSG array 
	 * @throws SQLException
	 */


	public Object createObjecReturn(ResultSet rs) throws SQLException {

		int idreturn=rs.getInt(1);
		String idbook= rs.getString(2);
		String MemberID=rs.getString(3);
		String copyid=rs.getString(4);
		Date issueDate=rs.getDate(5);
		Date returnDate=rs.getDate(6);
		Date actualReturnDate=rs.getDate(7);
		return new Return(idreturn,idbook,MemberID,copyid,issueDate,returnDate,actualReturnDate);
	}

	/**
	 * Parse the result set in to a Return object
	 * 
	 * @param rs - the data from the DB 
	 * @return new object to add to MSG array 
	 * @throws SQLException
	 */

	public Object createObjecReturnForActivitieReturn(ResultSet rs) throws SQLException {

		Date issueDate=rs.getDate(1);
		Date returnDate=rs.getDate(2);
		Date actualReturnDate=rs.getDate(3);
		String bookname= rs.getString(4);
		return new Return(issueDate,returnDate,actualReturnDate,bookname);
	}
	/**
	 * Create Object Returns For ActivityLog member 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */

	public Object createObject_ReturnsForActivityLog (ResultSet rs) throws SQLException{
		Date issueDate=rs.getDate(1);
		Date returnDate=rs.getDate(2);
		Date actualReturnDate=rs.getDate(3);
		String bookname= rs.getString(4);
		String  statusReturn=rs.getString(5);
		System.out.println(issueDate+" " +actualReturnDate+" "+ bookname+" "+statusReturn);
		return new Return(issueDate,actualReturnDate,bookname,statusReturn);

	}




	/**
	 * Parse the result set in to a Book object
	 * 
	 * @param rs - the data from the DB 
	 * @return new object to add to MSG array 
	 * @throws SQLException
	 */


	public Object createObject(ResultSet rs) throws SQLException {

		String idbook = rs.getString(1);
		String bookname = rs.getString(2);
		int Quantity = rs.getInt(3);
		String booklocation = rs.getString(4);
		int Editionnumber =rs.getInt(5);
		String Genre = rs.getString(6);
		String Author = rs.getString(7);
		String Description = rs.getString(8);;
		String tableofcontents = rs.getString(9);;
		String Tagged = rs.getString(10);;
		int currentlyonloan = rs.getInt(11);
		String Purchasedate = rs.getString(12);
		String Executiondate = rs.getString(13);
		System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
		File file =new File(System.getProperty("user.dir")+"\\"+tableofcontents+".PDF");
		byte[] pdf=null;

		try {
			pdf = Files.readAllBytes(file.toPath());
		} catch (IOException e) {

			e.printStackTrace();
		}

		finally
		{
			return new Book(idbook,bookname,Quantity,booklocation,Editionnumber,Genre,Author,Description,tableofcontents,Tagged,currentlyonloan,Purchasedate,Executiondate,pdf);
		}

	}	

	/**
	 * create Object Returns for my books member
	 * @param rs
	 * @return
	 * @throws SQLException
	 */

	public Object createObject_Returns(ResultSet rs) throws SQLException {


		String bookname = rs.getString(1);
		Date IssueDate = rs.getDate(2);
		Date ReturnDate = rs.getDate(3);
		int isLate = rs.getInt(4);
		if(isLate==1) {
			String Status="Late";
			return new Return(bookname,IssueDate,ReturnDate,Status);}
		else
		{String Status="On time";
		return new Return(bookname,IssueDate,ReturnDate,Status);
		}


	}
	/**
	 * create Object Employee for search Employees manager view
	 * @param rs
	 * @return
	 * @throws SQLException
	 */

	public Object createObjectEployee(ResultSet rs) throws SQLException {
		String iD = rs.getString(1);
		String lastname = rs.getString(2);
		String firstname = rs.getString(3);
		String workerNumber = rs.getString(4);
		String password = rs.getString(5);
		String email = rs.getString(6);
		boolean permission =Boolean.valueOf(rs.getString(7));
		boolean isLogged = Boolean.valueOf(rs.getString(8));
		String department = rs.getString(9);
		String phoneNumber=rs.getString(10);


		return new Employees( iD,  lastname,  firstname,  workerNumber,  password,  email,isLogged,  permission,  department,phoneNumber );

	}



	/**
	 * createObjectMember - to send to data as Object to the client 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */

	public Object createObjectMember(ResultSet rs) throws SQLException {
		boolean isLogged = Boolean.valueOf(rs.getString(11));
		String MemberID = rs.getString(1);
		String Lastname = rs.getString(2);
		String Firstname = rs.getString(3);
		String subscriptionMember = rs.getString(4);
		String Username = rs.getString(5);
		String Password = rs.getString(6);
		String Phonenumber = rs.getString(7);
		String Email = rs.getString(8);
		int Graduationyear = rs.getInt(9);
		String Status = rs.getString(10);
		int count=rs.getInt(12);
		return new Member(isLogged,MemberID,Lastname,Firstname,subscriptionMember,Username,Password,Phonenumber,Email,Graduationyear,Status,count);
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server starts listening for connections.
	 */
	protected void serverStarted()
	{
		System.out.println
		("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server stops listening for connections.
	 */
	protected void serverStopped()
	{
		System.out.println
		("Server has stopped listening for connections.");
	}



	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of 
	 * the server instance (there is no UI in this phase).
	 *
	 * @param args[0] The port number to listen on.  Defaults to 5555 
	 *          if no argument is entered.
	 */
	public void startServer(EchoServer sv)
	{
		int port = 0; //Port to listen on
		try 
		{
			sv.listen(); //Start listening for connections
			AutomatedServerFuncthion.client = new CommunicationController(Inet4Address.getLocalHost().getHostAddress(), 5555);
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new AutomatedServerFuncthion(), 100,60000*60*24);

		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}
	}


	/**
	 * logout_From_System = Changes the login status for system users when you press the LOGOUT button
	 * @param msg
	 * @param conn
	 * @return
	 */
	public Msg logout_From_System(Msg msg, Connection conn) 
	{
		boolean tmp;
		msg.result=true;
		ArrayList<Object> objectArr = new ArrayList<>();
		Statement stmt;
		String userName = (String) msg.dataToServer.get(0);
		System.out.println(userName);
		try {
			stmt = conn.createStatement();
			System.out.println(msg.getQuery());
			System.out.println("NOW EXCUTE UPDATE");
			int rs = stmt.executeUpdate(msg.getQuery());
			System.out.println("rs = " +rs);
			msg.dataFromServer.add(rs);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return msg;

	}
	/**
	 * getMemberByIdAndPass = When connecting to the system, 
	 * the user's authentication request is sent by the identity number and the user's password and 
	 * checks whether he exists
	 * @param msg
	 * @param conn
	 * @return
	 */
	public Msg getMemberByIdAndPass(Msg msg, Connection conn) 
	{
		boolean tmp;
		System.out.println("ENTER ECHO??");
		msg.result=true;
		ArrayList<Object> objectArr = new ArrayList<>();
		Statement stmt;
		String userName = (String) msg.dataToServer.get(0);
		System.out.println(userName);
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQuery());
			if(rs.next()==false)
			{
				System.out.println("RS.NEXT=FALSE");
				msg.result=false;
				rs.close();
				return msg;	
			}
			rs.previous();
			while(rs.next()) {
				System.out.println("ENTER WHILE");
				msg.dataFromServer.add(rs.getString(1));
				msg.dataFromServer.add(rs.getString(2));
				msg.dataFromServer.add(rs.getString(3));
				msg.dataFromServer.add(rs.getString(4));
				msg.dataFromServer.add(rs.getString(5));
				msg.dataFromServer.add(rs.getString(6));
				msg.dataFromServer.add(rs.getString(7));
				msg.dataFromServer.add(rs.getString(8));
				msg.dataFromServer.add(rs.getString(9));
				msg.dataFromServer.add(rs.getString(10));
				msg.dataFromServer.add(rs.getString(11));
				msg.dataFromServer.add(rs.getString(12));

			}

			System.out.println("in echo server get(10) = "+Integer.parseInt((String) msg.dataFromServer.get(10)));

			if(Integer.parseInt((String) msg.dataFromServer.get(10))==1)
				msg.dataFromServer.add(1);


			else
			{
				msg.dataFromServer.add(0);
				stmt = conn.createStatement();
				int rs1 = stmt.executeUpdate("UPDATE member SET isLogged = true WHERE MemberID = "+"'"+userName+"';");
				System.out.println("UPDATE member SET isLogged = true WHERE MemberID = "+"'"+userName+"';");
			}

			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//msg.setDataFromServer(objectArr);
		System.out.println(msg.dataFromServer.toString());

		return msg;

	}

	/**
	 * getMemberByIdAndGraduation = If a user forgets his password, a request is sent to the server to find the user password by
	 *  identity number and the dGraduation date of his to find his password
	 * @param msg
	 * @param conn
	 * @return
	 */

	public Msg getMemberByIdAndGraduation(Msg msg, Connection conn) {

		ArrayList<Object> objectArr = new ArrayList<>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQuery());
			while(rs.next()) {
				msg.dataFromServer.add(rs.getString(1));
				msg.dataFromServer.add(rs.getString(2));
				msg.dataFromServer.add(rs.getString(3));
				msg.dataFromServer.add(rs.getString(4));
				msg.dataFromServer.add(rs.getString(5));
				msg.dataFromServer.add(rs.getString(6));
				msg.dataFromServer.add(rs.getString(7));
				msg.dataFromServer.add(rs.getString(8));
				msg.dataFromServer.add(rs.getString(9));
				msg.dataFromServer.add(rs.getString(10));
				msg.dataFromServer.add(rs.getString(11));
				msg.dataFromServer.add(rs.getString(12));
			}
			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//msg.setDataFromServer(objectArr);
		System.out.println(msg.dataFromServer.toString());
		return msg;


	}
	/**
	 * searchBook = A query is sent to the DB to
	 *  Search Book by the four search options offered
	 * @param msg
	 * @param conn
	 * @return
	 */

	public Msg searchBook(Msg msg, Connection conn) {
		//	String search;

		ArrayList<Object> objectArr = new ArrayList<>();
		Statement stmt;
		try {
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(msg.getQuery());
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			while(rs.next()) {
				msg.result = true;
				Object obj = createObject(rs);
				System.out.println(obj.toString());
				msg.dataFromServer.add(obj);

			}
			if(!(msg.result))
			{
				Book nullBook =new Book();			
				msg.dataFromServer.add(nullBook);
			}

			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return msg;



	}
	/**
	 * updataMemberPhoneANDEmail=Update user information by the new data entering 
	 * @param msg
	 * @param conn
	 * @return
	 */

	public Msg updataMemberPhoneANDEmail(Msg msg, Connection conn) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(msg.getQuery());
			msg.result=true;
			msg.dataFromServer.add(msg.dataToServer.get(0));
			msg.dataFromServer.add(msg.dataToServer.get(1));


		}  catch (SQLException e) {
			e.printStackTrace();
			msg.result=false;
		}

		return msg;

	}
	/**
	 * searchBookFromController = Search DB to find books
	 * @param msg
	 * @param conn
	 * @return
	 */

	public Msg searchBookFromController(Msg msg, Connection conn) {

		ArrayList<Object> objectArr = new ArrayList<>();
		Statement stmt;
		try {
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(msg.getQuery());
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			while(rs.next()) {
				msg.result = true;
				Object obj = createObject(rs);
				System.out.println(obj.toString());
				msg.dataFromServer.add(obj);

			}
			if(!(msg.result))
			{
				Book nullBook =new Book();			
				msg.dataFromServer.add(nullBook);
			}

			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return msg;


	}
	/**
	 * MakeOrderBook = order a book according to several available tests  
	 * @param msg
	 * @param conn
	 * @return
	 */
	@SuppressWarnings("finally")
	public Msg MakeOrderBook(Msg msg, Connection conn) {
		Statement stmt;
		try {
			ArrayList<String> objectArr = new ArrayList<String>();//temp
			msg.result = false;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQuery());
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			int numberOfBookCurrentlyInOrder=0;
			Book orderbook=(Book) msg.dataToServer.get(0);
			while(rs.next()) {
				objectArr.add(rs.getString(1));//delete
				objectArr.add(rs.getString(2));
			}
			System.out.println(objectArr.size());
			numberOfBookCurrentlyInOrder = objectArr.size()/2;
			System.out.println(numberOfBookCurrentlyInOrder);
			if (objectArr.contains(msg.dataToServer.get(1)))
			{
				msg.dataFromServer.add("You allready in queue ");
				System.out.println("from if");
			}
			else
			{
				if(numberOfBookCurrentlyInOrder <orderbook.getQuantity() )
				{   
					msg.result = true;
					String memberID=(String)msg.dataToServer.get(1);
					numberOfBookCurrentlyInOrder++;
					msg.dataFromServer.add(numberOfBookCurrentlyInOrder);
					String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
					String newQuery="INSERT INTO orders (memberID, idbook, positionInLine, queueentryDate) VALUES(" +"'"+ memberID+"'"+","+"'"+ orderbook.getIdbook() +"'"+","+numberOfBookCurrentlyInOrder +","+"'"+ timeStamp +"');";
					stmt = conn.createStatement();
					int result = stmt.executeUpdate(newQuery);
				}
				else {
					msg.dataFromServer.add("We are sorry, all books were ordered.\nPlease try again later.");
				}
			}
			rs.close();
		}
		catch (SQLException e) {
			System.out.println("from exeption");
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		finally {
			return msg;
		}



	}

	/**
	 * getEmployeByWorkerNumberWorkerNumber - Check DB if the employee exists to login to the system
	 * @param msg
	 * @param conn
	 * @return
	 */

	public Msg getEmployeByWorkerNumberWorkerNumber(Msg msg, Connection conn) {

		ArrayList<Object> objectArr = new ArrayList<>();
		Statement stmt;
		boolean tmp;
		System.out.println("IN WORKER LOGIN ECHO");

		String userName=(String) msg.dataToServer.get(0);
		System.out.println(userName);
		try {
			msg.result=true;
			stmt = conn.createStatement();
			System.out.println("BEFORE EXECUTE");
			ResultSet rs = stmt.executeQuery(msg.getQuery());
			System.out.println(msg.getQuery());
			tmp=rs.next();
			System.out.println(tmp);
			if(tmp==false)
			{
				System.out.println("RS.NEXT=FALSE");
				msg.result=false;
				rs.close();
				return msg;

			}
			rs.previous();
			System.out.println("NOW ENTER WHILE");
			while(rs.next()) {
				msg.dataFromServer.add(rs.getString(1));
				msg.dataFromServer.add(rs.getString(2));
				msg.dataFromServer.add(rs.getString(3));
				msg.dataFromServer.add(rs.getString(4));
				msg.dataFromServer.add(rs.getString(5));
				msg.dataFromServer.add(rs.getString(6));
				msg.dataFromServer.add(rs.getString(7));
				msg.dataFromServer.add(rs.getString(8));
				msg.dataFromServer.add(rs.getString(9));
				msg.dataFromServer.add(rs.getString(10));

			}
			System.out.println(msg.dataFromServer.get(9).toString());
			System.out.println("in echo server get(7) = "+Integer.parseInt((String) msg.dataFromServer.get(7)));

			if(Integer.parseInt((String) msg.dataFromServer.get(7))==1)
				msg.dataFromServer.add(1);


			else
			{
				msg.dataFromServer.add(0);
				stmt = conn.createStatement();
				int rs1 = stmt.executeUpdate("UPDATE employe SET isLogged = true WHERE WorkerNumber = "+"'"+userName+"';");
				System.out.println("UPDATE empolye SET isLogged = true WHERE WorkerNumber = "+"'"+userName+"';");
			}

			rs.close();

		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			msg.result=false;
			e.printStackTrace();

		}

		//msg.setDataFromServer(objectArr);
		System.out.println(msg.dataFromServer.toString());
		return msg;


	}
	/**
	 * getMembers - search in DB for a member 
	 * @param msg
	 * @param conn
	 * @return
	 */
	public Msg getMembers(Msg msg, Connection conn){

		Statement stmt;
		msg.result=true;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQuery());
			while(rs.next()) {
				Object obj = createObjectMember(rs);
				System.out.println(obj.toString());
				msg.dataFromServer.add(obj);

			}
			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			msg.result=false;
			e.printStackTrace();
		}

		//msg.setDataFromServer(objectArr);
		System.out.println(msg.dataFromServer.toString());
		return msg;


	}

	/**
	 * loanaBook - add a loan to DB 
	 * @param newMsg
	 * @param con
	 * @return
	 * @throws SQLException 
	 */

	public Msg loanaBook(Msg newMsg, Connection con) throws SQLException  {
		Statement stmt;
		int result=1;
		String tagged=null; 
		newMsg.result=true;
		int date=Integer.parseInt(newMsg.dataToServer.get(2).toString());
		System.out.println(date);
		stmt = con.createStatement();
		System.out.println("loanaBook from server");
		Object obj=newMsg.dataToServer.get(0);
		String idBook=(String)obj;
		System.out.println(idBook);
		String newQuery="select Tagged from books where idbook='"+idBook+"';";
		System.out.println(newQuery);
		ResultSet rs = stmt.executeQuery(newQuery);		
		while(rs.next()) {
			newMsg.dataFromServer.add(rs.getString(1));
			tagged=rs.getString(1);}
		if(!((tagged.equals("wanted")) & date>3) ){
			stmt = con.createStatement();
			result = stmt.executeUpdate(newMsg.getQuery());
		}


		if (result==0) {
			newMsg.result=false;
		}

		rs.close();
		return newMsg;

	}	

	/**
	 * loanaBook - add a loan to DB 
	 * @param newMsg
	 * @param con
	 * @return
	 * @throws SQLException 
	 */

	public Msg loanaBook1(Msg newMsg, Connection con) throws SQLException  {
		Statement stmt;
		String if_member_wait=null;
		int result=1,idloan=0;
		int CurrentLoan = 0;
		String tagged=null,newQuery=null; 
		newMsg.result=true;
		int date=Integer.parseInt(newMsg.dataToServer.get(1).toString());
		System.out.println(date);
		stmt = con.createStatement();
		System.out.println("loanaBook from server");
		Object obj=newMsg.dataToServer.get(0);

		Loan newloan=(Loan)obj;

		ResultSet rs = stmt.executeQuery(newMsg.getQuery());		
		while(rs.next()) {
			newMsg.dataFromServer.add(rs.getString(1));
			tagged=rs.getString(1);}

		newQuery="insert into loans (MemberID,idbook,copyid,IssueDate,ReturnDate,rimenderSent) values ( '"+newloan.getMemberID()+"','"+newloan.getIdbook()+"','"+newloan.getCopyid()+"','"+newloan.getIssueDate().toString()+"','"+newloan.getReturnDate().toString()+"',0)";
		System.out.println(newQuery);
		if(!((tagged.equals("wanted")) & date>3) ){
			stmt = con.createStatement();
			result = stmt.executeUpdate(newQuery);
			if(result==1) {
				newQuery="SELECT currentlyonloan FROM books WHERE idbook = "+"'"+newloan.getIdbook()+"';";
				System.out.println(newQuery);
				stmt = con.createStatement();
				rs = stmt.executeQuery(newQuery);
				while(rs.next())
					CurrentLoan=rs.getInt(1);
				CurrentLoan++;
				newQuery="UPDATE books SET currentlyonloan = "+"'"+CurrentLoan+"' "+"WHERE idbook = "+"'"+newloan.getIdbook()+"';" ;
				stmt = con.createStatement();
				result = stmt.executeUpdate(newQuery);
				System.out.println(newQuery);
				newQuery="UPDATE copys SET status = 'loan' WHERE copyid='"+ newloan.getCopyid()+"';";
				stmt = con.createStatement();
				result = stmt.executeUpdate(newQuery);
				newQuery="SELECT memberID FROM orders WHERE idbook='"+newloan.getIdbook()+"' AND memberID='"+newloan.getMemberID()+"';";
				stmt = con.createStatement();
				rs = stmt.executeQuery(newQuery);
				while(rs.next())
					if_member_wait=rs.getString(1);

				if(if_member_wait!=null)
				{
					newQuery="DELETE FROM orders WHERE memberID = '"+newloan.getMemberID()+"' AND idbook = '"+newloan.getIdbook()+"';" ;
					stmt = con.createStatement();
					result = stmt.executeUpdate(newQuery);

				}
				if (result==0) {
					newMsg.result=false;
				}

			}



			if (result==0) {
				newMsg.result=false;
			}

			rs.close();

		}
		return newMsg;
	}	

	/**
	 * ReturnBook - return a book , adding to DB to returns table and removing from loans table
	 * @param newMsg
	 * @param con
	 * @return
	 */

	public Msg ReturnBook(Msg newMsg, Connection con) {


		Statement stmt;
		newMsg.result=true;
		int loanid=0,result=0,counter=0,actualReturn,aReturnDay=0,aReturnMount=0,aReturnYear=0,CurrentLoan = 0;
		String MemberID=null,idbook=null,status=null,ReturnDate=null,copyid=null,newQuery;
		Date IssueDate1=null,actualReturn1=null;
		Date ReturnDate1=null;
		String invitingID=null,email=null,bookName=null;
		String bookStatus="available";
		aReturnDay=Integer.parseInt(newMsg.dataToServer.get(3).toString().substring(8, 10));
		aReturnMount=Integer.parseInt(newMsg.dataToServer.get(3).toString().substring(4, 6));
		aReturnYear=Integer.parseInt(newMsg.dataToServer.get(3).toString().substring(0, 4));
		actualReturn1=new Date(aReturnYear,aReturnMount,aReturnDay);
		System.out.println("Before Query 1");
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(newMsg.getQuery());
			while(rs.next()) {
				loanid= rs.getInt(1);
				MemberID=rs.getString(2);
				idbook=rs.getString(3);
				copyid=rs.getString(4);
				IssueDate1=rs.getDate(5);
				ReturnDate1=rs.getDate(6);
			}


			System.out.println(actualReturn1.toString());
			boolean after=actualReturn1.after(ReturnDate1);
			System.out.println(after);
			System.out.println(actualReturn1.equals(ReturnDate1)); 
			newQuery="select member.memberID,member.email,books.bookname  FROM orders INNER JOIN member ON member.MemberID=orders.MemberID INNER JOIN books on orders.idbook = books.idbook where orders.idbook='"+idbook+"' AND orders.positionInLine=1;";
			stmt = con.createStatement();
			System.out.println(newQuery);
			ResultSet  result1 = stmt.executeQuery(newQuery);
			while(result1.next())
			{
				invitingID = result1.getString(1);
				email = result1.getString(2);
				bookName = result1.getString(3);
			}
			System.out.println(invitingID+email+bookName);
			if(invitingID!=null)
			{
				bookStatus="isSaved";
				newQuery="update orders set expiryDate = DATE_ADD(CURDATE(), interval 2 day)  where memberId = '"+invitingID+"' AND idbook='"+idbook+"';";
				System.out.println(newQuery);
				stmt = con.createStatement();
				stmt.executeUpdate(newQuery);
				System.out.println(email);
				sendemail(email, "your order is here", "your copy of "+bookName+"is wating for you in the labrray for 2 days");
				newQuery="update orders set positionInLine = positionInLine-1  where idbook='"+idbook+"';";
				System.out.println(newQuery);
				stmt = con.createStatement();
				stmt.executeUpdate(newQuery);
			}
			/*	if(after)  {
				System.out.println(after+"if ");
				newQuery="UPDATE member set counter=counter+1 where MemberID='"+MemberID+"' ";
				System.out.println(newQuery);
				stmt = con.createStatement();
				result = stmt.executeUpdate(newQuery);
				if(result==1) {
					newQuery="SELECT counter FROM member where MemberID='"+MemberID+"';";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(newQuery);
					while(rs.next()) {
						counter= rs.getInt(1);
					}
					if(counter==3) {
						newQuery="UPDATE member SET Status = 'Block' where MemberID='"+MemberID+"';";
						System.out.println(newQuery);
						stmt = con.createStatement();
						result = stmt.executeUpdate(newQuery);
						if(result==1) {
							newMsg.dataFromServer.add("Block");
						}

					}


				}


			}*/
			newQuery="insert into returns (idreturn,idbook,MemberID,copyid,issueDate,returnDate,actualReturnDate) values ('"+loanid+"','"+ idbook+"','"+MemberID +"','"+ copyid+"','"+ IssueDate1.toString()+"','"+ReturnDate1.toString() +"','"+newMsg.dataToServer.get(3).toString() +"');";	
			System.out.println(newQuery);
			stmt = con.createStatement();
			result = stmt.executeUpdate(newQuery);
			if(result==1) {
				newQuery="DELETE FROM loans WHERE idloan="+loanid+";";
				stmt = con.createStatement();
				result = stmt.executeUpdate(newQuery);
				if(result==1) {
					newQuery="SELECT currentlyonloan FROM books WHERE idbook = "+"'"+idbook+"';";
					System.out.println(newQuery);
					stmt = con.createStatement();
					rs = stmt.executeQuery(newQuery);
					while(rs.next())
						CurrentLoan=rs.getInt(1);
					CurrentLoan--;
					newQuery="UPDATE books SET currentlyonloan = "+"'"+CurrentLoan+"' "+"WHERE idbook = "+"'"+idbook+"';" ;
					stmt = con.createStatement();
					result = stmt.executeUpdate(newQuery);
					newQuery="UPDATE copys SET status = '"+bookStatus+"' WHERE copyid='"+ copyid+"';";

					stmt = con.createStatement();
					result = stmt.executeUpdate(newQuery);
					if (result==0) {
						newMsg.result=false;
					}

				}

				else {
					newMsg.result=false;
				}


			}
			else {

				newMsg.result=false;
			}
			result1.close();
			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			newMsg.result=false;
			e.printStackTrace();
		}

		newMsg.dataFromServer.add("Active");

		System.out.println(newMsg.dataFromServer.toString());
		return newMsg;


	}

	/**
	 * getAllActivitiesMember = Send a request to DB to import all returns and orders for the user who sent the request
	 * @param newMsg
	 * @param con
	 * @return
	 */

	public Msg getAllActivitiesMember(Msg newMsg, Connection con) {

		Statement stmt;
		int counterLoans=0,counterReturns=0;
		String newQuery=null;
		newMsg.result=true;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(newMsg.getQuery());
			while(rs.next()) {
				Object obj = createObjecLoanForActivitieLoan(rs);
				counterLoans++;
				System.out.println(obj.toString());
				newMsg.dataFromServer.add(obj);

			}
			//newQuey="SELECT * FROM returns where MemberID='"+newMsg.dataToServer.get(0).toString() +"';";
			newQuery="select  returns.issueDate,returns.returnDate,returns.actualReturnDate,books.bookname FROM returns INNER JOIN books on returns.idbook = books.idbook where returns.MemberID='"+ newMsg.dataToServer.get(0).toString()+"'";
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(newQuery);
				while(rs.next()) {
					Object obj = createObjecReturnForActivitieReturn(rs);
					counterReturns++;
					System.out.println(obj.toString());
					newMsg.dataFromServer.add(obj);

				}

				rs.close();
			}

			catch (SQLException e) {
				// TODO Auto-generated catch block
				newMsg.result=false;
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			newMsg.result=false;
			e1.printStackTrace();
		}
		finally {
			newMsg.dataFromServer.add(counterLoans);
			newMsg.dataFromServer.add(counterReturns);
			System.out.println(counterLoans);
			System.out.println(counterReturns);

			//msg.setDataFromServer(objectArr);
			System.out.println(newMsg.dataFromServer.toString());

			return newMsg;

		}



	}
	/**
	 * extendloan = Update db extension question of book
	 * @param newMsg
	 * @param con
	 * @return
	 */

	public Msg extendloan(Msg newMsg, Connection con) {

		Statement stmt;
		newMsg.result=true;
		int positionInLine=0,aReturnDay=0,aReturnMount=0,aReturnYear=0,result=0;
		String newQuery=null;
		String returnDateUpdate ;  
		Date newReturnDate=null;
		System.out.println("In server extendloan ");
		Calendar c = Calendar.getInstance();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(newMsg.getQuery());
			while(rs.next()) {
				positionInLine = rs.getInt(1);
				System.out.println(positionInLine);

			}

			if(positionInLine==0) {
				Loan extenLoan=(Loan)newMsg.dataToServer.get(0);

				newQuery="SELECT ReturnDate FROM obl.loans where idloan="+extenLoan.getIdloan()+";";
				System.out.println(newQuery);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(newQuery);
				while(rs.next()) {
					newReturnDate = rs.getDate(1);

				}
				System.out.println(newReturnDate.toString());
				c.setTime(newReturnDate);
				c.add(Calendar.DATE, 7);
				System.out.println(c.getTime());

				SimpleDateFormat toServer = new SimpleDateFormat("yyyy-MM-dd");
				returnDateUpdate = toServer.format(c.getTime());

				newQuery="UPDATE loans SET ReturnDate = '"+returnDateUpdate+"' where idloan="+extenLoan.getIdloan()+";";
				// newQuery="UPDATE loans SET ReturnDate = '"+localDate.toString()+"' where idloan="+extenLoan.getIdloan()+";";
				stmt = con.createStatement();
				result = stmt.executeUpdate(newQuery);
				newMsg.dataFromServer.add(extenLoan);
				newMsg.dataFromServer.add(returnDateUpdate);
				if(result==0) {
					newMsg.result=false;

				}
			}
			else {
				newMsg.result=false;
				String msgtoclient="Other people are waiting for the requested book,\nThe extension of the loan was rejected ";
				newMsg.dataFromServer.add(msgtoclient);
			}


			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			newMsg.result=false;
			e.printStackTrace();
		}

		//msg.setDataFromServer(objectArr);
		System.out.println(newMsg.dataFromServer.toString());
		return newMsg;
	}

	/**
	 * addNewMember = adding a new member to the DB
	 * @param msg
	 * @param conn
	 * @return
	 */
	public Msg addNewMember(Msg msg, Connection conn) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(msg.getQuery());
			msg.result=true;
			msg.dataFromServer.add(msg.dataToServer.get(0));
		}  catch (SQLException e) {
			e.printStackTrace();
			msg.result=false;
		}

		return msg;

	}
	/**
	 * sendingReminder= sending Email to member to remind him about a book to return or late in return , or to 
	 * inform him about his orders   
	 * @param msg
	 * @param conn
	 */

	public void sendingReminder(Msg msg, Connection conn) {
		String from="OBL library";
		String Subject="";
		String body="";
		String MemberID, firstName, lastName, email, status, idBook, issueDate;
		Date returnDate;
		String bookName;
		Statement stmt,stmt1;
		String updateQuery;
		int numberOfLateRetruns,idLoan,graduationyear;
		boolean flag=false;
		Calendar c = Calendar.getInstance(); 
		Calendar s = Calendar.getInstance(); 
		boolean reminder=false;

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("delete from message where dateToDelete = CURDATE();");
			stmt = conn.createStatement();

			stmt.executeUpdate("delete from orders where expiryDate = DATE_ADD(CURDATE(), interval 1 day);");
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select member.MemberID,member.Graduationyear  FROM member ;");

			while(rs.next())
			{

				MemberID=rs.getString(1);
				graduationyear=rs.getInt(2);
				System.out.println(MemberID);
				if(graduationyear < s.get(Calendar.YEAR))
				{
					flag = true;
					System.out.println("from if");
					stmt = conn.createStatement();
					ResultSet rs1 = stmt.executeQuery("select MemberID FROM loans where MemberID='"+MemberID+"';");
					if(rs1.next() == false)
						System.out.println("empty");
					if(rs1.next() == true)
						System.out.println("NOTempty");
					try {
						rs1.getString(1);
					}
					catch(SQLException e)
					{
						System.out.println("from if1");
						stmt1 = conn.createStatement();
						updateQuery= "update member set Status = 'Block' where MemberID ="+"'"+MemberID+"';";
						stmt1.executeUpdate(updateQuery);
						flag=false;
					}
					if(flag)
					{
						System.out.println("from else");
						stmt1 = conn.createStatement();
						updateQuery= "update member set Status = 'Frozen' where MemberID ="+"'"+MemberID+"';";
						stmt1.executeUpdate(updateQuery);

					}
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select member.MemberID,member.FirstName,member.LastName,member.Email,member.Status,loans.idbook,loans.IssueDate,loans.ReturnDate,books.bookname,loans.rimenderSent,loans.idloan,member.Graduationyear  FROM member INNER JOIN loans ON member.MemberID=loans.MemberID INNER JOIN books on loans.idbook = books.idbook;");
			while(rs.next()) {
				MemberID=rs.getString(1);
				firstName=rs.getString(2);
				lastName=rs.getString(3);
				email=rs.getString(4);
				status=rs.getString(5);
				idBook =rs.getString(6);
				issueDate=rs.getString(7);
				returnDate=rs.getDate(8);
				bookName = rs.getString(9);
				reminder = rs.getBoolean(10);
				idLoan = rs.getInt(11);
				graduationyear = rs.getInt(12);
				System.out.println(MemberID);
				c.setTime(returnDate); 
				c.add(Calendar.DATE, -1);
				if(c.get(Calendar.YEAR) == s.get(Calendar.YEAR)
						&& c.get(Calendar.MONTH) == s.get(Calendar.MONTH)
						&& c.get(Calendar.DAY_OF_YEAR) == s.get(Calendar.DAY_OF_YEAR))
				{

					Subject = "friendly reminder about the book " + bookName;
					body="Hello "+firstName +" "+ lastName + " you are required to return the book "+ bookName+" in 1 days. \nThank you\nlibrray stuff.";
					System.out.println(body);
					sendemail(email,Subject,body);

				}
				c.setTime(returnDate);
				System.out.println(c.getTime());
				System.out.println(s.getTime());
				if(((c.get(Calendar.YEAR) < s.get(Calendar.YEAR))
						||(c.get(Calendar.YEAR) == s.get(Calendar.YEAR)	& c.get(Calendar.MONTH) < s.get(Calendar.MONTH))
						||(c.get(Calendar.YEAR) == s.get(Calendar.YEAR)	& c.get(Calendar.MONTH) == s.get(Calendar.MONTH) & c.get(Calendar.DAY_OF_YEAR) < s.get(Calendar.DAY_OF_YEAR))
						)&(!reminder))

				{
					Subject = "you are late in return the book" + bookName;
					body="Hello "+firstName +" "+ lastName + " you are late in return of the book "+ bookName+" and your membership is now frozen\nLibrray stuff.";
					stmt1 = conn.createStatement();
					updateQuery= "update member set Status = 'Frozen',counter = counter+1 where MemberID ="+"'"+MemberID+"';";
					stmt1.executeUpdate(updateQuery);
					System.out.println(body);
					updateQuery= "update loans set rimenderSent = 1 where idloan="+"'"+idLoan+"';";
					stmt1.executeUpdate(updateQuery);

					sendemail(email,Subject,body);

				}
			}}
		catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MemberID,FirstName,lastName,email,counter FROM member where counter = 3;");
			while(rs.next()) {
				MemberID=rs.getString(1);
				firstName=rs.getString(2);
				lastName=rs.getString(3);
				email=rs.getString(4);
				numberOfLateRetruns = rs.getInt(5);
				if(numberOfLateRetruns >= 3)
				{
					stmt1 = conn.createStatement();
					updateQuery= "update member set Status = 'Block' where MemberID ="+"'"+MemberID+"';";
					stmt1.executeUpdate(updateQuery);

					Subject = "you account are lock";
					body="Hello "+firstName +" "+ lastName + " your account is lock due to excessive late returns and your membership is now block\nLibrray stuff.";
					stmt1 = conn.createStatement();
					updateQuery= "update member set Status = 'Block' where MemberID ="+"'"+MemberID+"';";
					stmt1.executeUpdate(updateQuery);

					sendemail(email,Subject,body);

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sendemail(String to, String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", "oblprojectg16");
		props.put("mail.smtp.password", "123456789Aa!@");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress("oblprojectg16"));
			InternetAddress[] toAddress = new InternetAddress[1];

			// To get the array of addresses

			toAddress[0] = new InternetAddress(to);


			message.addRecipient(Message.RecipientType.TO, toAddress[0]);


			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, "oblprojectg16", "123456789Aa!@");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (AddressException ae) {
			ae.printStackTrace();
		}
		catch (MessagingException me) {
			me.printStackTrace();
		}
	}


	/**
	 * upadteMemberStatus= update status if member is late or graduate 
	 * @param msg
	 * @param con
	 */

	public void upadteMemberStatus(Msg msg, Connection con) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(msg.getQuery());
			msg.result=true;
		}  catch (SQLException e) {
			e.printStackTrace();
			msg.result=false;
		}
	}
	public Msg RemoveBook(Msg msg, Connection conn) {

		Statement stmt ;
		int rs;
		Book book=(Book)msg.dataToServer.get(0);
		String Remove_copy_query="DELETE FROM copys WHERE idbook ="+"'"+book.getIdbook()+"';";
		System.out.println(Remove_copy_query);
		try {
			stmt = conn.createStatement();
			System.out.println("Execute HERE!!");
			rs = stmt.executeUpdate(msg.getQuery());

			msg.dataFromServer.add(msg.dataToServer.get(0));
			msg.dataFromServer.add(rs);
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(Remove_copy_query);
			System.out.println("AFTER Execute");




		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return msg;
	}

	/**
	 * EditBook = edit books details by management  
	 * @param msg
	 * @param conn
	 * @return
	 */
	public Msg EditBook(Msg msg, Connection conn) {
		Statement stmt;
		int rs,i;
		int isNewQuantityBigger=(int)msg.dataToServer.get(1);
		int oldQuantity=(int)msg.getDataToServer().get(2);
		int newQuantity=(int)msg.getDataToServer().get(3);
		String BookID=(String)msg.getDataToServer().get(4);
		int ToDelete, ToInsert;
		msg.result=true;
		System.out.println("FROM SERVER");
		try {
			stmt = conn.createStatement();
			System.out.println("1st query = "+msg.getQuery());
			rs=stmt.executeUpdate(msg.getQuery());
			msg.dataFromServer.add(msg.dataToServer.get(0));
			msg.dataFromServer.add(rs);
			if(rs==0)
			{
				System.out.println("RESULT = FALSE");	
				msg.result=false;

			}

			if(isNewQuantityBigger==1) 
			{	
				ToInsert=newQuantity-oldQuantity;

				stmt = conn.createStatement();
				for(i=oldQuantity;i<oldQuantity+ToInsert;i++)
				{
					System.out.println("in INSERT LOOP query = "+"INSERT INTO copys (idbook,copyid,status) VALUES ("+"'"+BookID+"','"+BookID+i+"','"+"available');");
					rs=stmt.executeUpdate("INSERT INTO copys (idbook,copyid,status) VALUES ("+"'"+BookID+"','"+BookID+i+"','"+"available');");
				}
			}
			if(isNewQuantityBigger==-1)
			{
				ToDelete=oldQuantity-newQuantity;
				stmt = conn.createStatement();
				for(i=oldQuantity-1;i>=newQuantity;i--)
				{
					System.out.println("in DELETE LOOP query = "+"DELETE FROM copys WHERE copyid = "+"'"+BookID+i+"';");
					rs=stmt.executeUpdate("DELETE FROM copys WHERE copyid = "+"'"+BookID+i+"';");
				}
			}



		}  
		catch(SQLIntegrityConstraintViolationException e)
		{
			msg.dataFromServer.add(0);
			return msg;
		}

		catch (SQLException e) {
			e.printStackTrace();


		}

		return msg;

	}

	/**
	 * AddNewBook = adding a new book to the DB by the management
	 * @param msg
	 * @param conn
	 * @return
	 */
	public Msg AddNewBook(Msg msg, Connection conn) {
		String bookname=(String)msg.getDataToServer().get(0);
		String bookid=(String)msg.getDataToServer().get(1);
		String edition=(String)msg.getDataToServer().get(2);
		String quantity= (String)msg.getDataToServer().get(3);
		byte[] file=(byte[])msg.getDataToServer().get(4);
		String query="SELECT Editionnumber FROM books WHERE bookname = "+"'"+bookname+"'"+";";
		String DupQuery="SELECT idbook FROM books WHERE idbook= '"+bookid+"';";
		String Msg="The book "+bookname+" has been added.";
		//String copy_query="INSERT INTO copys (idbook,copyid,status) VALUES ("+"'"+bookid+"','"+bookid+i+"','"+"available');";
		ArrayList<String> editionArray = new ArrayList<String>();
		System.out.println(query);
		System.out.println(DupQuery);
		Statement stmt;
		ResultSet rs1;
		int rs;
		int flag=0;
		String id=null;
		msg.result=true;
		System.out.println("FROM SERVER");
		try {
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery(DupQuery);
			System.out.println("AFTER DUPQUERY");
			while(rs1.next())
			{
				id=rs1.getString(1);
				System.out.println(id);
			}


			if(id!=null)
			{
				Msg="The ID "+bookid+ " allready exist";
				System.out.println("FROM SERVER SQL EXCEPTION");
				msg.dataFromServer.add(Msg);
				System.out.println(msg.dataFromServer.get(0));
				msg.result=false;
				return msg;
			}


			stmt = conn.createStatement();
			rs1 = stmt.executeQuery(query);
			ResultSetMetaData rsmd = (ResultSetMetaData) rs1.getMetaData();
			while(rs1.next()) {
				editionArray.add(rs1.getString(1));
			}
			for(int i=0;i<editionArray.size();i++)
				if(editionArray.get(i).equals(edition))
				{
					System.out.println("ENTER IF EDITION FLAG=1");
					Msg="The book "+bookname+" already has edition "+edition;
					msg.dataFromServer.add(Msg);
					flag=1;
				}
			if(flag==0)
			{
				for(int i=0;i<Integer.parseInt(quantity);i++)
				{
					System.out.println("now execute copy query");
					//System.out.println(copy_query);
					stmt = conn.createStatement();
					rs = stmt.executeUpdate("INSERT INTO copys (idbook,copyid,status) VALUES ("+"'"+bookid+"','"+bookid+i+"','"+"available');");
					System.out.println("AFTER copy query");

				}
				System.out.println("now execute");
				rs=stmt.executeUpdate(msg.getQuery());

				//msg.dataFromServer.add(msg.dataToServer.get(0));
				if(rs==0)
				{
					Msg="There was a problem adding the book, Please try again later";
					System.out.println("RESULT = FALSE RS=0");	
					msg.result=false;

				}
				else
				{
					bookname.replaceAll("\\s","");
					File temp = new File(System.getProperty("user.dir") +"\\"+bookname+".pdf");
					//System.out.println(Arrays.toString(file));
					try {
						FileOutputStream fos = new FileOutputStream(temp);
						fos.write(file);
						fos.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}


			}

		}  

		catch (SQLException e) {
			Msg="There was a problem adding the book, Please try again later";
			System.out.println("RESULT = FALSE SQL E");	
			msg.result=false;
			msg.dataFromServer.add(Msg);


		}
		msg.dataFromServer.add(Msg);
		return msg;

	}
	/**
	 * setNewValueForMember = update the member details 
	 * @param newMsg
	 * @param con
	 * @return
	 */
	public Msg setNewValueForMember(Msg newMsg, Connection con) {

		Statement stmt;
		newMsg.result=true;
		int result=0;
		try {
			stmt = conn.createStatement();
			result=stmt.executeUpdate(newMsg.getQuery());
			if(result==0) {
				newMsg.result=false;
			}



		}  catch (SQLException e) {
			newMsg.result=false;
			e.printStackTrace();

		}

		return newMsg;
	}
	/**
	 * getLoans = get member's loans 
	 * @param newMsg
	 * @param con
	 * @return
	 */

	public Msg getLoans(Msg newMsg, Connection con) {
		Statement stmt;
		newMsg.result=true;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(newMsg.getQuery());
			while(rs.next()) {
				Object obj = createObjecLoanForActivitieLoan(rs);
				System.out.println(obj.toString());
				newMsg.dataFromServer.add(obj);

			}
			rs.close();
		}
		catch (SQLException e) {
			newMsg.result=false;
			e.printStackTrace();

		}



		return newMsg;
	}
	/**
	 * getBook = get a specific book from the DB to view its details
	 * @param newMsg
	 * @param con
	 * @return
	 */
	private Msg getBook(Msg newMsg, Connection con) {
		String Min_Return_Date=null;
		Statement stmt;
		newMsg.result=true;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(newMsg.getQuery());
			while(rs.next()) {
				Object obj = createObject(rs);
				System.out.println(obj.toString());
				newMsg.dataFromServer.add(obj);

			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT MIN(ReturnDate) FROM loans;");
			while(rs.next()) {
				Min_Return_Date=rs.getString(1);
				newMsg.dataFromServer.add(Min_Return_Date);
			}
			rs.close();
		}
		catch (SQLException e) {
			newMsg.result=false;
			e.printStackTrace();

		}

		return newMsg;
	}

	/**
	 * extendLoanAuto = extend Loan via member's 
	 * @param newMsg
	 * @param con
	 * @return
	 */
	public Msg extendLoanAuto(Msg newMsg, Connection con) {
		Statement stmt;
		newMsg.result=true;
		int positionInLine=0,result=0;
		String newQuery=null;
		String returnDateUpdate ;  

		System.out.println("In server extendloan Auto ");
		Calendar c = Calendar.getInstance();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(newMsg.getQuery());
			while(rs.next()) {
				positionInLine = rs.getInt(1);
				System.out.println(positionInLine);

			}

			if(positionInLine==0) {
				returnDateUpdate=(String)newMsg.dataToServer.get(0);
				Loan extenLoan=(Loan)newMsg.dataToServer.get(1);
				newQuery="UPDATE loans SET ReturnDate = '"+returnDateUpdate+"' where idloan="+extenLoan.getIdloan()+";";
				stmt = conn.createStatement();
				result = stmt.executeUpdate(newQuery);
				newMsg.dataFromServer.add(extenLoan);
				newMsg.dataFromServer.add(returnDateUpdate);
				if(result==0) {
					newMsg.result=false;

				}
				else {
					Member member=(Member)newMsg.dataToServer.get(2);
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
					Calendar dateToDelete = Calendar.getInstance();
					dateToDelete.add(Calendar.DATE,3);
					SimpleDateFormat timeStamp1 = new SimpleDateFormat("yyyy-MM-dd");
					String dateToDeleteMsg = timeStamp1.format(dateToDelete.getTime());
					newQuery="insert into message (senderID,message,dateMsg,dateToDelete,messageRecipient) VALUES ('" +member.getMemberID() +"','The member extended the loan on the book : "+ extenLoan.getBookname()+" to "+ returnDateUpdate+" ','"+ timeStamp+"','"+dateToDeleteMsg+"','Librarian');";
					stmt = conn.createStatement();
					result = stmt.executeUpdate(newQuery);
					if(result==0) {
						newMsg.result=false;

					}
				}
			}
			else {
				newMsg.result=false;
				String msgtoclient="Other people are waiting for the requested book,\nThe extension of the loan was rejected ";
				newMsg.dataFromServer.add(msgtoclient);
			}


			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			newMsg.result=false;
			e.printStackTrace();
		}


		System.out.println(newMsg.dataFromServer.toString());
		return newMsg;
	}
	/**
	 * getMessageToLibrarian= Send a message to the librarian in the event of an extension by the user
	 * @param newMsg
	 * @param con
	 * @return
	 */
	private Msg getMessageToLibrarian(Msg newMsg, Connection con) {
		Statement stmt;
		ResultSet result;
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(newMsg.getQuery());
			while(result.next())
			{
				Object obj = createObjectMessage(result);
				newMsg.dataFromServer.add(obj);
			}
		}
		catch (SQLException e) {
			newMsg.result=false;
			e.printStackTrace();

		}
		return newMsg;

	}
	/**
	 * UpDataLibrarian= Librarian UpData his details
	 * @param newMsg
	 * @param con
	 * @return
	 */
	public Msg UpDataLibrarian(Msg newMsg, Connection con) {
		Statement stmt;
		int result=0;
		newMsg.result=true;
		try {
			stmt = conn.createStatement();
			result=stmt.executeUpdate(newMsg.getQuery());
			if(result==1) {
				newMsg.dataFromServer.add(newMsg.dataToServer.get(0));
			}
			else {
				newMsg.result=false;
			}


		}  catch (SQLException e) {

			newMsg.result=false;
			e.printStackTrace();
		}
		return newMsg;
	}
	/**
	 * setActivityLog =getting all the returns and orders of the member
	 * @param msg
	 * @param con
	 * @return
	 */

	private Msg setActivityLog(Msg msg, Connection con) {
		//	String search;

		ArrayList<Object> objectArr = new ArrayList<>();
		Statement stmt;
		System.out.println(msg.getQuery());
		try {
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(msg.getQuery());
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			msg.result=false;
			while(rs.next()) {
				msg.result = true;
				Object obj = createObject_Returns(rs);
				System.out.println(obj.toString());
				msg.dataFromServer.add(obj);

			}
			if(!(msg.result))
			{
				Return nullReturn =new Return();			
				msg.dataFromServer.add(nullReturn);
			}

			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return msg;



	}
	/**
	 * setActivityLog1  =getting all the returns and orders of the member
	 * @param msg
	 * @param con
	 * @return 
	 */
	public Msg setActivityLog1(Msg msg, Connection con) {
		Statement stmt;
		int returns=0,orders=0;
		try {
			Member member=(Member) msg.dataToServer.get(0);
			String newQuery="select  returns.issueDate,returns.returnDate,returns.actualReturnDate,books.bookname,returns.statusReturn FROM returns INNER JOIN books on returns.idbook = books.idbook where returns.MemberID='"+member.getMemberID() +"';";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(newQuery);
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			msg.result=false;
			while(rs.next()) {
				msg.result=true;
				Object obj = createObject_ReturnsForActivityLog(rs);
				returns++;
				System.out.println(obj.toString());
				msg.dataFromServer.add(obj);

			}
			if(!(msg.result))
			{
				Return nullReturn =new Return();			
				msg.dataFromServer.add(nullReturn);
			}

			newQuery="select  orders.memberID,orders.idbook,orders.positionInLine,books.bookname FROM orders INNER JOIN books on orders.idbook = books.idbook where orders.MemberID='"+member.getMemberID() +"';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(newQuery);
			rsmd = (ResultSetMetaData) rs.getMetaData();
			msg.result=false;
			while(rs.next()) {
				msg.result=true;
				Object obj = createObject_ordersForActivityLog(rs);
				orders++;
				System.out.println(obj.toString());
				msg.dataFromServer.add(obj);

			}
			if(!(msg.result))
			{
				orders nullorders=new orders();			
				msg.dataFromServer.add(nullorders);
			}

			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		msg.dataFromServer.add(returns);
		msg.dataFromServer.add(orders);
		return msg;



	}

	/**
	 * Bring the desired employee from the DB according to the desired search
	 * @param newMsg
	 * @param con
	 * @return
	 */


	public Msg getEmployees(Msg newMsg, Connection con) {


		Statement stmt;
		newMsg.result=true;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(newMsg.getQuery());
			while(rs.next()) {
				Object obj = createObjectEployee(rs);
				System.out.println(obj.toString());
				newMsg.dataFromServer.add(obj);

			}
			rs.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			newMsg.result=false;
		}

		//msg.setDataFromServer(objectArr);
		System.out.println(newMsg.dataFromServer.toString());
		return newMsg;



	}

	/**
	 * UpDataLibrarianInfo = by the manager 
	 * @param newMsg
	 * @param con
	 * @return
	 */

	public Msg UpDataLibrarianInfo(Msg newMsg, Connection con) {
		Statement stmt;
		int result=0;
		newMsg.result=true;
		try {
			stmt = conn.createStatement();
			result=stmt.executeUpdate(newMsg.getQuery());
			if(result==1) {
				System.out.println("result = 1");
				//newMsg.dataFromServer.add(newMsg.dataToServer.get(0));
			}
			else {
				newMsg.result=false;
				System.out.println("msg.result = false");

			}


		}  catch (SQLException e) {

			newMsg.result=false;
			e.printStackTrace();
		}
		System.out.println("RETURN");
		return newMsg;
	}
	/**
	 * getReportType2 = 
	 * @param newMsg
	 * @param con
	 * @return
	 */
	private Msg getReportType2(Msg newMsg, Connection con) {
		Statement stmt;
		ResultSet result;
		ArrayList<Integer> wanted = new ArrayList<Integer>(); 
		ArrayList<Integer> regular = new ArrayList<Integer>(); 
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery((String)newMsg.dataToServer.get(0));
			while(result.next())
			{
				wanted.add(result.getInt(1));	

			}
			newMsg.dataFromServer.add(wanted);
		}
		catch (SQLException e) {
			e.printStackTrace();

		}
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery((String)newMsg.dataToServer.get(1));
			while(result.next())
			{
				regular.add(result.getInt(1));	

			}
			newMsg.dataFromServer.add(regular);
		}
		catch (SQLException e) {
			e.printStackTrace();

		}
		return newMsg;

	}

	private Msg getReportType3(Msg newMsg, Connection con) {
		Statement stmt;
		ResultSet result;
		ArrayList<Integer> genral = new ArrayList<Integer>(); 
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(newMsg.getQuery());
			while(result.next())
			{
				genral.add(result.getInt(1));	

			}
			newMsg.dataFromServer.add(genral);
		}
		catch (SQLException e) {
			e.printStackTrace();

		}
		return newMsg;

	}
	private Msg getReportType3ByBook(Msg newMsg, Connection con) {
		Statement stmt;
		ResultSet result;
		ArrayList<Integer> genral = new ArrayList<Integer>(); 
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(newMsg.getQuery());
			while(result.next())
			{
				genral.add(result.getInt(1));	

			}
			newMsg.dataFromServer.add(genral);
		}
		catch (SQLException e) {
			e.printStackTrace();

		}
		return newMsg;

	}


	private Msg getReportType1(Msg newMsg, Connection con) {
		Statement stmt;
		ResultSet result;
		String query;
		ArrayList<Integer> resulrtarry= new ArrayList<Integer>(); 
		try {
			for (int i = 0; i < newMsg.dataToServer.size(); i++) {

				stmt = conn.createStatement();
				result = stmt.executeQuery((String)newMsg.dataToServer.get(i));
				while(result.next())
				{
					resulrtarry.add(result.getInt(1));	
				}
			}
			newMsg.dataFromServer.add(resulrtarry);

		}

		catch (SQLException e) {
			e.printStackTrace();

		}
		try {
			stmt = conn.createStatement();
			query="INSERT INTO reporttype1(Active,Frozen,Block,totalNumOfMember,activLoans,late,reportCreationDate) VALUES ("+resulrtarry.get(0)+","+resulrtarry.get(1)+","+resulrtarry.get(2)+","+resulrtarry.get(3)+","+resulrtarry.get(4)+","+resulrtarry.get(5)+",CURDATE());";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newMsg;

	}
	private Msg getReportType1ByDate(Msg newMsg, Connection con) {
		Statement stmt;
		ResultSet result;
		String query;
		ArrayList<Integer> resulrtarry= new ArrayList<Integer>(); 
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(newMsg.getQuery());
			while(result.next())
			{
				resulrtarry.add(result.getInt(1));
				resulrtarry.add(result.getInt(2));
				resulrtarry.add(result.getInt(3));
				resulrtarry.add(result.getInt(4));
				resulrtarry.add(result.getInt(5));
				resulrtarry.add(result.getInt(6));

			}
		}
		catch (SQLException e) {
			e.printStackTrace();

		}
		newMsg.dataFromServer.add(resulrtarry);
		for (int i = 0; i < resulrtarry.size(); i++) {
			System.out.println(resulrtarry.get(i));
		}
		return newMsg;

	}

	private Msg getActivityReport(Msg newMsg, Connection con) {
		Statement stmt;
		ResultSet result;

		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(newMsg.getQuery());
			while(result.next())
				newMsg.dataFromServer.add(result.getDate(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return newMsg;
	}

	private Msg getRturnsReport(Msg newMsg, Connection con) {
		Statement stmt;
		ResultSet result;

		try {
			stmt = conn.createStatement();
			System.out.println(newMsg.getQuery());
			result = stmt.executeQuery(newMsg.getQuery());
			while(result.next())
				newMsg.dataFromServer.add(result.getString(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newMsg;
	}

	/**
	 * cancelOrder= At the request of the user to cancel his order, 
	 * and delete from the DB and in the case of the queue on the book  the queue update accordingly
	 * @param newMsg
	 * @param con
	 * @return
	 */

	public Msg cancelOrder(Msg newMsg, Connection con) {

		Statement stmt;
		int result=0;
		newMsg.result=true;
		orders orderToCancel=(orders)newMsg.dataToServer.get(0);
		//	int positionInLine=orderToCancel.getPositionInLine();
		try {

			String newQuery="UPDATE orders SET positionInLine = positionInLine-1 WHERE  idbook='"+orderToCancel.getIdbook() +"' and positionInLine>"+orderToCancel.getPositionInLine()+" ;";
			stmt = conn.createStatement();
			result=stmt.executeUpdate(newQuery);
			System.out.println(result);
			if(result>=0) {
				System.out.println("result = 1");
				//newMsg.dataFromServer.add(newMsg.dataToServer.get(0));
				stmt = conn.createStatement();
				result=stmt.executeUpdate(newMsg.getQuery());
				if(result==0) {

					newMsg.result=false;
					System.out.println("msg.result = false the Order DELET");
				}
			}
			else {
				newMsg.result=false;
				System.out.println("msg.result = false the UPDATE positionInLine ");

			}


		}  catch (SQLException e) {

			newMsg.result=false;
			e.printStackTrace();
		}

		newMsg.dataFromServer.add(orderToCancel);
		System.out.println("RETURN");
		return newMsg;
	}



}
//End of EchoServer class
