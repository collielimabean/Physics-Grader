package rebuilt;

public class Scale 
{
	
	/** 
	* Used for tests and semester exams with appropriate scaling
	* unscaled top, scaled bottom - 100 pts max 
	*/ 
	public static final double[][] TestScoreScaler = 
	{  
	     
	{20,24,27,29.9,32,34,36,38,40,41,44,47,49,50,51,54,57,58,59,60,62,64,66,69,70,73,76,79,82,85,88,91,94,97,100}, 
	{60,64,66,67,70.5,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100}, 
	 
	}; 
	 
	/** 
	* Used for quizzes with appropriate scaling
	* unscaled top, scaled bottom - 30 pts max 
	*/ 
	public static final double[][] QuizScoreScaler = 
	{ 
	     
	{6,7,8,9,9.8,10.5,11.2,11.8,12.2,12.5,13.0,13.7,14.3,15.0,15.8,16.3,16.8,17.3,17.8,18.2,18.5,19.0,19.5,20.0,20.7,21.7,22.7,24.0,24.8,25.5,26.3,27.2,28.2,29.2,30}, 
	{18,19,19.5,20.1,21.0,21.3,21.6,21.9,22.2,22.5,22.8,23.1,23.4,23.7,24.0,24.3,24.6,24.9,25.2,25.5,25.8,26.1,26.4,26.7,27.0,27.3,27.6,27.9,28.2,28.5,28.8,29.1,29.4,29.7,30}, 
	     
	}; 
	
	/**
	 * This array contains the multiplier for free response scores. They are sorted by test type.
	 */
	public static final double[] FREE_RESPONSE_SCALER = {(50.0 / 45.0), 1 , 1};
	
	/**
	 * Scaled Multiple Choice total scores.
	 */
	public static final int[] numberOfMCScaled = {50, 25, 15};
	
	/**
	 * Given a key and a scale, this method returns the values higher and lower of the key in an integer array.
	 * @param key
	 * @param scale
	 * @return int[] of neighbor elements
	 */
	public static int[] getNeighborElements(double key, double[][] scale)
	{
		
		int lower = -1;
		int higher = -1;
		
		for(int i = 0; i < scale[0].length; i++)
		{
			
			if(key >= scale[0][i])
			{
				lower = i;
			}
			
		}
		
		for(int i = scale[0].length - 1; i >= 0; i--)
		{
			
			if(key <= scale[0][i])
			{
				higher = i;
			}
			
		}
		
		int[] array = {lower, higher};
		
		return array;
		
	}
}
