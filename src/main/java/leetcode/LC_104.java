package leetcode;

import java.util.Stack;

public class LC_104 {

    public static void main(String[] args) {

    }

    int max = 0;

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        traversal(root, 1);
        return max;
    }

    public void traversal(TreeNode node, int depth) {
        if (node.left == null && node.right == null) {
            max = Math.max(max, depth);
        }
        if (node.left != null) {
            traversal(node.left, depth + 1);
        }
        if (node.right != null) {
            traversal(node.right, depth + 1);
        }
    }

    class StackNode {
        TreeNode n;
        int height;

        public StackNode(TreeNode n, int height) {
            this.n = n;
            this.height = height;
        }
    }

    public int stack(TreeNode node) {
        Stack<StackNode> stack = new Stack<>();
        int length = 0;
        int maxL = 0;
        while (node != null || !stack.isEmpty()) {
            while(node != null) {
                length++;
                stack.push(new StackNode(node, length));
                node = node.left;
            }
            StackNode pop = stack.pop();
            maxL = Math.max(maxL, pop.height);
            length = pop.height;
            node = pop.n.right;
        }
        return maxL;
    }
}
