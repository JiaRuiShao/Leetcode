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
    
        private TreeNode dfs(TreeNode curr, TreeNode prev) {
            if (curr == null) return prev;
            TreeNode left = curr.left, right = curr.right;
            if (prev != null) {
                prev.right = curr;
                prev.left = null;
            }
            prev = curr;
            prev = dfs(left, prev);
            prev = dfs(right, prev);
            return prev;
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

    /**
     * Follow-Up: Can you flatten the tree in-place (with O(1) extra space)?
     * Time: O(n) Each node in the tree is used as rightMostLeaf at most once.
     * Space: O(1)
     */
    class Solution4_Iterative {
        public void flatten(TreeNode root) {
            TreeNode curr = root;
            while (curr != null) {
                TreeNode left = curr.left;
                if (left != null) {
                    TreeNode rightMostLeaf = left;
                    while (rightMostLeaf.right != null) {
                        rightMostLeaf = rightMostLeaf.right;
                    }
                    rightMostLeaf.right = curr.right;
                    curr.left = null;
                    curr.right = left;
                }
                curr = curr.right;
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
