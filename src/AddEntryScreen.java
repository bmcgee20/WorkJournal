import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class AddEntryScreen {
	Date currentDate = new Date();
	SimpleDateFormat dateFormatted = new SimpleDateFormat("E MM/dd/yyyy");
//Java swing elements (their is quite a bit but this is the only data entry screen)
	//Labels
	JLabel LabTitle = new JLabel("Entry "+dateFormatted+": ");
	JLabel LabDesc = new JLabel("*Describe Today: ");
	JLabel LabIssues = new JLabel("Issues: ");
	JLabel LabProject = new JLabel("*Project Name: ");
	JLabel LabHours = new JLabel("*Hours Worked: ");
	JLabel LabHappy = new JLabel("*Level of Fulfillment: ");
	JLabel LabLanguage = new JLabel("Programming Language Used: ");
	JLabel LabCoworkers = new JLabel("Coworkers: ");
	JLabel LabTitleField = new JLabel("Title of Entry: ");
	JLabel ErrorMsg = new JLabel();
	//Fields
	JTextArea FieldDescription = new JTextArea(5,30);
	JScrollPane FieldDesc = new JScrollPane(FieldDescription); //puts it in a scroll text
	JTextArea FieldIssues = new JTextArea(2,30);
	JScrollPane FieldIss = new JScrollPane(FieldIssues);
	JTextField FieldProject = new JTextField(20);
	JTextField FieldHours = new JTextField(2);
	JTextField FieldProgramLang = new JTextField(20);
	JTextField FieldCoWorkers = new JTextField(20);
	JTextField FieldTitle = new JTextField(20);
	//Slider
	JSlider SliderHappy = new JSlider(1,10,1);
	//button
	JButton ButSubmit = new JButton("Submit");
	//Panel Divisions
	JPanel PanLeft = new JPanel();
	JPanel PanRight = new JPanel();
	JPanel PanCapsule = new JPanel(); // encapsulated both right and left
	//frame
	JFrame f = new JFrame();
//Methods
	AddEntryScreen(){
    //Panel left setup
		//set up the scroll pane
		FieldDescription.setWrapStyleWord(true); //description scroll bar set up
		FieldDescription.setLineWrap(true);
		FieldDesc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		FieldDesc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		FieldIssues.setWrapStyleWord(true);  //issues scroll bar set up
		FieldIssues.setLineWrap(true);
		FieldIss.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		FieldIss.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		//SetUp Slider, gives it the ticks on the bottom and numbers
		SliderHappy.setPaintTicks(true);
		SliderHappy.setMajorTickSpacing(1);
		SliderHappy.setSnapToTicks(true);
		SliderHappy.setPaintLabels(true);
		
		//pick layout type
		PanLeft.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		//set up each grid component
		gc.gridx= 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		PanLeft.add(LabDesc,gc);
		
		Insets normal = new Insets(10,10,10,10);
		gc.gridx=0;
		gc.gridy=1;
		gc.weightx=1;
		gc.gridwidth=2;
		//gc.fill = GridBagConstraints.NONE;
		PanLeft.add(FieldDesc,gc);
		
		gc.gridx=0;
		gc.gridy=2;
		gc.insets = new Insets(10,0,2,0);
		gc.anchor = GridBagConstraints.WEST;
		gc.weightx=.4f;
		PanLeft.add(LabIssues, gc);

		gc.gridx = 0;
		gc.gridy=3;
		gc.insets = new Insets(2,0,2,0);
		gc.anchor = GridBagConstraints.WEST;
		gc.gridwidth=2;
		gc.weightx=1;
		PanLeft.add(FieldIss, gc);
		
		gc.gridx=0;
		gc.gridy=4;
		gc.insets = new Insets(5,0,0,0);

		gc.anchor = GridBagConstraints.WEST;
		PanLeft.add(LabProject,gc);
		
		gc.gridx=1;
		gc.gridy=4;
		PanLeft.add(FieldProject,gc);
		
		gc.gridx=0;
		gc.gridy=5;
		PanLeft.add(LabHours,gc);
		
		gc.gridx=1;
		gc.gridy=5;
		PanLeft.add(FieldHours,gc);
		
	//Panel Right setup
		PanRight.setLayout(new GridBagLayout());
		GridBagConstraints gd = new GridBagConstraints();
		gd.gridx = 0;
		gd.gridy= 0;
		gd.insets = normal;
		gd.anchor = GridBagConstraints.WEST;
		PanRight.add(LabHappy, gd);
		
		gd.gridx=1;
		gd.gridy =0;
		gd.insets = normal;
		PanRight.add(SliderHappy, gd);
		
		gd.gridx=0;
		gd.gridy=1;
		gd.anchor = GridBagConstraints.WEST;
		PanRight.add(LabLanguage, gd);
		
		gd.gridx=1;
		gd.gridy=1;
		PanRight.add(FieldProgramLang,gd);
		
		gd.gridx=0;
		gd.gridy=2;
		gd.anchor = GridBagConstraints.WEST;
		PanRight.add(LabCoworkers,gd);
		
		gd.gridx=1;
		gd.gridy=2;
		PanRight.add(FieldCoWorkers,gd);
		
		gd.gridx = 0;
		gd.gridy = 3;
		gd.anchor = GridBagConstraints.WEST;
		PanRight.add(LabTitleField,gd);
		
		gd.gridx=1;
		gd.gridy=3;
		PanRight.add(FieldTitle,gd);
		
		gd.gridx =0;
		gd.gridy=4;
		gc.anchor= GridBagConstraints.SOUTHWEST;
		PanRight.add(ErrorMsg,gd);
		
		gd.gridx=0;
		gd.gridy=5;
		gd.gridwidth=2;
		gd.anchor = GridBagConstraints.SOUTHEAST;
		gd.insets = new Insets(10,5,0,0);
		PanRight.add(ButSubmit,gd);
	//Encapsulating 
		PanCapsule.setBorder(BorderFactory.createTitledBorder(""));
		PanCapsule.setLayout(new FlowLayout());
		PanCapsule.add(PanLeft);
		PanCapsule.add(PanRight);
		f.add(PanCapsule);
		//f.add(PanLeft);
		//f.add(PanRight);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);//making the frame visible 
	//Listeners
		ButSubmit.addActionListener(new ActionListener(){ //when click submit do these things
			public void actionPerformed(ActionEvent e){
				if(SubmitCheck()){//If input is clean
					//db.uploadactivity
					f.setVisible(false);
					WelcomeMenuScreen backWindow = new WelcomeMenuScreen();
				}
			}
		});
	}
	//Listener methods
	private boolean SubmitCheck(){//checks required data is there and such before adding to database
		if(FieldDescription.getText().length()==0){
			Error("Description is required");
			return false;
		}else if(FieldProject.getText().length()==0){
			Error("Project title not filled out");
			return false;
		}else if(FieldHours.getText().length()==0){
			Error("Hours not filled out");
			return false;
		}
		return true;
	}
	private void Error(String message){ //sets the error msg label to this text
		ErrorMsg.setText(message);
	}
}
