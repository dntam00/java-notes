package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LC_1718 {

    public static void main(String[] args) {
        List<Integer> pool = new ArrayList<>(Arrays.asList(1,2));
        List<Integer> current = new ArrayList<>(0);
        int size = pool.size() * 2 - 1;
        for (int i = 0; i < size; i++) {
            current.add(0);
        }
        boolean check = check(pool, current, 0);
        System.out.println(current);
    }

    // 1, 2, 3, 4, 5

    private static boolean check(List<Integer> pool, List<Integer> current, int index) {
        if (pool.isEmpty()) {
            return true;
        }
        for (int i = pool.size() - 1; i >= 0; i--) {
            int start = index;
            Integer value = pool.get(i);
            while (start < current.size() && current.get(start) != 0) {
                start++;
            }
            if (start == current.size()) {
                continue;
            }
            if (value != 1 && (start + value >= current.size() || current.get(start + value) != 0)) {
                continue;
            }
            current.set(start, value);
            if (value != 1) {
                current.set(start + value, value);
            }
            pool.remove(value);
            boolean ok = check(pool, current, start + 1);
            if (ok) {
                return true;
            }
            pool.add(value);
            current.set(start, 0);
            if (value != 1) {
                current.set(start + value, 0);
            }
            pool.sort(Comparator.comparingInt(a -> a));
        }
        return false;
    }

    public int[] constructDistancedSequence(int targetNumber) {
        // Initialize the result sequence with size 2*n - 1 filled with 0s
        int[] resultSequence = new int[targetNumber * 2 - 1];

        // Keep track of which numbers are already placed in the sequence
        boolean[] isNumberUsed = new boolean[targetNumber + 1];

        // Start recursive backtracking to construct the sequence
        findLexicographicallyLargestSequence(
                0,
                resultSequence,
                isNumberUsed,
                targetNumber
        );

        return resultSequence;
    }

    // Recursive function to generate the desired sequence
    private boolean findLexicographicallyLargestSequence(
            int currentIndex,
            int[] resultSequence,
            boolean[] isNumberUsed,
            int targetNumber
    ) {
        // If we have filled all positions, return true indicating success
        if (currentIndex == resultSequence.length) {
            return true;
        }

        // If the current position is already filled, move to the next index
        if (resultSequence[currentIndex] != 0) {
            return findLexicographicallyLargestSequence(
                    currentIndex + 1,
                    resultSequence,
                    isNumberUsed,
                    targetNumber
            );
        }

        // Attempt to place numbers from targetNumber to 1 for a
        // lexicographically largest result
        for (
                int numberToPlace = targetNumber;
                numberToPlace >= 1;
                numberToPlace--
        ) {
            if (isNumberUsed[numberToPlace]) continue;

            isNumberUsed[numberToPlace] = true;
            resultSequence[currentIndex] = numberToPlace;

            // If placing number 1, move to the next index directly
            if (numberToPlace == 1) {
                if (
                        findLexicographicallyLargestSequence(
                                currentIndex + 1,
                                resultSequence,
                                isNumberUsed,
                                targetNumber
                        )
                ) {
                    return true;
                }
            }
            // Place larger numbers at two positions if valid
            else if (
                    currentIndex + numberToPlace < resultSequence.length &&
                            resultSequence[currentIndex + numberToPlace] == 0
            ) {
                resultSequence[currentIndex + numberToPlace] = numberToPlace;

                if (
                        findLexicographicallyLargestSequence(
                                currentIndex + 1,
                                resultSequence,
                                isNumberUsed,
                                targetNumber
                        )
                ) {
                    return true;
                }

                // Undo the placement for backtracking
                resultSequence[currentIndex + numberToPlace] = 0;
            }

            // Undo current placement and mark the number as unused
            resultSequence[currentIndex] = 0;
            isNumberUsed[numberToPlace] = false;
        }

        return false;
    }
}
