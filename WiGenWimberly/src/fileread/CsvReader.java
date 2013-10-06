/**
 * CsvReader.java
 * Implements FileReadStrategy Interface
 * Reads and gather's information from a CSV file and properly populates objects
 *
 * Constructor: 
 * public CsvReader()
 * 
 * public methods:
 * public SchoolManager readFile(String fileName)
 * 
 * Seth Wimberly
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

public class CsvReader implements FileReadStrategy
{
	
	private static final Logger LOG =  Logger.getLogger(CsvReader.class);
	private String splitLine[];
	private SchoolManager manager;
	private School school;
	
	
	/*
	 * Constructor
	 */
	public CsvReader()
	{
		PropertyConfigurator.configure(getClass().getResource("log4j-fileread.properties"));
		splitLine = new String[CsvFields.values().length];
		school = null;
		manager = null;
		
	}
	
	@Override
	public SchoolManager readFile(String fileName)
	{
		manager = new SchoolManager();
		school = new School("default", new PrimaryId(0));
		manager.addSchool(school);
		
		LOG.debug(manager.getSchoolList().get(0).getName());
		
		String strLine;
		FileInputStream fileInputStream;
		DataInputStream dataInputStream;
		BufferedReader br;
		
		try {
			fileInputStream = new FileInputStream(fileName);
			dataInputStream = new DataInputStream(fileInputStream);
			br = new BufferedReader(new InputStreamReader(dataInputStream));
		
		
			while ( (strLine = br.readLine()) != null)
			{
				collectInformation(strLine);
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
	 * Reads a line and collects information if available
	 * Parameter: String line
	 */
	private void collectInformation(String line)
	{
		Grade grade;
		Classroom classroom;
		PrimaryId gradeId;
		boolean end = false;
		splitLine = line.split(", ");
		
		if ( splitLine.length < 10) 
			end = true;
			
		LOG.debug(splitLine.length);
		
		if(!end) {
			
			gradeId = getGradeId(splitLine);

			PrimaryId classroomId = new PrimaryId(Long.parseLong(splitLine[CsvFields.CLASSROOM_ID.getValue()]));

			LOG.debug("gradeId: " + gradeId.getValue() + "classid: " + classroomId.getValue());

			//if the grade doesn't exist add it
			if (!school.gradeExists(gradeId)) {

				grade = new Grade(gradeId);
				school.addGrade(grade);
			}
			else {
				grade = school.getGrade(gradeId);
				LOG.debug("grade already added");
			}

			
			LOG.debug("classroomid " + classroomId.getValue());
			//check to see if the classroom exists
			if(!grade.classroomExists(classroomId))
			{
				LOG.debug("classroomid not added" + classroomId.getValue());
				PrimaryId teacherId = new PrimaryId(Long.parseLong(splitLine[CsvFields.TEACHER_1_ID.getValue()]));
				Person teacher = new Person(teacherId, splitLine[CsvFields.TEACHER_1_FIRST_NAME.getValue()], 
						splitLine[CsvFields.TEACHER_1_LAST_NAME.getValue()]);
				classroom = new Classroom(splitLine[CsvFields.CLASSROOM_NAME.getValue()], classroomId, teacher);

				grade.addClassroom(classroom);
				//check for a 2nd teacher
				if(!splitLine[CsvFields.TEACHER_2_ID.getValue()].equals(""))
				{
					PrimaryId teacher2Id = new PrimaryId(Long.parseLong(splitLine[CsvFields.TEACHER_2_ID.getValue()]));
					Person teacher2 = new Person(teacher2Id, splitLine[CsvFields.TEACHER_2_FIRST_NAME.getValue()], 
							splitLine[CsvFields.TEACHER_2_LAST_NAME.getValue()]);
					classroom.addTeacher(teacher2);
				}		 
			}
			else {
				classroom = grade.getClassroom(classroomId);
			}

			//check for a student
			if( !splitLine[CsvFields.STUDENT_ID.getValue()].equals("") )
			{
				PrimaryId studentId = new PrimaryId(Long.parseLong(splitLine[CsvFields.STUDENT_ID.getValue()]));
				Person student = new Person(studentId,splitLine[CsvFields.STUDENT_FIRST_NAME.getValue()], 
						splitLine[CsvFields.STUDENT_LAST_NAME.getValue()]);
				classroom.addStudent(student);
			}

		}
		 
	}
	
	/*
	 * Removes all whitespace at the end of a line
	 * Parameter: String line
	 */
    private String rightTrim(String line) 
    {
        return line.replaceAll("\\s+$", "");
    }
    
    /*
     * Takes the string array and returns the proper value of the gradeId. 
     * If the value is missing from the array it sets to a null value
     */
    private PrimaryId getGradeId(String splitLine[])
    {
    	PrimaryId gradeId;
    	
    	if(splitLine.length != CsvFields.values().length)
		{
			gradeId = new PrimaryId(School.NULL_GRADE);
		} 
		else {
			String curVal = rightTrim(splitLine[CsvFields.STUDENT_GRADE.getValue()]);
			gradeId = new PrimaryId(Long.parseLong(curVal));
		}
    	
    	return gradeId;
    }

}
