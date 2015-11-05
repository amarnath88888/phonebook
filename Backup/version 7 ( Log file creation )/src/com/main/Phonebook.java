package com.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.ContactsInitializer;
import com.search.ContactsSearch;
import com.writer.ContactsWriter;
import com.writer.CsvWriter;
import com.writer.ExcelWriter;
import com.writer.PDFWriter;
import com.writer.TextWriter;

public class Phonebook {
	
	static final Logger logger = Logger.getLogger(Phonebook.class);
	
	public static void main(String args[])	{
		ContactsInitializer i = new ContactsInitializer();
		DOMConfigurator.configure("log4j.xml");
		
		Phonebook p = new Phonebook();
		
		i.initProperty();
		i.initContacts();
		i.initXmlHeader();
		p.menu(i);
		logger.debug("Application Terminated");
	}
	
	public void menu(ContactsInitializer i)	{
		logger.debug("Entered menu() method");
		BufferedReader br = null;
		int choice;
		
		ContactsWriter cw = null;
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
				
				if(choice == 1)
					cw = new TextWriter();
				else if(choice == 2)
					cw = new CsvWriter();
				else if(choice == 3)
					cw = new ExcelWriter();
				else if(choice == 4)
					cw = new PDFWriter();
				
				switch(choice)	{
				case 0 : {
					System.out.println(ApplicationConstants.BYE);
					return;
				}
				case 1 : 
				case 2 : 
				case 3 : 
				case 4 : {
					cw.writeContacts(i);
				    break;
				}
				case 5 : {
					cs = new ContactsSearch();
					cs.searchName(i);
				    break;
				}
				default : {
					System.out.println(ApplicationConstants.VALID_CHOICE);
					logger.info("Invalid choice made");
					menu(i); 
				}
				}
			} while(true);
		} catch(Exception e)	{
			logger.error(e);
			e.printStackTrace();
		} finally	{
			if(br != null)
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