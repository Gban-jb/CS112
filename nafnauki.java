import java.util.Scanner;

public class nafnauki {                 // use “Main” for most judges
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();               // read the whole line

        int dot = file.lastIndexOf('.');           // find last '.'
        // dot is guaranteed to exist because the statement says
        // “The file name will always have a valid file extension”.
        String extension = file.substring(dot);    // keep the dot, too

        System.out.println(extension);
    }
}
