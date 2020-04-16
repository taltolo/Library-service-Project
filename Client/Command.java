package Client;
import java.io.Serializable;


/**
 * Uses for hands all the commands that will use for client requests and server handling
 *
 */
public enum Command implements Serializable
{
	getMembers,
	getMemberByuId,
	getMemberByIdAndPass,
	getBookByName,
	getBookByAauthor,
	getBookByGenre,
	getBookByText,
	getPassByMemberID,
	getMemberByIdAndGraduation,
	searchBook,
	updataMemberPhoneANDEmail,
	searchBookFromController,
	MakeOrderBook,
	getEmployeByWorkerNumberWorkerNumber,
	loanaBook,
	loanaBook1,
	ReturnBook,
	getAllActivitiesMember,
	extendloan,
	addNewMember,
	automatedRun,
	upadteMemberStatus, 
	RemoveBook, 
	EditBook,
	AddNewBook, 
	searchBookFromInventory,
	setNewValueForMember,
	searchBookFromControllerLibrarian,
	getLoans,
	getBook,
	permission0,
	permission1,
	permission2,
	permission3,
	extendLoanAuto,
	getMessageToLibrarian,
	UpDataLibrarian, 
	ActivityLog,
	getEmployees,
	logout,
	UpDataLibrarianInfo,
	getReportType2,
	getReportType3,
	getReportType3ByBook,
	getReportType1,
	getActivityReport,
	getRturnsReport,
	getReportType1ByDate,
	cancelOrder,

	

}
