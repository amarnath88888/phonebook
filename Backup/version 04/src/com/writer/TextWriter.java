package com.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.constants.ApplicationConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;


public class TextWriter {
	
	List<Contacts> list;
	Properties pro;
	String textoutput;
	BufferedWriter bw;
	
	public void writeText(ContactsInitializer i)	{
		try	{
			list= i.getList();
			pro=i.getProperties();
			textoutput = pro.getProperty(ApplicationConstants.TEXT_OUTPUT);
			bw = new BufferedWriter(new FileWriter(textoutput));
			
			bw.write(ApplicationConstants.CONTACT_NAME + ApplicationConstants.TAB + ApplicationConstants.TAB + ApplicationConstants.CONTACT_NUMBER + ApplicationConstants.NEW_LINE);
		    for (Contacts c : list)	{
		    	bw.write(c.getName()+ApplicationConstants.TAB + ApplicationConstants.TAB + c.getNumber() + ApplicationConstants.NEW_LINE);
		    }
		    System.out.println(ApplicationConstants.TEXT_CREATED);
		}
		catch (Exception e)	{
		    System.out.println(ApplicationConstants.TEXT_NOTCREATED);
			e.printStackTrace();
		}
		finally	{
			list = null;
			textoutput = null;
		    try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}