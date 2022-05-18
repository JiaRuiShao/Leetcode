public class _100 {

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

        /**
         * Post-Order Traversal.
         * Time: O(N)
         * Space: O(H) = O(N)
         *
         * @param p root of the first binary tree
         * @param q root of the second binary tree
         * @return true if the structures of the given two trees are the same
         */
        private boolean traverse(TreeNode p, TreeNode q) {
            if (p == null && q == null) return true;
            if (p == null || q == null || p.val != q.val) return false;

            return traverse(p.left, q.left) && traverse(p.right, q.right);
        }

        public boolean isSameTree(TreeNode p, TreeNode q) {
            return traverse(p, q);
        }
    }
}
