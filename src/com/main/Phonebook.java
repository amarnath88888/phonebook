package com.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;
import com.reader.CsvReader;
import com.reader.DbReader;
import com.reader.ExcelReader;
import com.reader.IContactsReader;
import com.reader.PDFReader;
import com.reader.TextReader;
import com.search.ContactsSearch;
import com.vcf.VcfGenerator;
import com.writer.DbWriter;
import com.writer.IContactsWriter;
import com.writer.CsvWriter;
import com.writer.ExcelWriter;
import com.writer.PDFWriter;
import com.writer.TextWriter;

public class Phonebook {
	
	public static final Logger logger = Logger.getLogger(Phonebook.class);
	
	public static void main(String args[])	{
		DOMConfigurator.configure("log4j.xml");
		ContactsInitializer contactsinitializer = new ContactsInitializer();
		
		Phonebook phonebook = new Phonebook();
		
		contactsinitializer.initProperty(null);
		contactsinitializer.initXmlHeader();
		contactsinitializer.initXmlBody();
		contactsinitializer.initContacts(null);
		phonebook.menu(contactsinitializer);
		logger.debug("Application Terminated");
	}
	
	public void menu(ContactsInitializer contactsInitializer)	{
		logger.debug("Entered menu() method");
		BufferedReader bufferedreader = null;
		int choice;
		
		IContactsWriter writer = null;
		IContactsReader reader = null;
		ContactsSearch search;
		VcfGenerator vcfgenerator = new VcfGenerator();
		
		List<Contacts> list;
		try	{
			bufferedreader  = new BufferedReader(new InputStreamReader(System.in));
			do	{
				System.out.println("1. Write contact info in a text file");
				System.out.println("2. Write contact info in a csv file");
				System.out.println("3. Write contact info in a excel file");
				System.out.println("4. Write contact info in a pdf file(Table)");
				System.out.println("5. Write contact info into Database(Table)");
				System.out.println("6. Read contact info from a text file");
				System.out.println("7. Read contact info from a csv file");
				System.out.println("8. Read contact info from a excel file");
				System.out.println("9. Read contact info from a pdf file(Table)");
				System.out.println("10. Read contact info from Database(Table)");
				System.out.println("11. Search contact by number");
				System.out.println("0. Exit");
				System.out.println("Enter your choice(0-9)");
				choice = Integer.parseInt(bufferedreader.readLine());
				
				if(choice == 1)
					writer = new TextWriter();
				else if(choice == 2)
					writer = new CsvWriter();
				else if(choice == 3)
					writer = new ExcelWriter();
				else if(choice == 4)
					writer = new PDFWriter();
				else if(choice == 5)
					writer = new DbWriter();
				else if(choice == 6)
					reader = new TextReader();
				else if(choice == 7)
					reader = new CsvReader();
				else if(choice == 8)
					reader = new ExcelReader();
				else if(choice == 9)
					reader = new PDFReader();
				else if(choice == 10)
					reader = new DbReader();
				
				switch(choice)	{
				case 0 : {
					System.out.println(ApplicationConstants.BYE);
					return;
				}
				case 1 : 
				case 2 : 
				case 3 :
				case 4 :
				case 5 : {
					writer.writeContacts(contactsInitializer);
				    break;
				}
				case 6 :
				case 7 :
				case 8 :
				case 9 :
				case 10 : {
					list = reader.readContacts(contactsInitializer);
					vcfgenerator.vcfXmlReader(contactsInitializer);
					vcfgenerator.generateVcf(contactsInitializer,list,null);
					break;
				}
				case 11 : {
					search = new ContactsSearch();
					search.searchName(contactsInitializer);
				    break;
				}
				default : {
					System.out.println(ApplicationConstants.VALID_CHOICE);
					logger.info("Invalid choice made");
					menu(contactsInitializer); 
				}
				}
			} while(true);
		} catch(Exception exception)	{
			logger.error(exception);
			exception.printStackTrace();
		} finally	{
			if(bufferedreader != null)
				try {
					bufferedreader.close();
					logger.debug(LoggerConstants.RESOURCES_RELEASED);					
				} catch (IOException exception) {
					logger.error(exception);
					exception.printStackTrace();
				}
		}
	}
}