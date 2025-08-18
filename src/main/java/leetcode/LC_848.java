package leetcode;

public class LC_848 {

    public static void main(String[] args) {
        String s = new LC_848().shiftingLetters("mkgfzkkuxownxvfvxasy", new int[]{505870226, 437526072, 266740649, 224336793, 532917782, 311122363, 567754492, 595798950, 81520022, 684110326, 137742843, 275267355, 856903962, 148291585, 919054234, 467541837, 622939912, 116899933, 983296461, 536563513});
        System.out.println(s);
    }

    public String shiftingLetters(String s, int[] shifts) {
        int len = shifts.length;
        long[] total = new long[len];

        total[len - 1] = shifts[len - 1];
        for (int i = len - 2; i >= 0; i--) {
            total[i] = total[i + 1] + shifts[i];
        }
        char[] arr = s.toCharArray();

        for (int i = 0; i < len; i++) {
            arr[i] = (char) ((arr[i] - 'a' + total[i]) % 26 + 'a');
        }
        return new String(arr);
    }
}
