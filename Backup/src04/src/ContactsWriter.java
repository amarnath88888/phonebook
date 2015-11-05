import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class ContactsWriter {
	private List<Contacts> l;
	private Map<String, Object> m;
	
	public ContactsWriter()	{
		l= new ArrayList<Contacts>();
		m = new HashMap<String, Object>();
	}
	
	public List<Contacts> getList()	{
		return l;
	}
	
	public void setList(List<Contacts> l)	{
		this.l = l;
	}
	public Map<String, Object> getMap() {
		return m;
	}

	public void setMap(Map<String, Object> m) {
		this.m = m;
	}

	public void writeContacts()	{
		try	{
			Contacts c;
			String input;
			ContactsGenerator cg= new ContactsGenerator();
			Properties pro = new Properties();
			FileInputStream fis = new FileInputStream(new File("properties\\Contacts.properties"));
			pro.load(fis);
			input = pro.getProperty("input");
			File folder = new File(input);
			File[] listOfFiles = folder.listFiles();
			for (File f : listOfFiles)	{
		    	FileInputStream fstream = new FileInputStream(f);
		    	DataInputStream in = new DataInputStream(fstream);
		    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    	while ( br.ready() )	{
		    		c= cg.generateContacts(br);
		    		writeList(c);
		    		writeMap(c);
		    	}
		    	in.close();
		    }
			fis.close();
			System.out.println(l.size()+" contacts added to list successfully !");
			System.out.println(m.size()+" contacts added to map successfully !");
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	private void writeList(Contacts c){
		l.add(c); // Adding contacts to List
	}
	
	private void writeMap(Contacts c){
		List<String> value = new ArrayList<String>();
		String key;
		key=c.getNumber();
		value.add(c.getName());
		m.put(key, value);
	}
}
