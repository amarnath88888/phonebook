package com.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.Contacts;
import com.contacts.ContactsHeader;
import com.contacts.ContactsInitializer;


public class TextWriter implements IContactsWriter {
	
	public static final Logger logger = Logger.getLogger(TextWriter.class);
	
	public void writeContacts(ContactsInitializer contactsinitializer)	{
		List<Contacts> list;
		List<ContactsHeader> text_header;
		Properties properties;
		String textoutput;
		BufferedWriter writer = null;
		ContactsHeader header;
		
		try	{
			list= contactsinitializer.getList();
			properties=contactsinitializer.getProperties();
			text_header = contactsinitializer.getText_header();
			textoutput = properties.getProperty(ApplicationConstants.TEXT_OUTPUT);
			writer = new BufferedWriter(new FileWriter(textoutput));
			header = text_header.get(0);
			writer.write(header.getTitle() + ApplicationConstants.TAB + ApplicationConstants.TAB);
			header = text_header.get(1);
			writer.write(header.getTitle() + ApplicationConstants.NEW_LINE);
		    for (Contacts contacts : list)	{
		    	writer.write(contacts.getName()+ApplicationConstants.TAB + ApplicationConstants.TAB + contacts.getNumber() + ApplicationConstants.NEW_LINE);
		    }
		    logger.info(ApplicationConstants.TEXT_CREATED);
			System.out.println(ApplicationConstants.TEXT_CREATED);
		} catch (Exception exception)	{
			logger.info(ApplicationConstants.TEXT_NOTCREATED);
			System.out.println(ApplicationConstants.TEXT_NOTCREATED);
		    logger.error(exception);
			exception.printStackTrace();
		} finally	{
			if(writer != null)	{
				try {
					writer.close();
					logger.debug(LoggerConstants.RESOURCES_RELEASED);
				} catch (IOException exception) {
					logger.error(exception);
					exception.printStackTrace();
				}
			}
		}
	}
}