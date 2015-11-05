import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ContactsSearch {
	public void searchName()	{
		String key=null;
		String value=null;
		Map<String, Object> m;
		List<String> li;
		ContactsWriter cr = new ContactsWriter();
		cr.writeContacts();
		m = cr.getMap();
		try	{
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter the mobile number to be searched (+91xxxxxxxxxx) : ");
			key = bf.readLine();
			if(m.containsKey(key))	{
				li = (ArrayList<String>) m.get(key);
				value = (String) li.get(0);
				System.out.println("Search Found !");
			    System.out.println("Contact Name : " + value);
			    System.out.println("Contact Number : " + key);
			}
			else	{
				System.out.println("Number not found !");
			}
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
}