import helper.TreeNode;

import java.util.*;

/**
 * 236. Lowest Common Ancestor of a Binary Tree
 */
public class _236 {
    // Time: O(n)
    // Space: O(h)
    class Solution1_DFS {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null) {
                return null;
            }
            if (root == p || root == q) {
                return root;
            }
            TreeNode left = lowestCommonAncestor(root.left, p, q);
            TreeNode right = lowestCommonAncestor(root.right, p, q);
            if (left != null && right != null) {
                return root;
            }
            return left != null ? left : right;
        }
    }

    class Solution2_BFS {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            Map<TreeNode, TreeNode> parent = new HashMap<>();
            Deque<TreeNode> queue = new LinkedList<>();

            parent.put(root, null);
            queue.offer(root);

            // Step 1: Build parent map until we have both p and q in it
            while (!parent.containsKey(p) || !parent.containsKey(q)) {
                TreeNode node = queue.poll();

                if (node.left != null) {
                    parent.put(node.left, node);
                    queue.push(node.left);
                }

                if (node.right != null) {
                    parent.put(node.right, node);
                    queue.push(node.right);
                }
            }

            // Step 2: Build a set of all ancestors of p
            Set<TreeNode> ancestors = new HashSet<>();
            while (p != null) {
                ancestors.add(p);
                p = parent.get(p);
            }

            // Step 3: The first ancestor of q that's also in p's ancestor set is the LCA
            while (!ancestors.contains(q)) {
                q = parent.get(q);
            }

            return q;
        }
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);

        TreeNode res = new _236().new Solution1_DFS().lowestCommonAncestor(root, new TreeNode(5), new TreeNode(1));
        System.out.println(res.val);
    }
}
