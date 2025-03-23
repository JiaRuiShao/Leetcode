import helper.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

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
         * Space: O(w) = O(n) if balanced; O(1) if skewed
         * @param root
         * @return
         */
        public int minDepth(TreeNode root) {
            Queue<TreeNode> queue = new LinkedList<>();
            int depth = 0;
            if (root != null) {
                queue.offer(root);
            }

            while (!queue.isEmpty()) {
                depth++;
                int sz = queue.size(); // we have to get size of queue before the for loop, or else the for loop will continue to next level nodes
                for (int i = 0; i < sz; i++) {
                    TreeNode curr = queue.poll();
                    if (curr.left == null && curr.right == null) {
                        return depth;
                    }
                    if (curr.left != null) {
                        queue.offer(curr.left);
                    }
                    if (curr.right != null) {
                        queue.offer(curr.right);
                    }
                }
            }

            return depth;
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
            }
            if (root.left != null) {
                traverseTree(root.left);
            }
            if (root.right != null) {
                traverseTree(root.right);
            }
            currDepth--;
        }
    }
    
    class Solution2_DFS_Backtrack2 {
        int minDepth = Integer.MAX_VALUE;

        public int minDepth(TreeNode root) {
            int currDepth = 0;
            if (root == null) {
                return currDepth;
            }
            dfs(root, currDepth);
            return minDepth;
        }
    
        private void dfs(TreeNode root, int currDepth) {
            currDepth++;
            if (root.left == null && root.right == null) { // pre-order dfs
                minDepth = Math.min(minDepth, currDepth);
            }
            if (root.left != null) {
                dfs(root.left, currDepth);
            }
            if (root.right != null) {
                dfs(root.right, currDepth);
            }
            // we need to call currDepth-- if it's declared as a global variable
        }
    }

    class Solution3_DFS_Without_Global_Variables { // count from top to bottom
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int minDepth = Integer.MAX_VALUE;
            int currDepth = 0;
            return findMinDepth(root, currDepth, minDepth);
        }
    
        private int findMinDepth(TreeNode root, int currDepth, int minDepth) {
            currDepth++;
            if (root.left == null && root.right == null) { // reach leave node, return minDepth
                return Math.min(minDepth, currDepth);
            }
            if (root.left != null) {
                minDepth = findMinDepth(root.left, currDepth, minDepth);
            }
            if (root.right != null) {
                minDepth = findMinDepth(root.right, currDepth, minDepth);
            }
            return minDepth;
        }
    }

    class Solution4_DFS_Without_Global_Variables { // count from bottom to top
        /**
         * Convert the problem into: find min depth from leaf node to root node 
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
