package leetcode;

import java.util.HashSet;
import java.util.Set;

public class LC_1079 {

    public static void main(String[] args) {
        System.out.println(new LC_1079().numTilePossibilities("AAB"));
    }

    public int numTilePossibilities(String tiles) {
        Set<String> result = new HashSet<>();
        int[] used = new int[tiles.length()];
//        int[] visited = new int[tiles.length()];
//        backtrack2(tiles, result, used, visited, new StringBuilder(), 1);
        backtrack(tiles, result, used, new StringBuilder());
        System.out.println(result);
        return result.size() - 1;
    }

    public void backtrack(String tiles, Set<String> result, int[] used, StringBuilder toCurrent) {
        if (result.contains(toCurrent.toString())) {
            return;
        }
        result.add(toCurrent.toString());
        for (int j = 0; j < tiles.length(); j++) {
            if (used[j] == 0) {
                used[j] = 1;
                toCurrent.append(tiles.charAt(j));
                backtrack(tiles, result, used, toCurrent);
                used[j] = 0;
                toCurrent.delete(toCurrent.length() - 1, toCurrent.length());
            }
        }
    }

    // A A B
    //

    public boolean backtrack2(String tiles, Set<String> result, int[] used, int[] visited, StringBuilder toCurrent, int expectLen) {
        String string = toCurrent.toString();
        if (string.length() == expectLen) {
            result.add(string);
            return true;
        }
        for (int i = 1; i <= tiles.length(); i++) {
            for (int j = 0; j < tiles.length(); j++) {
                if (toCurrent.length() == 0) {
                    boolean bre = false;
                    for (int k = 0; k < j; k++) {
                        if (tiles.charAt(k) == tiles.charAt(j) && visited[j] == 1) {
                            bre = true;
                            break;
                        }
                    }
                    if (bre) {
                        continue;
                    }
                }
                if (used[j] == 0) {
                    used[j] = 1;
                    toCurrent.append(tiles.charAt(j));
                    boolean b = backtrack2(tiles, result, used, visited, toCurrent, i);

                    // unset backtrack
                    used[j] = 0;
                    toCurrent.delete(toCurrent.length() - 1, toCurrent.length());

                    if (b) {
                        visited[j] = 1;
                    }
                }
            }
        }
        return false;
    }
}
