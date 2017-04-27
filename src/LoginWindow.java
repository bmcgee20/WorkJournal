import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import java.sql.*;

public class LoginWindow extends JFrame{

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
		//DB
		db.conn = db.dbConnector();
		
		
		panel.setBorder(BorderFactory.createTitledBorder("Login Info"));
		//Listeners
		FieldPassword.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//submit data
				LabError.setText("Enter worked");
						
			}
		});

		FieldUsername.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//submit data
				LabError.setText("Enter worked");
						
			}
		});
		ButEnter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println(String.valueOf(FieldPassword.getPassword()));
				if(db.CheckLoginAccount(FieldUsername.getText(),String.valueOf(FieldPassword.getPassword()))){
					f.setVisible(false);
					WelcomeMenuScreen Menu = new WelcomeMenuScreen();
				}
				else{
					LabError.setText("Username & Password Combo Wrong");
				}

			}
		});
		ButCreate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
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
	public boolean AddUser(){

				
		return true;
	}
	public static void main(String[] args){
/*
		if(DataBaseHelper.GetDB() == null){
			LabError.setText("Database Not Connected"); //will show user if the db is not there
		}
		else{
			System.out.println("Database connected"); //just for debuging will not show user
		}
*/	
		SwingUtilities.invokeLater(new Runnable(){//just needed for swing
			public void run(){
				new LoginWindow();
			}
		});
	}
}