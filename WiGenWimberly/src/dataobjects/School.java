/**
 * School.java
 * 
 * School Object class. Maintains an array of grades
 * 
 * Constructor:
 * public School (String name, PrimaryId primaryId)
 * 
 * Public methods:
 * public void addGrade(Grade grade)
 * public Grade getGrade(long PrimaryId)
 * public PrimaryId getPrimaryId()
 * public String getName()
 * 
 * 
 * Seth Wimberly
 */

package dataobjects;



public class School
{

	//Array of added grades  
	private Grade gradeArray[];
	private PrimaryId primaryId;
	private String name;
	
	public static final int GRADE_SIZE = 14;
	public static final long NULL_GRADE = 13;
	
	/*
	 * Constructor 
	 * Parameter: String name, PrimaryId, primaryId
	 */
	public School (String name, PrimaryId primaryId)
	{
		this.name = name;
		this.primaryId = primaryId;
		gradeArray = new Grade[GRADE_SIZE];
		initArray();
		//gradeExists = new boolean[GRADE_SIZE];
		
	}
	
	/*
	 * Initializes array with null values
	 */
	private void initArray()
	{
		for (int i = 0; i < GRADE_SIZE; i++)
		{
			gradeArray[i] = null;
		}
	}

	/*
	 * Adds a grade to the school
	 * Parameters: Grade grade
	 */
	public void addGrade(Grade grade)
	{
		int index = (int) grade.getPrimaryId().getValue();
		
		if (index < GRADE_SIZE && !gradeExists(grade.getPrimaryId()) ) {
			gradeArray[index] = grade;
			//gradeExists[index] = true;
		}
	}
	
	/*
	 * Determine if a grade exists by its PrimaryId
	 * Parameter: long PrimaryId
	 * Returns boolean true if exists
	 */
	public boolean gradeExists(PrimaryId primaryId)
	{
		boolean retVal = false;
		int index = (int) primaryId.getValue();
		
		if (gradeArray[index] != null)
		{
			retVal = true;
		}
		
		return retVal;
	}
	
	/*
	 * Return the given grade based on primaryId
	 */
	public Grade getGrade(PrimaryId primaryId)
	{
		int index = (int) primaryId.getValue();
		Grade retGrade  = null;
		
		if ( gradeExists(primaryId) ) 
		{
			retGrade = gradeArray[index];
		}
			
		return retGrade;
	}
	
	
	/*
	 * Getters and Setters
	 */
	public PrimaryId getPrimaryId()
	{
		return primaryId;
	}

	public String getName()
	{
		return name;
	}
	

	
}
