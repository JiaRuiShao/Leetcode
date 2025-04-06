import java.util.*;

/**
 * 104. Maximum Depth of Binary Tree
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
         * Time: O(n)
         * Space: O(h) = O(n)
         * @param root
         * @return
         */
        public int maxDepth(TreeNode root) {
            if (root == null) return 0;
            return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
        }
    }

    class Solution2_DFS {
        int maxDepth = 0;

        public int maxDepth(TreeNode root) {
            traverse(root, 0);
            return maxDepth;
        }
    
        private void traverse(TreeNode node, int depth) {
            if (node == null) {
                maxDepth = Math.max(maxDepth, depth);
                return;
            }
    
            depth++;
            traverse(node.left, depth);
            traverse(node.right, depth);        
        }
    }

    class Solution3_BFS {
        /**
         * Level-order traverse using null as an indicator of level end.
         * Time: O(n)
         * Space: O(n) when the tree is balanced
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

        /**
         * Normal BFS.
         * @param root
         * @return
         */
        public int maxDepth2(TreeNode root) {
            int maxDepth = 0;
            if (root == null) return maxDepth;
            Queue<TreeNode> q = new LinkedList<>();
            TreeNode curr;
            q.offer(root);
            while (!q.isEmpty()) {
                maxDepth++;
                int sz = q.size();
                for (int i = 0; i < sz; i++) {
                    curr = q.poll();
                    if (curr.left != null) q.offer(curr.left);
                    if (curr.right != null) q.offer(curr.right);
                }
            }
            return maxDepth;
        }
    }

    class Solution4_DFS_Iterative {
        /**
         * Time: O(n)
         * Space: O(2n) = O(n) if skewed
         * @param root
         * @return
         */
        public int maxDepth(TreeNode root) {
            int maxDepth = 0;
            if (root == null) return maxDepth;
            Deque<TreeNode> stack = new LinkedList<>();
            Deque<Integer> depth = new LinkedList<>();
            stack.push(root);
            depth.push(1);

            TreeNode curr = root;
            int currDepth = 0;
            while (!stack.isEmpty()) {
                curr = stack.pop();
                currDepth = depth.pop();
                if (currDepth > maxDepth) maxDepth = currDepth;
                if (curr.right != null) {
                    stack.push(curr.right);
                    depth.push(currDepth + 1);
                }
                if (curr.left != null) {
                    stack.push(curr.left);
                    depth.push(currDepth + 1);
                }
            }
            return maxDepth;
        }
    }
}
