package leetcode;

public class LC_1909 {

    public static void main(String[] args) {
        System.out.println(new LC_1909().canBeIncreasing(new int[]{2,3,1,2}));
    }

    public boolean canBeIncreasing(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                return boolCheck(nums, i >= 2 ? nums[i - 2] : -1, i) || boolCheck(nums, nums[i - 1], i + 1);
            }
        }
        return true;
    }

    private boolean boolCheck(int[] nums, int pre, int index) {
        int i = index;
        while (i < nums.length) {
            if (nums[i] <= pre) {
                return false;
            }
            pre = nums[i];
            i++;
        }
        return true;
    }
}
