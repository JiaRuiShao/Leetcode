import java.util.HashMap;
import java.util.Map;

import helper.TreeNode;

/**
 * 337. House Robber III
 */
public class _337 {
    // Time: O(n)
    // Space: O(n)
    class Solution1_Recursion_WithMemo {
        public int rob(TreeNode root) {
            return dp(root, new HashMap<TreeNode, Integer>());
        }

        private int dp(TreeNode root, Map<TreeNode, Integer> memo) {
            if (root == null) return 0;
            if (memo.containsKey(root)) return memo.get(root);
            int notRob = dp(root.left, memo) + dp(root.right, memo);
            int rob = root.val + (root.left == null ? 0 : dp(root.left.left, memo) + dp(root.left.right, memo)) + (root.right == null ? 0 : dp(root.right.left, memo) + dp(root.right.right, memo));
            memo.put(root, Math.max(notRob, rob));
            return memo.get(root);
        }
    }

    class Solution2_RecursionReturnTwoStates {
        class RobResult {
            int withRoot;    // Max money if we rob this node
            int withoutRoot; // Max money if we don't rob this node
            
            RobResult(int with, int without) {
                this.withRoot = with;
                this.withoutRoot = without;
            }
        }

        public int rob(TreeNode root) {
            RobResult result = robHelper(root);
            return Math.max(result.withRoot, result.withoutRoot);
        }

        private RobResult robHelper(TreeNode node) {
            if (node == null) {
                return new RobResult(0, 0);
            }
            
            RobResult left = robHelper(node.left);
            RobResult right = robHelper(node.right);
            
            // Rob this node
            int withRoot = node.val + left.withoutRoot + right.withoutRoot;
            
            // Don't rob this node
            int withoutRoot = Math.max(left.withRoot, left.withoutRoot)
                            + Math.max(right.withRoot, right.withoutRoot);
            
            return new RobResult(withRoot, withoutRoot);
        }
    }
}
