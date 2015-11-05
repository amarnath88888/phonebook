package com.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import com.constants.ApplicationConstants;
import com.constants.LoggerConstants;
import com.contacts.Contacts;
import com.contacts.ContactsInitializer;

public class ExcelReader implements IContactsReader{

	public static final Logger logger = Logger.getLogger(ExcelReader.class);
	
	public List<Contacts> readContacts(ContactsInitializer contactsInitializer) {
		List<Contacts> list = new ArrayList<Contacts>();
		Properties properties;
		Contacts contacts;
		String excelinput;
		FileInputStream inputstream = null;
		
		POIFSFileSystem myExcelFile;
		HSSFWorkbook myWorkBook;
		HSSFSheet sheet;
		HSSFRow row;
		HSSFCell cell0;
		HSSFCell cell1;
		Iterator<Row> rowIter;
		
		try	{
			properties=contactsInitializer.getProperties();
			excelinput = properties.getProperty(ApplicationConstants.EXCEL_INPUT);
			
			inputstream = new FileInputStream(excelinput);
			
			myExcelFile = new POIFSFileSystem(inputstream);
			myWorkBook = new HSSFWorkbook(myExcelFile);
            sheet = myWorkBook.getSheetAt(0);

            rowIter = sheet.rowIterator();
            
            rowIter.next();
            
            while (rowIter.hasNext()) {
            	row = (HSSFRow) rowIter.next();
            	cell0 = row.getCell(0);
            	cell1 = row.getCell(1);
            	cell0.getStringCellValue();
   				contacts = new Contacts();
				contacts.setName(cell0.getStringCellValue());
				contacts.setNumber(cell1.getStringCellValue());
				list.add(contacts);
				System.out.println(contacts.getName()+"--" + contacts.getNumber());
			}
			logger.info(list.size()+" contacts added to list from excel file successfully !");
			System.out.println(list.size()+" contacts added to list from excel file successfully !");
			
		} catch (Exception exception)	{
			System.out.println("contacts not created !");
			logger.error("contacts not created !");
			logger.error(exception);
			exception.printStackTrace();
		} finally	{
			if(inputstream != null)	{
				try {
					inputstream.close();
					logger.debug(LoggerConstants.RESOURCES_RELEASED);
				} catch (IOException exception) {
					logger.error(exception);
					exception.printStackTrace();
				}
			}
		}
		return list;
	}
}