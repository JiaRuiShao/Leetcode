/**
 * 110. Balanced Binary Tree
 * Given a binary tree, determine if it is height-balanced.
 * A height-balanced binary tree is defined as:
 * a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
 * In order to know whether the height of any left and right subtree differ by at least one, we need to know:
 * - the height of any left & right subtree of any node
 */
public class _110 {
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

    class Solution1_DP {

        /**
         * Time: O(n*n) = O(n^2)
         * Space: O(n*n) = O(n^2)
         * @param root TreeNode
         * @return true if the tree is balanced, false if not
         */
        public boolean isBalanced(TreeNode root) {
            if (root == null) return true;
            // check left & right subtree height & early-stop if not balanced
            if (Math.abs(getHeight(root.left) - getHeight(root.right)) > 1) return false;
            return isBalanced(root.left) && isBalanced(root.right);
        }

        /**
         * Get the height of the current node.
         * Time: O(h) = O(n)
         * Space: O(h) = O(n)
         * @param curr current TreeNode
         * @return the height of current node
         */
        private int getHeight(TreeNode curr) {
            if (curr == null) return 0;
            return Math.max(getHeight(curr.left), getHeight(curr.right)) + 1;
        }
    }

    class Solution2_DFS {
        private static class TreeInfo {
            int height; // the tree's height
            boolean isBalanced; // stores whether the tree at root is balanced
            TreeInfo(int height, boolean isBalanced) {
                this.height = height;
                this.isBalanced = isBalanced;
            }
        }

        public boolean isBalanced(TreeNode root) {
            return helper(root).isBalanced;
        }

        /**
         * DFS Approach to obtain the height of each node and thus check whether each subtree of current node as root is balanced.
         * Time: O(n)
         * Space: O(n)
         * @param curr current TreeNode
         * @return a utility class that stores the both height and balanced info
         */
        private TreeInfo helper(TreeNode curr) {
            if (curr == null) return new TreeInfo(-1, true);
            TreeInfo leftInfo = helper(curr.left);
            TreeInfo rightInfo = helper(curr.right);
            int currHeight = Math.max(leftInfo.height, rightInfo.height) + 1;
            boolean isBalanced = Math.abs(leftInfo.height - rightInfo.height) <= 1;
            if (!isBalanced || !leftInfo.isBalanced || !rightInfo.isBalanced) return new TreeInfo(currHeight, false);
            return new TreeInfo(currHeight, true);
        }
    }
}
