package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LC_1415 {

    private final List<String> result = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(new LC_1415().getHappyString(3, 9));
    }

    public String getHappyString(int n, int k) {
        String input = "abc";
        boolean backtrack = backtrack(input, n, k, new StringBuilder());
        if (backtrack) {
            return result.get(result.size() - 1);
        }
        return "";
    }

    public boolean backtrack(String input, int n, int k, StringBuilder current) {
        if (current.length() >= n) {
            result.add(current.toString());
            return result.size() == k;
        }
        char c = '0';
        if (current.length() > 0) {
            c = current.charAt(current.length() - 1);
        }
        for (int i = 0; i < input.length(); i++) {
            char next = input.charAt(i);
            if (next != c) {
                current.append(next);
                boolean backtrack = backtrack(input, n, k, current);
                if (backtrack) {
                    return true;
                }
                current.deleteCharAt(current.length() - 1);
            }
        }
        return false;
    }
}
