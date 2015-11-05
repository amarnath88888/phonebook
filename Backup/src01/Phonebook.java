import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Phonebook {
	public static void main(String args[])	{
		menu();
	}
	
	public static void menu()	{
		try	{
			do	{
				System.out.println("1. Print the contents line by line");
				System.out.println("2. Print the contents Character by character");
				System.out.println("3. List all files in a folder");
				System.out.println("4. Read contacts alone from the folder");
				System.out.println("5. Write the vcfcontacts in a text file");
				System.out.println("6. Write contact info in a text file");
				System.out.println("7. Write contact info in a csv file");
				System.out.println("8. Write contact info in a excel file");
				System.out.println("9. Write contact info in a pdf file");
				System.out.println("0. Exit");
				System.out.println("Enter your choice(0-n)");
				BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
				int choice=Integer.parseInt(br.readLine());
				switch(choice)	{
				case 0 : return;
				case 1 : Sample.fileReadLineByLine(); break;
				case 2 : Sample.fileReadCharByChar(); break;
				case 3 : Sample.listFiles(); break;
				case 4 : Contacts.contactsRead(); break;
				case 5 : Contacts.fileWrite(); break;
				case 6 : TextFile.textFileRead(); break;
				case 7 : CsvFile.csvFileRead(); break;
				case 8 : ExcelFile.excelFileRead(); break;
				//case 9 : PdfFile.pdfFileRead(); break;
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
