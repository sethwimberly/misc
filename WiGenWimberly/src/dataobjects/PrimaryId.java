/**
 * PrimaryId.java
 * 
 * Primary Id object. Maintains id as a long value;
 * 
 * Constructor:
 * public PrimaryId(long primaryId)
 * 
 * Public Method:
 * public long getValue()
 */
package dataobjects;

public class PrimaryId
{

	private long primaryId;
	
	public PrimaryId(long primaryId)
	{
		this.primaryId = primaryId;
	}

	public long getValue()
	{
		return primaryId;
	}
	
	
}
