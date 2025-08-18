package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LC_1971 {

    public static void main(String[] args) {

    }

    public boolean validPathLTE(int n, int[][] edges, int source, int destination) {
        // n nodes: label from 0 to n - 1
        List<List<Integer>> adjs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjs.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adjs.get(edge[0]).add(edge[1]);
            adjs.get(edge[1]).add(edge[0]);
        }

        int[] visited = new int[n];
        Deque<Integer> queues = new ArrayDeque<>();
        queues.add(source);
        while (!queues.isEmpty()) {
            int curr = queues.poll();
            if (curr == destination) {
                return true;
            }
            visited[curr] = 1;
            List<Integer> nodes = adjs.get((int)curr);
            nodes.forEach(c -> {
                if (visited[c] == 0) {
                    queues.offer(c);
                }
            });
        }
        return false;
    }
}
