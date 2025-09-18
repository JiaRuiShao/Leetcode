import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import helper.TreeNode;

/**
 * 662. Maximum Width of Binary Tree
 */
public class _662 {
    // In this BFS approach, we enqueue null childs and then trim them from both ends to get the correct width
    // This takes much more space than the index node approach
    class Solution0_BFS_EnqueueNull_MLE {
        public int widthOfBinaryTree(TreeNode root) {
            if (root == null) return 0;
            Deque<TreeNode> q = new ArrayDeque<>();
            q.offer(root);

            int width = 1;
            TreeNode dummy = new TreeNode(101);
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    TreeNode node = q.poll();
                    TreeNode left = node == dummy || node.left == null ? dummy : node.left;
                    TreeNode right = node == dummy || node.right == null ? dummy : node.right;
                    q.offer(left);
                    q.offer(right);
                }
                while (!q.isEmpty() && q.peek() == dummy) {
                    q.poll();
                }
                while (!q.isEmpty() && q.peekLast() == dummy) {
                    q.pollLast();
                }
                width = Math.max(width, q.size());
            }
            return width;
        }
    }

    class Solution1_BFS_IndexNode {
        class Node {
            TreeNode node;
            int id;
            public Node(TreeNode node, int id) {
                this.node = node;
                this.id = id;
            }
        }

        public int widthOfBinaryTree(TreeNode root) {
            if (root == null) return 0;
            Queue<Node> q = new ArrayDeque<>();
            q.offer(new Node(root, 1));

            int width = 0;
            while (!q.isEmpty()) {
                int size = q.size(), start = 0, end = 0;
                for (int i = 0; i < size; i++) {
                    Node node = q.poll();
                    if (i == 0) start = node.id;
                    if (i == size - 1) end = node.id;
                    if (node.node.left != null) {
                        q.offer(new Node(node.node.left, node.id * 2));
                    }
                    if (node.node.right != null) {
                        q.offer(new Node(node.node.right, node.id * 2 + 1));
                    }
                }
                width = Math.max(width, end - start + 1);
            }
            return width;
        }
    }

    class Solution2_DFS_IndexNode {
        int maxWidth;
        Map<Integer, Integer> leftMostIds;

        public int widthOfBinaryTree(TreeNode root) {
            maxWidth = 0;
            leftMostIds = new HashMap<>();
            dfs(root, 1, 1);
            return maxWidth;
        }

        private void dfs(TreeNode node, int id, int level) {
            if (node == null) return;
            leftMostIds.putIfAbsent(level, id);
            maxWidth = Math.max(maxWidth, id - leftMostIds.get(level) + 1);
            dfs(node.left, id * 2, level + 1);
            dfs(node.right, id * 2 + 1, level + 1);
        }
    }

    class Solution2_DFS_IndexNode_WithoutGlobalVar_WithNormalization {
        public int widthOfBinaryTree(TreeNode root) {
            if (root == null) return 0;
            Map<Integer, Long> leftmost = new HashMap<>(); // depth -> first index seen at this depth
            return (int) dfs(root, 0, 0L, leftmost);
        }

        private long dfs(TreeNode node, int depth, long pos, Map<Integer, Long> leftmost) {
            if (node == null) return 0;

            // Record the first (leftmost) index at this depth
            leftmost.computeIfAbsent(depth, d -> pos);

            long base = leftmost.get(depth);
            long widthHere = pos - base + 1; // width using current node as the rightmost at this depth

            // Normalize positions for children to keep numbers small:
            long rel = pos - base;
            long leftWidth  = dfs(node.left,  depth + 1, rel * 2,     leftmost);
            long rightWidth = dfs(node.right, depth + 1, rel * 2 + 1, leftmost);

            return Math.max(widthHere, Math.max(leftWidth, rightWidth));
        }
    }

}
