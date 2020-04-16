package Entity;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * Member id
	 */
	private String MemberID;
	/**
	 * Member Last name
	 */
	private String fullName;
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * Member First name
	 */
	private String message;
	
	
	public Message(String memberID, String lastname, String firstname, String message) {
		super();
		MemberID = memberID;
		fullName = firstname+" "+lastname;
		
		this.message = message;
	}
	public String getMemberID() {
		return MemberID;
	}
	public void setMemberID(String memberID) {
		MemberID = memberID;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
