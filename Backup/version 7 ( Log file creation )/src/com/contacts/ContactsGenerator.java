package com.contacts;

import java.io.BufferedReader;

import org.apache.log4j.Logger;

import com.constants.ApplicationConstants;
import com.contacts.Contacts;


public class ContactsGenerator {
	static final Logger logger = Logger.getLogger(ContactsGenerator.class);
	
	public Contacts generateContacts(BufferedReader br) throws Exception	{
		String str;
		int i;
		Contacts c = new Contacts();
		while ((str=br.readLine())!=null )	{
			if(str.startsWith(ApplicationConstants.START_1))	{
				i=str.indexOf(ApplicationConstants.INDEX_1);
				c.setName(str.substring(i+2));
				}
			if(str.startsWith(ApplicationConstants.START_2))	{
				i=str.indexOf(ApplicationConstants.INDEX_2);
				c.setNumber(str.substring(i+2));
			}
		}
		logger.debug("Contact generated");
		return c;
	}
}