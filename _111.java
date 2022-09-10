import helper.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 111. Minimum Depth of Binary Tree
 * Given a binary tree, find its minimum depth.
 * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
 */
public class _111 {
    class Solution1_BFS {
        /**
         * Faster than DFS -- traverse less nodes.
         * Time: O(n/2) if balanced; O(n) if skewed
         * Space: O(n) if balanced; O(1) if skewed
         * @param root
         * @return
         */
        public int minDepth(TreeNode root) {
            int currDepth = 0;
            if (root == null) return currDepth;
            Deque<TreeNode> q = new ArrayDeque<>();
            q.offer(root);

            TreeNode curr;
            while (!q.isEmpty()) {
                currDepth++;
                int sz = q.size();
                for (int i = 0; i < sz; i++) {
                    curr = q.poll();
                    // check whether this node is a leaf node
                    if (curr.left == null && curr.right == null) return currDepth;
                    if (curr.left != null) q.offer(curr.left);
                    if (curr.right != null) q.offer(curr.right);
                }
            }
            return currDepth;
        }
    }

    class Solution2_DFS_Backtrack {
        int currDepth = 0, minDepth = Integer.MAX_VALUE;

        /**
         * Classic DFS Backtrack.
         * Time: O(n)
         * Space: O(h) = O(logn) if balanced; = O(n) if skewed
         * @param root
         * @return
         */
        public int minDepth(TreeNode root) {
            if (root == null) return currDepth;
            traverseTree(root);
            return minDepth;
        }

        private void traverseTree(TreeNode root) {
            currDepth++;
            if (root.left == null && root.right == null) {
                minDepth = Math.min(minDepth, currDepth);
                return;
            }
            if (root.left != null) {
                traverseTree(root.left);
                currDepth--;
            }
            if (root.right != null) {
                traverseTree(root.right);
                currDepth--;
            }
        }
    }

    class Solution3_DP_SubProblem {
        /**
         * Divide the problem into sub-problems (here is find minDepth of left and right subtree)
         * Time: O(n)
         * Space: O(h) = O(logn) if balanced; = O(n) if skewed
         * @param root
         * @return
         */
        public int minDepth(TreeNode root) {
            if (root == null) return 0;
            int left = minDepth(root.left);
            int right = minDepth(root.right);
            if (left == 0) return right + 1;
            if (right == 0) return left + 1;
            return Math.min(left, right) + 1;
        }
    }
}
