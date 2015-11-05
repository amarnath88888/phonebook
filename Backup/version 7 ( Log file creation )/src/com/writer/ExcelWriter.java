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


public class ExcelWriter implements ContactsWriter {
	
	static final Logger logger = Logger.getLogger(ExcelWriter.class);
	public void writeContacts(ContactsInitializer i)	{
		List<Contacts> list;
		List<ContactsHeader> excel_header;
		Properties pro;		
		String exceloutput;		
		ContactsHeader header;
		FileOutputStream fos = null;
		
		int r=1;
		HSSFWorkbook hwb;
		HSSFSheet sheet;
		HSSFRow rowhead;
		HSSFRow row;
		try	{
			list= i.getList();
			pro=i.getProperties();
			exceloutput = pro.getProperty(ApplicationConstants.EXCEL_OUTPUT);
			
			fos =  new FileOutputStream(exceloutput);
			excel_header = i.getExcel_header();
			
			hwb = new HSSFWorkbook();
			sheet =  hwb.createSheet(ApplicationConstants.NEW_SHEET);

			rowhead= sheet.createRow(0);
			header = excel_header.get(0);
			rowhead.createCell(0).setCellValue(header.getTitle());
			header = excel_header.get(1);
			rowhead.createCell(1).setCellValue(header.getTitle());
			
			for (Contacts c : list)	{
				row = sheet.createRow(r++);
				row.createCell(0).setCellValue(c.getName());
				row.createCell(1).setCellValue(c.getNumber());
			}
			hwb.write(fos);
			logger.debug(LoggerConstants.RESOURCES_RELEASED);
			logger.info(ApplicationConstants.EXCEL_CREATED);
			System.out.println(ApplicationConstants.EXCEL_CREATED);
		}
		catch ( Exception e )	{
			logger.info(ApplicationConstants.EXCEL_NOTCREATED);
			System.out.println(ApplicationConstants.EXCEL_NOTCREATED);
			logger.error(e);
			e.printStackTrace();
		}
		finally	{
			if(fos !=null)
				try {
					fos.close();
					logger.debug(LoggerConstants.RESOURCES_RELEASED);
				} catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
		}
	}
}