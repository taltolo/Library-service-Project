
		/*
		 * Arrays.sort(numArray);
double median;
if (numArray.length % 2 == 0)
    median = ((double)numArray[numArray.length/2] + (double)numArray[numArray.length/2 - 1])/2;
else
    median = (double) numArray[numArray.length/2];
		 *///median code
		/*
		 *SELECT returnDate,actualReturnDate,DATEDIFF(actualReturnDate,returnDate)
FROM returns
where DATEDIFF(actualReturnDate,returnDate) > 0; *///query that give me the number of days of late in returns in general

		/*SELECT returnDate,actualReturnDate,DATEDIFF(actualReturnDate,returnDate)
FROM returns
where DATEDIFF(actualReturnDate,returnDate) > 0 and idbook = 'enter book id here';*/// query that give me the number of days of late in returns for one book 


		/*

		 * SELECT returnDate,actualReturnDate,DATEDIFF(actualReturnDate,returnDate)
FROM returns
inner join  books
on returns.idbook=books.idbook
where books.Tagged = 'Wanted'; *///query that give me the number of days loaning for wanted book
		/*
		 * SELECT returnDate,actualReturnDate,DATEDIFF(actualReturnDate,returnDate)
FROM returns
inner join  books
on returns.idbook=books.idbook
where books.Tagged = 'Regular'; 
		 *///query that give me the number of days loaning for regular book
	

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
 * this class open and generate an report of late returns of a specific book in a shape of bar chart
 * 
 *
 */
public class Type3ByBookReport   extends Application{

    @FXML
    private AnchorPane AnchorHome;

    @FXML
    private ImageView Image;
 
Msg newmsg;
 public Type3ByBookReport(Msg newmsg) {
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
		if(!((ArrayList<Series>) newmsg.dataFromServer.get(0)).isEmpty())
		{
dataFromServer=(ArrayList<Integer>) newmsg.dataFromServer.get(0);
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("book");    
		for(int d:dataFromServer)
			avg+=d;
		avg=avg/dataFromServer.size();
	
	series1.getData().add(new XYChart.Data("AVG", avg));//how much

	
	
		Collections.sort(dataFromServer);	
		if (dataFromServer.size() % 2 == 0)
			median = ((double)dataFromServer.get(dataFromServer.size()/2) + (double)dataFromServer.get(dataFromServer.size()/2 - 1))/2;
    	else
    		median = (double) dataFromServer.get(dataFromServer.size()/2);
    	
		
		series1.getData().add(new XYChart.Data("median", median));

		int[] array = new int[10];
		int max=Collections.max(dataFromServer);
		double limit= (double)max/10, sumofstep=0;
		sumofstep=limit;
		for (int i = 0; i < 10; i++) {
			if(i==9)
				sumofstep+=0.2;
			for(int d:dataFromServer)
			{
				if(d>=(sumofstep-limit) && d<=sumofstep)
				{
					array[i]++;
				}
			}
			sumofstep=sumofstep+limit;
		}
		limit= (double)max/10;
		sumofstep=limit;
		String toShow;
		for (int i = 0; i < 10; i++) {
			 toShow=String.format ("%.2f", sumofstep-limit);
			 toShow+="-";
			 toShow+=String.format ("%.2f", sumofstep);
			 series1.getData().add(new XYChart.Data(toShow, array[i]));
			sumofstep=sumofstep+limit;
		}
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
		}
		else
		{
			displayAlert(AlertType.ERROR, "Error", "search", "empty search");
		}
    }
	/**
	 * Show an Alert dialog with custom info
	 * @param type type alert
	 * @param title title window
	 * @param header header of the message
	 * @param content message
	 */
	public static void displayAlert(AlertType type , String title , String header , String content)
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
   
}