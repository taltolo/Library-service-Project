package Entity;

import java.io.Serializable;
import java.sql.Date;

public class orders implements Serializable {
	
	
	private String idbook;
	private String MemberID;
	private String statusOrder;
	private Date issueDate;
	private Date returnDate;
	
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
	public String getStatusOrder() {
		return statusOrder;
	}
	public void setStatusOrder(String statusOrder) {
		this.statusOrder = statusOrder;
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
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getStatusReturn() {
		return statusReturn;
	}
	public void setStatusReturn(String statusReturn) {
		this.statusReturn = statusReturn;
	}
	public String getOption() {
		return Option;
	}
	public void setOption(String option) {
		Option = option;
	}
	private Date actualReturnDate;
	private String bookname;
	private String statusReturn; 
	private String Option;
	private int positionInLine;
	public orders(String idbook, String memberID, String statusOrder, Date issueDate, Date returnDate,
			Date actualReturnDate, String bookname, String statusReturn, String option) {
		super();
		this.idbook = idbook;
		MemberID = memberID;
		this.statusOrder = statusOrder;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.actualReturnDate = actualReturnDate;
		this.bookname = bookname;
		this.statusReturn = statusReturn;
		Option = option;
	}
	public orders() {
		// TODO Auto-generated constructor stub
	}
	public orders(String memberID, String idbook, int positionInLine, String bookname) {
		MemberID = memberID;
		this.idbook = idbook;
		this.positionInLine=positionInLine;
		this.bookname = bookname;
		
		
	}
	public orders(String memberID, String idbook, int positionInLine, String bookname,String statusOrder, String option) {
		MemberID = memberID;
		this.idbook = idbook;
		this.positionInLine=positionInLine;
		this.bookname = bookname;
		this.statusOrder=statusOrder;
		Option = option;
		
		
	}
	public int getPositionInLine() {
		return positionInLine;
	}
	public void setPositionInLine(int positionInLine) {
		this.positionInLine = positionInLine;
	} 
	
	

}
