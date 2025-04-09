import helper.TreeNode;

/**
 * 235. Lowest Common Ancestor of a Binary Search Tree
 */
public class _235 {
    class Solution1_BFS {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            int smaller = p.val > q.val ? q.val : p.val;
            int larger = p.val > q.val ? p.val : q.val;
            return findLCA(root, smaller, larger);
        }
    
        private TreeNode findLCA(TreeNode root, int p, int q) {
            if (root == null) return root;
            if (q < root.val) return findLCA(root.left, p, q);
            if (p > root.val) return findLCA(root.right, p, q);
            return root; // if (p < root.val && q > root.val) 
        }
    }
}
