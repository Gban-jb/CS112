	import java.util.Scanner;
	public class LineLength {			//Class
		public static void main(String[] args) {
			Scanner input = new Scanner(System.in);
			System.out.print("Enter a line of text: ");
			String text = input.nextLine();
			int ch = 0;
			
			for(int i=0; i<text.length(); i++) { 				//int ch = text.length();
				 char line= text.charAt(i);
				ch = ch+1;
						}
				System.out.printf("That line is %d character(s) long.\n", ch);
				
		input.close();
		}
	}
