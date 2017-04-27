import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ViewEntriesScreen extends JFrame{
	//Frame Elements
	JFrame f = new JFrame();
	JLabel Title = new JLabel("Open this title");
	JPanel panel = new JPanel();
	JPanel innerpanel = new JPanel();
	JLabel bob = new JLabel("Bob");
	JButton butspawn = new JButton("Turn on");
	JButton butunder = new JButton("Tester");
	int i=0;
	ViewEntriesScreen(DataBaseHelper db){
		//Layout
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(butspawn);
		panel.add(innerpanel);
		innerpanel.setVisible(false);
		ArrayList<JPanel> lister = new ArrayList<>();
		lister =db.GetEntries();
		while(lister.size()!=i){
			innerpanel.add(lister.get(i));
			i++;
		}
		panel.add(butunder);
		butspawn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Open up a add entry screen
				innerpanel.setVisible(true);
			}
		});
		
		f.add(panel);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);//making the frame visible 
	}
}
