import helper.TreeNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 236. Lowest Common Ancestor of a Binary Tree.
 * *
 */
public class _236 {
    class Solution1_BFS_Backtrack {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // base cases
            if (root == null) return null;
            if (root.val == p.val || root.val == q.val) return root;
            // traversal
            TreeNode left = lowestCommonAncestor(root.left, p, q);
            TreeNode right = lowestCommonAncestor(root.right, p, q);
            // post-order
            if (left != null && right != null) return root;
            return right == null ? left : right;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);

        TreeNode res = new _236().new Solution1_BFS_Backtrack().lowestCommonAncestor(root, new TreeNode(5), new TreeNode(1));
        System.out.println(res.val);
    }
}
