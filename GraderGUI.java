package rebuilt;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GraderGUI implements ActionListener
{
	
	public static final String VERSION_NUMBER = "v1.3";
	private static final Dimension WINDOW_SIZE = new Dimension(750, 500);
	
	private JFrame frame;
	private JSplitPane split;
	
	private JPanel left;
	private JPanel right;
	
	private JButton calculate;
	private JButton regenerate;
	private JButton close;
	
	private JFileChooser chooser;
	private int component; // 0 for calculate, 1 for regenerate, 2 for save path after input csv is loaded
	
	private ScoreCalculator score;
	private ReadFile raw;
	
	public GraderGUI()
	{
		
		//Initialization of frame
		frame = new JFrame("APB Test Grader " + VERSION_NUMBER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WINDOW_SIZE);
		
		//Initialization of the panel on the left of the window
		left = new JPanel();
		left.setLayout(new GridLayout(0, 1, 0, 0));
		
		//Initialization of the panel of the right of the window
		right = new JPanel();
		
		//Initialization of the buttons
		calculate = new JButton("Load and Calculate File");
		regenerate = new JButton("Generate Template File");
		close = new JButton("Close");
		
		//Add action listeners
		calculate.addActionListener(this);
		regenerate.addActionListener(this);
		close.addActionListener(this);
		
		//Add the buttons to the left panel
		left.add(calculate);
		left.add(regenerate);
		left.add(close);
		
		//Combine the panels so that the 'left' JPanel will go on the left,
		//and the 'right' JPanel will go on the right.
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		
		frame.add(split);
	}
	
	public void show()
	{
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		
		Object src = event.getSource();
		
		resetJPanel(right);
		
		if(src == calculate)
		{
			
			chooser = new JFileChooser();
			chooser.addActionListener(this);
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			right.add(chooser);
			frame.validate();
			
			JOptionPane.showMessageDialog(frame, "Press OK to select the raw .csv file.");
			
			component = 0;
			
		}
		
		else if(src == regenerate)
		{
			
			chooser = new JFileChooser();
			chooser.addActionListener(this);
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			right.add(chooser);
			frame.validate();
			
			JOptionPane.showMessageDialog(frame, "Press OK to choose where to save the template file.");
			
			component = 1;

		}
		
		else if(src == close)
		{
			
			frame.setVisible(false);
			frame.dispose();
			System.exit(0);
			
		}
		
		else if(src == chooser)
		{
			
			//from calculate button
			if(component == 0)
			{
				
				if(event.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
				{
					
					if(chooser.getSelectedFile().getAbsolutePath().endsWith(".csv"))
					{
						
						raw = new ReadFile(chooser.getSelectedFile().getAbsolutePath());
						raw.process();
						
						try
						{
							raw.parse();
							score = new ScoreCalculator(raw.getRawMCScores(), raw.getRawFreeResponseScores()
									, raw.getTestType(), raw.getNumberOfMultipleChoice());
	
							//Get save path for output file
							chooser = new JFileChooser();
							chooser.addActionListener(this);
							chooser.setDialogType(JFileChooser.SAVE_DIALOG);
							right.add(chooser);
							frame.validate();
							
							JOptionPane.showMessageDialog(frame, "Press OK to choose where to save the scores.");
							
							component = 2;
						}
						
						catch (IllegalArgumentException e)
						{
							JOptionPane.showMessageDialog(frame, e.getMessage());
						}
		
					}
					
					else
					{
						JOptionPane.showMessageDialog(frame, "Please choose a file with extension .csv!");
					}
					
				}
				
				else
				{
					resetJPanel(right);
				}
				
			}
			
			//from regenerate button
			else if(component == 1)
			{
				
				if(event.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
				{
					
					PrintFile p = new PrintFile(chooser.getSelectedFile().getAbsolutePath());
					p.printTemplate();
					
					JOptionPane.showMessageDialog(frame, "Generation complete!");
					
				}
				
				else
				{
					resetJPanel(right);
				}
				
			}
			
			else if(component == 2)
			{
				
				if(event.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
				{
					
					PrintFile p = new PrintFile(chooser.getSelectedFile().getAbsolutePath());
					p.printPDF(score, raw);
					
					JOptionPane.showMessageDialog(frame, "Calculation complete!");
					
				}
				
				else
				{
					resetJPanel(right);
				}
				
			}
			
		}
	}
	
	private void resetJPanel(JPanel panel)
	{
		panel.removeAll();
		frame.repaint();
	}
	
	
}
