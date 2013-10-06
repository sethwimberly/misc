/**
 * Grade.java
 * 
 * Object for a grade in a school. Maintains a list of classrooms
 * 
 * Constructor: 
 * public Grade (PrimaryId primaryId)
 * 
 * Public Methods:
 * public void addClassroom (Classroom classroom)
 * public List<Classroom> getClassrooms()
 * public Classroom getClassroom(PrimaryId id)
 * public boolean classroomExists(PrimaryId id)
 * public PrimaryId getPrimaryId()
 */
package dataobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Grade
{

	private PrimaryId primaryId;
	private HashMap<Long,Classroom> classroomMap;
	private static final Logger LOG =  Logger.getLogger(Grade.class);

	/*
	 * Constructor
	 * 
	 * Parameters:
	 * PrimaryId primaryId
	 */
	public Grade (PrimaryId primaryId)
	{
		this.primaryId = primaryId;
	
		PropertyConfigurator.configure(getClass().getResource("log4j-dataobjects.properties"));
		classroomMap = new HashMap <Long, Classroom> ();
	}
	
	/*
	 * Adds a classroom
	 */
	public void addClassroom (Classroom classroom)
	{
		LOG.debug("Classroom Added1");
		
		if (!classroomExists(classroom.getPrimarayId()))
		{
			//classroomList.add(classroom);
			classroomMap.put(classroom.getPrimarayId().getValue(), classroom);	
			LOG.debug("Classroom Added2");
		}
		
	}
	
	/*
	 * Returns a list of the classrooms
	 */
	public List<Classroom> getClassrooms()
	{
		ArrayList <Classroom> rooms = new ArrayList<Classroom> ();
		for (Classroom val : classroomMap.values() )
		{
			rooms.add(val);
		}
		
		return rooms;
	}
	
	/*
	 * Returns a classroom given a classroom id
	 */
	public Classroom getClassroom(PrimaryId id)
	{
		return classroomMap.get(id.getValue());
	}
	
	/*
	 * Checks to see if a classroom exists by its Id
	 * Returns true if it does, false otherwise
	 */
	public boolean classroomExists(PrimaryId id)
	{
		return classroomMap.containsKey(id.getValue());
	}
	
	/*
	 * Returns this objects primary id
	 */
	public PrimaryId getPrimaryId()
	{
		return primaryId;
	}
	
	
}
