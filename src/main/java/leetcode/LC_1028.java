package leetcode;

public class LC_1028 {

    public static void main(String[] args) {
        TreeNode treeNode = new LC_1028().recoverFromPreorderV1("1-2--3--4-5--6--7");
        System.out.println(treeNode.val);
    }
    
    public TreeNode recoverFromPreorderV1(String traversal) {
        return dfs(traversal, -1);
    }

    int left = 0;

    public TreeNode dfs(String traversal, int height) {
        int currHeight = 0;
        while ((currHeight + left) < traversal.length() && traversal.charAt(currHeight + left) == '-') {
            currHeight++;
        }
        if (currHeight <= height) {
            return null;
        }
        int startNums = left + currHeight;
        while (startNums < traversal.length() && Character.isDigit(traversal.charAt(startNums))) {
            startNums++;
        }
        String numberStr = traversal.substring(left + currHeight, startNums);
        int number = Integer.parseInt(numberStr);
        TreeNode treeNode = new TreeNode(number);
        left = startNums;
        treeNode.left = dfs(traversal, height + 1);
        treeNode.right = dfs(traversal, height + 1);
        return treeNode;
    }
}

class Solution {

    public TreeNode recoverFromPreorder(String traversal) {
        if (traversal.isEmpty()) {
            return null;
        }
        int index = traversal.indexOf('-');
        if (index == -1) {
            return new TreeNode(Integer.parseInt(traversal));
        }
        TreeNode root = new TreeNode(Integer.parseInt(traversal.substring(0, index)));
        go(root, 1, traversal, index);
        return root;
    }

    int go(TreeNode node, int height, String traversal, int index) {
        // node -> root
        // height: 0
        // index -> current position
        int dashNums = 0;
        while ((dashNums + index) < traversal.length() && traversal.charAt(dashNums + index) == '-') {
            dashNums++;
        }
        int nextDash = 0;
        if (dashNums == height) {
            nextDash = traversal.indexOf('-', dashNums + index);
            if (nextDash == -1) {
                nextDash = traversal.length();
            }
            int nums = Integer.parseInt(traversal.substring(dashNums + index, nextDash));
            node.left = new TreeNode(nums);
            int newIndex = go(node.left, height + 1, traversal, nextDash);
            int newDashNums = 0;
            while ((newDashNums + newIndex) < traversal.length() && traversal.charAt(newDashNums + newIndex) == '-') {
                newDashNums++;
            }
            if (newDashNums == height) {
                int newNextDash = traversal.indexOf('-', newDashNums + newIndex);
                if (newNextDash == -1) {
                    newNextDash = traversal.length();
                }
                int rightNums = Integer.parseInt(traversal.substring(newDashNums + newIndex, newNextDash));
                node.right = new TreeNode(rightNums);
                return go(node.right, height + 1, traversal, newNextDash);
            }
            return newIndex;
        }
        return index;
    }
}