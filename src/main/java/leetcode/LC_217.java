package leetcode;

import java.util.HashSet;
import java.util.Set;

public class LC_217 {

    public static void main(String[] args) {

    }

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);
        for (int v : nums) {
            if (set.contains(v)) {
                return true;
            }
            set.add(v);
        }
        return false;
    }
}
