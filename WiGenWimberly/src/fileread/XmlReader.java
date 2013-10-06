/**
 * XmlReader.java
 * 
 * Reads an XML file and populates data objects
 * Implements FileReadStrategy
 * 
 * Constructor:
 * public XmlReader()
 * 
 * Public methods:
 * public SchoolManager readFile(String fileName)
 */
package fileread;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import dataobjects.Classroom;
import dataobjects.Grade;
import dataobjects.Person;
import dataobjects.PrimaryId;
import dataobjects.School;
import dataobjects.SchoolManager;

public class XmlReader implements FileReadStrategy
{
	
	private SchoolManager manager;
	private School currentSchool;
	private Grade currentGrade;
	private Classroom currentClassroom;
	private static final Logger LOG =  Logger.getLogger(XmlReader.class);
	
	
	public XmlReader()
	{
		this.manager = new SchoolManager();
		this.currentSchool = null;
		this.currentGrade = null;
		this.currentClassroom = null;
		PropertyConfigurator.configure(getClass().getResource("log4j-fileread.properties"));
	}
	
	
	@Override
	public SchoolManager readFile(String fileName)
	{
		String strLine;
		FileInputStream fileInputStream;
		DataInputStream dataInputStream;
		BufferedReader br;
		
		try {
			fileInputStream = new FileInputStream(fileName);
			dataInputStream = new DataInputStream(fileInputStream);
			br = new BufferedReader(new InputStreamReader(dataInputStream));

			
			while ( (strLine = br.readLine()) != null )
			{
				checkForData(strLine);	
			}
		}
		
		catch (FileNotFoundException ex) {
			LOG.fatal("File Not Found: " + fileName);
			System.exit(1);
		}
		catch (IOException ex) {
			LOG.fatal("Unable to read file: " + fileName);
			System.exit(1);
		}


		return manager;
	}
	
	
	/*
	 * check to see if a string matches a relevant key
	 */
	private void checkForData(String line)
	{
		
		if( line.contains("<school id=")) 
		{
			addSchool(line);
		}
		
		else if ( line.contains("<grade id=\"") )
		{
			addGrade(line);
		}
		
		else if ( line.contains("<classroom id=\"") )
		{
			addClassroom(line);	
		}
		
		else if ( line.contains("<teacher id=\"") || line.contains("<student id=\""))
		{
			addPerson(line);
		}
		
		else if (line.contains("</classroom>)")){
			currentClassroom = null;
		}
		
		else if (line.contains("</grade>)")){
			currentGrade = null;
		}
		
		else if (line.contains("</school>)")){
			currentSchool = null;
		}
		
	}
	
	/*
	 * Adds school information from the current line 
	 */
	private void addSchool(String line)
	{
		String vals[], name;
		long id;
		
		line = line.replace("<school id=\"", "");
		line = leftTrim(line);
		vals = line.split("\" ", 2);
		id =  Long.parseLong(vals[0]);
		line = vals[1];
		line = line.replace("name=\"", "");
		vals = line.split("\"");
		name = vals[0];
		
		currentSchool = new School(name,new PrimaryId(id));
		manager.addSchool(currentSchool);
		
		LOG.debug("name= " + name + ", id= " + id);
	}
	
	
	/*
	 * adds a grade to the current school
	 * Parameter: String line
	 */
	private void addGrade(String line)
	{
		String vals[];
		long id;
		
		line = line.replace("<grade id=\"", "");
		line = leftTrim(line);
		vals = line.split("\"",2);
		id = Long.parseLong(vals[0]);
		
		currentGrade = new Grade(new PrimaryId(id));
		currentSchool.addGrade(currentGrade);
	}
	
	/*
	 * adds a classroom to the current grade
	 * Parameter: String line
	 */
	private void addClassroom(String line)
	{
		String vals[];
		String name;
		long id;
		
		line = line.replace("<classroom id=\"", "");
		line = leftTrim(line);
		vals = line.split("\" ",2);
		id =  Long.parseLong(vals[0]);
		line = vals[1];
		line = line.replace("name=\"", "");
		vals = line.split("\"");
		name = vals[0];
		
		currentClassroom = new Classroom(name, new PrimaryId(id));
		currentGrade.addClassroom(currentClassroom);
		LOG.debug("name= " + name + ", id= " + id);
			
	}
	
	
	/*
	 * creates a person object and adds the person to the current classroom
	 * Parameters: String line
	 */
	private void addPerson(String line)
	{
		String vals[];
		String firstName,lastName;
		long id;
		
		boolean isTeacher = line.contains("<teacher id=\"");

		line = (isTeacher ? line.replace("<teacher id=\"","") : line.replace("<student id=\"",""));
		line = leftTrim(line);
		vals = line.split("\" ", 2);
		id =  Long.parseLong(vals[0]);
		line = vals[1];
		line = line.replace("first_name=\"", "");
		vals = line.split("\"",2);
		firstName = leftTrim(vals[0]);
		line = vals[1];
		line = line.replace("last_name=\"", "");
		vals = line.split("\"",2);
		lastName = leftTrim(vals[0]);

		if (isTeacher) {
			currentClassroom.addTeacher(new Person(new PrimaryId(id), firstName, lastName));
		} 
		else {
			currentClassroom.addStudent(new Person(new PrimaryId(id), firstName, lastName));
		}

		LOG.debug("fname= " + firstName + ", lname= " + lastName + ", id= " + id);
	}
	
	
	/*
	 * Removes all whitespace from the beginning of a line
	 * Parameter: String line
	 */
    private String leftTrim(String line) 
    {
        return line.replaceAll("^\\s+", "");
    }
    
}
