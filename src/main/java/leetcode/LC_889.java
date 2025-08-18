package leetcode;

import java.util.HashMap;

public class LC_889 {

    HashMap<Integer, Integer> indexs = new HashMap<>();

    public static void main(String[] args) {
//        TreeNode treeNode = new LC889().constructFromPrePost(new int[]{1, 2, 4, 5, 3, 6, 7}, new int[]{4, 5, 2, 6, 7, 3, 1});
        TreeNode treeNode = new LC_889().constructFromPrePost(new int[]{3,4,1,2}, new int[]{1,4,2,3});
        System.out.println(treeNode.val);
    }

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {

        for (int i = 0; i < postorder.length; i++) {
            indexs.put(postorder[i], i);
        }

        int length = preorder.length - 1;
        return traversal(preorder, postorder, 0, length, 0, length);
    }

    public TreeNode traversal(int[] preorder, int[] postorder, int pr1, int pr2, int po1, int po2) {
        if (pr1 > pr2) {
            return null;
        }
        TreeNode treeNode = new TreeNode(preorder[pr1]);
        if (pr1 == pr2) {
            return treeNode;
        }
        // last index of left subtree
        int m = indexs.get(preorder[pr1 + 1]);

        int leftSize = m - po1 + 1;

        int newPr1 = pr1 + 1;
        treeNode.left = traversal(preorder, postorder, newPr1, pr1 + leftSize, po1, m);

        int newPr21 = pr1 + leftSize + 1;

        treeNode.right = traversal(preorder, postorder,  newPr21, pr2, m + 1, po2 - 1);
        return treeNode;
    }
}
