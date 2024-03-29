import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ViewDataScreen extends JFrame{
	//charts and stuff
	JFrame f = new JFrame();
	JRadioButton Hours = new JRadioButton("Hours over time",true);
	JRadioButton fulfill = new JRadioButton("Fulfillment over time");
	JRadioButton hoursproject = new JRadioButton("Hours on projects");
	JRadioButton avgful = new JRadioButton("Fulfillment on projects");
	JButton submit = new JButton("Submit");
	ButtonGroup group = new ButtonGroup();
	
	ViewDataScreen(){

		group.add(Hours);
		group.add(fulfill);
		group.add(hoursproject);
		group.add(avgful);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx=0;
		gc.gridy=0;
		gc.insets = new Insets(10,10,0,10);
		gc.anchor = GridBagConstraints.NORTHWEST;
		panel.add(Hours,gc);
		
		gc.gridx=0;
		gc.gridy=1;
		panel.add(fulfill,gc);
		
		gc.gridx=0;
		gc.gridy=2;
		panel.add(hoursproject,gc);
		
		gc.gridx=0;
		gc.gridy=3;
		panel.add(avgful,gc);
		
		gc.gridx=0;
		gc.gridy=4;
		gc.weightx=1;
		gc.insets = new Insets(10,5,10,0);
		gc.anchor = GridBagConstraints.EAST;
		
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Open up the view data screen (Charts)
				if(Hours.isSelected()){
					Charts chart = new Charts(1);
				}else if(fulfill.isSelected()){
					Charts chart = new Charts(2);
				}else if(hoursproject.isSelected()){
					Charts chart = new Charts(3);
				}else if(avgful.isSelected()){
					Charts chart = new Charts(4);
				}
			}
		});
		panel.add(submit,gc);
		panel.setBorder(BorderFactory.createTitledBorder("Chart Choices"));
			
		f.add(panel);
		Hours.setSelected(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setSize(300, 250);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
