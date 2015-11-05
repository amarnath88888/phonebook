package com.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.constants.ApplicationConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;


public class CsvWriter implements ContactsWriter {
	
	public void writeContacts(ContactsInitializer i)	{
		List<Contacts> list;
		Properties pro;		
		String csvoutput;		
		FileWriter fw = null;
		
		try	{
			list= i.getList();
			pro=i.getProperties();
			
			csvoutput = pro.getProperty(ApplicationConstants.CSV_OUTPUT);
			
			fw = new FileWriter(csvoutput);
			
			fw.append(ApplicationConstants.CONTACT_NAME);
			fw.append(ApplicationConstants.COMMA);
			fw.append(ApplicationConstants.CONTACT_NUMBER);
			fw.append(ApplicationConstants.NEW_LINE);
			
			for (Contacts c : list)	{
		    	fw.append(c.getName());
		    	fw.append(ApplicationConstants.COMMA);
		    	fw.append(c.getNumber());
		    	fw.append(ApplicationConstants.NEW_LINE);
			}
			System.out.println(ApplicationConstants.CSV_CREATED);
		}
		catch (Exception e)	{
			System.out.println(ApplicationConstants.CSV_NOTCREATED);
			e.printStackTrace();
		}
		finally	{
			if(fw != null)
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
