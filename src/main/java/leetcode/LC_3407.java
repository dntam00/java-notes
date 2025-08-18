package leetcode;

public class LC_3407 {

    public static void main(String[] args) {
        System.out.println(new LC_3407().hasMatch("ckckkk", "ck*kc"));
    }

    public boolean hasMatch(String s, String p) {
        int index = p.indexOf('*');
        if (index == -1) {
            return s.contains(p);
        }
        String prefix = p.substring(0, index);
        String suffix = p.substring(index + 1);

        if (prefix.isEmpty()) {
            return s.contains(suffix);
        }


        int curr = s.indexOf(prefix);
        while (curr != -1) {
            int exist = s.indexOf(suffix, curr + prefix.length());
            if (exist != -1) {
                return true;
            }
            curr = s.indexOf(prefix, curr + 1);
        }
        return false;
    }
}
