package com.writer;

import java.util.List;
import org.apache.log4j.Logger;

import com.constants.ApplicationConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;
import com.dbevents.DbAccess;

public class DbWriter implements IContactsWriter{
	
	public static final Logger logger = Logger.getLogger(DbWriter.class);
	
	public void writeContacts(ContactsInitializer contactsinitializer) {
		List<Contacts> list;
		DbAccess dbAccess = DbAccess.getInstance();
		//String createtable_query = ApplicationConstants.CREATE_TABLE;
		String insertcontacts_query;
		int count = 0;
		try	{
			list= contactsinitializer.getList();
			/*if (dbAccess.executeUpdate(createtable_query) > 0)	{
		    logger.info(ApplicationConstants.TABLE_CREATED);
			System.out.println(ApplicationConstants.TABLE_CREATED);
			}*/
			for (Contacts contacts : list)	{
		    	insertcontacts_query = "insert into contacts values ('" + contacts.getName()+ "','" + contacts.getNumber() + "')";
		    	if (dbAccess.executeUpdate(insertcontacts_query) > 0) {
		    		count++;
		    	}
		    }
		    logger.info(ApplicationConstants.TABLE_CREATED + " with " + count + " rows.");
			System.out.println(ApplicationConstants.TABLE_CREATED + " with " + count + " rows.");
		} catch (Exception exception)	{
			logger.info(ApplicationConstants.TABLE_NOTCREATED);
			System.out.println(ApplicationConstants.TABLE_NOTCREATED);
		    logger.error(exception);
			exception.printStackTrace();
		}
	}
}
