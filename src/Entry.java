
public class Entry {
	public String Username,Desc,Issues,ProjectName,ProgramUsed,Coworkers,PostTitle= new String();
	public int Hours;
	public int Fulfillment;
	public int Time;
	Entry(String username, String desc, String issues, String projectname, String programused, String coworkers, String posttitle, int hours, int fulfillment, int time){
		Username = username;
		Desc = desc;
		Issues = issues;
		ProjectName = projectname;
		ProgramUsed = programused;
		Coworkers = coworkers;
		PostTitle = posttitle;
		Hours = hours;
		Fulfillment = fulfillment;
		Time = time;
	}
	public boolean MoreCurrent(Entry tester, Entry other){ //if test more current than object return true
		if(tester.Time>other.Time){
			return true;
		}
		else{
			return false;
		}
	}
}
