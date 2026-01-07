import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import helper.TreeNode;

/**
 * 199. Binary Tree Right Side View
 */
public class _199 {
    // Time: O(n)
    // Space: O(h)
    class Solution1_DFS {
        public List<Integer> rightSideView(TreeNode root) {
            List<Integer> rights = new ArrayList<>();
            traverse(root, 0, rights);
            return rights;
        }

        private void traverse(TreeNode root, int level, List<Integer> nodes) {
            if (root == null) return;
            if (nodes.size() == level) nodes.add(root.val);
            traverse(root.right, level + 1, nodes);
            traverse(root.left, level + 1, nodes);
        }
    }

    // Time: O(n)
    // Space: O(w)
    class Solution2_BFS {
        public List<Integer> rightSideView(TreeNode root) {
            List<Integer> rightNodes = new ArrayList<>();
            if (root == null) return rightNodes;
            Queue<TreeNode> q = new ArrayDeque<>();
            q.offer(root);
            while (!q.isEmpty()) {
                int size = q.size();
                rightNodes.add(q.peek().val);
                for (int i = 0; i < size; i++) {
                    TreeNode curr = q.poll();
                    if (curr.right != null) {
                        q.offer(curr.right);
                    }
                    if (curr.left != null) {
                        q.offer(curr.left);
                    }
                }
            }
            return rightNodes;
        }
    }
}
