package Controllers;

import Entity.Book;
import Entity.Member;
import Gui.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Entity.Msg;

import Client.Command;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.Serializable;
import Client.Main;

/***
 * 
 * Controller class to perform Search Books actions
 *
 */

public class SearchBookController {
	
	/*public void createaBookSearchRequest(String where , String search) throws IOException {
		
		   Msg msg=new Msg("SELECT * FROM books WHERE " +where+ " LIKE " + search+ ";", Command.searchBookFromController);
	            msg.dataToServer.add(where);
	            msg.dataToServer.add(search);
	            where=null;
	            search=null;
	            System.out.println("SERACH"+Thread.currentThread().getId());
	            Main.client.sendToServer(msg);
		
	}*/
	
	
    

	

}
