package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LC_2423 {

    public static void main(String[] args) {
        System.out.println(new LC_2423().equalFrequency("abcc"));
    }

    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2423.Remove%20Letter%20To%20Equalize%20Frequency/README_EN.md
    // dummy data at first, update this dummy data when looping through array

    public boolean equalFrequency(String word) {
        int[] freq = new int[26];
        for (int i = 0; i < word.length(); i++) {
            freq[word.charAt(i) - 'a']++;
        }
        Map<Integer, Integer> nums = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            if (freq[i] != 0) {
                if (nums.containsKey(freq[i])) {
                    nums.put(freq[i], nums.get(freq[i]) + 1);
                } else {
                    nums.put(freq[i], 1);
                }
            }
        }

        List<Integer> keyList = new ArrayList<>(nums.keySet());

        for (Integer integer : keyList) {
            HashMap<Integer, Integer> copy = new HashMap<>(nums);
            int key = integer;
            Integer val = nums.get(key);
            if (val == 1) {
                copy.remove(key);
            }
            if (key != 1) {
                if (copy.containsKey(key - 1)) {
                    copy.put(key - 1, copy.get(key - 1) + 1);
                } else {
                    copy.put(key - 1, 1);
                }
            }
            if (copy.size() <= 1) {
                return true;
            }
        }

        return false;
    }
}
