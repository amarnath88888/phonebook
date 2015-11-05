package com.writer;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import com.constants.ApplicationConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFWriter {
	List<Contacts> list;
	Properties pro;
	
	String pdfoutput;
	
	Document document;
	
	public PDFWriter(ContactsInitializer i)	{
		list= i.getList();
		pro=i.getProperties();
		pdfoutput = pro.getProperty(ApplicationConstants.PDF_OUTPUT);
		document=new Document();
	}
		
	public void writePdfTable()	{
		PdfPTable table;
		
		table = new PdfPTable(2);
		table.addCell(ApplicationConstants.CONTACT_NAME);
		table.addCell(ApplicationConstants.CONTACT_NUMBER);
		for (Contacts c : list)	{
			table.addCell(c.getName());
		    table.addCell(c.getNumber());
		}
		
		try	{
			PdfWriter.getInstance(document,new FileOutputStream(pdfoutput));
			document.open();
			document.add(table);
		    System.out.println(ApplicationConstants.PDF_CREATED);
		}
		catch (Exception e)	{
		    System.out.println(ApplicationConstants.PDF_NOTCREATED);
			e.printStackTrace();
		}
		finally	{
			document.close();
		}
	}
}
