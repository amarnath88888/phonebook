package com.writer;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.Contacts;
import com.contacts.ContactsHeader;
import com.contacts.ContactsInitializer;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFWriter implements ContactsWriter {
		
	static final Logger logger = Logger.getLogger(PDFWriter.class);
	public void writeContacts(ContactsInitializer i)	{
		List<Contacts> list;
		List<ContactsHeader> pdf_header;
		Properties pro;		
		String pdfoutput;
		ContactsHeader header;
		
		Document document;
		PdfPTable table;
		
		list= i.getList();
		pro=i.getProperties();
		pdfoutput = pro.getProperty(ApplicationConstants.PDF_OUTPUT);
		pdf_header = i.getPdf_header();
				
		document=new Document();
		table = new PdfPTable(2);
		
		header = pdf_header.get(0);
		table.addCell(header.getTitle());
		header = pdf_header.get(1);
		table.addCell(header.getTitle());
		
		for (Contacts c : list)	{
			table.addCell(c.getName());
		    table.addCell(c.getNumber());
		}
		
		try	{
			PdfWriter.getInstance(document,new FileOutputStream(pdfoutput));
			document.open();
			document.add(table);
			logger.info(ApplicationConstants.PDF_CREATED);
			System.out.println(ApplicationConstants.PDF_CREATED);
		}
		catch (Exception e)	{
			logger.info(ApplicationConstants.PDF_NOTCREATED);
			System.out.println(ApplicationConstants.PDF_NOTCREATED);
		    logger.error(e);
			e.printStackTrace();
		}
		finally	{
			document.close();
			logger.debug(LoggerConstants.RESOURCES_RELEASED);
		}
	}
}
