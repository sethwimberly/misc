/**
 * XmlWriter.java
 * 
 * implements FileWriteStrategy
 * 
 * Constructor:
 * public XmlWriter()
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


public class XmlWriter implements FileWriteStrategy
{

	private static final Logger LOG =  Logger.getLogger(XmlWriter.class);
	private static final String XML_VERSION = "<?xml version=\"1.0\"?>\n\n\n";
	private static final String XML_NS = "http://www.wirelessgeneration.com/wgen.xsd";
	private static final String SCHOOL_INDENT = "";
	private static final String GRADE_INDENT = "\t";
	private static final String CLASSROOM_INDENT = "\t\t";
	private static final String PERSON_INDENT = "\t\t\t";
	
	private List<School> schoolList;
	
	
	
	public XmlWriter()
	{
		PropertyConfigurator.configure(getClass().getResource("log4j-filewrite.properties"));
		
	}
	
	@Override
	public void writeFile(SchoolManager schoolManager, String fileName)
	{
		BufferedWriter out;
		schoolList = schoolManager.getSchoolList();
		
		try
		{
			out = new BufferedWriter(new FileWriter(fileName));
			
			out.write(XML_VERSION);
			
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
	 * writes element specific to school info then passes to others
	 * 
	 * Parameters: School s, BufferedWriter out
	 */
	private void writeSchool(School s, BufferedWriter out) throws IOException
	{
		Grade curGrade;
		String toWrite = SCHOOL_INDENT + "<school id=\"" + s.getPrimaryId().getValue() + "\"";
		toWrite += " name=\"" + s.getName() + "\" xlmns=\"" + XML_NS + "\">\n";
		
		out.write(toWrite);
		LOG.debug("wrote: " + toWrite);
		
		for (int i = 0; i < School.GRADE_SIZE; i++ )
		{
			if ( (curGrade = s.getGrade(new PrimaryId(i))) != null )
			{
				writeGrade(curGrade, out);
			}
			
		}
		
		out.write(SCHOOL_INDENT+"</school>\n");
	}
	

	/*
	 * Writes grade information to file
	 * 
	 * Parameters: 
	 * Grade g, BufferedWriter out
	 * 
	 * Throws:
	 * IOException
	 */	
	private void writeGrade(Grade g, BufferedWriter out) throws IOException
	{
		
		List<Classroom> rooms;
		String toWrite;
		long gradeId = g.getPrimaryId().getValue();
		if (gradeId == 13)
		{
			toWrite = GRADE_INDENT + "<grade id=\"none\"> \n";
		}
		else 
		{
			toWrite = GRADE_INDENT + "<grade id=\"" + g.getPrimaryId().getValue() + "\"> \n";
		}
		
		
		out.write(toWrite);
		LOG.debug("wrote: " + toWrite);
		
		rooms = g.getClassrooms();
		LOG.debug("rooms size " + rooms.size());
		
		for (Classroom room : rooms)
		{
			writeClassroom(room, out);
		}
		
		out.write(GRADE_INDENT + "</grade>\n");
		
		
	}
	
	/*
	 * Writes classroom information to file
	 * 
	 * Parameters: 
	 * Classroom c, BufferedWriter out
	 * 
	 * Throws:
	 * IOException
	 */
	private void writeClassroom(Classroom c, BufferedWriter out) throws IOException
	{
		List<Person> teacherList = c.getTeacherList();
		List<Person> studentList = c.getStudentList();
		
		String toWrite = CLASSROOM_INDENT + "<classroom id=\"" + c.getPrimarayId().getValue();
		toWrite += "\" name=\"" + c.getClassName() + "\">\n";
		
		out.write(toWrite);
		LOG.debug("wrote: " + toWrite);
		
		for(Person p : teacherList)
		{
			writePerson(p, out, true);
		}
		
		out.write("\n");
		
		for(Person p: studentList)
		{
			writePerson(p, out, false);
		}
		
		out.write(CLASSROOM_INDENT + "</classroom>\n");
		
	}
	
	
	/*
	 * Writes people information to file
	 *  
	 * Parameters: 
	 * Person p, BufferedWriter out, boolean isTeacher
	 * 
	 * Throws:
	 * IOException
	 */
	 private void writePerson(Person p, BufferedWriter out, boolean isTeacher) throws IOException
	 {
		 String toWrite = PERSON_INDENT +  (isTeacher ? "<teacher id=\"" : "<student id=\"");
		 toWrite += p.getPrimaryId().getValue() + "\" first_name=\"" + p.getfName() + "\" last_name=\"" + p.getlName() + "\"/>\n";
		 
		 out.write(toWrite);
		 LOG.debug("wrote: " + toWrite);
	 }

}
