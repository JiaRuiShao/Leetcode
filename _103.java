import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import helper.TreeNode;

/**
 * 103. Binary Tree Zigzag Level Order Traversal
 */
public class _103 {
    class Solution1_DFS {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> zigzag = new ArrayList<>();
            dfs(root, true, 0, zigzag);
            return zigzag;
        }

        private void dfs(TreeNode root, boolean leftToRight, int level, List<List<Integer>> zigzag) {
            if (root == null) {
                return;
            }
            if (zigzag.size() == level) {
                zigzag.add(new LinkedList<>());
            }
            if (leftToRight) {
                zigzag.get(level).add(root.val);
            } else {
                zigzag.get(level).add(0, root.val);
            }
            dfs(root.left, !leftToRight, level + 1, zigzag);
            dfs(root.right, !leftToRight, level + 1, zigzag);
        }
    }

    class Solution2_BFS {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) return result;
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            boolean leftToRight = true;
            
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                LinkedList<Integer> currentLevel = new LinkedList<>();
                
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    
                    // Add to front or back based on direction
                    if (leftToRight) {
                        currentLevel.addLast(node.val);
                    } else {
                        currentLevel.addFirst(node.val);
                    }
                    
                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }
                
                result.add(currentLevel);
                leftToRight = !leftToRight;
            }
            
            return result;
        }
    }
}
