package conwayJavaFX;

import java.util.ArrayList;

import java.util.Arrays;
/***
 * <b> The DataStructure Class: Sparse Array Implementation Using 2D Integer Array </b>
 * <p> Description: This data structure is used as base object in the Conway's Game of Life which only saves the
 * cells which are alive. This implementation of saving only alive cells reduces the space used by objects by a very large extent
 * and is a Sparse Matrix Implementation. Each cell will be saved as {row, col} array. 
 * @author JSGREWAL 2019-03-07 Implemented 2D Sparse Integer Array
 */

public class DataStructure implements MemoryDataStructureInterface {

	private int[][] cellIsAlive; // This integer 2D array will be used to store row-column pairs of alive cells.
	private long numberOfRows; // The Number of Rows
	private long numberOfColumns; // The Number of Columns
	/***
	 * Constructor of the Sparse Matrix
	 * @param height initial number of rows 
	 * @param width initial number of columns
	 */
	public DataStructure(long height, long width) {
		numberOfRows = height;
		numberOfColumns = width;
		int h = (int)height;
		int w = (int)width;
		// For Sparse Matrix Initialization Purpose Only
		cellIsAlive = new int[h][w];
	}
	//----------------------------------------------------------------------------------------------------------
	//---------------- The Controller Layer Methods-------------------------------------------------------------
	/***
	 * The readDataStructure routine takes the row and column index of a cell as input
	 * and tells if the cell is alive or not.
	 * According to the sparse-matrix implementation, if the cell is *present* in the
	 * cellIsAlive 2D integer array, then it is alive and if it is *absent* then it is dead.
	 * @param row The row index of cell
	 * @param col The col index of cell
	 * @return True if Cell is Alive and False if Cell is not alive.
	 */
	public boolean readDataStructure(long row, long col) {
		int r = (int)row;
		int c = (int)col;
		int[] theArray = {r,c};
		for (int[] presentArray: cellIsAlive) if (Arrays.equals(presentArray, theArray)) return true;
		return false;
	}

	/***
	 * The writeDataStructure routine takes the row index, column index and boolean value telling if the
	 * input cell is alive and then inserts it to the cellIsAlive data-structure *if the cell is alive*.
	 * In case the cell is not alive, then according to the sparse-matrix implementation, it won't be part 
	 * of this 2D Array.
	 * @param row Row index of Cell
	 * @param col Col index of Cell
	 * @param isAlive True (Alive) or False (Dead)
	 * */
	public void writeDataStructure(long row, long col, boolean isAlive) {
		int r = (int)row;
		int c = (int)col;
		int[] theArray = {r,c};
		boolean alreadyAlive=false;

		//Convert the array to List
		ArrayList<int[]> theList = new ArrayList<int[]>();
		for (int i=0; i<cellIsAlive.length;i++) { 
			theList.add(cellIsAlive[i]);
		}
		// Check if the element is already present in the list
		for (int j=0;j<theList.size();j++) {
			if (Arrays.equals(theList.get(j),theArray)) {
				alreadyAlive=true; break;
			}
		}
		// If the input cell is alive, then put it into the array
		if (isAlive) {
			if (!alreadyAlive) theList.add(theArray); 
		}
		// If the input cell is dead, then remove it from the array
		else {
			if(alreadyAlive) theList.remove(theArray);
		}

		// Again, convert the list back to array
		cellIsAlive = new int[theList.size()][2];
		for (int ndx =0; ndx<theList.size();ndx++) {
			cellIsAlive[ndx]=theList.get(ndx);
		} 


	}
	//----------------------------------------------------------------------------------------------------------

	//---------------- Getters ---------------------------------------------------------------------------------
	/***
	 * Getter for Number of Rows in Hashtable
	 */

	public long getDataStructureHeight() {
		return numberOfRows;
	}
	/***
	 * Getter for Number of Cols in Hashtable
	 */
	public long getDataStructureWidth() {
		return numberOfColumns;
	}

	/***
	 * Getter for Size of Data Structure in Bytes
	 * @return Size in bytes
	 */
	public long getDataStructureSize() {
		long numRows = cellIsAlive.length;
		long numCols = 2;
		long numberOfElements = numCols*numRows;
		long totalSize = numberOfElements * 4;
		return totalSize;
	} 

}
