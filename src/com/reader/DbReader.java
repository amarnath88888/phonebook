package com.reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.constants.ApplicationConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;
import com.dbevents.DbAccess;

public class DbReader implements IContactsReader{
	
public static final Logger logger = Logger.getLogger(DbReader.class);
	
	public List<Contacts> readContacts(ContactsInitializer contactsInitializer) {
		List<Contacts> list = new ArrayList<Contacts>();
		Contacts contacts;
		DbAccess dbAccess = DbAccess.getInstance();
		String select_query = ApplicationConstants.SELECT_QUERY;
		ResultSet rs = dbAccess.executeQuery(select_query);
		try	{
			while (null != rs && rs.next())	{
				contacts = new Contacts();
				contacts.setName(rs.getString(ApplicationConstants.ONE));
				contacts.setNumber(rs.getString(ApplicationConstants.TWO));
				list.add(contacts);
				System.out.println(contacts.getName()+"--" + contacts.getNumber());
			}
			logger.info(list.size()+" contacts added to list from text file successfully !");
			System.out.println(list.size()+" contacts added to list from text file successfully !");
		} catch (SQLException sqlException) {
			System.out.println("SQL Exception occurred. ");
			System.out.println("contacts not created !");
			logger.error("SQL Exception occured");
			logger.error("contacts not created !");
			logger.error(sqlException);
			sqlException.printStackTrace();
		} finally	{
			dbAccess.closeResultSet(rs);
		}
		return list;
	}
}
