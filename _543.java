import helper.TreeNode;

/**
 * 543. Diameter of Binary Tree
 */
public class _543 {
    class Solution1_Subproblem {
    
        int maxDiameter = 0;
    
        public int diameterOfBinaryTree(TreeNode root) {
            maxDepth(root);
            return maxDiameter;
        }
    
        private int maxDepth(TreeNode node) {
            if (node == null) return 0;
            int left = maxDepth(node.left);
            int right = maxDepth(node.right);
            maxDiameter = Math.max(maxDiameter, left + right);
            return Math.max(left, right) + 1;
        }
    }
}
