import java.util.Scanner;

public class antipalindrome {
    public static void main(String[] args) {
        String line = new Scanner(System.in).nextLine();

        // 1. keep letters only, lowercase
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray())
            if (Character.isLetter(c)) sb.append(Character.toLowerCase(c));
        String s = sb.toString();

        // 2. quick exit if not enough letters
        if (s.length() < 2) {
            System.out.println("Anti-palindrome");
            return;
        }

        // 3. center expansion
        int n = s.length();
        for (int center = 0; center < n; center++) {
            // odd length (aba)
            if (expand(s, center, center)) return;
            // even length (abba)
            if (expand(s, center, center + 1)) return;
        }

        // 4. none found
        System.out.println("Anti-palindrome");
    }

    // expands while same chars; if a palindrome of len>=2 appears, print and exit
    private static boolean expand(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            if (r - l + 1 >= 2) {          // found one!
                System.out.println("Palindrome");
                return true;
            }
            l--; r++;
        }
        return false;
    }
}
