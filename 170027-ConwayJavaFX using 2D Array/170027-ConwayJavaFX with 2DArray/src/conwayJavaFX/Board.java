package conwayJavaFX;

import java.util.Scanner;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/*******
 * <p> Title: Board Class. </p>
 * 
 * <p> Description: A JavaFX demonstration application: This entity class describes the board that
 * enables Conway's Game of Life </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2018 </p>
 * 
 * @author Lynn Robert Carter, Jaskirat
 * 
 * @version 2.00	2018-04-27 An implementation baseline for JavaFX graphics
 * @version 2.01	2019-03-10 Implementation of Performance Analysis Routines
 * 
 * 
 */

public class Board {
	
	
		
	// For space-time complexity evaluation
	static boolean firstStep=true;
	final long MEGABYTES = 1024L*1024L;
	long initiationTime = 0; long endingTime = 0;
	/**********************************************************************************************

	Class Attributes

	 **********************************************************************************************/

	// These are the major attribute values for this application
	protected DataStructure cellIsAlive;
	
	
	/**********************************************************************************************

	Constructors

	 **********************************************************************************************/

	/**********
	 * This is the default constructor.  We do not expect it to be used.
	 */
	public Board () {
		cellIsAlive = new DataStructure(3, 3);				// Initialized to false by default;
	}

	/**********
	 * This constructor creates a board of a specific size. This constructor is used to produce 
	 * instances of a board for the purposes of animation.
	 */
	public Board (int height, int width) {
		cellIsAlive = new DataStructure(height, width);		// Initialized to false by default;
	}

	/**********
	 * This constructor creates a board of a specific size and the initializes if based on input
	 * from a specified scanner. This method assumes that the format of the data in the file has
	 * already been checked by the user interface and any errors in the format would have been
	 * dealt with by that code.
	 */
	public Board (int height, int width, Scanner inputReader) {
		cellIsAlive = new DataStructure(height, width);		// Initialized to false by default;

		// The input is a sequence of lines, two integer values per line. Each pair is the x
		// and the y coordinate where a living cell should be placed.
		while (inputReader.hasNextLine()) {
			String inputLine = inputReader.nextLine();	// Keep reading lines until they are gone
			Scanner input = new Scanner(inputLine);		// Set up a scanner for each line
			int x = input.nextInt();					// Extract the pair of coordinates
			int y = input.nextInt();
			cellIsAlive.writeDataStructure(y, x, true);	// Signal that that cell is alive
			input.close();								// Close the Scanner
		}
		//After writing the data, calculate object size.
		System.out.println("The size of object (2D Array) is "+fetchObjectSize()+" bytes");
	}

	

	/**********************************************************************************************

	Standard support methods

	 **********************************************************************************************/

	/**********
	 * This is the debugging toString method, written to support boards up through 999 rows.
	 */
	public String toString() {
		String result = "";								// Insert the right number of zeros so the
		for (int y = 1; y < cellIsAlive.getDataStructureHeight()-1; y++) {		// element in the board align with
			if (y<10) result += "[00" + y + "] ";				// one another
			else if (y<100) result += "[0" + y + "] ";
			else result += "[" + y + "] ";
			for (int x = 1; x < cellIsAlive.getDataStructureWidth()-1; x++)
				if (cellIsAlive.readDataStructure(x, y)) result += "*";	// Display an asterisk if the cell is alive
				else result += " ";						// else display a blank
			result += "\n";								// Append an end of line for the next row
		}
		return result;									// Return the string to the caller
	}

	/**********************************************************************************************

	Board oriented methods

	 **********************************************************************************************/

	/**********
	 * This method places a number of black squares into a specified window pane, one for each live
	 * cell on *this* board.
	 */
	public void populateCanvas(Pane p) {
		for (int x = 1; x < cellIsAlive.getDataStructureWidth()-1; x++)
			for (int y = 1; y < cellIsAlive.getDataStructureHeight()-1; y++)
				if (cellIsAlive.readDataStructure(x, y)) {
					Rectangle rectangle = new Rectangle(5,5,Color.BLACK);
					rectangle.relocate(6*x, 6*y);
					p.getChildren().add(rectangle);
				}
	}

	/**********
	 * This method determines which cells are alive for *this* board based on which cells were 
	 * alive on the previous board.
	 * 
	 * The method creates a new board (each element defaults to false) and then examines each cell
	 * in the previous board that is not on the boundary to see how many of the 8 potential cells 
	 * are currently alive.  If the examined cell *was* alive, then it must have at least two but
	 * no more than three alive neighbors to stay alive.  If the examined cell *was not* alive on
	 * the previous board, it must have exact three alive neighbors on the previous board for the
	 * cell to be alive on *this* board.
	 */
	public void step (Board previous) {
		
		 
		if (firstStep) {
			initiationTime = System.currentTimeMillis();

		}

		// Allocate a new copy of this board so all elements are false
		cellIsAlive = new DataStructure (cellIsAlive.getDataStructureWidth(), cellIsAlive.getDataStructureWidth());

		// Iterate through all of the elements on the previous boards, not on the boundary
		for (int x = 1; x < cellIsAlive.getDataStructureWidth()-1; x++)
			for (int y = 1; y < cellIsAlive.getDataStructureHeight()-1; y++) {

				// Count the number of alive neighbor cells on the previous board
				int numberAliveCells = 0;
				if (previous.cellIsAlive.readDataStructure(x-1, y-1)) numberAliveCells++;
				if (previous.cellIsAlive.readDataStructure(x-1, y)) numberAliveCells++;
				if (previous.cellIsAlive.readDataStructure(x-1, y+1)) numberAliveCells++;
				if (previous.cellIsAlive.readDataStructure(x, y-1)) numberAliveCells++;
				if (previous.cellIsAlive.readDataStructure(x, y+1)) numberAliveCells++;
				if (previous.cellIsAlive.readDataStructure(x+1, y-1)) numberAliveCells++;
				if (previous.cellIsAlive.readDataStructure(x+1, y)) numberAliveCells++;
				if (previous.cellIsAlive.readDataStructure(x+1, y+1)) numberAliveCells++;

				// If the cell was alive, it must have 2 or 3 alive neighbors to remain alive
				if (previous.cellIsAlive.readDataStructure(x, y)) { 
					if (numberAliveCells >= 2 && numberAliveCells <= 3)
						this.cellIsAlive.writeDataStructure(x, y, true);
					// If the cell was alive, and it does not have exactly 2 or 3 neighbors, it should die
					else this.cellIsAlive.writeDataStructure(x, y, false);	
					// if the cell was not alive, it must have exactly 3 alive neighbors to give birth
				} else if (numberAliveCells == 3)
					this.cellIsAlive.writeDataStructure(x, y, true);
			}
		
		if (firstStep) {
			firstStep=false;
		}

	}
	protected static void firstStepIsDone() {
		firstStep=false;
	}
	/***
	 * Getter for Size of Object
	 * @return Size of Object
	 */
	private long fetchObjectSize() {

		return cellIsAlive.getDataStructureSize();
	}
	/***
	 *  This routine calculates the time taken for processing 100 Generations
	 */
	public void fetchPerformance_Time() {
		endingTime = System.currentTimeMillis();
		System.out.println(("Time taken for processing 100 generations (Using 2D Array): "
		+(endingTime-initiationTime)+" nanoseconds"));		
		
	}

}
