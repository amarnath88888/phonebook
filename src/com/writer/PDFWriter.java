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


public class PDFWriter implements IContactsWriter {
		
	public static final Logger logger = Logger.getLogger(PDFWriter.class);
	
	public void writeContacts(ContactsInitializer contactsinitializer)	{
		List<Contacts> list;
		List<ContactsHeader> pdf_header;
		Properties properties;		
		String pdfoutput;
		ContactsHeader header;
		
		Document document;
		PdfPTable table;
		
		list= contactsinitializer.getList();
		properties=contactsinitializer.getProperties();
		pdfoutput = properties.getProperty(ApplicationConstants.PDF_OUTPUT);
		pdf_header = contactsinitializer.getPdf_header();
				
		document=new Document();
		table = new PdfPTable(2);
		
		header = pdf_header.get(0);
		table.addCell(header.getTitle());
		header = pdf_header.get(1);
		table.addCell(header.getTitle());
		
		for (Contacts contacts : list)	{
			table.addCell(contacts.getName());
		    table.addCell(contacts.getNumber());
		}
		
		try	{
			PdfWriter.getInstance(document,new FileOutputStream(pdfoutput));
			document.open();
			document.add(table);
			logger.info(ApplicationConstants.PDF_CREATED);
			System.out.println(ApplicationConstants.PDF_CREATED);
		} catch (Exception exception)	{
			logger.info(ApplicationConstants.PDF_NOTCREATED);
			System.out.println(ApplicationConstants.PDF_NOTCREATED);
		    logger.error(exception);
			exception.printStackTrace();
		} finally	{
			document.close();
			logger.debug(LoggerConstants.RESOURCES_RELEASED);
		}
	}
}