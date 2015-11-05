import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


class ContactsReader {
	private List<Contacts> l;
	
	ContactsReader()	{
		l= new ArrayList<Contacts>();
	}
	
	List<Contacts> getList()	{
		return l;
	}
	
	void readContacts()	{
		try	{
			File folder = new File("input\\temp");
			File[] listOfFiles = folder.listFiles();
			Contacts c= new Contacts();
			Contacts temp;
			for (File f : listOfFiles)	{
		    	FileInputStream fstream = new FileInputStream(f);
		    	DataInputStream in = new DataInputStream(fstream);
		    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    	while ( br.ready() )	{
		    		temp= generateContacts(br,c);
		    		l.add(temp);
		    		System.out.println(temp.getName()+" "+c.getNumber());
		    		c.setName(null);
		    		c.setNumber(null);
		    	}
		    	in.close();
		    }
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		System.out.println(l.size()+" contacts created successfully !");
	}
	
	private Contacts generateContacts(BufferedReader br, Contacts c) throws Exception	{
		String str;
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
