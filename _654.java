import helper.TreeNode;

/**
 * 654. Maximum Binary Tree
 */
class _654 {
    class Solution1 {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            return build(nums, 0, nums.length - 1);
        }

        /**
         * Build the tree given nums[lo..hi] & return the root.
         * Time: O(n + n-1 + n-2 + ... + 1) = O(n^2)
         * Space: O(n)
         *
         * @param nums the input arr
         * @param lo left arr range
         * @param hi right arr range
         * @return the root of the Binary Tree
         */
        private TreeNode build(int[] nums, int lo, int hi) {
            // base case
            if (lo > hi) {
                return null;
            }

            // find the max value & its index in the given array scope
            int index = -1, maxVal = Integer.MIN_VALUE;
            for (int i = lo; i <= hi; i++) {
                if (maxVal < nums[i]) {
                    index = i;
                    maxVal = nums[i];
                }
            }

            TreeNode root = new TreeNode(maxVal);
            // recursively build left & right sub tree
            root.left = build(nums, lo, index - 1);
            root.right = build(nums, index + 1, hi);

            return root;
        }
    }

    class Solution2 {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            return build(0, nums.length, nums);
        }
    
         // [left, right)
        private TreeNode build(int left, int right, int[] nums) {
            if (left >= right) return null;
            int max = -1, idx = 0;
            for (int i = left; i < right; i++) {
                if (nums[i] > max) {
                    max = nums[i];
                    idx = i;
                }
            }
            TreeNode root = new TreeNode(max);
            root.left = build(left, idx, nums);
            root.right = build(idx + 1, right, nums);
            return root;
        }
    }
}
