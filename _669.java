import helper.TreeNode;

/**
 * 669. Trim a Binary Search Tree
 * 
 * Clarification:
 * - Assume range [low, high] is valid?
 * 
 * Edge Cases:
 * - Root trimmed
 * - All nodes trimmed
 * - Skewed Tree
 */
public class _669 {
    // Time: O(n)
    // Space: O(h) = O(n)
    class Solution1_Recursion {
        // return the valid nodes (root, left, right)
        public TreeNode trimBST(TreeNode root, int low, int high) {
            if (root == null) return null;
            if (root.val < low) { // left subtree nodes are invalid
                return trimBST(root.right, low, high);
            } else if (root.val > high) { // right subtree nodes are invalid
                return trimBST(root.left, low, high);
            } else {
                root.left = trimBST(root.left, low, high);
                root.right = trimBST(root.right, low, high);
                return root;
            }
        }
    }

    // Time: O(n)
    // Space: O(1)
    class Followup_Iterative {
        public TreeNode trimBST(TreeNode root, int low, int high) {
            // Step 1: Find valid root
            while (root != null && (root.val < low || root.val > high)) {
                if (root.val < low) {
                    root = root.right;
                } else {
                    root = root.left;
                }
            }
            
            if (root == null) return null;
            
            // Step 2: Trim left subtree
            TreeNode node = root;
            while (node != null) {
                while (node.left != null && node.left.val < low) {
                    node.left = node.left.right;
                }
                node = node.left;
            }
            
            // Step 3: Trim right subtree
            node = root;
            while (node != null) {
                while (node.right != null && node.right.val > high) {
                    node.right = node.right.left;
                }
                node = node.right;
            }
            
            return root;
        }
    }
}
