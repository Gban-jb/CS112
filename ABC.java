import java.util.Scanner;
import java.util.*;
public class ABC {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int [] num = new int[3];
		for(int i = 0; i<=2; i++) {
			num[i] = sc.nextInt();
		}
		
		String words = sc.next();
		
		Arrays.sort(num);
		
		for(char c: words.toCharArray()) {
			if(c=='A')
				System.out.print(num[0]+" ");
			else if(c=='B')
			System.out.print(num[1]+" ");
			else if(c=='C')
				System.out.print(num[2]+" ");
		}
		sc.close();
	}
}
