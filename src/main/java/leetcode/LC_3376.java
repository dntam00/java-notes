package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LC_3376 {

    public static void main(String[] args) {
        List<Integer> strength = new ArrayList<>(Arrays.asList(3,4,1));
        System.out.println(new LC_3376().findMinimumTime(strength, 1));
        System.out.println(new LC_3376().findMinimumTimeV1(strength, 1));
    }

    public int findMinimumTime(List<Integer> strength, int k) {
        int[] visited = new int[strength.size()];
        backtrack(visited, strength, k, new ArrayList<>());
        int minEnergy = Integer.MAX_VALUE;
        for (int i = 0; i < result.size(); i++) {
            List<Integer> list = result.get(i);
            int x = 1;
            int energy = 0;
            for (int j = 0; j < list.size(); j++) {
                energy += (int)Math.ceil(list.get(j) / (double)x);
                x += k;
            }
            if (energy < minEnergy) {
                minEnergy = energy;
            }
        }
        return minEnergy;
    }

    List<List<Integer>> result = new ArrayList<>();

    public void backtrack(int[] visited,  List<Integer> strength, int k, List<Integer> current) {
        if (current.size() == strength.size()) {
            result.add(new ArrayList<>(current));
        }
        for (int i = 0; i < strength.size(); i++) {
            if (visited[i] == 0) {
                current.add(strength.get(i));
                visited[i] = 1;
                backtrack(visited, strength, k, current);
                current.remove(current.size() - 1);
                visited[i] = 0;
            }
        }
    }

    public int findMinimumTimeV1(List<Integer> strength, int k) {
        int[] visited = new int[strength.size()];
        backtrackV1(visited, strength, 1, k, 0);
        return minTime;
    }

    int minTime = Integer.MAX_VALUE;

    public void backtrackV1(int[] visited, List<Integer> strength, int x, int k, int val) {
        if (strength.isEmpty()) {
            minTime = Math.min(minTime,val);
            return;
        }
        if (val > minTime) {
            return;
        }
        for (int i = 0; i < strength.size(); i++) {
//            if (visited[i] == 0) {
                ArrayList<Integer> integers = new ArrayList<>(strength);
                integers.remove(i);
                visited[i] = 1;
                backtrackV1(visited, integers,x + k, k, val + (int)Math.ceil(strength.get(i) / (double)x));
                visited[i] = 0;
//            }
        }
    }
}
