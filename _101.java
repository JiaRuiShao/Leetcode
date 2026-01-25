import java.util.LinkedList;
import java.util.Queue;

import helper.TreeNode;

/**
 * 101. Symmetric Tree
 */
public class _101 {
    class Solution1_Recursion {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) return true;
            return dfs(root.left, root.right);
        }

        private boolean dfs(TreeNode left, TreeNode right) {
            if (left == null && right == null) return true;
            if (left == null || right == null) return false;
            if (left.val != right.val) return false;
            return dfs(left.left, right.right) && dfs(left.right, right.left);
        }
    }

    class Solution2_BFS {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) return true;
            
            Queue<Pair> queue = new LinkedList<>();
            queue.offer(new Pair(root.left, root.right));
            
            while (!queue.isEmpty()) {
                Pair pair = queue.poll();
                TreeNode left = pair.left;
                TreeNode right = pair.right;
                
                if (left == null && right == null) {
                    continue;
                }
                
                if (left == null || right == null || left.val != right.val) {
                    return false;
                }
                
                queue.offer(new Pair(left.left, right.right));
                queue.offer(new Pair(left.right, right.left));
            }
            
            return true;
        }

        // we use this helper class because Queue doesn't allow for null values
        private class Pair {
            TreeNode left;
            TreeNode right;
            
            Pair(TreeNode left, TreeNode right) {
                this.left = left;
                this.right = right;
            }
        }
    }
}
