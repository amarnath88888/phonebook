package com.reader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;

public class TextReader implements IContactsReader{

	public static final Logger logger = Logger.getLogger(TextReader.class);
	
	public List<Contacts> readContacts(ContactsInitializer contactsInitializer) {
		List<Contacts> list = new ArrayList<Contacts>();
		Properties properties;
		Contacts contacts;
		String textinput;
		String strLine;
		String contact[];
		BufferedReader reader = null;
		try	{
			properties=contactsInitializer.getProperties();
			textinput = properties.getProperty(ApplicationConstants.TEXT_INPUT);
			reader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(textinput))));
			reader.readLine();
			while ((strLine = reader.readLine()) != null)   {
				contact = strLine.split(ApplicationConstants.TAB);
				contacts = new Contacts();
				contacts.setName(contact[0]);
				contacts.setNumber(contact[2]);
				list.add(contacts);
				System.out.println(contacts.getName()+"--" + contacts.getNumber());
			}
			logger.info(list.size()+" contacts added to list from text file successfully !");
			System.out.println(list.size()+" contacts added to list from text file successfully !");
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
				} catch (IOException exception) {
					logger.error(exception);
					exception.printStackTrace();
				}
			}
		}
		return list;
	}
}