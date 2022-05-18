public class _814 {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Solution {

        private boolean traverse(TreeNode root) {
            // base case
            if (root == null) return false;
            // traverse to left & right subtree
            boolean left = traverse(root.left);
            boolean right = traverse(root.right);

            // post-order position
            if (!left) root.left = null;
            if (!right) root.right = null;

            // return true if left subtree, right subtree contains 1 or current node val is 1
            return left || right || root.val == 1;
        }

        /**
         * BFS Traversal to prune the tree where every subtree (of the given tree) not containing a 1.
         * Time: O(N)
         * Space: O(H) = O(N)
         *
         * @param root the given tree root node
         * @return the root of the result tree
         */
        public TreeNode pruneTree(TreeNode root) {
            return traverse(root) ? root : null;
        }
    }
}
