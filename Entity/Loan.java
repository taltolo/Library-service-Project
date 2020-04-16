package Entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Loan implements Serializable{
	
	private int idloan;
	private String MemberID;
	private String idbook;
	private String bookname;
	private String Tagged;
	private String copyid;
	private Date IssueDate;
	private Date ReturnDate;
	private boolean rimenderSent;
	public boolean isRimenderSent() {
		return rimenderSent;
	}
	
	public byte[] pdf;
	public byte[] getPdf() {
		return pdf;
	}
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}
	public Loan(int idloan, String memberID, String idbook, String bookname, String tagged, String copyid,
			Date issueDate, Date returnDate, boolean rimenderSent) {
		super();
		this.idloan = idloan;
		MemberID = memberID;
		this.idbook = idbook;
		this.bookname = bookname;
		Tagged = tagged;
		this.copyid = copyid;
		IssueDate = issueDate;
		ReturnDate = returnDate;
		this.rimenderSent = rimenderSent;
	}
	public Loan(int idloan, String memberID, String idbook, String bookname, String tagged, String copyid,
			Date issueDate, Date returnDate, boolean rimenderSent, String tableofcontents) {
		super();
		this.idloan = idloan;
		MemberID = memberID;
		this.idbook = idbook;
		this.bookname = bookname;
		Tagged = tagged;
		this.copyid = copyid;
		IssueDate = issueDate;
		ReturnDate = returnDate;
		this.rimenderSent = rimenderSent;
		
	}
	public void setRimenderSent(boolean rimenderSent) {
		this.rimenderSent = rimenderSent;
	}
	public int getIdloan() {
		return idloan;
	}
	public void setIdloan(int idloan) {
		this.idloan = idloan;
	}

	public String getMemberID() {
		return MemberID;
	}
	public void setMemberID(String memberID) {
		MemberID = memberID;
	}
	public String getIdbook() {
		return idbook;
	}
	public void setIdbook(String idbook) {
		this.idbook = idbook;
	}
	public String getCopyid() {
		return copyid;
	}
	public void setCopyid(String copyid) {
		this.copyid = copyid;
	}
	public Date getIssueDate() {
		return IssueDate;
	}
	public void setIssueDate(Date issueDate) {
		IssueDate = issueDate;
	}
	public Date getReturnDate() {
		return ReturnDate;
	}
	public void setReturnDate(Date returnDate) {
		ReturnDate = returnDate;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getTagged() {
		return Tagged;
	}
	public void setTagged(String tagged) {
		Tagged = tagged;
	}
	public Loan(int idloan, String bookname, String tagged, Date issueDate, Date returnDate) {
		super();
		this.idloan = idloan;
		this.bookname = bookname;
		Tagged = tagged;
		IssueDate = issueDate;
		ReturnDate = returnDate;
	}
	public Loan(int idloan, String memberID, String idbook, String copyid, Date issueDate, Date returnDate) {
		super();
		this.idloan = idloan;
		MemberID = memberID;
		this.idbook = idbook;
		this.copyid = copyid;
		IssueDate = issueDate;
		ReturnDate = returnDate;
	}
	public Loan() {
		
		
	}
	
	public Loan( String memberID, String idbook, String copyid, Date issueDate, Date returnDate) {
		super();
		
		MemberID = memberID;
		this.idbook = idbook;
		this.copyid = copyid;
		IssueDate = issueDate;
		ReturnDate = returnDate;
	}
	public Loan(int idloan, String memberID, String idbook, String bookname, String tagged, String copyid,
			Date issueDate, Date returnDate) {
		super();
		this.idloan = idloan;
		MemberID = memberID;
		this.idbook = idbook;
		this.bookname = bookname;
		Tagged = tagged;
		this.copyid = copyid;
		IssueDate = issueDate;
		ReturnDate = returnDate;
	}
	
public Loan(String bookname, String tagged, Date issueDate, Date returnDate) {
		
		this.bookname = bookname;
		Tagged = tagged;
		IssueDate = issueDate;
		ReturnDate = returnDate;
	}
public Loan(int idloan, String idbook, String copyid, String bookname, String tagged, Date issueDate,
		Date returnDate) {
	super();
	this.idloan = idloan;
	this.idbook = idbook;
	this.bookname = bookname;
	Tagged = tagged;
	this.copyid = copyid;
	IssueDate = issueDate;
	ReturnDate = returnDate;
}
public Loan(int idloan, String memberID, String idbook, String copyid, Date issueDate, Date returnDate,boolean isreimend) {
	super();
	this.idloan = idloan;
	MemberID = memberID;
	this.idbook = idbook;
	this.copyid = copyid;
	IssueDate = issueDate;
	ReturnDate = returnDate;
	this.rimenderSent = isreimend;
}



}
