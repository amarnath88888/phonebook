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
	private Properties properties;
	
	private List<ContactsHeader> text_header;
	private List<ContactsHeader> csv_header;
	private List<ContactsHeader> excel_header;
	private List<ContactsHeader> pdf_header;
	
	private List<ContactsBody> name_body;
	private List<ContactsBody> number_body;
	
	private List<Contacts> list;
	private Map<String, List<String>> map;
	
	public static final Logger logger = Logger.getLogger(ContactsInitializer.class);
	
	public ContactsInitializer()	{
		// initialize class variables
		properties = new Properties();
		list= new ArrayList<Contacts>();
		map = new HashMap<String, List<String>>();
		text_header = new ArrayList<ContactsHeader>();
		csv_header = new ArrayList<ContactsHeader>();
		excel_header = new ArrayList<ContactsHeader>();
		pdf_header = new ArrayList<ContactsHeader>();
		name_body = new ArrayList<ContactsBody>();
		number_body = new ArrayList<ContactsBody>();
	}
	
	public Properties getProperties()	{
		return properties;
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

	public List<ContactsBody> getName_body() {
		return name_body;
	}

	public void setName_body(List<ContactsBody> name_body) {
		this.name_body = name_body;
	}

	public List<ContactsBody> getNumber_body() {
		return number_body;
	}

	public void setNumber_body(List<ContactsBody> number_body) {
		this.number_body = number_body;
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
	
	public void initProperty(String path) {
		FileInputStream input_stream = null;		
		try {
			if (path == null)	{
			input_stream = new FileInputStream(new File(ApplicationConstants.PROPERTIES_PATH));
			}
			else	{
				input_stream = new FileInputStream(new File(path));
			}
		} catch (FileNotFoundException exception) {
			logger.error(exception);
			exception.printStackTrace();
		}
		try {
			properties.load(input_stream);
			logger.debug("Property file loaded");
		} catch (IOException exception) {
			logger.error(exception);
			exception.printStackTrace();
		}
		if(input_stream != null){
			try {
				input_stream.close();
				logger.debug(LoggerConstants.RESOURCES_RELEASED);
			} catch (IOException exception) {
				logger.error(exception);
				exception.printStackTrace();
			}
		}
	}
	
	public void initContacts(String path) {
		String input = null;
		Contacts contacts;
		ContactsGenerator contacts_generator;
		File folder;
		File[] listOfFiles;
		BufferedReader reader = null;
		
		contacts_generator = new ContactsGenerator();
		if(path == null)	{
			input = properties.getProperty(ApplicationConstants.VCF_INPUT);
		}
		else	{
			input = path;
		}		
		folder = new File(input);
		listOfFiles = folder.listFiles();
		
		try {
			for (File file : listOfFiles)	{	    	
				reader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file))));
				while ( reader.ready() )	{
					contacts= contacts_generator.generateContacts(reader,this);
					writeList(contacts);
					writeMap(contacts);				
				}
			}
			logger.info(list.size()+" contacts added to list successfully !");
			logger.info(map.size()+" contacts added to map successfully !");
			System.out.println(list.size()+" contacts added to list successfully !");
			System.out.println(map.size()+" contacts added to map successfully !");
		}catch (Exception exception) {
			System.out.println("contacts not created !");
			logger.error("contacts not created !");
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
	
	private void writeList(Contacts contacts){
		list.add(contacts); // Adding contacts to List
	}
	
	private void writeMap(Contacts contacts){
		List<String> value = new ArrayList<String>();
		String key;
		key=contacts.getNumber();
		value.add(contacts.getName());
		map.put(key, value);
	}
	
	public void initXmlHeader()	{
		String contactheader_xmlinput;
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
				
        contactheader_xmlinput = properties.getProperty(ApplicationConstants.CONTACTHEADER_XMLINPUT);
        
		try {
			digester.parse(this.getClass().getClassLoader().getResourceAsStream(contactheader_xmlinput));
		} catch (IOException exception) {
			logger.error(exception);
			exception.printStackTrace();
		} catch (SAXException exception) {
			logger.error(exception);
			exception.printStackTrace();
		}
		logger.info(text_header);
		logger.info(csv_header);
		logger.info(excel_header);
		logger.info(pdf_header);
	}
	public void addTextTitle(ContactsHeader header)	{
		text_header.add(header);
	}
	public void addCsvTitle(ContactsHeader header)	{
		csv_header.add(header);
	}
	public void addExcelTitle(ContactsHeader header)	{
		excel_header.add(header);
	}
	public void addPdfTitle(ContactsHeader header)	{
		pdf_header.add(header);
	}
	
	public void initXmlBody() {
		String contactbody_xmlinput;
		Digester digester = new Digester();
		
        logger.info("Push the current object onto the stack");		
		digester.push(this);

		logger.info("Creates a new instance of the ContactBody class for name");
		digester.addObjectCreate( "vcard/name", ContactsBody.class );
		digester.addBeanPropertySetter( "vcard/name/begin", "begin" );
		digester.addBeanPropertySetter( "vcard/name/index", "index" );
		digester.addSetNext( "vcard/name", "addNameBody" );
		
		logger.info("Creates a new instance of the ContactBody class for number");
		digester.addObjectCreate( "vcard/number", ContactsBody.class );
		digester.addBeanPropertySetter( "vcard/number/begin", "begin" );
		digester.addBeanPropertySetter( "vcard/number/index", "index" );
		digester.addSetNext( "vcard/number", "addNumberBody" );
		
        contactbody_xmlinput = properties.getProperty(ApplicationConstants.CONTACTBODY_XMLINPUT);
        
		try {
			digester.parse(this.getClass().getClassLoader().getResourceAsStream(contactbody_xmlinput));
		} catch (IOException exception) {
			logger.error(exception);
			exception.printStackTrace();
		} catch (SAXException exception) {
			logger.error(exception);
			exception.printStackTrace();
		}
		logger.info(name_body);
		logger.info(number_body);	
	}
	public void addNameBody(ContactsBody body)	{
		name_body.add(body);
	}
	public void addNumberBody(ContactsBody body)	{
		number_body.add(body);
	}
}