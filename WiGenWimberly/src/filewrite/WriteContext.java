/**
 * WriteContext.java
 * 
 * Part of Strategy Pattern.
 * Allows the context to be set for file writing
 * 
 * Constructor:
 * public WriteContext(FileWriteStrategy strategy)
 * 
 * Public methods:
 * public void writeFile(SchoolManager schoolManager, String fileName)
 * public void changeContext(FileWriteStrategy strategy)
 * 
 */

package filewrite;

import dataobjects.SchoolManager;

public class WriteContext
{

	FileWriteStrategy strategy;
	
	/*
	 * Constructor
	 * 
	 * Parameter:
	 * FileWriteStrategy strategy
	 */
	public WriteContext(FileWriteStrategy strategy)
	{
		this.strategy = strategy;
	}

	
	/*
	 * Call the strategies writeFile method
	 */
	public void writeFile(SchoolManager schoolManager, String fileName)
	{
		strategy.writeFile(schoolManager, fileName);
	}
	
	/*
	 * Change context without creating a new instance
	 */
	public void changeContext(FileWriteStrategy strategy)
	{
		this.strategy = strategy;
	}
	
}
