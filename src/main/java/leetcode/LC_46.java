package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LC_46 {

    List<List<Integer>> result;

    public static void main(String[] args) {
        int[] inputs = {1, 2};
        int[] visited = new int[2];
        new LC_46().permutation(inputs, visited, new ArrayList<>());
    }

    private void permutation(int[] inputs, int[] visited, List<Integer> current) {
        if (current.size() == inputs.length) {
            List<Integer> e = List.copyOf(current);
            result.add(e);
//            System.out.println(current);
            return;
        }
        for (int i = 0; i < inputs.length; i++) {
            if (visited[i] == 0) {
                visited[i] = 1;
                current.add(inputs[i]);
                permutation(inputs, visited, current);
                current.remove(Integer.valueOf(inputs[i]));
                visited[i] = 0;
            }
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        // Arrays.sort(nums); // not necessary
        backtrack(list, new ArrayList<>(), nums);
        return list;
    }

    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums){
        if(tempList.size() == nums.length){
            list.add(new ArrayList<>(tempList));
        } else{
            for (int num : nums) {
                if (tempList.contains(num)) continue; // element already exists, skip
                tempList.add(num);
                backtrack(list, tempList, nums);
                tempList.remove(tempList.size() - 1);
            }
        }
    }
}
