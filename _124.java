import helper.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 124. Binary Tree Maximum Path Sum
 */
public class _124 {
    class Solution1_DFS {
        int maxSum;
        public int maxPathSum(TreeNode root) {
            maxSum = Integer.MIN_VALUE;
            dfs(root);
            return maxSum;
        }

        private int dfs(TreeNode root) {
            if (root == null) {
                return 0;
            }

            int leftSum = Math.max(0, dfs(root.left));
            int rightSum = Math.max(0, dfs(root.right));
            maxSum = Math.max(maxSum, leftSum + rightSum + root.val);
            return Math.max(leftSum, rightSum) + root.val;
        }
    }

    public static class Solution2 {
        int max = Integer.MIN_VALUE;

        public int maxPathSum(TreeNode root) {
            Map<TreeNode, Integer> map = new HashMap<>();
            dfs(root, map);
            return max;
        }

        private int dfs(TreeNode root, Map<TreeNode, Integer> map) {
            // base case
            if (root == null) {
                return 0;
            }

            // if there's cache for this node, directly return the previously calculated path sum
            if (map.containsKey(root)) {
                return map.get(root);
            }

            // traverse to teh left & right subtree, drop their value if negative
            int left = Math.max(0, dfs(root.left, map));
            int right = Math.max(0, dfs(root.right, map));
            // update the max path
            max = Math.max(max, root.val + left + right);
            // store the path sum into the map cache
            int pathSum = root.val + Math.max(left, right);
            map.put(root, pathSum);

            return pathSum;
        }
    }
}
