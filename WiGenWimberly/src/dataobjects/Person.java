/**
 * Person.java
 * 
 * Person Object for Students and Teachers. Maintains id and Name
 * 
 * Constructor:
 * public Person (PrimaryId primaryId, String firstName, String lastName)
 * 
 * Public Methods:
 * public PrimaryId getPrimaryId()
 * public String getlName()
 * public String getfName()
 * 
 */

package dataobjects;

public class Person
{
	String firstName;
	String lastName;
	PrimaryId primaryId;
	
	public Person (PrimaryId primaryId, String firstName, String lastName)
	{
		this.primaryId = primaryId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/*
	 * return primary id
	 */
	public PrimaryId getPrimaryId()
	{
		return primaryId;
	}

	/*
	 * returns person's last name
	 */
	public String getlName()
	{
		return lastName;
	}


	/*
	 * returns person's first name
	 */
	public String getfName()
	{
		return firstName;
	}

}
