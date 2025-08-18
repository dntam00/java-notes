package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LC_56 {

    public static void main(String[] args) {

    }

    public int[][] merge(int[][] intervals) {
        // sort based on start time
        // [[1,3],[2,6],[8,10],[15,18]]
        //
        List<int[]> list = Arrays.asList(intervals);
        List<int[]> result = new ArrayList<>();
        list.sort((a, b) -> a[0] - b[0]);
        int[] curr = list.get(0);
        int i = 1;
        while(i < list.size()) {
            if (list.get(i)[0] > curr[1]) {
                result.add(new int[]{curr[0], curr[1]});
                curr = list.get(i);
                i++;
                continue;
            }
            curr[1] = list.get(i)[1];
            i++;
        }
        return result.toArray(new int[0][]);
    }
}
