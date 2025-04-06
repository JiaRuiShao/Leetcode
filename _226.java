import java.util.LinkedList;
import java.util.Queue;

import helper.TreeNode;

/**
 * 226. Invert Binary Tree
 * Given the root of a binary tree, invert the tree, and return its root.
 */
public class _226 {

    class Solution1_Subproblem {
        /**
         * BFS Backtrack Solution.
         * Time: O(N) where N is #node in this tree
         * Space: O(2*H) = O(2N) = O(N)
         *
         * @param root
         * @return
         */
        public TreeNode invertTree(TreeNode root) {
            if (root == null) return root;
            TreeNode left = invertTree(root.left);
            TreeNode right = invertTree(root.right);
            root.right = left;
            root.left = right;
            return root;
        }
    }

    class Solution2_BFS_Iterative {
        /**
         * Breath-First-Search Iterative Solution.
         * Time: O(N)
         * Space: O(N) if balanced; O(1) if not balanced
         * @param root
         * @return
         */
        public TreeNode invertTree(TreeNode root) {
            Queue<TreeNode> q = new LinkedList<>();
            if (root != null) q.offer(root);
            // start BFS
            while (!q.isEmpty()) {
                TreeNode curr = q.poll();
                if (curr.left != null) q.offer(curr.left);
                if (curr.right != null) q.offer(curr.right);
                TreeNode temp = curr.left;
                curr.left = curr.right;
                curr.right = temp;
            }
            return root;
        }
    }
}
