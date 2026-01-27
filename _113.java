import java.util.ArrayList;
import java.util.List;

import helper.TreeNode;

/**
 * 113. Path Sum II
 */
public class _113 {
    class Solution1_Backtrack {
        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            List<List<Integer>> nodes = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            backtrack(root, targetSum, path, nodes);
            return nodes;
        }

        private void backtrack(TreeNode root, int k, List<Integer> path, List<List<Integer>> res) {
            if (root == null) {
                return;
            }
            path.add(root.val);
            k -= root.val;
            if (k == 0 && root.left == null && root.right == null) {
                res.add(new ArrayList<>(path));
            }
            backtrack(root.left, k, path, res);
            backtrack(root.right, k, path, res);
            path.remove(path.size() - 1);
        }
    }
}
