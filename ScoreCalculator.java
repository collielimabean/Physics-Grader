package rebuilt;

public class ScoreCalculator 
{
	
	/** 
	* Used for tests and semester exams with appropriate scaling
	* unscaled top, scaled bottom - 100 pts max 
	*/ 
	public final double[][] TestScoreScaler = 
	{  
	     
	{20,24,27,29.9,32,34,36,38,40,41,44,47,49,50,51,54,57,58,59,60,62,64,66,69,70,73,76,79,82,85,88,91,94,97,100}, 
	{60,64,66,67,70.5,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100}, 
	 
	}; 
	 
	/** 
	 * Used for quizzes with appropriate scaling
	* unscaled top, scaled bottom - 30 pts max 
	*/ 
	public final double[][] QuizScoreScaler = 
	{ 
	     
	{6,7,8,9,9.8,10.5,11.2,11.8,12.2,12.5,13.0,13.7,14.3,15.0,15.8,16.3,16.8,17.3,17.8,18.2,18.5,19.0,19.5,20.0,20.7,21.7,22.7,24.0,24.8,25.5,26.3,27.2,28.2,29.2,30}, 
	{18,19,19.5,20.1,21.0,21.3,21.6,21.9,22.2,22.5,22.8,23.1,23.4,23.7,24.0,24.3,24.6,24.9,25.2,25.5,25.8,26.1,26.4,26.7,27.0,27.3,27.6,27.9,28.2,28.5,28.8,29.1,29.4,29.7,30}, 
	     
	}; 
	
	public final double[] FREE_RESPONSE_SCALER = {(50.0 / 45.0), 1 , 1};
	
	private int testType;
	private int totalNumber;
	
	private double MCscaler;
	
	private int[] RawMC;
	private int[] RawFR;
	
	private double[] ScaledMC;
	private double[] ScaledFR;
	
	private double[] uncurved;
	private double[] curved;
	
	public ScoreCalculator(int[] RawMC, int[] RawFR, int type, int totNumber)
	{
		this.RawMC = RawMC;
		this.RawFR = RawFR;
		testType = type;
		totalNumber = totNumber;
		
		scaleMC();
		scaleFR();
		calculateUncurvedPercentages();
		calculateCurvedPercentages();
	}
	
	private void scaleMC()
	{
		switch(testType)
		{
			case 0:
				MCscaler = 50.0 / totalNumber;
				break;
				
			case 1:
				MCscaler = 25.0 / totalNumber;
				break;
				
			case 2:
				MCscaler = 15.0 / totalNumber;
				break;
		}
		
		ScaledMC = new double[RawMC.length];
		
		for(int i = 0; i < RawMC.length; i++)
		{
			
			ScaledMC[i] = RawMC[i] * MCscaler;
			
		}
	}
	
	private void scaleFR()
	{
		
		ScaledFR = new double[RawFR.length];
		
		for(int i = 0; i < RawFR.length; i++)
		{	
			ScaledFR[i] = RawFR[i] * FREE_RESPONSE_SCALER[testType];			
		}
		
	}
	
	private void calculateUncurvedPercentages()
	{
		
		uncurved = new double[RawMC.length];
		
		for(int i = 0; i < uncurved.length; i++)
		{		
			uncurved[i] = ScaledMC[i] + ScaledFR[i];		
		}
		
	}
	
	private void calculateCurvedPercentages()
	{
		
		double[] doubled = new double[uncurved.length];
		
		for(int i = 0; i < doubled.length; i++)
		{
			doubled[i] = uncurved[i] * 2;
		}
		
		curved = new double[uncurved.length];
		
		switch(testType)
		{
			case 0:
			case 1:
				
				for(int i = 0; i < doubled.length; i++)
				{
					
					int[] neighbors = getNeighborElements(doubled[i], TestScoreScaler);
					
					int lower = neighbors[0];
					int higher = neighbors[1];
					
					if(lower != 0 && higher == -1)
					{
						curved[i] = 100;
					}
					
					else if(lower == -1 && higher == 0)
					{
						curved[i] = -1;
					}
					
					else
					{
						curved[i] = (TestScoreScaler[1][lower] + TestScoreScaler[1][higher]) / 2.0 ;
					}
					
				}
				
				break;
				
			case 2:
				
				for(int i = 0; i < uncurved.length; i++)
				{
					
					int[] neighbors = getNeighborElements(doubled[i], QuizScoreScaler);
						
					int lower = neighbors[0];
					int higher = neighbors[1];
					
					if(lower != 0 && higher == -1)
					{
						curved[i] = 100;
					}
					
					else if(lower == -1 && higher == 0)
					{
						curved[i] = -1;
					}
					
					else
					{
						curved[i] = (QuizScoreScaler[1][lower] + QuizScoreScaler[1][higher]) / 2.0 ;
					}
						
				}
				
				break;
		}
		
	}
	
	private int[] getNeighborElements(double key, double[][] scale)
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
	
	public double getMCScaler()
	{
		return MCscaler;
	}
	
	public double[] getUncurvedPercentages()
	{
		return uncurved;
	}
	
	public double[] getCurvedPercentages()
	{
		return curved;
	}
	
	public double[] getScaledMC()
	{
		return ScaledMC;
	}
	
	public double[] getScaledFR()
	{
		return ScaledFR;
	}
}
