import java.io.FileWriter;
import java.util.List;


public class CsvWriter {
	public static void csvFileRead()	{
		try	{
			ContactsReader cr= new ContactsReader();
			List<Contacts> list= cr.getList();
			FileWriter fw = new FileWriter("output\\contacts.csv");
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
			System.out.println("CSV File Created successfully !");
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
}
