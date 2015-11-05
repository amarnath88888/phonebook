import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFWriter {
	public static void writePdfPara()	{
		try	{
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			ContactsWriter cr= new ContactsWriter();
			cr.writeContacts();
			List<Contacts> list= cr.getList();
			Properties pro = new Properties();
			FileInputStream fis = new FileInputStream(new File("properties\\Contacts.properties"));
			pro.load(fis);
			String pdfoutput = pro.getProperty("pdfparaoutput");
			Document document=new Document();
			PdfWriter.getInstance(document,new FileOutputStream(pdfoutput));
			document.open();
			for (Contacts c : list)	{
				Paragraph p = new Paragraph(c.getName()+"     "+c.getNumber());
		    	document.add(p);
			}
		    document.close();
		    fis.close();
		    System.out.println("PDF file created successfully !");
		    bf.read();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	public static void writePdfTable()	{
		try	{
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			ContactsWriter cr= new ContactsWriter();
			cr.writeContacts();
			List<Contacts> list= cr.getList();
			Properties pro = new Properties();
			FileInputStream fis = new FileInputStream(new File("properties\\Contacts.properties"));
			pro.load(fis);
			String pdfoutput = pro.getProperty("pdftableoutput");
			Document document=new Document();
			PdfWriter.getInstance(document,new FileOutputStream(pdfoutput));
			document.open();
			PdfPTable table = new PdfPTable(2);
			table.addCell("Name");
			table.addCell("Mobile Number");
			for (Contacts c : list)	{
				table.addCell(c.getName());
			    table.addCell(c.getNumber());
			}
			document.add(table);        
		    document.close();
		    fis.close();
		    System.out.println("PDF file created successfully !");
		    bf.read();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
}
