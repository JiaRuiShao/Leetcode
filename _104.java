import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 104. Maximum Depth of Binary Tree
 * Given the root of a binary tree, return its maximum depth.
 *
 * A binary tree's maximum depth is the number of nodes along the longest path
 * from the root node down to the farthest leaf node.
 */
public class _104 {
    private static final class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    class Solution1_DP {
        /**
         * Divide the problem into sub-problems -- height of tree = height of root node = max(h_left_subtree, h_right_subtree) + 1.
         * Time: O(h) = O(n)
         * Space: O(h) = O(n)
         * @param root
         * @return
         */
        public int maxDepth(TreeNode root) {
            if (root == null) return 0;
            int left  = maxDepth(root.left);
            int right  = maxDepth(root.right);
            return Math.max(left, right) + 1;
        }
    }

    class Solution2_DFS {
        int height, maxHeight;

        /**
         * Classic BFS Backtrack.
         * @param root
         * @return
         */
        public int maxDepth(TreeNode root) {
            traverse(root);
            return maxHeight;
        }

        /**
         * Time: O(n)
         * Space: O(h) = O(n)
         * @param curr current TreeNode
         */
        private void traverse(TreeNode curr) {
            if (curr == null) return;
            height++;
            if (height > maxHeight) maxHeight = height;
            traverse(curr.left);
            traverse(curr.right);
            height--;
        }
    }

    class Solution3_BFS {
        /**
         * Level-order traverse.
         * Time: O(n)
         * Space: O(n)
         * @param root
         * @return
         */
        public int maxDepth(TreeNode root) {
            int maxDepth = 0;
            if (root == null) return maxDepth;
            Queue<TreeNode> q = new LinkedList<>();
            TreeNode curr;
            q.offer(root);
            q.offer(null); // marks the end of first level
            while (q.peek() != null) {
                curr = q.poll();
                if (curr.left != null) q.offer(curr.left);
                if (curr.right != null) q.offer(curr.right);
                if (q.peek() == null) { // move to the next level/depth
                    q.offer(q.poll()); // add the null back to the queue
                    maxDepth++;
                }
            }
            return maxDepth;
        }
    }
}
