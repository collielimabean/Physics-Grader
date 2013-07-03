package rebuilt;

public class Student 
{
	
	private String name;
	private int RawMC;
	private int RawFR;
	
	private double ScaledMC;
	private double ScaledFR;
	
	private double uncurved;
	private double curved;
	
	public Student(String Name, int mc, int fr)
	{
		name = Name;
		RawMC = mc;
		RawFR = fr;
	}

	public String getName()
	{
		return name;
	}
	
	public int getRawMCScore()
	{
		return RawMC;
	}
	
	public int getRawFRScore()
	{
		return RawFR;
	}
	
	public double getScaledMCScore()
	{
		return ScaledMC;
	}
	
	public double getScaledFRScore()
	{
		return ScaledFR;
	}
	
	public double getUncurvedScore()
	{
		return uncurved;
	}
	
	public double getCurvedScore()
	{
		return curved;
	}
	
	public void computeScaledMC(double scaler)
	{
		ScaledMC = RawMC * scaler;
	}
	
	public void computeScaledFR(int testType)
	{
		ScaledFR = RawFR * Scale.FREE_RESPONSE_SCALER[testType];
	}
	
	public void computeUncurvedPercentages()
	{
		uncurved = ScaledMC + ScaledFR;		
	}
	
	public void computeCurvedPercentages(int testType)
	{
		
		double doubled = uncurved * 2;
		
		switch(testType)
		{
			case 0:
				curved = this.scale(uncurved, Scale.TestScoreScaler);
				break;
			case 1:
				
				curved = this.scale(doubled, Scale.TestScoreScaler);
				break;
				
			case 2:
				
				curved = this.scale(doubled, Scale.QuizScoreScaler);
				break;
		}
		
	}
	
	private double scale(double uncurved, double[][] scaler)
	{
		
		double result;
		
		int[] near = Scale.getNeighborElements(uncurved, scaler);
		
		int low = near[0];
		int high = near[1];
		
		if(low != 0 && high == -1)
		{
			result = 100;
		}
		
		else if(low == -1 && high == 0)
		{
			result = -1;
		}
		
		else
		{
			result = (scaler[1][low] + scaler[1][high]) / 2.0 ;
		}
		
		return result;
		
	}
	
	
}
