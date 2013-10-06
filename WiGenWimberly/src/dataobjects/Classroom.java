/**
 * Classroom.java
 * 
 * Classroom Object class. Maintains a list of students and teachers and its classroom info
 * 
 * Constructors:
 * public Classroom (String className, PrimaryId primaryId, Person teacher)
 * public Classroom (String className, PrimaryId primaryId)
 *  
 *  Public Methods:
 *  public void addStudent(Person student)
 *	public void addTeacher(Person teacher)
 *	public String getClassName()
 *	public List<Person> getTeacherList()
 *	public List<Person> getStudentList()
 *	public PrimaryId getPrimarayId()
 *
 */

package dataobjects;

import java.util.ArrayList;
import java.util.List;

public class Classroom
{

	private ArrayList<Person> studentList;
	private ArrayList<Person> teacherList;
	private String className;
	private PrimaryId primarayId;
	
	public Classroom (String className, PrimaryId primaryId, Person teacher)
	{
		this.className = className;
		this.primarayId = primaryId;
		
		studentList = new ArrayList<Person>();
		teacherList = new ArrayList<Person>();
		
		teacherList.add(teacher);
	}
	
	public Classroom (String className, PrimaryId primaryId)
	{
		this.className = className;
		this.primarayId = primaryId;
		
		studentList = new ArrayList<Person>();
		teacherList = new ArrayList<Person>();
	}
	
	public void addStudent(Person student)
	{
		studentList.add(student);
	}
	
	public void addTeacher(Person teacher)
	{
		teacherList.add(teacher);
	}


	public String getClassName()
	{
		return className;
	}

	
	/*
	 * Return list of teachers
	 */
	public List<Person> getTeacherList()
	{
		return teacherList;
	}

	/*
	 * return list of students
	 */
	public List<Person> getStudentList()
	{
		return studentList;
	}
	
	public PrimaryId getPrimarayId()
	{
		return primarayId;
	}
	
}
