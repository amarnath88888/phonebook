package com.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.ContactsInitializer;


public class ContactsSearch {
	public static final Logger logger = Logger.getLogger(ContactsSearch.class);
	
	public void searchName(ContactsInitializer contactsinitializer)	{
		String key=null;
		String value=null;
		
		Map<String, List<String>> map;
		List<String> list;

		BufferedReader reader = null;
		map = contactsinitializer.getMap();
		
		try	{
			reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(ApplicationConstants.MOBILE_NUMBER);
			key = reader.readLine();
			logger.info("Got mobile number from user");
			if(map.containsKey(key))	{
				list = (List<String>) map.get(key);
				value = (String) list.get(0);
				logger.info(ApplicationConstants.SEARCH_FOUND);
				logger.info(ApplicationConstants.CONTACT_NAME + value);
				logger.info(ApplicationConstants.CONTACT_NUMBER + key);
				System.out.println(ApplicationConstants.SEARCH_FOUND);
			    System.out.println(ApplicationConstants.CONTACT_NAME + value);
			    System.out.println(ApplicationConstants.CONTACT_NUMBER + key);
			}
			else	{
				logger.info(ApplicationConstants.NOT_FOUND);
				System.out.println(ApplicationConstants.NOT_FOUND);
			}
		} catch (Exception exception)	{
			logger.info(ApplicationConstants.SEARCH_EXCEPTION);
			System.out.println(ApplicationConstants.SEARCH_EXCEPTION);
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
	}
}