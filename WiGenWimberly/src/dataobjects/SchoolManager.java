/**
 * SchoolManager.java
 * 
 * Maintains a list of all schools. Currently files only contain 1 school. If this changed
 * class would be very helpful in maintaining multiple schools or reading multiple files and
 * writing to a single file. 
 * 
 */
package dataobjects;

import java.util.ArrayList;
import java.util.List;

public class SchoolManager
{
	
	//list of all added schools
	private List<School> schoolList;
	
	public SchoolManager()
	{
		schoolList = new ArrayList<School>();
	}
	
	public void addSchool(School school)
	{
		schoolList.add(school);
	}
	
	public List<School> getSchoolList()
	{
		return schoolList;
	}
	
}
