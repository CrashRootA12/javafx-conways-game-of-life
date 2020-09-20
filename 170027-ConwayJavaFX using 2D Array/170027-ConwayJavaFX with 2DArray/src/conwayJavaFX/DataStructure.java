package conwayJavaFX;
/***
 * <b> The DataStructure Class: 2 D Boolean Array Implementation 
 * @author LRCarter
 */

public class DataStructure implements MemoryDataStructureInterface {

	private boolean cellIsAlive[][];
	private long numberOfRows;
	private long numberOfColumns;
	/***
	 * Constructs the array of dimensions height x width
	 * @param height number of rows
	 * @param width number of cols
	 */
	public DataStructure(long height, long width) {
		numberOfRows = height;
		numberOfColumns = width;
		int h = (int)height;
		int w = (int)width;
		cellIsAlive = new boolean[h][w];
	}
	/***
	 * This routine will take row index and column index as parameters and tell if the cell is alive or not.
	 * @param row The Row Index
	 * @param col The Col Index
	 * @return True if cell is Alive and False if cell is dead
	 */

	public boolean readDataStructure(long row, long col) {
		int r = (int)row;
		int c = (int)col;
		return cellIsAlive [c][r];
	}
	/***
	 * This routine will take row index, column index and boolean signifier (cell is alive or dead) and insert it to 
	 * the data-structure.
	 * @param row The row index
	 * @param col The col index
	 * @param isAlive True (Alive) or False (Dead)
	 */
	public void writeDataStructure(long row, long col, boolean isAlive) {
		int r = (int)row;
		int c = (int)col;
		cellIsAlive [c][r] = isAlive;
	}
	/***
	 * Getter for Data Structure Height
	 * @return the height of data structure
	 */
	public long getDataStructureHeight() {
		return numberOfRows;
	}
	/***
	 * Getter for Data Structure Width
	 * @return the width of data structure
	 */
	public long getDataStructureWidth() {
		return numberOfColumns;
	}

	/***
	 * Getter for Size Occupied by Data Structure
	 * @return Size in bytes
	 */
	public long getDataStructureSize() {
		long numberOfRows = cellIsAlive.length;
		long numberOfCols = cellIsAlive[0].length;
		long numberOfElements = numberOfCols*numberOfRows;
		long totalSize = numberOfElements * 2;
		return totalSize;
	}

}
