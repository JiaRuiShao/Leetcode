import java.util.Stack;

/**
 * 114. Flatten Binary Tree to Linked List
 */
public class _114 {
    // If we're allowed to return a new root node
    class Solution0_DFS_Not_Work {
        TreeNode curr;
        public TreeNode flatten(TreeNode root) {
            TreeNode dummyHead = new TreeNode();
            curr = dummyHead;
            dfs(root);
            return dummyHead.right;
        }
    
        private void dfs(TreeNode node) {
            if (node == null) return;
            curr.right = new TreeNode(node.val);
            curr = curr.right;
            dfs(node.left);
            dfs(node.right);
        }
    }

    class Solution1_DFS {

        TreeNode prev = null;
    
        public void flatten(TreeNode root) {
            if (root == null) return;
            TreeNode left = root.left, right = root.right;
            if (prev != null) { // pre-order flatten
                prev.right = root;
                prev.left = null;
            }
            prev = root;
            flatten(left);
            flatten(right);
        }
    }

    class Solution2_DFS_Without_Global_Variable {
        public void flatten(TreeNode root) {
            dfs(root, null);
        }
    
        private TreeNode dfs(TreeNode node, TreeNode prev) {
            if (node == null) return prev;
            TreeNode left = node.left, right = node.right;
            if (prev != null) { // pre-order flatten
                prev.right = node;
                prev.left = null;
            }
            prev = dfs(left, node);
            return dfs(right, prev);
        }
    }

    class Solution3_Subproblem {
        public void flatten(TreeNode root) {
            if (root == null) return;
            TreeNode left = root.left;
            TreeNode right = root.right;
            flatten(root.left);
            flatten(root.right);
            if (left != null) {
                root.right = left;
                root.left = null;
                while (left.right != null) {
                    left = left.right;
                }
                left.right = right;
            }
        }
    }

    class Solution4_Iterative {
        void flatten(TreeNode root) {
            // base case
            if (root == null) return;
            
            TreeNode node = root;
            while (node != null) {
                
                // If the node has a left child
                if (node.left != null) {
                    
                    // Find the rightmost node in root's left sub tree
                    TreeNode rightmost = node.left;
                    while (rightmost.right != null) {
                        rightmost = rightmost.right;
                    }
                    
                    // rewire the connections
                    rightmost.right = node.right;
                    node.right = node.left;
                    node.left = null;
                }
                
                // move on to the right side of the tree
                node = node.right;
            }
        }
    }

    private static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
