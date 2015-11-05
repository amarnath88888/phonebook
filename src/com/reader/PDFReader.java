package com.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PDFReader implements IContactsReader{

	public static final Logger logger = Logger.getLogger(PDFReader.class);
	
	private List<Contacts> list;
	
	public PDFReader()	{
		list = new ArrayList<Contacts>();
	}
	
	public List<Contacts> readContacts(ContactsInitializer contactsInitializer) {
		Properties properties;
		String pdfinput;
		String contents;
		String[] content;
		String[] contact;
		
		int no_of_pages;
		int page_no = ApplicationConstants.TWO;
		
		PdfReader reader = null;
		try	{
			properties=contactsInitializer.getProperties();
			pdfinput = properties.getProperty(ApplicationConstants.PDF_INPUT);
			reader = new PdfReader(pdfinput);
			
			contents = PdfTextExtractor.getTextFromPage(reader, ApplicationConstants.ONE);
			content = contents.split(ApplicationConstants.NEW_LINE,ApplicationConstants.TWO);
			contact = content[1].split(ApplicationConstants.NEW_LINE);
			genarateContacts(contact);
			
			no_of_pages = reader.getNumberOfPages();
			
			while(page_no<=no_of_pages)	{
				contents = PdfTextExtractor.getTextFromPage(reader, page_no);
				contact = contents.split(ApplicationConstants.NEW_LINE);
				genarateContacts(contact);
				page_no ++;
			}
			logger.info(list.size()+" contacts added to list from pdf file successfully !");
			System.out.println(list.size()+" contacts added to list from pdf file successfully !");
			
		} catch (Exception exception)	{
			System.out.println("contacts not created !");
			logger.error("contacts not created !");
			logger.error(exception);
			exception.printStackTrace();
		} finally	{
			if(reader != null)	{
				try {
					reader.close();
					logger.debug(LoggerConstants.RESOURCES_RELEASED);
				} catch (Exception exception) {
					logger.error(exception);
					exception.printStackTrace();
				}
			}
		}
		return list;
	}
	private void genarateContacts(String[] contact)	{
		Contacts contacts;
		int index;
		for (String line : contact)	{
			index = line.lastIndexOf(ApplicationConstants.SPACE);
			contacts = new Contacts();
			contacts.setName(line.substring(0, index));
			contacts.setNumber(line.substring(index+1));
			list.add(contacts);
			System.out.println(contacts.getName()+"--" + contacts.getNumber());
		}
	}
}