import helper.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class _124 {
    public static class Solution1 {
        int max = Integer.MIN_VALUE;

        public int maxPathSum(TreeNode root) {
            dfs(root);
            return max;
        }

        private int dfs(TreeNode root) {
            if (root == null) {
                return 0;
            }

            int left = Math.max(dfs(root.left), 0);
            int right = Math.max(dfs(root.right), 0);

            max = Math.max(max, root.val + left + right);

            return root.val + Math.max(left, right);
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
