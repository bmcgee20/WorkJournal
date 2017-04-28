import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class Charts extends JFrame{
	Charts(int type){ //1-4 based on radiobutton
		if(type==1){
			BuildBarChart(1);
		}
	}
	void BuildBarChart(int type){
		if(type==1){//if hours per project
			JFreeChart pieChart = ChartFactory.createBarChart("Hours Per Project", "Project", "Hours", createDataset(),PlotOrientation.VERTICAL,true,true,false);
			ChartPanel chartPanel = new ChartPanel(pieChart);
			chartPanel.setPreferredSize(new Dimension(500,270));
			setContentPane(chartPanel);

			
		}
		else if(type==2){//if fulfillment per project
			
		}
	}
	void BuildLineGraph(int type){
		if(type==1){//hours per time
			
		}
		else if(type==2){ //fulfillment over time
			
		}
	}
	private CategoryDataset createDataset(){
		//rows
		//all the project names need to be queried to here
		DataBaseHelper db= new DataBaseHelper();
		db.dbConnector();
		ArrayList<String> hoursPairs = new ArrayList<>();
		hoursPairs = db.getTwo();
		int i=0;
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		while(hoursPairs.size()<i){
			dataset.addValue(Double.parseDouble(hoursPairs.get(i+1)), hoursPairs.get(i),hoursPairs.get(i) );
			i+=2;
		}
		return dataset;
	}
}
