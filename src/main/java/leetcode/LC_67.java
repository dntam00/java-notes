package leetcode;

public class LC_67 {

    public static void main(String[] args) {
        System.out.println(new LC_67().addBinary("1011110", "10"));
        System.out.println(new LC_67().addBinaryV2("1011110", "10"));
    }

    public String addBinaryV2(String a, String b) {
        StringBuilder bd = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;

        int mem = 0;
        while (i >= 0 || j >= 0 || mem > 0) {
            int i1 = 0;
            int j1 = 0;
            if (i >= 0) {
                i1 = a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                j1 = b.charAt(j) - '0';
                j--;
            }
            int n = i1 + j1 + mem;
            int r = n % 2;
            mem = n / 2;
            bd.append(r);
        }
        return bd.reverse().toString();
    }

    public String addBinary(String a, String b) {
        StringBuilder bd = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int mem = 0;
        while (i >= 0 && j >= 0) {
            int i1 = (a.charAt(i) - '0' + b.charAt(j) - '0') + mem;
            if (i1 >= 2) {
                mem = 1;
                bd.append(i1 - 2);
            } else {
                bd.append(i1);
                mem = 0;
            }
            i--;
            j--;
        }
        while(i >= 0) {
            int i1 = a.charAt(i) + mem - '0';
            if (i1 >= 2) {
                bd.append(i1 - 2);
                mem = 1;
            } else {
                bd.append(i1);
                mem = 0;
            }
            i--;
        }
        while(j >= 0) {
            int i1 = b.charAt(j) + mem - '0';
            if (i1 >= 2) {
                bd.append(i1 - 2);
                mem = 1;
            } else {
                bd.append(i1);
                mem = 0;
            }
            j--;
        }
        if (mem == 1) {
            bd.append('1');
        }
        return bd.reverse().toString();
    }

//    public String addBinary(String a, String b) {
//        StringBuilder sb = new StringBuilder();
//        int carry = 0;
//        int i = a.length() - 1;
//        int j = b.length() - 1;
//
//        while (i >= 0 || j >= 0 || carry > 0) {
//            int sum = carry;
//            if (i >= 0) {
//                sum += a.charAt(i--) - '0';
//            }
//            if (j >= 0) {
//                sum += b.charAt(j--) - '0';
//            }
//            sb.append(sum % 2);
//            carry = sum / 2;
//        }
//
//        return sb.reverse().toString();
//    }
}
