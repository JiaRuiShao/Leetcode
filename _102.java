import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class _102 {
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

    class Solution1 {

        /**
         * Breath-First Search.
         * @param root tree root node
         * @return the level order traversal of the given tree's nodes values
         */
        public List<List<Integer>> levelOrder(TreeNode root) {

            List<List<Integer>> res = new LinkedList<>();
            if (root == null) return res;

            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            while (!q.isEmpty()) {
                List<Integer> tmp = new LinkedList<>();
                int sz = q.size();
                for (int i = 0; i < sz; i++) {
                    TreeNode curr = q.poll();
                    tmp.add(curr.val);
                    // add the child of current node if not null
                    if (curr.left != null) q.offer(curr.left);
                    if (curr.right != null) q.offer(curr.right);
                }
                res.add(new ArrayList<>(tmp));
            }

            return res;
        }
    }

    class Solution2 {

        private void traverse(TreeNode root, List<List<Integer>> res, int depth) {
            if (root == null) return;
            if (depth == res.size()) res.add(new ArrayList<Integer>());
            res.get(depth).add(root.val);
            traverse(root.left, res, depth + 1);
            traverse(root.right, res, depth + 1);
        }

        /**
         * Depth-First Search.
         * Time: O(N)
         * Space: O(NlogN + H) = O(NlogN + N)
         *
         * @param root tree root node
         * @return the level order traversal of the given tree's nodes values
         */
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> res = new LinkedList<>();
            traverse(root, res, 0);
            return res;
        }
    }
}
