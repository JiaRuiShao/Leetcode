import helper.TreeNode;

/**
 * 1644. Lowest Common Ancestor of a Binary Tree II
 */
public class _1644 {
    class Solution1_DFS {
        int found = 0;
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            TreeNode lca = findLCA(root, p, q);
            return found == 2 ? lca : null;
        }
        
        private TreeNode findLCA(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null) return null;
            TreeNode left = findLCA(root.left, p, q);
            TreeNode right = findLCA(root.right, p, q);
            if (left != null && right != null) {
                return root;
            }
            if (root == q || root == p) {
                found++;
                return root;
            }
            return left != null ? left : right;
        }
    }
}
