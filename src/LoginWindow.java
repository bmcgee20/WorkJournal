import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import java.sql.*;

public class LoginWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JFrame f = new JFrame("Work Journal");
	JPanel panel = new JPanel();
	JLabel LabUsername = new JLabel("Username: ");
	JLabel LabPassword = new JLabel("Password: ");
	static JLabel LabError = new JLabel("Error your username was not found");
	JPasswordField FieldPassword = new JPasswordField("password",15);
	JTextField FieldUsername = new JTextField("user",15);
	JButton ButEnter = new JButton("Enter");
	JButton ButCreate = new JButton("Create User");
	DataBaseHelper db = new DataBaseHelper();

	
	LoginWindow(){


		/*try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		*/
		//DB
		db.conn = db.dbConnector();
		
		
		panel.setBorder(BorderFactory.createTitledBorder("Login Info"));
		//Listeners
		FieldPassword.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LoginListen();//enter in the username field will login
			}
		});

		FieldUsername.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LoginListen();//enter in password field will login
			}
		});
		ButEnter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LoginListen();//clicking enter will login
			}
		});
		ButCreate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddUser(); //clicking create account will create account
			}
		});
		//Layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx= 0;
		gc.gridy = 0;
		gc.insets = new Insets(15,10,5,5); // top right bot left 
		panel.add(LabUsername,gc);
		
		gc.gridx = 1;
		gc.gridy=0;
		panel.add(FieldUsername, gc);
		
		gc.gridx=0;
		gc.gridy=1;
		panel.add(LabPassword, gc);
		
		gc.gridx=1;
		gc.gridy=1;
		panel.add(FieldPassword,gc);
		

		gc.gridx=0;
		gc.gridy=2;
		gc.gridwidth = 0;
		//gc.insets = new Insets(20,-20,5,5);
		panel.add(ButCreate, gc);
		
		gc.gridx=1;
		gc.gridy = 2;
		gc.weightx =1;
		//gc.insets = new Insets(20,5,5,0);
		gc.anchor = GridBagConstraints.EAST;
		panel.add(ButEnter, gc);
		
		gc.gridx=0;
		gc.gridy=3;
		gc.anchor= GridBagConstraints.WEST;
		gc.insets = new Insets(20,5,5,0);
		gc.gridwidth=2;
		panel.add(LabError, gc);
		

		f.add(panel);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//f.setSize(350,350);//400 width and 500 height  
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);//making the frame visible 
		
	
	
		
	}
	//Listener methods
	public void AddUser(){ //method used in button and on enter in either textfields
		//check if both fields user and password are filled
		if(FieldUsername.getText().length()!=0){
			if(String.valueOf(FieldPassword.getPassword()).length()!=0){
				//throw into the database and check for duplicates
				boolean worked= db.AddUser(FieldUsername.getText(), String.valueOf(FieldPassword.getPassword()));
				if(worked==false){
					LabError.setText("Account already made");
				}
			}
			else{
				LabError.setText("Please Enter a Password.");
			}
		}
		else{
			LabError.setText("Please Enter a Username.");
		}		
	}
	public void LoginListen(){
		System.out.println(String.valueOf(FieldPassword.getPassword()));
		if(db.CheckLoginAccount(FieldUsername.getText(),String.valueOf(FieldPassword.getPassword()))){
			f.setVisible(false);
			WelcomeMenuScreen Menu = new WelcomeMenuScreen(db);
		}
		else{
			LabError.setText("Username & Password Combo Wrong");
		}

	}
	
	public static void main(String[] args){
		try{ //changes the look of the application style
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			System.out.println("Could not find Nimbus will just use default");
		}
	
		SwingUtilities.invokeLater(new Runnable(){//just needed for swing
			public void run(){
				new LoginWindow();
			}
		});
	}
}
