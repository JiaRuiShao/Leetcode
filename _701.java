import helper.TreeNode;

public class _701 {
    class Solution {
        /**
         * Time: O(H) - O(N)
         * Space: O(H) - O(N)
         *
         * @param root the root node in the given BST
         * @param val the new node val
         * @return the root of the BST
         */
        public TreeNode insertIntoBST(TreeNode root, int val) {
            if (root == null) return new TreeNode(val); // find the place to insert the new val
            if (root.val < val) root.right = insertIntoBST(root.right, val);
            if (root.val > val) root.left = insertIntoBST(root.left, val);
            return root;
        }
    }
}
