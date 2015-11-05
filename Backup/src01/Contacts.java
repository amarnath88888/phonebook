import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class Contacts {
	public static void contactsRead()	{
		try	{
			File folder = new File("input\\temp");
			File[] listOfFiles = folder.listFiles();
			for (File f : listOfFiles)	{
				/*if (f.isFile())
				 * System.out.println("File name : "+f.getName());*/
				FileInputStream fstream = new FileInputStream(f);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				read(br);
				in.close();
			}
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		System.out.println("vcf contact text file created succesfully !");
	}
	
	public static void read(BufferedReader br) throws Exception	{
		char ch;
		while ( br.ready() )	{
			ch = (char)br.read();
			if(ch=='E')
				display(br);
			if (ch=='L')
				display(br);
			//Thread.sleep(10);
		}
	}
	
	public static void display(BufferedReader br) throws Exception	{
		char ch;
		ch = (char)br.read();
		if(ch==':')	{
			while(ch !='\n')	{
				ch = (char)br.read();
				System.out.print(ch);
			}
		}
	}
	
	public static void fileWrite()	{
		try	{
			File folder = new File("input\\temp");
			File[] listOfFiles = folder.listFiles();
		    FileWriter fs = new FileWriter("output\\vcfcontacts.txt");
		    BufferedWriter out = new BufferedWriter(fs);
		    char ch;
		    for (File f : listOfFiles)	{
		    	FileInputStream fstream = new FileInputStream(f);
		    	DataInputStream in = new DataInputStream(fstream);
		    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    	while ( br.ready() )	{
		    		ch = (char)br.read();
		    		out.write(ch);
		    	}
		    	in.close();
		    }
		    out.close();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
}
