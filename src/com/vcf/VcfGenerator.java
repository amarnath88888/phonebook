package com.vcf;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;

public class VcfGenerator {
	private String format;
	public static final Logger logger = Logger.getLogger(VcfGenerator.class);
	
	public void vcfXmlReader(ContactsInitializer contactsinitializer)	{
		String vcf_xmlinput;
		Properties properties = contactsinitializer.getProperties();
		Digester digester = new Digester();
		
        logger.info("Push the current object onto the stack");		
		digester.push(this);

		logger.info("Creates a new instance of the VcfFormat class for VCFContact Format");
		digester.addObjectCreate( "vcard", VcfFormat.class );
		digester.addBeanPropertySetter( "vcard/format", "format" );
		digester.addSetNext( "vcard", "formatVcf" );
		
		vcf_xmlinput = properties.getProperty(ApplicationConstants.VCF_XMLINPUT);
		try {
			digester.parse(this.getClass().getClassLoader().getResourceAsStream(vcf_xmlinput));
		} catch (IOException exception) {
			logger.error(exception);
			exception.printStackTrace();
		} catch (SAXException exception) {
			logger.error(exception);
			exception.printStackTrace();
		}	
	}
	public void formatVcf(VcfFormat format)	{
		this.format = format.getFormat();
		logger.info("VCF file format is stored in a string");
	}
	public void generateVcf(ContactsInitializer contactsinitializer, List<Contacts> list, String path) {
		String lines[];
		String vcfoutput;
		String filename;
		BufferedWriter writer = null;
		Properties properties = contactsinitializer.getProperties();
		
		if (path == null)	{
			vcfoutput = properties.getProperty(ApplicationConstants.VCF_OUTPUT);
		}
		else	{
			vcfoutput = path;
		}
		lines = format.split(ApplicationConstants.NEW_LINE);
		for (Contacts contact : list)	{
			filename = contact.getName()+".vcf";
			try {
				writer = new BufferedWriter(new FileWriter(vcfoutput+filename));
				for (String line : lines)	{
					writer.write(line);
					if(line.startsWith(contactsinitializer.getName_body().get(0).getBegin()))	{
						writer.write(contact.getName());
					}
					if(line.startsWith(contactsinitializer.getNumber_body().get(0).getBegin()))	{
						writer.write(contact.getNumber());
					}
					writer.write(ApplicationConstants.NEW_LINE);
				}
			    logger.info(contact.getName() + " " + ApplicationConstants.VCF_CREATED);
				System.out.println(contact.getName() + " " + ApplicationConstants.VCF_CREATED);
			} catch (IOException exception) {
				logger.info(contact.getName() + " " + ApplicationConstants.VCF_NOTCREATED);
				System.out.println(contact.getName() + " " + ApplicationConstants.VCF_NOTCREATED);
			    logger.error(exception);
				exception.printStackTrace();
			} finally	{
				if(writer != null)	{
					try {
						writer.close();
						logger.debug(LoggerConstants.RESOURCES_RELEASED);
					} catch (IOException exception) {
						logger.error(exception);
						exception.printStackTrace();
					}
				}
			}
		}
	}
}