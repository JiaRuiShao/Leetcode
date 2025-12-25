import helper.TreeNode;

/**
 * 530. Minimum Absolute Difference in BST
 */
public class _530 {
    // In-Order Traversal
    class Solution1_Recursion {
        private Integer prev = null;
        private int minDiff = Integer.MAX_VALUE;
        public int getMinimumDifference(TreeNode root) {
            traverse(root);
            return minDiff;
        }

        private void traverse(TreeNode root) {
            if (root == null) return;
            traverse(root.left);
            if (prev != null) minDiff = Math.min(minDiff, Math.abs(prev - root.val));
            prev = root.val;
            traverse(root.right);
        }
    }

    // Time: O(n)
    // Space: O(1)
    class Followup_Morris {
        public int getMinimumDifference(TreeNode root) {
            TreeNode prev = null;
            TreeNode curr = root;
            int minDiff = Integer.MAX_VALUE;

            while (curr != null) {
                if (curr.left == null) {
                    minDiff = getMinDiff(prev, curr, minDiff);
                    prev = curr;
                    curr = curr.right;
                } else {
                    TreeNode predecessor = curr.left;
                    while (predecessor.right != null && predecessor.right != curr) {
                        predecessor = predecessor.right;
                    }
                    if (predecessor.right == null) {
                        predecessor.right = curr;
                        curr = curr.left;
                    } else {
                        minDiff = getMinDiff(prev, curr, minDiff);
                        prev = curr;
                        predecessor.right = null;
                        curr = curr.right;
                    }
                }
            }
            return minDiff;
        }

        private int getMinDiff(TreeNode prev, TreeNode curr, int min) {
            if (prev == null) return min;
            int diff = Math.abs(prev.val - curr.val);
            return Math.min(diff, min);
        }
    }
}
