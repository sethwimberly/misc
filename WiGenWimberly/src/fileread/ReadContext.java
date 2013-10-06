/**
 * ReadContext.java
 * 
 * Allows for a read context to be specified and changed. Part of Strategy pattern
 * 
 * Constructor:
 * public ReadContext(FileReadStrategy strategy)
 * 
 * Public Methods:
 * public SchoolManager readFile(String fileName)
 * public void changeContext(FileReadStrategy strategy)
 * 
 */
package fileread;

import dataobjects.SchoolManager;

public class ReadContext
{

	FileReadStrategy strategy;
	
	/*
	 * Constructor
	 * 
	 * Parameter:
	 * FileReadStrategy strategy
	 */
	public ReadContext(FileReadStrategy strategy)
	{
		this.strategy = strategy;
	}

	
	/*
	 * Call the strategies readfile method
	 */
	public SchoolManager readFile(String fileName)
	{
		return strategy.readFile(fileName);
	}
	
	/*
	 * Change context without creating a new instance
	 */
	public void changeContext(FileReadStrategy strategy)
	{
		this.strategy = strategy;
	}
	
	
}
