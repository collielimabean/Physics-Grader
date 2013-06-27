package rebuilt;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class GraderTester
{

	public static void main(String[] args)
	{
		
		SwingUtilities.invokeLater(new Runnable()
		{
			
			public void run()
			{
				
				setDesign();
				
				GraderGUI gui = new GraderGUI();
				gui.show();
				
			}
			
		});
		
	}
	
	
	private static void setDesign()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		catch (Exception e)
		{
			
		}
	}
}
