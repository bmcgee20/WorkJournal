
import java.sql.*;

import javax.swing.*;

public class DataBaseHelper {
	public Connection conn = null;
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
				return true;
			}
				
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public Connection getDB(){
		return conn;
	}
}
