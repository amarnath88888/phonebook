import java.io.BufferedReader;


public class ContactsGenerator {
	
	public Contacts generateContacts(BufferedReader br) throws Exception	{
		String str;
		int i;
		Contacts c= new Contacts();
		while ((str=br.readLine())!=null )	{
			if(str.startsWith("N;C"))	{
				i=str.indexOf("E:");
				c.setName(str.substring(i+2));
				}
			if(str.startsWith("TEL"))	{
				i=str.indexOf("L:");
				c.setNumber(str.substring(i+2));
			}
		}
		return c;
	}
}