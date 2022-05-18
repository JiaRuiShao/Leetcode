public class _112 {
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

    class Solution1 {

        /**
         * Find Path Sum. (Tree Traversal Framework)
         * Time: O(N)
         * Space: O(H) = O(N)
         *
         * @param root the given tree root node
         * @param targetSum the target sum
         * @return true if the tree has a root-to-leaf path that values
         * along the path sum to the target; false if not
         */
        public boolean hasPathSum(TreeNode root, int targetSum) {
            // base cases
            if (root == null) return false;
            if (root.left == null && root.right == null && targetSum == root.val) return true;

            // select & traverse
            boolean left = hasPathSum(root.left, targetSum - root.val);
            boolean right = hasPathSum(root.right, targetSum - root.val);

            if (left || right) return true;
            return false;
        }
    }

    class Solution2 {
        /**
         * Find Path Sum. (Divide into subproblems dp)
         * Time: O(N)
         * Space: O(H) = O(N)
         *
         * @param root the given tree root node
         * @param targetSum the target sum
         * @return true if the tree has a root-to-leaf path that values
         * along the path sum to the target; false if not
         */
        public boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null) {
                return false;
            }
            if (root.left == null && root.right == null && root.val == targetSum) {
                return true;
            }

            return hasPathSum(root.left, targetSum - root.val)
                    || hasPathSum(root.right, targetSum - root.val);
        }
    }
}
