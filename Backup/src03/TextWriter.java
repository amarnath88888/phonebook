import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;


public class TextWriter {
	
	public static void writeText()	{
		try	{
			ContactsReader cr= new ContactsReader();
			List<Contacts> list= cr.getList();
			BufferedWriter bw = new BufferedWriter(new FileWriter("output\\contacts.txt"));
			
		    for (Contacts c : list)	{
		    	System.out.println(c.getName()+" "+c.getNumber());
		    	bw.write(c.getName()+"\t");
		    	bw.write(c.getNumber());
		    	bw.newLine();
		    }
		    bw.close();
		    System.out.println("Text file created successfully !");
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
}