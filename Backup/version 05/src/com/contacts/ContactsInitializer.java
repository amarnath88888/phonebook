package com.contacts;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.constants.ApplicationConstants;
import com.contacts.Contacts;


public class ContactsInitializer {
	private Properties pro;
	
	private List<Contacts> l;
	private Map<String, List<String>> m;
	
	public ContactsInitializer()	{
		l= new ArrayList<Contacts>();
		m = new HashMap<String, List<String>>();
		pro = new Properties();
	}
	
	public Properties getProperties()	{
		return pro;
	}
	
	public List<Contacts> getList()	{
		return l;
	}
	
	public void setList(List<Contacts> l)	{
		this.l = l;
	}
	
	public Map<String, List<String>> getMap() {
		return m;
	}

	public void setMap(Map<String, List<String>> m) {
		this.m = m;
	}
	
	public void initProperty() {
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(new File(ApplicationConstants.PROPERTIES_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			pro.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(fis != null){
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void initContacts() {
		String input;
		Contacts c;
		ContactsGenerator cg;
		File folder;
		File[] listOfFiles;
		BufferedReader br = null;
		
		cg = new ContactsGenerator();
		input = pro.getProperty(ApplicationConstants.PROPERTIES_INPUT);
		folder = new File(input);
		listOfFiles = folder.listFiles();
		
		try {
			for (File f : listOfFiles)	{	    	
				br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(f))));
				while ( br.ready() )	{
					c= cg.generateContacts(br);
					writeList(c);
					writeMap(c);				
				}
			}  
			System.out.println(l.size()+" contacts added to list successfully !");
			System.out.println(m.size()+" contacts added to map successfully !");
		}catch (Exception e) {
			System.out.println("contacts not created !");
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
	
	private void writeList(Contacts c){
		l.add(c); // Adding contacts to List
	}
	
	private void writeMap(Contacts c){
		List<String> value = new ArrayList<String>();
		String key;
		key=c.getNumber();
		value.add(c.getName());
		m.put(key, value);
	}
}
