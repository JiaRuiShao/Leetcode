import java.util.HashSet;
import java.util.Set;

import helper.TreeNode;

/**
 * 1676. Lowest Common Ancestor of a Binary Tree IV
 */
public class _1676 {
    class Solution1_DFS {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
            Set<Integer> set = new HashSet<>();
            for (TreeNode node : nodes) {
                set.add(node.val);
            }
            return findLCA(root, set);
        }

        private TreeNode findLCA(TreeNode root, Set<Integer> set) {
            if (root == null) return null; 
            if (set.contains(root.val)) return root;       
            TreeNode left = findLCA(root.left, set);
            TreeNode right = findLCA(root.right, set);
            if (left != null && right != null) return root;
            return left != null ? left : right;
        }
    }
}
