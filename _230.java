import helper.TreeNode;

/**
 * 230. Kth Smallest Element in a BST.
 */
public class _230 {
    static class Solution_BFS_Traversal {
        int rank = 0;
        int val = 0;

        /**
         * In order Traversal.
         * Time: O(n)
         * Space: O(h)
         *  
         * @param root root
         * @param k kth smallest element
         */
        private void traverse(TreeNode root, int k) {
            if (root == null || rank >= k) return;
            traverse(root.left, k);
            if (++rank == k) val = root.val;
            traverse(root.right, k);
        }

        public int kthSmallest(TreeNode root, int k) {
            traverse(root, k);
            return val;
        }
    }
}
