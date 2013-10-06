/**
 * FileReadStrategy.java
 * 
 * Interface for reading files and implementing Strategy Pattern
 */
package fileread;

import dataobjects.SchoolManager;

public interface FileReadStrategy
{
	/*
	 * Method for reading a given file.
	 * Returns a SchoolManager Object which contains all schools read by the file
	 */
	SchoolManager readFile(String fileName);
}
