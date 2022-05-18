import helper.TreeNode;

public class _98 {
    class Solution {
        /**
         * Compare the largest val in the left subtree & the smallest val
         * in the right subtree with the root.val.
         *
         * @param root the given tree root node
         * @return true if the given tree is a BST
         */
        public boolean isValidBST(TreeNode root) {
            return valid(root, null, null);
        }

        /**
         * A helper function to traverse the tree with extra information.
         * Time: O(N)
         * Space: O(H) = O(N)
         *
         * @param root the node in curr call stack
         * @param min the min value in the right subtree
         * @param max the max value in the left subtree
         * @return true if it's a valid binary tree; false if not
         */
        boolean valid(TreeNode root, Integer min, Integer max) {
            // base case
            if (root == null) {
                return true;
            }
            // not valid
            if ((min != null && root.val <= min) || (max != null && root.val >= max)) {
                return false;
            }
            // pass the root val in the left subtree as the max value, in the right subtree as the min value
            return valid(root.left, min, root.val) && valid(root.right, root.val, max);
        }
    }
}
