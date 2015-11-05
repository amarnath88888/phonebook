import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWriter {
	public static void writeExcel()	{
		try	{
			FileOutputStream fileOut =  new FileOutputStream("output\\contacts.xls");
			ContactsReader cr= new ContactsReader();
			List<Contacts> list= cr.getList();
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
			System.out.println("Your excel file has been generated!");
		}
		catch ( Exception e )	{
			e.printStackTrace();
		}
	}
}