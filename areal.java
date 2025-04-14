import java.util.Scanner;
public class areal{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        long a = input.nextLong();
        long b = (long)Math.sqrt(a);
        long p = 4 * b;
        System.out.println(p);
    }
}