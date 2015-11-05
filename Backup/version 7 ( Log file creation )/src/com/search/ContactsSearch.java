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
	static final Logger logger = Logger.getLogger(ContactsSearch.class);
	public void searchName(ContactsInitializer i)	{
		String key=null;
		String value=null;
		
		Map<String, List<String>> m;
		List<String> li;

		BufferedReader br = null;
		m = i.getMap();
		
		try	{
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(ApplicationConstants.MOBILE_NUMBER);
			key = br.readLine();
			logger.info("Got mobile number from user");
			if(m.containsKey(key))	{
				li = (List<String>) m.get(key);
				value = (String) li.get(0);
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
		} catch (Exception e)	{
			logger.info(ApplicationConstants.SEARCH_EXCEPTION);
			System.out.println(ApplicationConstants.SEARCH_EXCEPTION);
			logger.error(e);
			e.printStackTrace();
		} finally	{
			if(br != null)	{
				try {
					br.close();
					logger.debug(LoggerConstants.RESOURCES_RELEASED);
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
			}
		}
	}
}