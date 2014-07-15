//package pixelHero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * should this class be immutable?
 * memory vs. time vs. parallelism considerations
 * assumes square array
 * 
 */

///TODO: add in timestep field
//TODO: add method to initialize by string
//TODO: add method to compute next state use conway rules

/*
 * right now interface is tied to internal representation -> how to generalize?
 * should this be a generic class?
 * 
 */
public class PixelHero{
private GameObject lattice[][]; //TODO: stores an int should this be an enum, or class?
private int rows;
private int columns;
private int currentStep;

	public PixelHero(int rows, int columns){
		currentStep = 0;
		if(rows <= 0 || columns <= 0)
		{
			throw new IllegalArgumentException("rows and columns must both be nonzero") ;
		}
		lattice = new GameObject[rows][columns];//assume Java initializes to 0
		this.rows = rows;
		this.columns = columns;
	};
	
	public PixelHero(GameObject [][] initialState){
		currentStep = 0;
		this.rows = initialState.length;
		this.columns = initialState[0].length;
		lattice = new GameObject[rows][columns];
		
		if(rows <= 0 || columns <= 0 || rows != columns )
		{
			throw new IllegalArgumentException("rows and columns must both be nonzero and equal length. rows: "+rows+" , columns: "+columns);
		}
		for(int row = 0; row < rows; row++)
		{
			for(int column = 0; column < columns; column++)
			{
				lattice[row][column] = initialState[row][column];
			}
		}	
		
	};
	
	public void reset()
	{
		for(int row = 0; row < rows; row++)
		{
			for(int column = 0; column < columns; column++)
			{
				lattice[row][column] = null;
			}
		}	
		currentStep = 0;
	}
	
	public void loadStateFromFile(File cellsFile)
	{
		BufferedReader reader = null;
		
		reset();

		try {
		    reader = new BufferedReader(new FileReader(cellsFile));
		    String text = null;
		    int rowIdx = 0;

		    while ((text = reader.readLine()) != null) {
		        //parse text string
		    	if(text.charAt(0) != '!'){//else is a comment
		    		
		    		for(int columnIdx = 0; columnIdx < text.length(); columnIdx ++)
		    		{
		    			if(columnIdx < columns && rowIdx < rows) 
		    			{
		    				if(text.charAt(columnIdx) == '.')
		    				{
		    					lattice[rowIdx][columnIdx] = null;
		    				} 
		    				else if(text.charAt(columnIdx) == 'O')
		    				{
		    					lattice[rowIdx][columnIdx] = null; //TODO: figure out what to instantiate
		    				}
		    			}
		    		}
		    		rowIdx++;
		    	}
		    	
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}
		
	}
	//TODO: rename this to something more appropriate
	public GameObject getStateAt(int row, int column)
	{
		return lattice[row][column];
	}
	
	
	/*
	 * return the size of 1st dimension (number of rows)
	 */
	public int getRows()
	{
		return rows;
	}
	/*
	 * return the size of 2nd dimension (number of columns)
	 */
	public int getColumns()
	{
		return columns;
	}
	/*
	 * since Java returns by reference here, this is leaking internals
	 * what can we do about it?
	 */
	
	public GameObject[][] getCurrentState()
	{
		return lattice;
	}
	
	public int getCurrentStep()
	{
		return currentStep;
	}
	
	public void computeNextState(){
		int nextState [][] = new int[rows][columns];
		
		//perform "concurrent" computation of next state
		for(int row = 0; row < rows; row++)
		{
			for(int column = 0; column < columns; column++)
			{
				//nextState[row][column] = applyRule(row,column,lattice); //TODO: figure out what to do here
			}
		}		
		
		//replace lattice with next state
		//lattice = nextState;		
		currentStep++;
	}
	
	
	
	//TODO: validate arguments
	private GameObject getLeft(int row, int column, GameObject [][] a_lattice)
	{
		if(column ==0)
		{
			return a_lattice[row][a_lattice[row].length - 1];
		}
		else
		{
			return a_lattice[row][column - 1];
		}
	}
	
	private GameObject getRight(int row, int column, GameObject [][] a_lattice)
	{
		if(column == a_lattice[row].length - 1)
		{
			return a_lattice[row][0];
		}
		else
		{
			return a_lattice[row][column + 1]; 
		}
		
	}
	
	private GameObject getUp(int row, int column, GameObject [][] a_lattice)
	{
		if(row == 0)
		{
			return a_lattice[a_lattice.length - 1][column];
		}
		else
		{
			return a_lattice[row-1][column]; 
		}
		
	}
	
	private GameObject getDown(int row, int column, GameObject [][] a_lattice)
	{
		if(row == a_lattice.length - 1)
		{
			return a_lattice[0][column];
		}
		else
		{
			return a_lattice[row+1][column]; 
		}
		
	}
	
	private int getUpRight(int row, int column, int [][] a_lattice)
	{
		if(row == 0)
		{
		
			if(column == a_lattice[row].length - 1)
			{
				return a_lattice[a_lattice.length - 1][0];
			}
			else
			{
				return a_lattice[a_lattice.length - 1][column + 1]; 
			}
			
		}
		else
		{
			
			if(column == a_lattice[row].length - 1)
			{
				return a_lattice[row-1][0];
			}
			else
			{
				return a_lattice[row-1][column + 1]; 
			}
		}
	
	}
	private GameObject getUpLeft(int row, int column, GameObject [][] a_lattice)
	{
		if(row == 0)
		{
		
			if(column ==0)
			{
				
				return a_lattice[a_lattice.length - 1][a_lattice[row].length - 1];
			}
			else
			{
				return a_lattice[a_lattice.length - 1][column - 1]; 
			}
			
		}
		else
		{
			
			if(column == 0)
			{
				return a_lattice[row-1][a_lattice[row].length - 1];
			}
			else
			{
				return a_lattice[row-1][column - 1]; 
			}
		}
	
	}
	private GameObject getDownRight(int row, int column, GameObject [][] a_lattice)
	{
		if(row == a_lattice.length - 1)
		{
		
			if(column == a_lattice[row].length - 1)
			{
				return a_lattice[0][0];
			}
			else
			{
				return a_lattice[0][column + 1]; 
			}
			
		}
		else
		{
			
			if(column == a_lattice[row].length - 1)
			{
				return a_lattice[row+1][0];
			}
			else
			{
				return a_lattice[row+1][column + 1]; 
			}
		}
	
	}
	private GameObject getDownLeft(int row, int column, GameObject [][] a_lattice)
	{
		if(row == a_lattice.length - 1)
		{
		
			if(column == 0)
			{
				return a_lattice[0][a_lattice[row].length - 1];
			}
			else
			{
				return a_lattice[0][column - 1]; 
			}
			
		}
		else
		{
			
			if(column == 0)
			{
				return a_lattice[row+1][a_lattice[row].length - 1];
			}
			else
			{
				return a_lattice[row+1][column - 1]; 
			}
		}
	
	}
	public String toString()
	{
		String result = "";
		for(int row = 0; row < rows; row++)
		{
			for(int column = 0; column < columns; column++)
			{
				result += getStateAt(row, column);
			}
			result += "\n";
		}
		return result;
	}
	
	public void placeGameObject(int row, int column, GameObject object)
	{
		lattice[row][column] = object;
	}
	
	public void removeGameObject(int row, int column)
	{
		lattice[row][column] = null;

	}
}
