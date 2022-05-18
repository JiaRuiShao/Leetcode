import java.util.Stack;

public class _114 {

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

    static class SolutionRecursion {

        /**
         * Time: O(N)
         * Space: O(H) where H is the height of the binary tree. It's O(logN) if the tree is a balanced tree; It's O(n) if else
         */
        void flatten(TreeNode root) {
            // base case
            if (root == null)
                return;

            flatten(root.left);
            flatten(root.right);

            /**** post order ****/
            TreeNode left = root.left;
            TreeNode right = root.right;

            // make left subtree as right subtree
            root.left = null;
            root.right = left;

            // add the original right subtree to the end of the original left subtree
            while (root.right != null) {
                root = root.right;
            }

            root.right = right;
        }
    }

    static class SolutionRecursion2 {

        /**
         * Time: O(N)
         * Space: O(H) where H is the height of the binary tree. It's O(logN) if the tree is a balanced tree; It's O(n) if else
         * Ref: https://leetcode.com/problems/flatten-binary-tree-to-linked-list/solution/
         */
        TreeNode flat(TreeNode root) {
            // base cases
            if (root == null) return null;
            if (root.left == null && root.right == null) return root;

            // recursively flatten the left & right subtree
            TreeNode leftTail = flat(root.left);
            TreeNode rightTail = flat(root.right);

            // post-order
            if (leftTail != null) {
                leftTail.right = root.right;
                root.right = root.left;
                root.left = null;
            }

            // return the "rightmost" node 
            return rightTail == null ? leftTail : rightTail;
        }

        void flatten(TreeNode root) {
            flat(root);           
        }
    }

    static class SolutionIterative {

        /**
         * Time: O(N)
         * Space: O(1)
         */
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
}
