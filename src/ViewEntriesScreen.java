import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ViewEntriesScreen extends JFrame{
	//Frame Elements
	JFrame f = new JFrame();
	JPanel panel = new JPanel();
	int i=0;
	ViewEntriesScreen(DataBaseHelper db){
		//Layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		//add in all the panels 
		ArrayList<JPanel> lister = new ArrayList<>();
		ArrayList<JLabel> titlelister = new ArrayList<>();
		titlelister = db.GetTitles();
		lister =db.GetEntries();
		final ArrayList<JPanel> listcheck = lister;
		final ArrayList<JLabel> titlelistCheck = titlelister;
		while(lister.size()!=i){
			//add the tile in a panel and then a panel under itt
			//when clicked find the indexx of the title and then find index of the panel for desc and then pop it up or down
			gc.gridx=0;
			gc.gridy=i;
			gc.anchor = GridBagConstraints.WEST;
			gc.insets = new Insets(0,0,10,100);
			JLabel Titler = titlelistCheck.get(i);
			final JLabel titles = Titler;
			panel.add(Titler,gc);
			
			gc.gridx=0;
			gc.gridy=i+1;
			gc.insets = new Insets(10,0,10,10);
			listcheck.get(i).setVisible(false);
			panel.add(lister.get(i),gc);
			i++;
			Titler.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					listcheck.get(titlelistCheck.indexOf(titles)).setVisible(true);
				}
			});
		}

		JPanel master =  new JPanel();
		master.setBorder(BorderFactory.createTitledBorder(""));

		master.add(panel);
		f.add(master);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);//making the frame visible 
	}
}
