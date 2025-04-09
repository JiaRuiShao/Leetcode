import helper.TreeNode;

/**
 * 1644. Lowest Common Ancestor of a Binary Tree II
 */
public class _1644 {
    class Solution1_DFS {
        int found = 0;
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            TreeNode lca = findLCA(root, p.val, q.val);
            return found == 2 ? lca : null;
        }
        
        private TreeNode findLCA(TreeNode root, int p, int q) {
            if (root == null) return null;
            TreeNode left = findLCA(root.left, p, q);
            TreeNode right = findLCA(root.right, p, q);
            // check if curr node is LCA
            if (root.val == p || root.val == q) {
                found++;
                return root;
            }
            // check if both childs are LCA
            if (left != null && right != null) {
                return root;
            }
            // return LCA from left / right subtree
            return left != null ? left : right;
        }
    }
}
