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


public class TextWriter implements ContactsWriter {
	
	static final Logger logger = Logger.getLogger(TextWriter.class);
	public void writeContacts(ContactsInitializer i)	{
		List<Contacts> list;
		List<ContactsHeader> text_header;
		Properties pro;
		String textoutput;
		BufferedWriter bw = null;
		ContactsHeader header;
		
		try	{
			list= i.getList();
			pro=i.getProperties();
			text_header = i.getText_header();
			textoutput = pro.getProperty(ApplicationConstants.TEXT_OUTPUT);
			bw = new BufferedWriter(new FileWriter(textoutput));
			header = text_header.get(0);
			bw.write(header.getTitle() + ApplicationConstants.TAB + ApplicationConstants.TAB);
			header = text_header.get(1);
			bw.write(header.getTitle() + ApplicationConstants.NEW_LINE);
		    for (Contacts c : list)	{
		    	bw.write(c.getName()+ApplicationConstants.TAB + ApplicationConstants.TAB + c.getNumber() + ApplicationConstants.NEW_LINE);
		    }
		    logger.info(ApplicationConstants.TEXT_CREATED);
			System.out.println(ApplicationConstants.TEXT_CREATED);
		}
		catch (Exception e)	{
			logger.info(ApplicationConstants.TEXT_NOTCREATED);
			System.out.println(ApplicationConstants.TEXT_NOTCREATED);
		    logger.error(e);
			e.printStackTrace();
		}
		finally	{
			if(bw != null)
				try {
					bw.close();
					logger.debug(LoggerConstants.RESOURCES_RELEASED);
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
		}
	}
}