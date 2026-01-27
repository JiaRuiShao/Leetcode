/**
 * 687. Longest Univalue Path
 */
public class _687 {
    class Solution1_DFS {
        private int maxLen;
        public int longestUnivaluePath(TreeNode root) {
            maxLen = 0;
            dfs(root);
            return maxLen; 
        }

        private int dfs(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int left = dfs(root.left);
            int right = dfs(root.right);
            if (root.left != null && root.val == root.left.val) {
                left++;
            } else {
                left = 0;
            }
            if (root.right != null && root.val == root.right.val) {
                right++;
            } else {
                right = 0;
            }
            maxLen = Math.max(maxLen, left + right);
            return Math.max(left, right);
        }
    }
}
