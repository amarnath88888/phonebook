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


public class CsvWriter implements ContactsWriter {
	
	static final Logger logger = Logger.getLogger(CsvWriter.class);
	public void writeContacts(ContactsInitializer i)	{
		List<Contacts> list;
		List<ContactsHeader> csv_header;
		Properties pro;		
		String csvoutput;		
		FileWriter fw = null;
		ContactsHeader header;
		
		try	{
			list= i.getList();
			pro=i.getProperties();
			csv_header = i.getCsv_header();
						
			csvoutput = pro.getProperty(ApplicationConstants.CSV_OUTPUT);
			
			fw = new FileWriter(csvoutput);
			header = csv_header.get(0);
			fw.append(header.getTitle());
			fw.append(ApplicationConstants.COMMA);
			header = csv_header.get(1);
			fw.append(header.getTitle());
			fw.append(ApplicationConstants.NEW_LINE);
			
			for (Contacts c : list)	{
		    	fw.append(c.getName());
		    	fw.append(ApplicationConstants.COMMA);
		    	fw.append(c.getNumber());
		    	fw.append(ApplicationConstants.NEW_LINE);
			}
			logger.info(ApplicationConstants.CSV_CREATED);
			System.out.println(ApplicationConstants.CSV_CREATED);
		}
		catch (Exception e)	{
			logger.info(ApplicationConstants.CSV_NOTCREATED);
			System.out.println(ApplicationConstants.CSV_NOTCREATED);
			logger.error(e);
			e.printStackTrace();
		}
		finally	{
			if(fw != null)
				try {
					fw.close();
					logger.debug(LoggerConstants.RESOURCES_RELEASED);
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
		}
	}
}
