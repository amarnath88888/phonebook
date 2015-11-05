package com.contacts;

import java.io.BufferedReader;
import org.apache.log4j.Logger;
import com.contacts.Contacts;


public class ContactsGenerator {
	public static final Logger logger = Logger.getLogger(ContactsGenerator.class);
	
	public Contacts generateContacts(BufferedReader reader, ContactsInitializer contactsInitializer) throws Exception	{
		String line;
		int index;
		// creates a contact object everytime when a contact is read from the vcf file
		Contacts contacts = new Contacts();
		while ((line=reader.readLine())!=null )	{
			if(line.startsWith(contactsInitializer.getName_body().get(0).getBegin()))	{
				index=line.indexOf(contactsInitializer.getName_body().get(0).getIndex());
				// fetches the contact name from the file
				contacts.setName(line.substring(index+2));
				}
			if(line.startsWith(contactsInitializer.getNumber_body().get(0).getBegin()))	{
				index=line.indexOf(contactsInitializer.getNumber_body().get(0).getIndex());
				// fetches the contact number from the file
				contacts.setNumber(line.substring(index+2));
			}
		}
		logger.debug("Contact generated");
		// returns the contact bean to store in a collection
		return contacts;
	}
}