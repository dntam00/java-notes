package leetcode;

public class LC_383 {

    public static void main(String[] args) {

    }

    public boolean canConstruct(String ransomNote, String magazine) {
        if (magazine.length() < ransomNote.length()) {
            return false;
        }
        int[] map = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            map[magazine.charAt(i) - 'a']++;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            if (map[ransomNote.charAt(i) - 'a'] == 0) {
                return false;
            }
            map[ransomNote.charAt(i) - 'a']--;
        }
        return true;
    }
}
