import helper.TreeNode;

public class _230 {
    class Solution {
        int n = 0;
        int val = 0;

        private void traverse(TreeNode root, int k) {
            if (root == null || n >= k) return;
            traverse(root.left, k);
            if (++n == k) val = root.val;
            traverse(root.right, k);
        }

        public int kthSmallest(TreeNode root, int k) {
            traverse(root, k);
            return val;
        }
    }
}
