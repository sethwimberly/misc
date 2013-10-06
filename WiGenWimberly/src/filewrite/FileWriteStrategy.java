/**
 * FileWriteStrategy.java
 * 
 * Interface to implement strategy pattern and ensure that all file writers are used the same
 * 
 */

package filewrite;

import dataobjects.SchoolManager;

public interface FileWriteStrategy
{
	void writeFile(SchoolManager schoolManager, String fileName);
}
