package leetcode;

public class LC_838 {

    public static void main(String[] args) {
        System.out.println(new LC_838().pushDominoes(".L.R...LR..L"));
    }

    public String pushDominoes(String dominoes) {
        int previousRightIndex = -1;
        int previousLeftIndex = 0;
        char[] str = dominoes.toCharArray();
        for (int i = 0; i < dominoes.length(); i++) {
            if (dominoes.charAt(i) == '.') {
                continue;
            }
            if (dominoes.charAt(i) == 'L') {
                if (previousRightIndex == -1) {
                    replaceChars(str, previousLeftIndex, i, 'L');
                } else {
                    int space = i - previousRightIndex;
                    if (space % 2 == 1) {
                        replaceChars(str, previousRightIndex, (i + previousRightIndex) / 2, 'R');
                        replaceChars(str, (i + previousRightIndex) / 2 + 1, i, 'L');
                    } else {
                        replaceChars(str, previousRightIndex, (i + previousRightIndex) / 2 - 1, 'R');
                        replaceChars(str, (i + previousRightIndex) / 2 + 1, i, 'L');
                    }
                    previousRightIndex = -1;
                }
                previousLeftIndex = i + 1;
            }
            if (dominoes.charAt(i) == 'R') {
                if (previousRightIndex != -1) {
                    replaceChars(str, previousRightIndex, i - 1, 'R');
                }
                previousRightIndex = i;
            }
        }
        if (previousRightIndex != -1) {
            replaceChars(str, previousRightIndex, dominoes.length() - 1 ,'R');
        }
        return new String(str);
    }

    public void replaceChars(char[] str, int start, int end, char replacement) {
        for (int i = start; i <= end; i++) {
            str[i] = replacement;
        }
    }
}
