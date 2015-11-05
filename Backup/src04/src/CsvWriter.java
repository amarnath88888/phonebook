import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;


public class CsvWriter {
	public static void csvFileRead()	{
		try	{
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			ContactsWriter cr= new ContactsWriter();
			cr.writeContacts();
			List<Contacts> list= cr.getList();
			Properties pro = new Properties();
			FileInputStream fis = new FileInputStream(new File("properties\\Contacts.properties"));
			pro.load(fis);
			String csvoutput = pro.getProperty("csvoutput");
			FileWriter fw = new FileWriter(csvoutput);
			fw.append("Name");
			fw.append(',');
			fw.append("Mobile number");
			fw.append('\n');
			for (Contacts c : list)	{
		    	System.out.println(c.getName()+" "+c.getNumber());
		    	fw.append(c.getName());
		    	fw.append(',');
		    	fw.append(c.getNumber());
		    	fw.append('\n');
			}
			fw.close();
			fis.close();
			System.out.println("CSV File Created successfully !");
			bf.read();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
}
