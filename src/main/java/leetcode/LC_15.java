package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LC_15 {

    public static void main(String[] args) {
        LC_15 lc = new LC_15();
//        int[] nums = {2,-3,0,-2,-5,-5,-4,1,2,-2,2,0,2,-4,5,5,-10};
        int[] nums = {0,0,0,0,0,0,0,0,0,0};
        List<List<Integer>> result = lc.threeSum(nums);
        System.out.println(result);
    }

//    public List<List<Integer>> threeSum(int[] nums) {
//        Map<Integer, Integer> freq = new HashMap<>();
//        for (int i = 0; i < nums.length; i++) {
//            Integer val = freq.get(nums[i]);
//            if (val != null) {
//                freq.put(nums[i], val + 1);
//            } else {
//                freq.put(nums[i], 1);
//            }
//        }
//        List<List<Integer>> result = new ArrayList<>();
//        List<Integer> integers = new ArrayList<>(freq.keySet());
//        for (int num : integers) {
//            int target = -num;
//            Map<Integer, Integer> clone = new HashMap<>(freq);
//            Integer f = clone.get(num);
//            if (f == 1) {
//                clone.remove(num);
//            } else {
//                clone.put(target, f - 1);
//            }
//            List<List<Integer>> r = twoSum(clone, num, target);
//            result.addAll(r);
//        }
//        return result;
//    }
//
//    public List<List<Integer>> twoSum(Map<Integer, Integer> freq, int key, int target) {
//        List<List<Integer>> res = new ArrayList<>();
//        List<Integer> integers = new ArrayList<>(freq.keySet());
//        for (Integer c : integers) {
//            int find = target - c;
//            Integer firstNum = freq.get(c);
//            Integer secondNum = freq.get(find);
//            if (c != find && secondNum != null) {
//                res.add(Arrays.asList(c, target, key));
//                freq.remove(c);
//                freq.remove(find);
//            } else if (c == find && firstNum > 1) {
//                res.add(Arrays.asList(c, c, key));
//                freq.remove(c);
//            }
//        }
//        return res;
//    }


    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        System.out.println(Arrays.toString(nums));

        for (int i = 0; i < nums.length - 2; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int target = -nums[i];
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                }
                if (nums[i] + nums[left] + nums[right] >= 0) {
                    right--;
                }
                if (nums[i] + nums[left] + nums[right] < 0) {
                    left++;
                }
            }
        }
        return result;
    }
}
