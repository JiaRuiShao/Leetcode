import helper.TreeNode;

public class _108 {
    static class Solution {

        /**
         * Traverse the input arr & build the BST.
         * Time: O(N)
         * Space: O(H) = O(logN)
         *
         * @param num the input int arr
         * @param lo lower bound of current tree
         * @param hi higher bound of current tree
         * @return the root node of current BST
         */
        private TreeNode buildBST(int[] num, int lo, int hi) {
            if (lo > hi) return null;
            int rootIdx = lo + (hi - lo) / 2;
            TreeNode root = new TreeNode(num[rootIdx]);
            root.left = buildBST(num, lo, rootIdx - 1);
            root.right = buildBST(num, rootIdx + 1, hi);
            return root;
        }

        public TreeNode sortedArrayToBST(int[] nums) {
            return buildBST(nums, 0, nums.length - 1);
        }
    }
}
