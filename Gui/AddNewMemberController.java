package Gui;

import Client.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddNewMemberController {

    @FXML
    private AnchorPane AnchSerchBook;

    @FXML
    private Label AddNewMemberLabel;

    @FXML
    private Button saveBnt;

    @FXML
    private GridPane Gridpane;

    @FXML
    private Label FirstnameLabel;

    @FXML
    private Label LastnameLabel;

    @FXML
    private Label IDLabel;

    @FXML
    private Label EmailLabel;

    @FXML
    private Label PhonenumberLabel;

    @FXML
    private Label GraduationyearLabel;

    @FXML
    private TextField FirstnameField;

    @FXML
    private TextField LastnameFiled;

    @FXML
    private TextField IDField;

    @FXML
    private TextField EmailField;

    @FXML
    private TextField PhonenumberField;

    @FXML
    private TextField GraduationyearField;

    @FXML
    private Button Cancelbnt;
    
	public static Stage AddNewMemberStage;

	@FXML
	void CancelbntHandler(ActionEvent event) {

		Main.client.addNewMemberController.AddNewMemberStage.close();

	}

	public static boolean validateName( String name )
	{
		return name.matches( "^[A-Z]{1}[a-z]+$" );
	} 



	@FXML
	void saveBntHandler(ActionEvent event) {
		boolean input = false;
		String errorMsg = "The following fields are wrong:";
		String firstName = FirstnameField.getText();
		String lastName = LastnameFiled.getText();
		String ID =IDField.getText();
		String email = EmailField.getText();
		String phone = PhonenumberField.getText();
		String Graduationyear = GraduationyearField.getText();
		int GraduationyearInt=0;
		if (!validateName(firstName))
		{
			errorMsg=errorMsg+"\nFirst name ";
			input=true;
		}
		if (!validateName(lastName))
		{
			errorMsg=errorMsg+"\nlast name";
			input=true;
		}
		if (!phone.matches("^[0][5][0-9]{8}"))
		{
			if(!phone.matches("^[0][2,3,4,8,9][0-9]{7}"))
				errorMsg=errorMsg+"\nphone number";
			input=true;

		}
		if (!ID.matches("[0-9]{7,9}"))
		{
			errorMsg=errorMsg+"\nid number";
			input=true;
		}
		if(!email.matches("^[a-zA-Z0-9.!#$%&'*+=?^_`\\{|\\}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"))
		{
			errorMsg=errorMsg+"\nemail";
			input=true;
		}
		try
		{
			GraduationyearInt = Integer.parseInt(Graduationyear);
			if(Graduationyear.contains("^[2][0][0-9]{2}"))
				throw new NumberFormatException();
		}
		catch (NumberFormatException e)
		{
			errorMsg=errorMsg+"\nGraduation year";
			input = true;
		}
		finally {
			if(input){
				errorMsg=errorMsg+".";
				displayAlert(Alert.AlertType.ERROR,"Add new member", "ERROR Message ", errorMsg);
			}
			else {
				Main.librariancontroller.addNewMember(firstName,lastName,ID,ID,email,phone,GraduationyearInt);

			}
		}
	}	
	/**
	 * Show an Alert dialog with custom info
	 * @param type type alert
	 * @param title title window
	 * @param header header of the message
	 * @param content message
	 */
	public  void displayAlert(AlertType type , String title , String header , String content)
	{

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = new Alert(type);
				alert.setTitle(title);
				alert.setHeaderText(header);
				alert.setContentText(content);
				alert.showAndWait();
			}
		});
	}



	public void initialize() {
		Main.client.addNewMemberController=this;
	}

}
