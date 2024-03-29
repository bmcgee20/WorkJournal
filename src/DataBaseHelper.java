
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

public class DataBaseHelper {
	public Connection conn = null;
	public static String currentUser=null;
	public Connection dbConnector(){
		try{
			Class.forName("org.sqlite.JDBC");
			String path=this.getClass().getResource("JavaDB.db").getPath();
			//DataBaseHelper.class.getResourceAsStream("JavaDB.db");
			path = path.replaceAll("/","\\\\\\\\"); //puts url in right format
			path = path.substring(2); 
			System.out.println(path);
			path = path.replaceAll("%20", " "); //fixes space in paths
			//System.out.println(path);
			conn = DriverManager.getConnection("jdbc:sqlite:"+path);
			LoginWindow.LabError.setText("We are connected");
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
				JLabel Project = new JLabel("Project: " +result.getString("ProjectName"));
				gc.insets = new Insets(5,0,5,0);
				gc.gridx=0;
				gc.gridy=y;
				panel.add(Project,gc);
				y++;
				gc.anchor = GridBagConstraints.WEST;
				JLabel WordDesc = new JLabel("Description:");
				gc.gridx=0;
				gc.gridy=y;
				panel.add(WordDesc,gc);
				y++;
				
				//JLabel Desc = new JLabel(result.getString("Desc"));
				JTextArea Desc = new JTextArea(2,20);
				Desc.setText(result.getString("Desc"));
				//Desc.setOpaque(false);
				JScrollPane DescScroll = new JScrollPane(Desc);
				Desc.setWrapStyleWord(true);
				Desc.setEditable(false);
				Desc.setLineWrap(true);
				DescScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				gc.insets = new Insets(5,5,0,2);
				gc.gridx=0;
				gc.gridy=y;
				panel.add(DescScroll,gc);
				y++;
				
				if(result.getString("Issues").length()!=0){ //check if not mandatory field are put in
					JLabel Iss = new JLabel("Issues:");
					gc.gridx=0;
					gc.gridy=y;
					panel.add(Iss,gc);
					y++;
					
					JTextField Issues = new JTextField(20);
					Issues.setText(result.getString("Issues"));
					Issues.setEditable(false);
					//Issues.setOpaque(false);
					gc.insets = new Insets(5,5,0,2);
					gc.gridx=0;
					gc.gridy=y;
					panel.add(Issues,gc);
					y++; //change the grid height dynamically based on what it added or not
				}
				

				
				JLabel Hours = new JLabel("Hours: "+ result.getString("Hours"));
				gc.gridx=0;
				gc.gridy=y;
				gc.insets = new Insets(0,0,5,0);
				panel.add(Hours,gc);
				y++;
				
				JLabel Happy = new JLabel("Fulfillment: "+result.getInt("Fulfillment"));
				gc.gridx=0;
				gc.gridy=y;
				gc.insets = new Insets(0,0,5,0);
				panel.add(Happy,gc);
				y++;
				if(result.getString("ProgramUsed").length()!=0){
					System.out.println(result.getString("ProgramUsed").length()+"Length");
					JLabel Programs = new JLabel("Programs Used:");
					gc.gridx=0;
					gc.gridy=y;
					panel.add(Programs,gc);
					y++;
					
					JTextField Programming = new JTextField(20);
					Programming.setText(result.getString("ProgramUsed"));
					//Programming.setOpaque(false);
					gc.gridx = 0;
					gc.anchor = GridBagConstraints.WEST;
					gc.gridy=y;
					
					panel.add(Programming,gc);
					
				}
				PanelList.add(panel);
			}
			return PanelList;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}		
	}
	
	public ArrayList<JLabel> GetTitles(){//get list of titles from user to set panel title for view entries
		String command = "SELECT * FROM EntryTable WHERE Username = ? ORDER BY time DESC;";
		ArrayList<JLabel> TitleList = new ArrayList<>();
		try(Connection conn = this.dbConnector();
			PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, DataBaseHelper.currentUser);
			ResultSet result = state.executeQuery();
			//ImageIcon image = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT));
			
			while(result.next()){
				System.out.println("Desc is "+result.getString("Desc"));
				JLabel TextButton = new JLabel("� "+result.getString("PostTitle"));
				TitleList.add(TextButton);
			}
			return TitleList;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}		
	}
	public ArrayList<String> getHoursVsTime(){
		//return arraylist like this Project, time , project , time //just convert the number to string before it
		String command = "SELECT * FROM EntryTable WHERE Username = ? ORDER BY ProjectName ASC;";
		ArrayList<String> HourTime = new ArrayList<>();
		try(Connection conn = this.dbConnector();
			PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, DataBaseHelper.currentUser);
			ResultSet result = state.executeQuery();
			if(!result.next()){
			}
			else{
				do{ //cause it needs to add last index of result set
					
					System.out.println("Proj is "+result.getString("ProjectName"));
					String projectName = new String(result.getString("ProjectName"));
					if(!HourTime.contains(projectName)){ //if the project is not already added add it
						System.out.println("Adding "+projectName);
						HourTime.add(projectName);
						int timer = result.getInt("Hours");
						String actual = String.valueOf(timer);
						System.out.println("convert string hours "+actual);
						HourTime.add(actual);
					}else{ //if it is already in set increase hours on project
						System.out.println("Already exist "+ projectName);
						int addtime = result.getInt("Hours");
						int index = HourTime.indexOf(projectName);
						addtime+=Integer.parseInt(HourTime.get(index+1));
						String real = Integer.toString(addtime);
						HourTime.set(index+1, real);
					}
				}
				while(result.next());
			}

			
			return HourTime;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}	
	}
	public ArrayList<String> getProjectVsFulfillment(){
		//return arraylist like this Project, time , project , time //just convert the number to string before it
		String command = "SELECT * FROM EntryTable WHERE Username = ? ORDER BY ProjectName ASC;";
		ArrayList<String> FullProj = new ArrayList<>();
		try(Connection conn = this.dbConnector();
			PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, DataBaseHelper.currentUser);
			ResultSet result = state.executeQuery();
			int AmountOfProjectInstances= 0; //keeps up with the average
			float InstanceAvg=0;
			//get a list with the current average, counts of it   reset everytime it hits a new project
			if(!result.next()){
			}
			else{
				do{ //cause it needs to add last index of result set
					
					System.out.println("Proj is "+result.getString("ProjectName"));
					String projectName = new String(result.getString("ProjectName"));
					if(!FullProj.contains(projectName)){ //if the project is not already added add it
						System.out.println("Adding "+projectName);
						FullProj.add(projectName);
						float happy = result.getFloat("Fulfillment");
						String actual = String.valueOf(happy);
						FullProj.add(actual);
						AmountOfProjectInstances=1; //reset these values
						InstanceAvg = happy;
						
					}else{ //if it is already in set increase hours on project
						float happy = result.getFloat("Fulfillment");
						int index = FullProj.indexOf(projectName);
						//FullProj.set(index, real);
						AmountOfProjectInstances++;
						InstanceAvg = (((InstanceAvg*(AmountOfProjectInstances-1)))+happy)/AmountOfProjectInstances;
						FullProj.set(index+1, Float.toString(InstanceAvg));
					}
				}
				while(result.next());
			}

			
			return FullProj;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}	
	}
	
	//Method to get Hours and fulfillment vs time
	
	//convert epoch time to date format for the lister and do  ///Hours or Happy///Time///
	public ArrayList<String> getHoursAndHappy(String query){ //give happy or hours as query
		//return arraylist like this Project, time , project , time //just convert the number to string before it
		String command = "SELECT * FROM EntryTable WHERE Username = ? ORDER BY Time ASC;";
		ArrayList<String> Lister = new ArrayList<>();
		try(Connection conn = this.dbConnector();
			PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, DataBaseHelper.currentUser);
			ResultSet result = state.executeQuery();
			
			if(!result.next()){
			}
			else{
				do{ //cause it needs to add last index of result set
					//get time
					String EpochTime = new String(result.getString("Time"));
					//convert
					long epoch = Long.parseLong(EpochTime);
					SimpleDateFormat dater = new SimpleDateFormat("MM/dd");
					String realDate = new String(dater.format(new Date(epoch)));
					System.out.println("Date is "+realDate +"  "+ EpochTime);
					Lister.add(realDate);
					//add value of fulfillment or Hours both of which are ints 
					int queryvalue = (result.getInt(query));
					Lister.add(String.valueOf(queryvalue));
				}
				while(result.next());
			}
			return Lister;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}	
	}
	
	//makes sure they dont add more than one entry per day, returns true if the user added a entry today
	public Boolean CheckIfEntryToday(){ 
		String command = "SELECT * FROM EntryTable WHERE Username = ? ORDER BY Time DESC;";
		ArrayList<String> Lister = new ArrayList<>();
		try(Connection conn = this.dbConnector();
			PreparedStatement state = conn.prepareStatement(command)){
			state.setString(1, DataBaseHelper.currentUser);
			ResultSet result = state.executeQuery();
			
			if(result.next()){ //if their is no entries at all assume false
				String EpochTime = new String(result.getString("Time"));
				//convert
				long epoch = Long.parseLong(EpochTime);
				SimpleDateFormat dater = new SimpleDateFormat("MM/dd");
				String realDate = new String(dater.format(new Date(epoch)));
				SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd");
				String date = dateForm.format(new Date());
				if(date.equals(realDate)){
					System.out.println(date+realDate);
					return true;
				}
				else{
					return false;
				}
			}
			return false;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return true;
		}	
	}

}
