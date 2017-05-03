import javax.swing.*;

import java.awt.*;
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
		int ii=0;
		while(lister!=null && lister.size()!=i){
			gc.gridx=0;
			gc.gridy=ii;
			//gc.weightx=.5f;
			gc.insets = new Insets(10,0,10,10);
			lister.get(ii).setBorder(BorderFactory.createTitledBorder(titlelister.get(ii).getText()));
			panel.add(lister.get(i),gc);
			ii++;
			i++;
		}
		JPanel master =  new JPanel();
		JScrollPane scroll = new JScrollPane(panel);
		//master.setBorder(BorderFactory.createTitledBorder(""));
		scroll.setPreferredSize(new Dimension(300,600));
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		master.add(scroll);
		f.add(master);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);//making the frame visible 
	}
}
