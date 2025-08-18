package leetcode;

public class LC_125 {

    public static void main(String[] args) {
        boolean res = new LC_125().isPalindrome("hello");
        System.out.println(res);
    }

    public boolean isPalindrome(String s) {
        s = s.toLowerCase();
        StringBuilder bd = new StringBuilder();
        byte[] arr = s.getBytes();
        for (byte b : arr) {
            if (((b >= 'a' && b <= 'z') || (b >= '0' && b <= '9'))) {
                bd.append((char) b);
            }
        }
        String input = bd.toString();
        int i = 0;
        int j = input.length() - 1;
        while (i <= j) {
            if (input.charAt(i) != input.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }


    public boolean isPalindromeV2(String s) {
        int i = 0, j = s.length() - 1;
        int len = s.length();
        while (i <= j) {
            while (i < len && !Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i))) {
                i++;
            }
            while (j > 0 && !Character.isLetter(s.charAt(j)) && !Character.isDigit(s.charAt(j))) {
                j--;
            }
            if (j < 0 || i >= len) {
                return true;
            }
            if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
