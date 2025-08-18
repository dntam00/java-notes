package leetcode;

import java.util.Stack;

public class LC_226 {

    public TreeNode invertTree(TreeNode root) {
        // return dfs(root);
        return iterative(root);
    }

    public TreeNode dfs(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode left = root.left;
        root.left = dfs(root.right);
        root.right = dfs(left);
        return root;
    }

    public TreeNode iterative(TreeNode root) {
        if (root == null) {
            return null;
        }
        Stack<TreeNode> s = new Stack<>();
        s.push(root);
        while (!s.isEmpty()) {
            TreeNode curr = s.pop();
            if (curr == null) {
                continue;
            }
            s.push(curr.left);
            s.push(curr.right);
            TreeNode temp = curr.left;
            curr.left = curr.right;
            curr.right = temp;
        }
        return root;
    }
}
