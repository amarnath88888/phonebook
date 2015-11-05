import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Phonebook {
	public static void main(String args[])	{
		menu();
	}
	
	public static void menu()	{
		try	{
			do	{
				System.out.println("1. Write contact info in a text file");
				System.out.println("2. Write contact info in a csv file");
				System.out.println("3. Write contact info in a excel file");
				System.out.println("4. Write contact info in a pdf file(Paragraph)");
				System.out.println("5. Write contact info in a pdf file(Table)");
				System.out.println("6. Search contact by number");
				System.out.println("0. Exit");
				System.out.println("Enter your choice(0-n)");
				BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
				int choice=Integer.parseInt(br.readLine());
				switch(choice)	{
				case 0 : return;
				case 1 : TextWriter.writeText(); break;
				case 2 : CsvWriter.csvFileRead(); break;
				case 3 : ExcelWriter.writeExcel(); break;
				case 4 : PDFWriter.writePdfPara(); break;
				case 5 : PDFWriter.writePdfTable(); break;
				case 6 : {
					ContactsSearch cs = new ContactsSearch();
					cs.searchName();
					break;
				}
				default : System.out.println("Enter a valid choice"); menu(); 
				}
			}
			while(true);
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
	}
}
