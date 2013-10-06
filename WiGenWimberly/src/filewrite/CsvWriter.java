/**
 * CsvWriter.java
 * 
 * implements FileWriteStrategy
 * 
 * Constructor:
 * public CsvWriter()
 * 
 * Public Methods:
 * public void writeFile(SchoolManager schoolManager, String fileName)
 * 
 */

package filewrite;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import dataobjects.Classroom;
import dataobjects.Grade;
import dataobjects.Person;
import dataobjects.PrimaryId;
import dataobjects.School;
import dataobjects.SchoolManager;

public class CsvWriter implements FileWriteStrategy
{
	
	
	private static final Logger LOG =  Logger.getLogger(CsvWriter.class);
	private List<School> schoolList;
	
	/*
	 * Constructor
	 */
	public CsvWriter()
	{
		PropertyConfigurator.configure(getClass().getResource("log4j-filewrite.properties"));
	}

	
	
	@Override
	public void writeFile(SchoolManager schoolManager, String fileName)
	{
		BufferedWriter out;
		schoolList = schoolManager.getSchoolList();
		
		LOG.debug("CSV writer called");

		try
		{
			out = new BufferedWriter(new FileWriter(fileName));
			for (School s : schoolList)
			{
				writeSchool(s, out);
			}
			
			out.close();
		}
		catch (IOException e)
		{
			LOG.fatal("Unable to write file: " + fileName);
			System.exit(1);
		}

		
	}


	/*
	 * Write School information to file
	 * 
	 * Parameters:
	 * School s, BufferedWriter out 
	 * 
	 * Throws:
	 * IOException
	 */
	private void writeSchool(School s, BufferedWriter out) throws IOException
	{
		Grade curGrade;
		
		for (int i = 0; i < School.GRADE_SIZE; i++ )
		{
			if ( (curGrade = s.getGrade(new PrimaryId(i))) != null )
			{
				writeGrade(curGrade,out);

			}
			
		}
	}
	
	/*
	 * Loops grades to extract information
	 * 
	 * Parameters:
	 * Grade curGrade, BufferedWriter out 
	 * 
	 * Throws:
	 * IOException
	 */
	private void writeGrade(Grade curGrade, BufferedWriter out) throws IOException
	{
		Person teacher1 = null, teacher2 = null;
		
		LOG.debug("non null grade: " + curGrade.getPrimaryId().getValue());
		for (Classroom c : curGrade.getClassrooms())
		{
			//Check for 1 or 2 teachers.
			for (int j = 0; j < c.getTeacherList().size() && j < 2; j++)
			{
				if (j == 0)
					teacher1 = c.getTeacherList().get(j);
				else
					teacher2 = c.getTeacherList().get(j);
			}
			
		writePerson(teacher1, teacher2, c, curGrade,out);	
		
		teacher1 = null;
		teacher2 = null;
		}
	}
	
	/*
	 * Writes all the information to the file
	 * 
	 * Parameters:
	 * Person teacher1, Person teacher2, Classroom c, Grade curGrade, BufferedWriter out
	 * 
	 * Throws:
	 * IOException
	 */
	private void writePerson (Person teacher1, Person teacher2, Classroom c, Grade curGrade, BufferedWriter out) throws IOException
	{
		String outString;
		
		//loop student list and write info
		for (Person p : c.getStudentList())
		{
			outString = "" + c.getPrimarayId().getValue() + ", " + c.getClassName();
			outString += ", " + teacher1.getPrimaryId().getValue() + ", " + teacher1.getlName() + ", " + teacher1.getfName();
			if (teacher2 != null)
				outString += ", " + teacher2.getPrimaryId().getValue() + ", " + teacher2.getlName() + ", " + teacher2.getfName();
			else 
				outString += ", , , ";
			
			outString += ", " + p.getPrimaryId().getValue() + ", " + p.getlName() + ", " + p.getfName() + ", " + curGrade.getPrimaryId().getValue() + "\n";
			
			LOG.debug(outString);
			out.write(outString);
	
		}
	
		//if no students exist write other info
		if (c.getStudentList().size() == 0)
		{
			outString = "" + c.getPrimarayId().getValue() + ", " + c.getClassName();
			outString += ", " + teacher1.getPrimaryId().getValue() + ", " + teacher1.getlName() + ", " + teacher1.getfName();
			if (teacher2 != null)
				outString += ", " + teacher2.getPrimaryId().getValue() + ", " + teacher2.getlName() + ", " + teacher2.getfName();
			else 
				outString += ", , ,";
			outString += " , , " + curGrade.getPrimaryId().getValue() + "\n";
			
			out.write(outString);
			LOG.debug(outString);
			
		}
		
	}
	

}
