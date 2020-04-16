
	package Gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import Entity.Msg;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * 
 * this class open and generate an activity report in a shape of  bar chart.
 *
 */
public class Type1report   extends Application{

    @FXML
    private AnchorPane AnchorHome;

    @FXML
    private ImageView Image;
 
Msg newmsg;
 public Type1report(Msg newmsg) {
	super();
	this.newmsg = newmsg;
}

    @Override public void start(Stage stage) {
    	double median;
    	ArrayList<XYChart.Series> temp = new ArrayList<XYChart.Series>();
		ArrayList<Integer> dataFromServer = new ArrayList<Integer>(); 
		double avg=0;
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String,Number> bc = 
				new BarChart<String,Number>(xAxis,yAxis);
		bc.setTitle("landing time");
		xAxis.setLabel("book type");       
		yAxis.setLabel("time(in days)");
dataFromServer=(ArrayList<Integer>) newmsg.dataFromServer.get(0);
	
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Active");    
		series1.getData().add(new XYChart.Data("Member Status", dataFromServer.get(0)));
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Block");    
		series2.getData().add(new XYChart.Data("Member Status", dataFromServer.get(2)));
		
		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Frozen");    
		series3.getData().add(new XYChart.Data("Member Status", dataFromServer.get(1)));
		
		XYChart.Series series4 = new XYChart.Series();
		series4.setName("Total members");    
		series4.getData().add(new XYChart.Data("Member Status", dataFromServer.get(3)));
		
	
		XYChart.Series series5 = new XYChart.Series();
		series5.setName("In time");    
		series5.getData().add(new XYChart.Data("loans Status", dataFromServer.get(4)));
		
		XYChart.Series series6 = new XYChart.Series();
		series6.setName("Late");    
		series6.getData().add(new XYChart.Data("loans Status", dataFromServer.get(5)));
		
		
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1, series2,series3,series4,series5,series6);
        stage.setScene(scene);
        stage.show();
		}
	
    }
	
   
