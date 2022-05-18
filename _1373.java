import helper.TreeNode;

public class _1373 {
    /**
     * Valid Solution but Time Limit Exceeded.
     */
    static class Solution1 {
        int maxSum = 0, currSum;

        private boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
            if (root == null) return true;
            currSum += root.val;
            if ((min != null && root.val <= min.val) || (max != null && root.val >= max.val)) return false;
            return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
        }

        private void traverse(TreeNode root) {
            if (root == null) return;
            currSum = 0;
            if (isValidBST(root, null, null) && currSum > maxSum) {
                maxSum = currSum;
            }
            traverse(root.left);
            traverse(root.right);
        }

        public int maxSumBST(TreeNode root) {
            traverse(root);
            return maxSum;
        }
    }

    public static class Solution2 {
        public int maxSumBST(TreeNode root) {
            return traverse(root)[4];
        }

        /**
         * Utilize the post-order traverse to help us check if the tree is a BST, its tree key sum.
         * In order to do that, we need to store the following information:
         * 1. Whether the left and right subtrees are BSTs.
         * 2. The maximum value of the left subtree and the minimum value of the right subtree.
         * 3. The sum of the node values of the left and right subtrees.
         *
         * res[0] records whether the binary tree rooted is BST, if it is 1, it means it is BST, if it is 0, it means it is not BST
         * res[1] records the minimum value among all nodes of the binary tree rooted at
         * res[2] records the maximum value among all nodes of the binary tree rooted at
         * res[3] records the sum of all node values of the binary tree rooted at
         * res[4] records the global max sum
         */
        private int[] traverse(TreeNode root) {
            if (root == null) {
                return new int[]{1, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 0}; // initial min val is INTEGER.MAX_VALUE & max val is INTEGER.MIN_VALUE
            }
            int[] left = traverse(root.left);
            int[] right = traverse(root.right);
            int subTreeMaxSum = Math.max(left[4], right[4]);
            // if the left & right subtree is valid BST && root val > max val in left subtree && root val < min val in the right subtree
            if (left[0] == 1 && right[0] == 1 && root.val > left[2] && root.val < right[1]) {
                int minVal = left[1] == Integer.MAX_VALUE ? root.val : left[1];
                int maxVal = right[2] == Integer.MIN_VALUE ? root.val : right[2];
                int sum = root.val + left[3] + right[3];
                int maxSum = Math.max(subTreeMaxSum, sum);
                return new int[]{1, minVal, maxVal, sum, maxSum};
            } else {
                return new int[]{0, 0, 0, 0, subTreeMaxSum};
            }
        }
    }
}
