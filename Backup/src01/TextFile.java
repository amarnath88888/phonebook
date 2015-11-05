import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class TextFile {
	
	public static void textFileRead()	{
		try	{
			File folder = new File("input\\temp");
			File[] listOfFiles = folder.listFiles();
			FileWriter fs = new FileWriter("output\\contacts.txt");
			BufferedWriter bw = new BufferedWriter(fs);
		    for (File f : listOfFiles)	{
		    	FileInputStream fstream = new FileInputStream(f);
		    	DataInputStream in = new DataInputStream(fstream);
		    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    	while ( br.ready() )	{
		    		textFileWrite(br,bw);
		    	}
		    	in.close();
		    }
		    bw.close();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		System.out.println("Text file created successfully !");
	}
	
	public static void textFileWrite(BufferedReader br,BufferedWriter bw) throws Exception	{
		String str;
		while ((str=br.readLine())!=null )	{
			if(str.startsWith("N;C"))	{
				int i=str.indexOf("E:");
				bw.write(str.substring(i+2)+"\t");
			}
			if(str.startsWith("TEL"))	{
				int i=str.indexOf("L:");
				bw.write(str.substring(i+2));
				bw.newLine();
			}
		}
	}
}