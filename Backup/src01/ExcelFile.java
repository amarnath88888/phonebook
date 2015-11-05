import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelFile {
	public static void excelFileRead()	{
		try	{
			FileOutputStream fileOut =  new FileOutputStream("output\\contacts.xls");
			File folder = new File("input\\temp");
			File[] listOfFiles = folder.listFiles();
			HSSFWorkbook hwb=new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("new sheet");

			HSSFRow rowhead= sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Name");
			rowhead.createCell(1).setCellValue("Mobile Number");
			
			int i=1;
			for (File f : listOfFiles)	{
				FileInputStream fstream = new FileInputStream(f);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				HSSFRow row= sheet.createRow(i++);
				
				while ( br.ready() )	{
					excelFileWrite(br,row);
				}
				in.close();
			}
			
			hwb.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated!");
		}
		catch ( Exception e )	{
			e.printStackTrace();
		}
	}
	public static void excelFileWrite(BufferedReader br,HSSFRow row) throws Exception	{
		String str;
		while ((str=br.readLine())!=null )	{
			if(str.startsWith("N;C"))	{
				int i=str.indexOf("E:");
				row.createCell(0).setCellValue(str.substring(i+2));
			}
			if(str.startsWith("TEL"))	{
				int i=str.indexOf("L:");
				row.createCell(1).setCellValue(str.substring(i+2));
			}
		}
	}
}