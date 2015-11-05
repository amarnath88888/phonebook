package com.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.constants.ApplicationConstants;
import com.contacts.ContactsInitializer;


public class ContactsSearch {
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
			if(m.containsKey(key))	{
				li = (List<String>) m.get(key);
				value = (String) li.get(0);
				System.out.println(ApplicationConstants.SEARCH_FOUND);
			    System.out.println(ApplicationConstants.CONTACT_NAME + value);
			    System.out.println(ApplicationConstants.CONTACT_NUMBER + key);
			}
			else	{
				System.out.println(ApplicationConstants.NOT_FOUND);
			}
		} catch (Exception e)	{
			System.out.println(ApplicationConstants.SEARCH_EXCEPTION);
			e.printStackTrace();
		} finally	{
			if(br != null)	{
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}