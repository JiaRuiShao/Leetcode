import helper.TreeNode;

/**
 * 112. Path Sum
 */
public class _112 {
    class Solution1_DFS {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null) {
                return false;
            }
            targetSum -= root.val;
            if (targetSum == 0 && root.left == null && root.right == null) {
                return true;
            }
            return hasPathSum(root.left, targetSum) || hasPathSum(root.right, targetSum);
        }
    }
}
