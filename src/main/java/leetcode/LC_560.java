package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LC_560 {

    public static void main(String[] args) {
//        System.out.println(new LC_560().subarraySum(new int[]{1,2,3}, 3));
//        System.out.println(new LC_560().subarraySum(new int[]{-1,-1,-1}, -3));
        System.out.println(new LC_560().subarraySum(new int[]{6,4,3,1}, 10));
    }

    public int subarraySum(int[] nums, int k) {
        int[] preSum = new int[nums.length];
        preSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        Map<Integer, List<Integer>> indexs = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> l = indexs.computeIfAbsent(preSum[i], key -> new ArrayList<>());
            l.add(i);
        }
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (preSum[i] == k) {
                count++;
            }
            int n = preSum[i] + k;
            List<Integer> found = indexs.getOrDefault(n, new ArrayList<>());
            for (Integer item : found) {
                if (item > i) {
                    count++;
                }
            }
        }
        return count;
    }
}
