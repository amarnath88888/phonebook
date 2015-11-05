package com.writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.constants.ApplicationConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;

public class ExcelWriter {
	List<Contacts> list;
	Properties pro;
	
	String exceloutput;
	
	FileOutputStream fos;
	
	public void writeExcel(ContactsInitializer i)	{
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
			
			hwb = new HSSFWorkbook();
			sheet =  hwb.createSheet(ApplicationConstants.NEW_SHEET);

			rowhead= sheet.createRow(0);
			rowhead.createCell(0).setCellValue(ApplicationConstants.CONTACT_NAME);
			rowhead.createCell(1).setCellValue(ApplicationConstants.CONTACT_NUMBER);
			
			for (Contacts c : list)	{
				row = sheet.createRow(r++);
				row.createCell(0).setCellValue(c.getName());
				row.createCell(1).setCellValue(c.getNumber());
			}
			
			hwb.write(fos);
			System.out.println(ApplicationConstants.EXCEL_CREATED);
		}
		catch ( Exception e )	{
			System.out.println(ApplicationConstants.EXCEL_NOTCREATED);
			e.printStackTrace();
		}
		finally	{
			row = null;
			rowhead = null;
			sheet = null;
			hwb = null;
			list = null;
			exceloutput = null;
		    try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
}