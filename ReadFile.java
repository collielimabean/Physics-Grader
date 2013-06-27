package rebuilt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile 
{
	
	private File input;
	
	private ArrayList<String[]> raw;
	private String[] StudentNames;
	private int[] RawMultipleChoice;
	private int[] RawFreeResponse;
	private String date;
	private int testType;
	private int totalNumber;
	
	public static final int[] MAX_MC = {39, 20, 15};
	public static final int[] MAX_FR = {45, 25, 15};
	
	public ReadFile(String filePath)
	{
		input = new File(filePath);
	}
	
	public void process()
	{
		
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(input));
			
			String dataRow = reader.readLine(); 
	          
		    raw = new ArrayList<String[]>(); 
		          
	        while (dataRow != null) 
	        { 
	              
	        	raw.add(dataRow.split(",")); 
	              
	            dataRow = reader.readLine();
	            
	        } 
	          
	         reader.close(); 
		} 
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Parses all student information as well as checks if information is valid
	 * @throws IllegalArgumentException
	 */
	public void parse() throws IllegalArgumentException
	{
		
		StudentNames = new String[raw.size() - 5];
		RawMultipleChoice = new int[raw.size() - 5];
		RawFreeResponse = new int[raw.size() - 5];
		
		//get date
		date = raw.get(0)[1];
		
		//get test type
		testType = Integer.parseInt(raw.get(1)[1]);	
		
		//check test type
		if( !(testType == 0 || testType == 1 || testType == 2))
		{
			throw new IllegalArgumentException("Test Type is set incorrectly!");
		}
		
		//get total number of multiple choice in test
		totalNumber = Integer.parseInt(raw.get(2)[1]);
		
		//check if totalNumber is valid
		if(!(totalNumber > 0 && totalNumber <= MAX_MC[testType]))
		{
			throw new IllegalArgumentException("Total Number of Multiple Choice is set incorrectly!");
		}
		
		//parse student information
	    for(int i = 0; i < raw.size() - 5; i++) 
	    { 
	    	
	        StudentNames[i] = raw.get(i+5)[0]; 
	        RawMultipleChoice[i] = Integer.parseInt(raw.get(i+5)[1]); 
	        RawFreeResponse[i] = Integer.parseInt(raw.get(i+5)[2]); 
	         
	    } 
	    
	    //check multiple choice scores
	    for(int i = 0; i < RawMultipleChoice.length; i++)
	    {
	    	
	    	if(RawMultipleChoice[i] > totalNumber)
	    	{
	    		throw new IllegalArgumentException(StudentNames[i] + " has an MC score larger than the total Number of MC!");
	    	}
	    	
	    	if(RawMultipleChoice[i] <= 0)
	    	{
	    		throw new IllegalArgumentException(StudentNames[i] + " has an MC score less than or equal to 0!");
	    	}
	    	
	    }
	    
	    //check free response scores
	    for(int i = 0; i < RawFreeResponse.length; i++)
	    {
	    	
	    	if(RawFreeResponse[i] > MAX_FR[testType])
	    	{
	    		throw new IllegalArgumentException("Student at index " + (i+6) + " has an FR score greater than the maximum!");
	    	}
	    	
	    	else if(RawFreeResponse[i] < 0)
	    	{
	    		throw new IllegalArgumentException("Student at index " + (i+6) + " has an FR score less than 0!");
	    	}
	    }
	    
	}
	
	public ArrayList<String[]> getRaw()
	{
		return raw;
	}
	
	public String[] getStudentNames()
	{
		return StudentNames;
	}
	
	public int[] getRawMCScores()
	{
		return RawMultipleChoice;
	}
	
	public int[] getRawFreeResponseScores()
	{
		return RawFreeResponse;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public int getTestType()
	{
		return testType;
	}
	
	public int getNumberOfMultipleChoice()
	{
		return totalNumber;
	}
	
}
