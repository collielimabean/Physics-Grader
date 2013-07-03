package rebuilt;

import java.util.ArrayList;

public class ScoreCalculator 
{
	
	private int testType;
	private int totalNumber;
	
	private double MCscaler;
	
	private ArrayList<Student> students;
	
	public ScoreCalculator(ArrayList<Student> studentList, int type, int totNumber)
	{
		students = studentList;
		testType = type;
		totalNumber = totNumber;
	}
	
	public void calculate()
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
		
		for(Student s : students)
		{
			s.computeScaledMC(MCscaler);
			s.computeScaledFR(testType);
			s.computeUncurvedPercentages();
			s.computeCurvedPercentages(testType);
		}
	}
	
	public ArrayList<Student> getStudentList()
	{
		return students;
	}
	
	public double getMCScaler()
	{
		return MCscaler;
	}
}
