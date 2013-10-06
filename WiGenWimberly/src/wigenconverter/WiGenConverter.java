/**
 * Seth Wimberly
 * 11/23/11
 * 
 * WiGenConverter.java 
 * Program takes an xml or csv file as input. Parses data and writes to the other version.
 * If additional formats need to be supported add a file reader or writer that implements the reader 
 * interface or the writer interface.
 * 
 * See README for more information
 * 
 */

package wigenconverter;

import dataobjects.SchoolManager;
import fileread.CsvReader;
import fileread.ReadContext;
import fileread.XmlReader;
import filewrite.CsvWriter;
import filewrite.WriteContext;
import filewrite.XmlWriter;




//import fileread.XmlReader;

public class WiGenConverter
{

	
	public static void main(String[] args)
	{
		String outputFile = "";
		
		if (args.length < 1 || args.length > 2)
		{
			printUsage();
			System.exit(1);
		}
		
		if (args.length == 2)
		{
			outputFile = args[1];
		}
		
		String fileExtension[] = args[0].split("\\.");
		ReadContext readContext = null;
		WriteContext writeContext = null;
		SchoolManager manager;
		
		
		if (fileExtension[1].equalsIgnoreCase("txt")) {
			readContext = new ReadContext(new CsvReader());
			writeContext = new WriteContext(new XmlWriter());
			if (outputFile.equals(""))
				outputFile = "output.xml";
		} 
		else if (fileExtension[1].equalsIgnoreCase("xml")) {
			readContext = new ReadContext(new XmlReader());
			writeContext = new WriteContext(new CsvWriter());
			if (outputFile.equals(""))
				outputFile = "output.txt";
		}
		else {
			printUsage();
			System.out.println("Unsupported file type");
			System.exit(1);
		}
		
		
		manager = readContext.readFile(args[0]);
		
		writeContext.writeFile(manager, outputFile);
		
		
		
	}
	
	private static void printUsage()
	{
		System.out.println("\nTo use must specify a filename to read.");
		System.out.println("java WiGenConverter [filename] <outputFile>\n");
	}

}
