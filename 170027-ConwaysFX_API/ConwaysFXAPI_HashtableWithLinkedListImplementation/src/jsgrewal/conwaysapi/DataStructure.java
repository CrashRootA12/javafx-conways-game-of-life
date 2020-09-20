package jsgrewal.conwaysapi;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;
/***
 * <b> The DataStructure Class: Sparse Array Implementation Using Hashtable Data Structure </b>
 * <p> Description: This data structure is used as base object in the Conway's Game of Life which only saves the
 * cells which are alive. This implementation of saving only alive cells reduces the space used by objects by a very large extent
 * and is a Sparse Matrix Implementation. For faster searching and fetching value, the DataStructure is implemented using Hashtables
 * and the hash-collisions are prevented by implementing LinkedLists so that in case multiple values get assigned to the same key, the values
 * will form a Linked List and thus, prevent hash collisions.
 * 
 * @author JSGREWAL 2019-03-07 Implemented Hashtable With LinkedList
 */

public class DataStructure implements MemoryDataStructureInterface {

	// --------------------------------------------------------------------------------------------------------
	// The Hash-Table Data Structure which is going to save the location of
	// alive cells in the board. 

	private Hashtable<Integer,LinkedList<Integer>> cellIsAlive ; // The data-structure in which alive cells will be stored
	//----------------------------------------------------------------------------------------------------------
	private long numberOfRows; // Number of Rows in Hash-Table
	private long numberOfColumns; // Number of Columns in Hash-Table
	//----------------------------------------------------------------------------------------------------------

	/***
	 * The basic constructor of the Data-Structure Object takes the height (number of rows) and width (number of cols)
	 * of the Hash-Table as parameters and set-up a hash-table of same dimensions. 
	 * 
	 * This Hashtable is having {Row Index} as keys and {LinkedList of Column Indexes} as values.
	 * For example if the cells (2, 4), (2,5) and (3,7) are alive, then the following Hashtable will look like:
	 *
	 *
	 *<table><caption>The Sparse Matrix </caption><tr><th>Key</th><th>Value</th></tr><tr><td>2<br></td><td>LinkedList(4,5)</td></tr><tr><td>3</td><td>LinkedList(3)</td></tr></table>
	 * @param height Height (Number of Rows) of Hashtable
	 * @param width Width (Number of Cols) of Hashtable
	 */
	public DataStructure(long height, long width) {
		numberOfRows = height;
		numberOfColumns = width;
		// Since we are going to use hashtable for saving memory, we will dynamically use it
		cellIsAlive =new Hashtable<Integer,LinkedList<Integer>>();		
	}
	//----------------------------------------------------------------------------------------------------------
	//---------------- The Controller Layer Methods-------------------------------------------------------------
	/***
	 * The readDataStructure routine takes the row and column index of a cell as input
	 * and tells if the cell is alive or not.
	 * According to the sparse-matrix implementation, if the cell is *present* in the
	 * cellIsAlive hash-table, then it is alive and if it is *absent* then it is dead.
	 * @param row The row index of cell
	 * @param col The col index of cell
	 * @return True if Cell is Alive and False if Cell is not alive.
	 */
	public boolean readDataStructure(long row, long col) {
		int r = (int)row; 
		int c = (int) col;
		// If there is no cell associated with the entered row index, then return false
		if (!cellIsAlive.containsKey(r)) return false; 
		// If there are  cells associated with the entered row index, then check if the
		// entered column index related to the entered row index is present in the structure.
		// If yes, return true
		if (cellIsAlive.get(r).contains(c)) return true;
		// Otherwise, cell is not alive return false
		return false;
	}
	/***
	 * The writeDataStructure routine takes the row index, column index and boolean value telling if the
	 * input cell is alive and then inserts it to the cellIsAlive data-structure *if the cell is alive*.
	 * In case the cell is not alive, then according to the sparse-matrix implementation, it won't be part 
	 * of this Hashtable.
	 * @param row Row index of Cell
	 * @param col Col index of Cell
	 * @param isAlive True (Alive) or False (Dead)
	 */
	public void writeDataStructure(long row, long col, boolean isAlive) {
		int r = (int)row;
		int c = (int) col;
		if (isAlive) { 		// If the input cell is alive
			if (cellIsAlive.containsKey(r)) { // If there are other columns (alive) of same row in the structure 
				LinkedList<Integer>theListAssociatedWithThisRow = cellIsAlive.get(r); // Fetch all the columns
				if (theListAssociatedWithThisRow.contains(c)) // If input column is already inside the list
					return;  // Then do nothing
				else { 
					theListAssociatedWithThisRow.add(c); // Otherwise add the column to the list
					cellIsAlive.put(r, theListAssociatedWithThisRow); // and assign the updated list to the same row
				}
			} else { // If there are no other columns of same row, meaning that the row is absent in the sparse matrix
				LinkedList<Integer> theListAssociatedWithThisRow = new LinkedList<Integer>(); // Create a new list
				theListAssociatedWithThisRow.add(c); // with the input column in it
				cellIsAlive.put(r, theListAssociatedWithThisRow); // and assign it to the new data structure
			}
		}
		else { // If the input cell is dead, that means it *must not* be in this sparse array 
			if (cellIsAlive.containsKey(r)) { // If the input row is present in the sparse matrix
				LinkedList <Integer>theListAssociatedWithThisRow = cellIsAlive.get(r); // Fetch all the columns related to that row

				if (theListAssociatedWithThisRow.contains(c)) { // If the input column is present in the list 
					theListAssociatedWithThisRow.remove(c);  // then remove it from the list
					if (theListAssociatedWithThisRow.isEmpty() ) { // In case the list is now empty
						cellIsAlive.remove(r); // Remove it from the data-structure
					}
					cellIsAlive.put(r, theListAssociatedWithThisRow); // and assign the updated list to the data-structure

				} 
				else // The cell is already absent in the structure so
					return; // do nothing.
			}
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
		// First, get the number of rows in the data-structure
		long numberOfRows = (long)cellIsAlive.size();
		long numberOfCols = 0;
		// Calculate the size occupied by rows
		long sizeOccupiedByRows = numberOfRows * 4L;
		//Convert the Key-Value pairs to a Set
		Set<Integer> theKeySet = cellIsAlive.keySet();
		// Iterate through each pair and fetch the number of cols
		for (Integer i: theKeySet) {
			LinkedList<Integer> l = cellIsAlive.get(i);
			numberOfCols+=l.size();
		}
		// Calculate the size occupied by cols
		long sizeOccupiedByCols = numberOfCols*4L;
		// Calculate the total size
		long totalSizeOccupiedByObject = sizeOccupiedByRows+sizeOccupiedByCols;
		return totalSizeOccupiedByObject;
	}
	


}
