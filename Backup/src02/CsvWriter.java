import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class CsvWriter {
	public static void csvFileRead()	{
		try	{
			File folder = new File("input\\temp");
			File[] listOfFiles = folder.listFiles();
			FileWriter fw = new FileWriter("output\\contacts.csv");
			fw.append("Name");
			fw.append(',');
			fw.append("Mobile number");
			fw.append('\n');
			for (File f : listOfFiles)	{
				FileInputStream fstream = new FileInputStream(f);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				while ( br.ready() )	{
					csvFileWrite(br,fw);
				}
				in.close();
			}
			fw.close();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		System.out.println("CSV File Created successfully !");
	}
	
	public static void csvFileWrite(BufferedReader br,FileWriter fw) throws Exception	{
		String str;
		while ((str=br.readLine())!=null )	{
			if(str.startsWith("N;C"))	{
				int i=str.indexOf("E:");
				fw.append(str.substring(i+2));
				fw.append(',');
			}
			if(str.startsWith("TEL"))	{
				int i=str.indexOf("L:");
				fw.append(str.substring(i+2));
				fw.append('\n');
			}
		}
	}
}
