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

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.Contacts;


public class ContactsInitializer {
	private Properties pro;
	
	private List<ContactsHeader> text_header;
	private List<ContactsHeader> csv_header;
	private List<ContactsHeader> excel_header;
	private List<ContactsHeader> pdf_header;
	
	private List<Contacts> list;
	private Map<String, List<String>> map;
	
	static final Logger logger = Logger.getLogger(ContactsInitializer.class);
	
	public ContactsInitializer()	{
		list= new ArrayList<Contacts>();
		text_header = new ArrayList<ContactsHeader>();
		csv_header = new ArrayList<ContactsHeader>();
		excel_header = new ArrayList<ContactsHeader>();
		pdf_header = new ArrayList<ContactsHeader>();
		map = new HashMap<String, List<String>>();
		pro = new Properties();
	}
	
	public Properties getProperties()	{
		return pro;
	}
	
	public void setText_header(List<ContactsHeader> text_header) {
		this.text_header = text_header;
	}

	public List<ContactsHeader> getText_header() {
		return text_header;
	}

	public void setCsv_header(List<ContactsHeader> csv_header) {
		this.csv_header = csv_header;
	}

	public List<ContactsHeader> getCsv_header() {
		return csv_header;
	}

	public void setExcel_header(List<ContactsHeader> excel_header) {
		this.excel_header = excel_header;
	}

	public List<ContactsHeader> getExcel_header() {
		return excel_header;
	}

	public void setPdf_header(List<ContactsHeader> pdf_header) {
		this.pdf_header = pdf_header;
	}

	public List<ContactsHeader> getPdf_header() {
		return pdf_header;
	}

	public List<Contacts> getList()	{
		return list;
	}
	
	public void setList(List<Contacts> l)	{
		this.list = l;
	}

	public Map<String, List<String>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<String>> m) {
		this.map = m;
	}
	
	public void initProperty() {
		FileInputStream fis = null;		
		try {
			fis = new FileInputStream(new File(ApplicationConstants.PROPERTIES_PATH));
		} catch (FileNotFoundException e) {
			logger.error(e);
			e.printStackTrace();
		}
		try {
			pro.load(fis);
			logger.debug("Property file loaded");
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
		if(fis != null){
			try {
				fis.close();
				logger.debug(LoggerConstants.RESOURCES_RELEASED);
			} catch (IOException e) {
				logger.error(e);
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
			logger.info(list.size()+" contacts added to list successfully !");
			logger.info(map.size()+" contacts added to map successfully !");
			System.out.println(list.size()+" contacts added to list successfully !");
			System.out.println(map.size()+" contacts added to map successfully !");
		}catch (Exception e) {
			System.out.println("contacts not created !");
			logger.error("contacts not created !");
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
	
	private void writeList(Contacts c){
		list.add(c); // Adding contacts to List
	}
	
	private void writeMap(Contacts c){
		List<String> value = new ArrayList<String>();
		String key;
		key=c.getNumber();
		value.add(c.getName());
		map.put(key, value);
	}
	
	public void initXmlHeader()	{
		String contact_xmlinput;
		Digester digester = new Digester();
		
        logger.info("Push the current object onto the stack");		
		digester.push(this);

		logger.info("Creates a new instance of the ContactHeader class for Text Contact");
		digester.addObjectCreate( "contacts/textcontact/header", ContactsHeader.class );
		digester.addBeanPropertySetter( "contacts/textcontact/header/title", "title" );
		digester.addSetNext( "contacts/textcontact/header", "addTextTitle" );
		
		logger.info("Creates a new instance of the ContactHeader class for CSV Contact");
		digester.addObjectCreate( "contacts/csvcontact/header", ContactsHeader.class );
		digester.addBeanPropertySetter( "contacts/csvcontact/header/title", "title" );
		digester.addSetNext( "contacts/csvcontact/header", "addCsvTitle" );
		
		logger.info("Creates a new instance of the ContactHeader class for Excel Contact");
		digester.addObjectCreate( "contacts/excelcontact/header", ContactsHeader.class );
		digester.addBeanPropertySetter( "contacts/excelcontact/header/title", "title" );
		digester.addBeanPropertySetter( "contacts/excelcontact/header/font", "font" );
		digester.addBeanPropertySetter( "contacts/excelcontact/header/size", "size" );
		digester.addBeanPropertySetter( "contacts/excelcontact/header/color", "color" );
		digester.addSetNext( "contacts/excelcontact/header", "addExcelTitle" );
		
		logger.info("Creates a new instance of the ContactHeader class for PDF Contact");
		digester.addObjectCreate( "contacts/pdfcontact/header", ContactsHeader.class );
		digester.addBeanPropertySetter( "contacts/pdfcontact/header/title", "title" );
		digester.addBeanPropertySetter( "contacts/pdfcontact/header/font", "font" );
		digester.addBeanPropertySetter( "contacts/pdfcontact/header/size", "size" );
		digester.addBeanPropertySetter( "contacts/pdfcontact/header/color", "color" );
		digester.addSetNext( "contacts/pdfcontact/header", "addPdfTitle" );
				
        contact_xmlinput = pro.getProperty(ApplicationConstants.CONTACT_XMLINPUT);
        
		try {
			digester.parse(this.getClass().getClassLoader().getResourceAsStream(contact_xmlinput));
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (SAXException e) {
			logger.error(e);
			e.printStackTrace();
		}
		logger.info(text_header);
		logger.info(csv_header);
		logger.info(excel_header);
		logger.info(pdf_header);
		System.out.println(text_header);
		System.out.println(csv_header);
		System.out.println(excel_header);
		System.out.println(pdf_header);
	}
	public void addTextTitle(ContactsHeader c)	{
		text_header.add(c);
	}
	public void addCsvTitle(ContactsHeader c)	{
		csv_header.add(c);
	}
	public void addExcelTitle(ContactsHeader c)	{
		excel_header.add(c);
	}
	public void addPdfTitle(ContactsHeader c)	{
		pdf_header.add(c);
	}
}