import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ContactsReader {
	
	private List<Contacts> l;
	
	ContactsReader()	{
		l= new ArrayList<Contacts>();
		readContacts();
	}
	
	List<Contacts> getList()	{
		return l;
	}
	
	private void readContacts()	{
		try	{
			File folder = new File("input\\temp");
			File[] listOfFiles = folder.listFiles();
			for (File f : listOfFiles)	{
		    	FileInputStream fstream = new FileInputStream(f);
		    	DataInputStream in = new DataInputStream(fstream);
		    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    	while ( br.ready() )	{
		    		Contacts temp= generateContacts(br);
		    		l.add(temp);
		    		System.out.println(temp.getName()+" "+temp.getNumber());
		    	}
		    	in.close();
		    }
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		System.out.println(l.size()+" contacts created successfully !");
	}
	
	private Contacts generateContacts(BufferedReader br) throws Exception	{
		String str;
		Contacts c= new Contacts();
		while ((str=br.readLine())!=null )	{
			if(str.startsWith("N;C"))	{
				int i=str.indexOf("E:");
				c.setName(str.substring(i+2));
				}
			if(str.startsWith("TEL"))	{
				int i=str.indexOf("L:");
				c.setNumber(str.substring(i+2));
			}
		}
		return c;
	}
}