import java.util.LinkedList;
import java.util.List;

/**
 * Definition for a binary tree node.
 */
public class _94 {

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Solution {

        /**
         * In-order Traversal.
         * Time: O(N)
         * Space: O(H) = O(N)
         *
         * @param root node ref in this call stack
         * @param res result list
         */
        private void traverse(TreeNode root, List<Integer> res) {
            if (root == null) return;
            traverse(root.left, res);
            res.add(root.val);
            traverse(root.right, res);
        }

        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> res = new LinkedList<>();
            traverse(root, res);
            return res;
        }
    }
}
