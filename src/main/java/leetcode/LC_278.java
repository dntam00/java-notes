package leetcode;

public class LC_278 {

    public static void main(String[] args) {
        int bad = new LC_278().firstBadVersion(2126753390);
        System.out.println(bad);

    }

    public int firstBadVersion(int n) {
        int l = 1, h = n;
        // [l, h] is search interval
        while (l <= h) {
            int m = l + (h - l ) / 2;
            if (isBadVersion(m)) {
                h = m - 1;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    private boolean isBadVersion(int version) {
        return version >= 1702766719;
    }
}
