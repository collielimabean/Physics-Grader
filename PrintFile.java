package rebuilt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PrintFile 
{
	
	private String path;

	
	public PrintFile(String filePath)
	{
		path = filePath;
	}
	
	public void printTemplate()
	{
		
		path += ".csv";
		
		try 
		{
			PrintWriter writer = new PrintWriter(new FileOutputStream(path));
			
			writer.println("Date,<Date>,,,");
			writer.println("Test Type,<Number>,0 for Examination,1 for Test,2 for Quiz");
			writer.println("Total Number of Multiple Choice,<Number>,,,");
			writer.println(",,,,");
			writer.println("Name,MC,FR,");
			
			writer.close();
			
		} 
		
		catch (FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(null, "Error - FileNotFoundException in PrintFile class!");
		}
		
	}
	
	public void printPDF(ArrayList<Student> studentList, ReadFile raw, double MCScaler)
	{
		
		try
		{
			Document doc = new Document();
			PdfWriter.getInstance(doc, new FileOutputStream(path + ".pdf"));
			
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setGroupingUsed(false);
					
			doc.open();
			
			for(int i = 0; i < studentList.size(); i++)
			{
				
				doc.add(new Paragraph("Date: " + raw.getDate()));
				
				doc.add(new Paragraph(" "));
				
				doc.add(new Paragraph("Name: " + studentList.get(i).getName()));
				
				doc.add(new Paragraph(" "));
				
				doc.add(new Paragraph("Multiple Choice Score: " + studentList.get(i).getRawMCScore() 
						+ " / " + raw.getNumberOfMultipleChoice() ));
				doc.add(new Paragraph("Free Response Score: " + studentList.get(i).getRawFRScore() 
						+ " / " + ReadFile.MAX_FR[raw.getTestType()]));
				
				doc.add(new Paragraph(" "));
				
				doc.add(new Paragraph("Scaled Multiple Choice Score: " + studentList.get(i).getRawMCScore() + " * "
										+ format.format(MCScaler) + " = " 
										+ format.format(studentList.get(i).getScaledMCScore())
										+ " / " + Scale.numberOfMCScaled[raw.getTestType()]));
				
				doc.add(new Paragraph("Scaled Free Response Score: " + studentList.get(i).getRawFRScore() + " * "
										+ format.format(Scale.FREE_RESPONSE_SCALER[raw.getTestType()]) + " = " 
										+ format.format(studentList.get(i).getScaledFRScore()) + " / " 
										+ Scale.numberOfMCScaled[raw.getTestType()]));
				
				
				
				doc.add(new Paragraph(" "));
				
				switch(raw.getTestType())
				{
					case 0:
						doc.add(new Paragraph("Uncurved Score: " + format.format(studentList.get(i).getUncurvedScore()) 
												+ " / " + 100));
						break;
						
					case 1:
						
						doc.add(new Paragraph("Uncurved Score: " + format.format(studentList.get(i).getUncurvedScore() * 2) 
												+ " / " + 100));
						
						break;
					
					case 2:
						
						doc.add(new Paragraph("Uncurved Score: " + format.format(studentList.get(i).getUncurvedScore()) 
												+ " * 2 = " + format.format(studentList.get(i).getUncurvedScore() * 2) 
												+ " / " + 60));
						
						break;
						
				}
				
				doc.add(new Paragraph(" "));
				
				if(raw.getTestType() == 2)
				{
					
					if(studentList.get(i).getUncurvedScore() > 0)
					{
						doc.add(new Paragraph("Curved Score: " + format.format((studentList.get(i).getCurvedScore() / 30) * 100) 
								   + " / " + 100));
					}
					
					else
					{
						doc.add(new Paragraph("Curved Score: " + "               " + " / " + 100));
					}
					
				}
				
				else
				{
					if(studentList.get(i).getCurvedScore() > 0)
					{
						System.out.println(studentList.get(i).getCurvedScore());
						doc.add(new Paragraph("Curved Score: " + format.format(studentList.get(i).getCurvedScore()) 
												+ " / " + 100));
					}
					
					else
					{
						System.out.println(studentList.get(i).getCurvedScore());
						doc.add(new Paragraph("Curved Score: " + "               " + " / " + 100));
					}
				}
			
				if( (i + 1) % 3 == 0 && i > 0)
				{
					doc.newPage();
				}
				
				else
				{
					doc.add(new Paragraph("-----------------------------------------------------------------------------"));
				}
				
				
			}
			
			doc.close();
		}
		
		catch (DocumentException e)
		{
			e.printStackTrace();
		} 
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
	}
}
