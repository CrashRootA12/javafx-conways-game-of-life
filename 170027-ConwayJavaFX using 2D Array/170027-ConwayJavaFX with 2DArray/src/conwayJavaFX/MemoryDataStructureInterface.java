package conwayJavaFX;
/****
 * <b> Title: The Memory Data Structure Interface </b>
 * <p>Description: This interface contains various methods related to storing and retrieving the state of live or dead cells
 * in the Conway's Game of Life.
 * @author LRCarter, Jaskirat
 * 
 */
public interface MemoryDataStructureInterface {
	/***
	 * This routine will take row index and column index as parameters and tell if the cell is alive or not.
	 * @param row The Row Index
	 * @param col The Col Index
	 * @return True if cell is Alive and False if cell is dead
	 */
	public boolean readDataStructure(long row, long col);
	
	/***
	 * This routine will take row index, column index and boolean signifier (cell is alive or dead) and insert it to 
	 * the data-structure.
	 * @param row The row index
	 * @param col The col index
	 * @param isAlive True (Alive) or False (Dead)
	 */
	public void writeDataStructure(long row, long col, boolean isAlive);

	/***
	 * Getter for Data Structure Height
	 * @return the height of data structure
	 */
	public long getDataStructureHeight();
	
	/***
	 * Getter for Data Structure Width
	 * @return the width of data structure
	 */
	public long getDataStructureWidth();
	/***
	 * Getter for Size Occupied by Data Structure
	 * @return Size in bytes
	 * @author Jaskirat
	 */
	public long getDataStructureSize();

}
