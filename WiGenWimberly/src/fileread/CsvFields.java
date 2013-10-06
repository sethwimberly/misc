/**
 * CsvFields.java
 * 
 * Enum for indexing array based on CSV results
 * 
 * public method:
 * public int getValue()
 * 
 */
package fileread;

public enum CsvFields
{
	CLASSROOM_ID (0),
	CLASSROOM_NAME (1), 
	TEACHER_1_ID (2), 
	TEACHER_1_LAST_NAME (3), 
	TEACHER_1_FIRST_NAME (4), 
	TEACHER_2_ID (5), 
	TEACHER_2_LAST_NAME (6), 
	TEACHER_2_FIRST_NAME (7), 
	STUDENT_ID (8), 
	STUDENT_LAST_NAME (9), 
	STUDENT_FIRST_NAME (10), 
	STUDENT_GRADE (11);
	
	private final int value;
	
	private CsvFields(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
	
}
