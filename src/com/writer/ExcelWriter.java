package com.writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.Contacts;
import com.contacts.ContactsHeader;
import com.contacts.ContactsInitializer;


public class ExcelWriter implements IContactsWriter {
	
	public static final Logger logger = Logger.getLogger(ExcelWriter.class);
	
	public void writeContacts(ContactsInitializer contactsinitializer)	{
		List<Contacts> list;
		List<ContactsHeader> excel_header;
		Properties properties;
		String exceloutput;	
		ContactsHeader header;
		FileOutputStream outputstream = null;
		
		int index=1;
		HSSFWorkbook workbook;
		HSSFSheet sheet;
		HSSFRow rowhead;
		HSSFRow row;
		try	{
			list= contactsinitializer.getList();
			properties=contactsinitializer.getProperties();
			exceloutput = properties.getProperty(ApplicationConstants.EXCEL_OUTPUT);
			
			outputstream =  new FileOutputStream(exceloutput);
			excel_header = contactsinitializer.getExcel_header();
			
			workbook = new HSSFWorkbook();
			sheet =  workbook.createSheet(ApplicationConstants.NEW_SHEET);

			rowhead= sheet.createRow(0);
			header = excel_header.get(0);
			rowhead.createCell(0).setCellValue(header.getTitle());
			header = excel_header.get(1);
			rowhead.createCell(1).setCellValue(header.getTitle());
			
			for (Contacts contacts : list)	{
				row = sheet.createRow(index++);
				row.createCell(0).setCellValue(contacts.getName());
				row.createCell(1).setCellValue(contacts.getNumber());
			}
			workbook.write(outputstream);
			logger.debug(LoggerConstants.RESOURCES_RELEASED);
			logger.info(ApplicationConstants.EXCEL_CREATED);
			System.out.println(ApplicationConstants.EXCEL_CREATED);
		}
		catch ( Exception exception )	{
			logger.info(ApplicationConstants.EXCEL_NOTCREATED);
			System.out.println(ApplicationConstants.EXCEL_NOTCREATED);
			logger.error(exception);
			exception.printStackTrace();
		}
		finally	{
			if(outputstream !=null)	{
				try {
					outputstream.close();
					logger.debug(LoggerConstants.RESOURCES_RELEASED);
				} catch (IOException exception) {
					logger.error(exception);
					exception.printStackTrace();
				}
			}
		}
	}
}