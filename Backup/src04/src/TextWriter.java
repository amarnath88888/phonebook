import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;


public class TextWriter {
	
	public static void writeText()	{
		try	{
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			ContactsWriter cr= new ContactsWriter();
			cr.writeContacts();
			List<Contacts> list= cr.getList();
			Properties pro = new Properties();
			FileInputStream fis = new FileInputStream(new File("properties\\Contacts.properties"));
			pro.load(fis);
			String textoutput = pro.getProperty("textoutput");
			BufferedWriter bw = new BufferedWriter(new FileWriter(textoutput));
			
		    for (Contacts c : list)	{
		    	System.out.println(c.getName()+" "+c.getNumber());
		    	bw.write(c.getName()+"\t");
		    	bw.write(c.getNumber());
		    	bw.newLine();
		    }
		    bw.close();
		    fis.close();
		    System.out.println("Text file created successfully !");
		    bf.read();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
}