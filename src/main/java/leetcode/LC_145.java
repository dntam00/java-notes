package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class LC_145 {

    public static void main(String[] args) {

    }

    public List<Integer> postorderTraversal(TreeNode root) {
        traversal(root);
        return res;
    }

    List<Integer> res = new ArrayList<>();

    public void traversal(TreeNode node) {
        if (node == null) {
            return;
        }

        traversal(node.left);
        traversal(node.right);

        res.add(node.val);
    }

    public List<Integer> stackMethod(TreeNode root) {
        if (root == null) {
            return null;
        }
        Stack<TreeNode> stack = new Stack<>();
        LinkedList<Integer> ans = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode peek = stack.pop();
            ans.addFirst(peek.val);
            if (peek.left != null) {
                stack.push(peek.left);
            }
            if (peek.right != null) {
                stack.push(peek.right);
            }
        }
        return ans;
    }
}
