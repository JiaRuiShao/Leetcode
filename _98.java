import helper.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 98. Validate Binary Search Tree.
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 * A valid BST is defined as follows:
 *  - The left subtree of a node contains only nodes with keys less than the node's key.
 * - The right subtree of a node contains only nodes with keys greater than the node's key.
 * - Both the left and right subtrees must also be binary search trees.
 */
public class _98 {
    class Solution1_Recursive_DP {
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

    class Solution2_Recursive_Traverse {  // valid bst in-order traverse should be in ascending order
        Integer prev = null;
        boolean isBST = true;

        public boolean isValidBST(TreeNode root) {
            traverse(root);
            return isBST;
        }

        private void traverse(TreeNode curr) {
            if (curr == null || !isBST) return;
            traverse(curr.left);
            if (prev != null && prev >= curr.val) isBST = false;
            prev = curr.val;
            traverse(curr.right);
        }
    }

    class Solution3_Iterative_Traverse {
        public boolean isValidBST(TreeNode root) {
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode visited = new TreeNode(-1);
            Integer prev = null;

            pushLeftBranch(root, stack);
            while (!stack.isEmpty()) {
                TreeNode curr = stack.peek();
                if ((curr.left == null || curr.left == visited) && curr.right != visited) {
                    // In-Order Position
                    if (prev != null && prev >= curr.val) return false;
                    prev = curr.val;
                    pushLeftBranch(curr.right, stack);
                }

                if (curr.right == null || curr.right == visited) {
                    visited = stack.pop();
                }
            }
            return true;
        }

        private void pushLeftBranch(TreeNode root, Deque<TreeNode> stack) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }
    }

    class Solution4_Iterative_DP {


    }
}
