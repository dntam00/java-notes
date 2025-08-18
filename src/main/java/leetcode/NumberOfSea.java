package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NumberOfSea {
    public static void main(String[] args) {
        int[][] grid1 = {
                {-1, 1, 1, 1, 0, 1},
                {0, 1, 0, 1, 0, 0},
                {1, 1, 5, 1, 1, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 1}
        };
        System.out.println("Test case 1: " + pondSizes(grid1));
    }

    private static final List<int[]> steps = Arrays.asList(new int[]{-1, -1}, new int[]{0, -1}, new int[]{1, -1}, new int[]{-1, 0}, new int[]{1, 0}, new int[]{-1, 1}, new int[]{0, 1}, new int[]{1, 1});


    public static List<Integer> pondSizes(int[][] land) {


        List<Integer> pondSizes = new ArrayList<>();

        if (land.length == 0) {
            return pondSizes;
        }

        int rows = land.length;
        int cols = land[0].length;


        // row - col
        // List<int[]> steps = Arrays.asList([]{-1, -1}, []{0, -1}, []{1, -1}, []{-1, 0}, []{1, 0}, []{-1, 1}, []{0, 1}, []{1, 1});

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int area = bfs(land, i, j, rows, cols);
                if (area != 0) {
                    pondSizes.add(area);
                }
            }
        }


        return pondSizes;
    }

    private static int bfs(int[][] land, int i, int j, int rows, int cols) {
        LinkedList<int[]> queues = new LinkedList<>();

        if (land[i][j] == 0) {
            queues.offerLast(new int[]{i, j});
        }

        int area = 0;
        while (!queues.isEmpty()) {
            area++;
            int[] current = queues.pollFirst();
            int r = current[0];
            int c = current[1];
            land[r][c] = -1;

            for (int[] s : steps) {
                int nR = r + s[0];
                int nC = c + s[1];
                if (nR >= 0 && nR < rows && nC >= 0 && nC < cols && land[nR][nC] == 0) {
                    land[nR][nC] = -1;
                    queues.offerLast(new int[]{nR, nC});
                }
            }
        }

        return area;
    }
}
