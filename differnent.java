import java.util.Scanner;

public class differnent{

	public static void main(String[] args){
		Scanner input = new Scanner(System.in);

		while(input.hasNextLong()) {
			long a = input.nextLong();

			if(input.hasNextLong()) {
				long b = input.nextLong();
				System.out.print(diff(a, b));
			}

			else {
				break;
			}
		}
		input.close();
	}  

	public static long diff(long a, long b){
		return Math.abs(a - b);
	}
}