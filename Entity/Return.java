package Entity;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Return implements Serializable  {

	
	private int idreturn;
	private String idbook;
	private String MemberID;
	private String copyid;
	private Date issueDate;
	private Date returnDate;
	private Date actualReturnDate;
	private String bookname;
	private String statusReturn; 
	  public static ArrayList< Return> ReturnBook=new ArrayList< Return>();

	public static ArrayList<Return> getReturnBook() {
		return ReturnBook;
	}
	public static void setReturnBook(ArrayList<Return> returnBook) {
		ReturnBook = returnBook;
	}
	public Return(Date issueDate, Date returnDate, Date actualReturnDate, String bookname, String statusReturn) {
		super();
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.actualReturnDate = actualReturnDate;
		this.bookname = bookname;
		this.statusReturn = statusReturn;
	}
	public String getstatusReturn() {
		return statusReturn;
	}
	public void setstatusReturn(String statusReturn) {
		this.statusReturn = statusReturn;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public int getIdreturn() {
		return idreturn;
	}
	public void setIdreturn(int idreturn) {
		this.idreturn = idreturn;
	}
	public String getIdbook() {
		return idbook;
	}
	public void setIdbook(String idbook) {
		this.idbook = idbook;
	}
	public String getMemberID() {
		return MemberID;
	}
	public void setMemberID(String memberID) {
		MemberID = memberID;
	}
	public String getCopyid() {
		return copyid;
	}
	public void setCopyid(String copyid) {
		this.copyid = copyid;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Date getActualReturnDate() {
		return actualReturnDate;
	}
	public void setActualReturnDate(Date actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}
	public Return(int idreturn, String idbook, String memberID, String copyid, Date issueDate, Date returnDate,
			Date actualReturnDate) {
		super();
		this.idreturn = idreturn;
		this.idbook = idbook;
		MemberID = memberID;
		this.copyid = copyid;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.actualReturnDate = actualReturnDate;
	}
	public Return() {
		super();
	}
	public Return(Date issueDate, Date returnDate, Date actualReturnDate, String bookname) {
		super();
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.actualReturnDate = actualReturnDate;
		this.bookname = bookname;
	}
	public Return(String bookname, Date issueDate, Date returnDate, String rimenderSent) {
		super();
		this.bookname = bookname;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.statusReturn = rimenderSent;
	}
	public Return(Date issueDate, Date actualReturnDate, String bookname, String rimenderSent) {
		super();
		
		this.issueDate = issueDate;
		this.actualReturnDate = actualReturnDate;
		this.bookname = bookname;
		this.statusReturn = rimenderSent;
	}

	
}
