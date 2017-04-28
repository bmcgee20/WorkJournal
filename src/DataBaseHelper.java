
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

public class DataBaseHelper {
	public Connection conn = null;
	public static String currentUser=null;
	public Connection dbConnector(){
		try{
			Class.forName("org.sqlite.JDBC");
			String path=this.getClass().getResource("JavaDB.db").getPath();
			//System.out.println(path);
			path = path.replaceAll("/","\\\\\\\\"); //puts url in right format
			path = path.substring(2); 
			System.out.println(path);
			path = path.replaceAll("%20", " "); //fixes space in paths
			//System.out.println(path);
			conn = DriverManager.getConnection("jdbc:sqlite:"+path);
			LoginWindow.LabError.setText("We are connected");
			//System.out.println("Ehllo");
			return conn;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
	/*
	 * 
	 * Login Database Methods
	 */
	//adds user to the database
	public boolean AddUser(String username,String password){
		String command = "INSERT INTO LoginInfo(Username,Password) VALUES(?,?)";
		//check for duplicates
		if(CheckNoDuplicateAccounts(username)==false){ //if account already made dont make it
			return false;
		}
		try(Connection conn = this.dbConnector();
				PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, username);
			state.setString(2, password);
			state.executeUpdate();	
			return true;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
		
		
	}
	//checks for account duplicates on creation of profile
	public boolean CheckNoDuplicateAccounts(String username){ //return true if no duplicates
		String command = "SELECT Username FROM LoginInfo WHERE Username = ?";
		try(Connection conn = this.dbConnector();
			PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, username);
			ResultSet result = state.executeQuery();
			
			if(!result.next()){ //there is no accounts with this username
				return true;
			}
			else{
				return false;
			}
				
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	//checks if the login details are right
	public boolean CheckLoginAccount(String username,String password){
		String command = "SELECT * FROM LoginInfo WHERE Username = ? AND password = ?";
		try(Connection conn = this.dbConnector();
			PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, username);
			state.setString(2, password);
			ResultSet result = state.executeQuery();
			
			if(!result.next()){ //no accounts match
				return false;
			}
			else{
				this.currentUser = username;
				return true;
			}
				
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	/*
	 * 
	 * AddEntryScreen database methods
	 * 
	 */
	public boolean AddEntry(String username, String desc,String issues, String project, int hours, int happy, String program, String coworkers,
							String title, float time){
		String command = "INSERT INTO EntryTable(Username,Desc,Issues,ProjectName,Hours,Fulfillment,ProgramUsed,Coworkers,PostTitle, Time) VALUES(?,?,?,?,?,?,?,?,?,?)";
		//check for duplicates
		try(Connection conn = this.dbConnector();
				PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, username);
			state.setString(2, desc);
			state.setString(3, issues);
			state.setString(4, project);
			state.setInt(5, hours);
			state.setInt(6, happy);
			state.setString(7, program);
			state.setString(8, coworkers);
			state.setString(9, title);
			state.setFloat(10, time);
			state.executeUpdate();	
			return true;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
		
		
	}
	/*
	 * View Entry Screen methods
	 * 
	 */
	public ArrayList<JPanel> GetEntries(){
		String command = "SELECT * FROM EntryTable WHERE Username = ? ORDER BY time DESC;";
		ArrayList<JPanel> PanelList = new ArrayList<>();
		try(Connection conn = this.dbConnector();
			PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, DataBaseHelper.currentUser);
			ResultSet result = state.executeQuery();
			
			while(result.next()){//make panels for each result
				System.out.println("Desc is "+result.getString("Desc"));
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());
				GridBagConstraints gc = new GridBagConstraints();
				int y = 0;
				gc.anchor = GridBagConstraints.WEST;
				JLabel WordDesc = new JLabel("Description:");
				gc.gridx=0;
				gc.gridy=0;
				panel.add(WordDesc,gc);

				JLabel Desc = new JLabel(result.getString("Desc"));
				gc.gridx=0;
				gc.gridy=1;
				panel.add(Desc,gc);
				
				y=2;
				if(result.getString("Issues").length()!=0){ //check if not mandatory field are put in
					JLabel Iss = new JLabel("Issues:");
					gc.gridx=0;
					gc.gridy=2;
					panel.add(Iss,gc);
					
					JLabel Issues = new JLabel(result.getString("Issues"));
					gc.gridx=0;
					gc.gridy=3;
					panel.add(Issues,gc);
					y=4; //change the grid height dynamically based on what it added or not
				}
				
				JLabel Project = new JLabel("Project: " +result.getString("ProjectName"));
				gc.gridx=0;
				gc.gridy=y;
				panel.add(Project,gc);
				y++;
				
				JLabel Hours = new JLabel("Hours: "+ result.getString("Hours"));
				gc.gridx=0;
				gc.gridy=y;
				panel.add(Hours,gc);
				y++;
				
				JLabel Happy = new JLabel("Fulfillment: "+result.getInt("Fulfillment"));
				gc.gridx=0;
				gc.gridy=y;
				panel.add(Happy,gc);
				y++;
				
				JLabel Programs = new JLabel("Programming Language Used: "+result.getString("Fulfillment"));
				gc.gridx=0;
				gc.gridy=y;
				panel.add(Programs,gc);
				
				PanelList.add(panel);
			}
			return PanelList;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}		
	}
	
	public ArrayList<JLabel> GetTitles(){
		String command = "SELECT * FROM EntryTable WHERE Username = ? ORDER BY time DESC;";
		ArrayList<JLabel> TitleList = new ArrayList<>();
		try(Connection conn = this.dbConnector();
			PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, DataBaseHelper.currentUser);
			ResultSet result = state.executeQuery();
			String path= this.getClass().getResource("triangle.png").getPath();
			ImageIcon image = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT));
			
			while(result.next()){
				System.out.println("Desc is "+result.getString("Desc"));
				JLabel TextButton = new JLabel(result.getString("PostTitle"),image,JLabel.LEFT);
				TitleList.add(TextButton);
			}
			return TitleList;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}		
	}
	
	public Connection getDB(){
		return conn;
	}
	
}
