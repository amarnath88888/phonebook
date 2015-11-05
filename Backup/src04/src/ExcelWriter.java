import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWriter {
	public static void writeExcel()	{
		try	{
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			ContactsWriter cr= new ContactsWriter();
			cr.writeContacts();
			List<Contacts> list= cr.getList();
			Properties pro = new Properties();
			FileInputStream fis = new FileInputStream(new File("properties\\Contacts.properties"));
			pro.load(fis);
			String exceloutput = pro.getProperty("exceloutput");
			FileOutputStream fileOut =  new FileOutputStream(exceloutput);
			HSSFWorkbook hwb=new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("new sheet");

			HSSFRow rowhead= sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Name");
			rowhead.createCell(1).setCellValue("Mobile Number");
			
			int i=1;
			for (Contacts c : list)	{
				HSSFRow row= sheet.createRow(i++);
				row.createCell(0).setCellValue(c.getName());
				row.createCell(1).setCellValue(c.getNumber());
			}
			hwb.write(fileOut);
			fileOut.close();
			fis.close();
			System.out.println("Your excel file has been generated!");
			bf.read();
		}
		catch ( Exception e )	{
			e.printStackTrace();
		}
	}
}