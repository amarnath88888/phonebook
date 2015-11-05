import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Sample {

	public static void fileReadLineByLine()	{
		try	{
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream("input\\temp\\A ravind Airte.vcf");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console
				System.out.println (strLine);
				Thread.sleep(1000);
				}
			//Close the input stream
			in.close();
			}
		catch (Exception e){//Catch exception if any
			e.printStackTrace();
		}
	}
	
	public static void fileReadCharByChar()	  {
		try	{
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream("input\\temp\\A ravind Airte.vcf");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in),1);
			char ch;
			while ( br.ready() )   {
				ch = (char)br.read();
				// Print the content on the console
				System.out.print (ch);
				Thread.sleep(50);
			}
			//Close the input stream
			in.close();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	public static void listFiles()	{
		try	{
			File folder = new File("input\\temp");
			File[] listOfFiles = folder.listFiles();
			for (File f : listOfFiles)	{
				if (f.isFile())
					System.out.println(f.getName());
				fileDisplay(f);
			}
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	public static void fileDisplay(File f)	{
		try	{
			FileInputStream fstream = new FileInputStream(f);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			char ch;
			while ( br.ready() )	{
				ch = (char)br.read();
				System.out.print (ch);
				Thread.sleep(20);
			}
			in.close();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
}