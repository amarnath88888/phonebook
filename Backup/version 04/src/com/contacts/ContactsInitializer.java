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
import com.search.ContactsSearch;
import com.writer.CsvWriter;
import com.writer.ExcelWriter;
import com.writer.PDFWriter;
import com.writer.TextWriter;


public class ContactsInitializer {
	private Properties pro;
	
	private List<Contacts> l;
	private Map<String, List<String>> m;
	
	private FileInputStream fis;
	
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
			try {
				br.close();
				listOfFiles = null;
				folder = null;
				cg = null;
			} catch (IOException e) {
				e.printStackTrace();
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
	
	public void menu(ContactsInitializer i)	{
		BufferedReader br = null;
		int choice;
		
		TextWriter tw;
		CsvWriter csvw;
		ExcelWriter ew;
		PDFWriter pdfw;
		ContactsSearch cs;
		try	{
			br  = new BufferedReader(new InputStreamReader(System.in));
			do	{
				System.out.println("1. Write contact info in a text file");
				System.out.println("2. Write contact info in a csv file");
				System.out.println("3. Write contact info in a excel file");
				System.out.println("4. Write contact info in a pdf file(Table)");
				System.out.println("5. Search contact by number");
				System.out.println("0. Exit");
				System.out.println("Enter your choice(0-5)");
				choice = Integer.parseInt(br.readLine());
				switch(choice)	{
				case 0 : {
					System.out.println(ApplicationConstants.BYE);
					i.destructor();
					return;
				}
				case 1 : {
					tw = new TextWriter();
					tw.writeText(i);
				    break;
				}
				case 2 : {
					csvw = new CsvWriter();
					csvw.csvFileRead(i);
				    break;
				}
				case 3 : {
					ew = new ExcelWriter();
					ew.writeExcel(i);
				    break;
				}
				case 4 : {
					pdfw = new PDFWriter(i);
					pdfw.writePdfTable();
				    break;
				}
				case 5 : {
					cs = new ContactsSearch();
					cs.searchName(i);
				    break;
				}
				default : System.out.println(ApplicationConstants.VALID_CHOICE); menu(i); 
				}
			}
			while(true);
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
		finally	{
			tw = null;
			csvw = null;
			ew = null;
			pdfw = null;
			cs = null;
			
		    try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void destructor() {	
		l = null;
		m = null;
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
