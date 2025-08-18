package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LC_1980 {

    String result = "";

    public static void main(String[] args) {
        String[] nums = {"111","011","001"};
        System.out.println(new LC_1980().findDifferentBinaryString(nums));
    }

    public String findDifferentBinaryString(String[] nums) {
        Set<String> strings = new HashSet<>(nums.length);
        strings.addAll(Arrays.asList(nums));
        backtrack(strings, new StringBuilder(), nums.length);
        return result;
    }

    public boolean backtrack(Set<String> nums, StringBuilder current, int n) {
        if (current.length() == n) {
            if (nums.contains(current.toString())) {
                return false;
            }
            result = current.toString();
            return true;
        }
        current.append('0');
        boolean backtrack = backtrack(nums, current, n);
        if (backtrack) {
            return true;
        }
        current.deleteCharAt(current.length() - 1);
        current.append('1');
        backtrack = backtrack(nums, current, n);
        current.deleteCharAt(current.length() - 1);
        return backtrack;
    }
}
