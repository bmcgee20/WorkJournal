import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Charts{
	DataBaseHelper db= new DataBaseHelper();
	
	Charts(int type){ //1-4 based on radiobutton
		db.dbConnector();
		if(type ==1){
			BuildLineGraph(1);
		}
		if(type==2){
			BuildLineGraph(2);
		}
		if(type==3){
			BuildBarChart(1); //Hours on Projects
		}
		if(type==4){
			BuildBarChart(2);
		}
	}
	void BuildBarChart(int type){
		if(type==1){//if hours per project
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			ArrayList<String> hoursPairs = new ArrayList<>();
			hoursPairs = db.getHoursVsTime();
			int i =0;
			while(hoursPairs.size()-1>i){ //build chart dataset
				String ProjName = hoursPairs.get(i);
				i++;
				int ProjHours = Integer.parseInt(hoursPairs.get(i));
				i++;
				dataset.setValue(ProjHours, "Hours", ProjName); //value, y axis, x axis
			}
			JFreeChart chart = ChartFactory.createBarChart("Hours On Projects", "Project Names", "Hours", dataset, PlotOrientation.VERTICAL, false, true, false);
			CategoryPlot p = chart.getCategoryPlot();
			p.setRangeGridlinePaint(Color.black); //color of the grids
			ChartFrame frame = new ChartFrame("Hours vs Time", chart);//title of new frame and chart to show
			frame.setVisible(true);
			frame.setSize(500,400);
			frame.setLocationRelativeTo(null);

		}
		else if(type==2){//if fulfillment per project
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			ArrayList<String> FullPairs = new ArrayList<>();
			FullPairs = db.getProjectVsFulfillment();
			int i =0;
			while(FullPairs.size()-1>i){ //build chart dataset
				String ProjName = FullPairs.get(i);
				i++;
				float Fullfill = Float.parseFloat((FullPairs.get(i)));
				i++;
				System.out.println("Project "+ProjName+"  Full: "+Fullfill);
				dataset.setValue(Fullfill, "Fulfillment", ProjName); //value, y axis, x axis
			}
			JFreeChart chart = ChartFactory.createBarChart("Avg. Fulfillment Per Project", "Project Names", "Fulfillment", dataset, PlotOrientation.VERTICAL, false, true, false);
			CategoryPlot p = chart.getCategoryPlot();
			p.setRangeGridlinePaint(Color.black); //color of the grids
			ChartFrame frame = new ChartFrame("Fulfillment Vs Project", chart);//title of new frame and chart to show
			frame.setVisible(true);
			frame.setSize(500,400);
			frame.setLocationRelativeTo(null);
		}
	}
	void BuildLineGraph(int type){
		if(type==1){//hours per time
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			ArrayList<String> FullPairs = new ArrayList<>();
			FullPairs = db.getHoursAndHappy("Hours");//set sql query value
			int i =0;
			while(FullPairs.size()-1>i){ //build chart dataset
				String Date = FullPairs.get(i);
				i++;
				int hours = Integer.parseInt(((FullPairs.get(i))));
				i++;
				System.out.println("Hours "+hours+"  Date: "+Date);
				dataset.setValue(hours, "Hours", Date); //value, y axis, x axis
			}
			JFreeChart chart = ChartFactory.createLineChart("Hours Per Day Over Time", "Date", "Hours", dataset, PlotOrientation.VERTICAL, false, true, false);
			CategoryPlot p = chart.getCategoryPlot();
			p.setRangeGridlinePaint(Color.black); //color of the grids
			ChartFrame frame = new ChartFrame("Hours vs Time", chart);//title of new frame and chart to show
			frame.setVisible(true);
			frame.setSize(500,400);
			frame.setLocationRelativeTo(null);
		
		}
		else if(type==2){ //fulfillment over time
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			ArrayList<String> FullPairs = new ArrayList<>();
			FullPairs = db.getHoursAndHappy("Fulfillment"); //set sql query value
			int i =0;
			while(FullPairs.size()-1>i){ //build chart dataset
				String Date = FullPairs.get(i);
				i++;
				int happy = Integer.parseInt(((FullPairs.get(i))));
				i++;
				System.out.println("happy "+happy+"  Date: "+Date);
				dataset.setValue(happy, "Fulfillment", Date); //value, y axis, x axis
			}
			JFreeChart chart = ChartFactory.createLineChart("Fulfillment Over Time", "Date", "Fulfillment", dataset, PlotOrientation.VERTICAL, false, true, false);
			CategoryPlot p = chart.getCategoryPlot();
			p.setRangeGridlinePaint(Color.black); //color of the grids
			ChartFrame frame = new ChartFrame("Fulfillment vs Time", chart);//title of new frame and chart to show
			frame.setVisible(true);
			frame.setSize(500,400);
			frame.setLocationRelativeTo(null);
		
		}
	}

}
