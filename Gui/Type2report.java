

	package Gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import Entity.Msg;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *  this class open and generate an borrow report in a shape of bar chart
 * 
 *
 */
public class Type2report   extends Application{

    @FXML
    private AnchorPane AnchorHome;

    @FXML
    private ImageView Image;
 
Msg newmsg;
 public Type2report(Msg newmsg) {
	super();
	this.newmsg = newmsg;
}

    @Override public void start(Stage stage) {
    	double medianRegular,medianWanted;
    	ArrayList<XYChart.Series> temp = new ArrayList<XYChart.Series>();
		ArrayList<Integer> wanted = new ArrayList<Integer>(); 
		ArrayList<Integer> regular = new ArrayList<Integer>(); 
		ArrayList<Integer> combined = new ArrayList<Integer>(); 
		wanted = (ArrayList<Integer>)newmsg.dataFromServer.get(0);
		regular = (ArrayList<Integer>)newmsg.dataFromServer.get(1);
		double avgRegular=0,avgWanted=0;
		combined.addAll(wanted);
		combined.addAll(regular);
		stage.setTitle("borrow");
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String,Number> bc = 
				new BarChart<String,Number>(xAxis,yAxis);
		bc.setTitle("landing time");
		xAxis.setLabel("book type");       
		yAxis.setLabel("time(in days)");

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("regular");    
		for(int d:regular)
			avgRegular+=d;
		avgRegular=avgRegular/regular.size();
	
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("wanted");
	   
		for(int d:wanted)
			avgWanted+=d;
		avgWanted=avgWanted/wanted.size();
		series2.getData().add(new XYChart.Data("AVG", avgWanted));
		series1.getData().add(new XYChart.Data("AVG", avgRegular));//how much

	
		Collections.sort(regular);	
		if (regular.size() % 2 == 0)
    	    medianRegular = ((double)regular.get(regular.size()/2) + (double)regular.get(regular.size()/2 - 1))/2;
    	else
    		medianRegular = (double) regular.get(regular.size()/2);
		
		Collections.sort(wanted);	
		if (wanted.size() % 2 == 0)
    	    medianWanted = ((double)wanted.get(wanted.size()/2) + (double)wanted.get(wanted.size()/2 - 1))/2;
    	else
    		medianWanted = (double) wanted.get(wanted.size()/2);
    	
		
		series2.getData().add(new XYChart.Data("median", medianRegular));
		series1.getData().add(new XYChart.Data("median", medianWanted));

		int[] wantedarray = new int[10];
		int maxWanted=Collections.max(wanted);
		double limitWanted= (double)maxWanted/10, sumofstep=0;
		sumofstep=limitWanted;
		for (int i = 0; i < 10; i++) {
			if(i==9)
				sumofstep+=0.2;
			for(int d:wanted)
			{
				if(d>=(sumofstep-limitWanted) && d<=sumofstep)
				{
					wantedarray[i]++;
				}
			}
			sumofstep=sumofstep+limitWanted;
		}
		limitWanted= (double)maxWanted/10;
		sumofstep=limitWanted;
		String toShow;
		for (int i = 0; i < 10; i++) {
			//temp.add(new XYChart.Series());
			//temp.get(i).setName("days");
			 toShow=String.format ("%.2f", sumofstep-limitWanted);
			 toShow+="-";
			 toShow+=String.format ("%.2f", sumofstep);
				

			//temp.get(i).getData().add(new XYChart.Data(toShow, wantedarray[i]));
			 series1.getData().add(new XYChart.Data(toShow, wantedarray[i]));
			//bc.getData().add(temp.get(i));
			sumofstep=sumofstep+limitWanted;
		}
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1, series2);
        stage.setScene(scene);
        stage.show();
    }
 
   
}