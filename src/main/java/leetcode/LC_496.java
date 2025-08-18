package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class LC_496 {

    public static void main(String[] args) {
        int[] ints = new LC_496().nextGreaterElementV2(new int[]{4,1,2}, new int[]{1,3,4,2});
        int[] ints2 = new LC_496().nextGreaterElementV2(new int[]{1,3,5,2,4}, new int[]{5,4,3,2,1});
        System.out.println(Arrays.toString(ints));
        System.out.println(Arrays.toString(ints2));
    }

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            map.put(nums2[i], i);
        }
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            int val = nums1[i];
            int index = map.get(val);
            boolean find = false;
            for (int j = index + 1; j < nums2.length; j++) {
                if (nums2[j] > val) {
                    find = true;
                    res[i] = nums2[j];
                    break;
                }
            }
            if (!find) {
                res[i] = -1;
            }
        }
        return res;
    }

    // monolithic stack implementation
    public int[] nextGreaterElementV1(int[] nums1, int[] nums2) {
        Stack<Integer> stack = new Stack<>();
        int len2 = nums2.length;
        Map<Integer, Integer> map = new HashMap<>();
        stack.push(nums2[nums2.length - 1]);
        for (int i = len2 - 1 ; i >= 0; i--) {
            while (!stack.isEmpty()) {
                Integer peek = stack.peek();
                if (peek > nums2[i]) {
                    map.put(nums2[i], peek);
                    stack.push(nums2[i]);
                    break;
                } else {
                    stack.pop();
                }
            }
            if (stack.isEmpty()) {
                map.put(nums2[i], -1);
            }
            stack.push(nums2[i]);
        }
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }


    public int[] nextGreaterElementV2(int[] nums1, int[] nums2) {
        int[] pair = new int[10001];
        for (int i = 0; i < nums2.length; i++) {
            pair[nums2[i]] = i;
        }
        int[] n = nextGreater(nums2);
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            int nextGreaterIndex = n[pair[nums1[i]]];
            res[i] = nextGreaterIndex == -1 ? -1 : nums2[nextGreaterIndex];
        }
        return res;
    }

    public int[] nextGreater(int[] arr) {
        Stack<Integer> s = new Stack<>();
        int[] n = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            while(!s.isEmpty() && (arr[s.peek()] < arr[i])) {
                n[s.pop()] = i;
            }
            s.push(i);
        }
        while(!s.isEmpty()) {
            n[s.pop()] = -1;
        }
        return n;
    }
}
