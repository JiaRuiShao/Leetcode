import java.util.HashMap;
import java.util.Map;

import helper.TreeNode;

/**
 * 437. Path Sum III
 */
public class _437 {
    // Time: O(n)
    // Space: O(n)
    class Solution1_Backtrack {
        public int pathSum(TreeNode root, int targetSum) {
            // presum[child] - presum[p] = targetSum ==> presum[p] = presum[child] - targetSum
            Map<Long, Integer> preSumFreq = new HashMap<>();
            preSumFreq.put(0L, 1);
            return dfs(root, 0, targetSum, preSumFreq);
        }

        private int dfs(TreeNode root, long sum, int targetSum, Map<Long, Integer> preSumFreq) {
            if (root == null) {
                return 0;
            }

            int count = 0;
            sum += root.val;
            long k = sum - targetSum;
            if (preSumFreq.containsKey(k)) {
                count += preSumFreq.get(k);
            }
            preSumFreq.put(sum, preSumFreq.getOrDefault(sum, 0) + 1);

            int left = dfs(root.left, sum, targetSum, preSumFreq);
            int right = dfs(root.right, sum, targetSum, preSumFreq);
            // backtrack to not let subling path use current prefix sum
            preSumFreq.put(sum, preSumFreq.getOrDefault(sum, 0) - 1);
            return count + left + right;
        }
    }
}
