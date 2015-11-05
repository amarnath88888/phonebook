package com.writer;

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


public class CsvWriter implements IContactsWriter {
	
	public static final Logger logger = Logger.getLogger(CsvWriter.class);
	
	public void writeContacts(ContactsInitializer contactsinitializer)	{
		List<Contacts> list;
		List<ContactsHeader> csv_header;
		Properties properties;		
		String csvoutput;		
		FileWriter writer = null;
		ContactsHeader header;
		
		try	{
			list= contactsinitializer.getList();
			properties=contactsinitializer.getProperties();
			csv_header = contactsinitializer.getCsv_header();
						
			csvoutput = properties.getProperty(ApplicationConstants.CSV_OUTPUT);
			
			writer = new FileWriter(csvoutput);
			header = csv_header.get(0);
			writer.append(header.getTitle());
			writer.append(ApplicationConstants.COMMA);
			header = csv_header.get(1);
			writer.append(header.getTitle());
			writer.append(ApplicationConstants.NEW_LINE);
			
			for (Contacts c : list)	{
		    	writer.append(c.getName());
		    	writer.append(ApplicationConstants.COMMA);
		    	writer.append(c.getNumber());
		    	writer.append(ApplicationConstants.NEW_LINE);
			}
			logger.info(ApplicationConstants.CSV_CREATED);
			System.out.println(ApplicationConstants.CSV_CREATED);
		}
		catch (Exception exception)	{
			logger.info(ApplicationConstants.CSV_NOTCREATED);
			System.out.println(ApplicationConstants.CSV_NOTCREATED);
			logger.error(exception);
			exception.printStackTrace();
		}
		finally	{
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