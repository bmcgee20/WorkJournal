import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class WelcomeMenuScreen {
	JFrame f = new JFrame();
		
	JPanel PanWelcomeMenu = new JPanel(); //set border text later to welcome username
	JButton ButAddEntry = new JButton("Add Entry");
	JButton ButViewEntry = new JButton("View Entries");
	JButton ButViewData = new JButton("View Data");
	
	WelcomeMenuScreen(){
		//Grab username from database
		PanWelcomeMenu.setBorder(BorderFactory.createTitledBorder("Welcome Username"));
		//Listeners
		ButAddEntry.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Open up a add entry screen
				f.setVisible(false);
				AddEntryScreen AddDataWindow = new AddEntryScreen();
			}
		});

		ButViewEntry.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Open up the view entry screen
				
						
			}
		});
		ButViewData.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Open up the view data screen (Charts)
				
			}
		});
		//Layout
		PanWelcomeMenu.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		Insets normal = new Insets(10,10,10,10);
		gc.gridx= 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = normal;
		PanWelcomeMenu.add(ButAddEntry,gc);

		gc.gridx=0;
		gc.gridy=1;
		gc.insets =normal;
		gc.fill = GridBagConstraints.BOTH;
		PanWelcomeMenu.add(ButViewData,gc);
		
		gc.gridx=0;
		gc.gridy=2;
		gc.insets = normal;
		gc.fill = GridBagConstraints.BOTH;
		PanWelcomeMenu.add(ButViewEntry,gc);
		f.add(PanWelcomeMenu);
		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);//making the frame visible 
	}
}
