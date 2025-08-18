package leetcode;

public class LC_1261 {

    public static void main(String[] args) {

    }
}

class FindElements {

    TreeNode r;
    int max;

    public FindElements(TreeNode root) {
        r = root;
        if (r != null) {
            r.val = 0;
            recover(r);
        }
    }

    public void recover(TreeNode root) {
        if (root.left != null) {
            root.left.val = root.val * 2 + 1;
            recover(root.left);
        }
        if (root.right != null) {
            root.right.val = root.val * 2 + 2;
            recover(root.right);
        }
    }

    public boolean check(TreeNode node, int target) {
        if (node == null) {
            return false;
        }
        if (node.val == target) {
            return true;
        }
        if (check(node.left, target)) {
            return true;
        }
        if (check(node.right, target)) {
            return true;
        }
        return false;
    }

    public boolean find(int target) {
        TreeNode node = r;
        return check(node, target);
        // while (node != null) {
        //     // System.out.println(node.val);
        //     if (node.val == target) {
        //         return true;
        //     }
        //     if (target < node.val) {
        //         return false;
        //     }

        //     if (target >= (node.val * 2 + 2)) {
        //         node = node.right;
        //         continue;
        //     }
        //     node = node.left;
        // }
        // return false;
        // 0
        //.     2
        //.   5   6
        //            14
        //
    }
}