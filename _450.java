import helper.TreeNode;

public class _450 {
    static class Solution {

        TreeNode parent = null;
        TreeNode newRoot;

        /**
         * Find the smallest (leftest) node / successor in teh right subtree.
         *
         * @param root the given tree node
         * @return the leftest node in the right subtree
         */
        private TreeNode findSuccessor(TreeNode root) {
            // here we choose the leftest right node
            TreeNode curr = root.right, prev = curr;
            while (curr.left != null) {
                prev = curr;
                curr = curr.left;
            } // terminate when curr points to the leftest right node
            if (prev != curr) prev.left = curr.right; // connect the right subtree of target node to parent.left
            curr.left = root.left; // set target left subtree
            if (curr != root.right) curr.right = root.right; // set target right subtree
            return curr;
        }

        /**
         * Find the largest (rightest) node / predecessor in the left subtree.
         *
         * @param root the given tree node
         * @return the rightest node in the left subtree
         */
        private TreeNode findPredecessor(TreeNode root) {
            TreeNode curr = root.left, prev = curr;
            while (curr.right != null) {
                prev = curr;
                curr = curr.right;
            }
            if (prev != curr) prev.right = curr.left;
            if (curr != root.left) curr.left = root.left;
            curr.right = root.right;
            return curr;
        }

        /**
         * Time: O(H) = O(N) unbalanced
         * Space: O(H) = O(N) unbalanced
         *
         * @param root the current node in the call stack
         * @param k the target key value
         * @param isLeft true if the current node is the left child of its parent
         */
        private void traverse(TreeNode root, int k, boolean isLeft) {
            if (root == null) return;
            if (root.val == k) { // found the node to delete
                TreeNode validChild = null;
                // 1. find the valid child node to be the new root
                // 1.1 - if current node is the leaf (no children)
                if (root.left == null && root.right == null) validChild = null;

                // 1.2 - if current node has two children
                // find the largest node in the left subtree OR
                // the smallest node in the right subtree
                if (root.left != null && root.right != null) validChild = findSuccessor(root);

                // 1.3 - if current node has only one valid child
                else if (root.left == null) validChild = root.right;
                else validChild = root.left;

                // 2. connect the parent & the new root
                if (parent == null) newRoot = validChild;
                else if (isLeft) parent.left = validChild;
                else parent.right = validChild;

                // disconnect the root node from its original child tree
                root.left = null;
                root.right = null;

            } else if (root.val < k) { // traverse to the right subtree
                parent = root;
                traverse(root.right, k, false);
            } else { // root.val > k, traverse to the left subtree
                parent = root;
                traverse(root.left, k, true);
            }
        }

        public TreeNode deleteNode(TreeNode root, int key) {
            newRoot = root;
            traverse(root, key, true);
            return newRoot;
        }
    }

    static class Solution2 {
        /**
         * credit: https://discuss.leetcode.com/topic/65792/recursive-easy-to-understand-java-solution
         * Steps:
         * 1. Recursively find the node that has the same value as the key, while setting the left/right nodes equal to the returned subtree
         * 2. Once the node is found, have to handle the below 4 cases
         * a. node doesn't have left or right - return null
         * b. node only has left subtree- return the left subtree
         * c. node only has right subtree- return the right subtree
         * d. node has both left and right - find the minimum value in the right subtree, set that value to the currently found node, then recursively delete the minimum value in the right subtree
         */
        public TreeNode deleteNode(TreeNode root, int key) {
            if (root == null) {
                return null;
            }
            if (root.val > key) {
                root.left = deleteNode(root.left, key);
            } else if (root.val < key) {
                root.right = deleteNode(root.right, key);
            } else { // root.val == key
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                }

                TreeNode minNode = getMin(root.right); // get the smallest/leftest node in the right subtree
                root.val = minNode.val;
                root.right = deleteNode(root.right, root.val); /**Notice here the key becomes the minNode val**/
            }
            return root;
        }

        private TreeNode getMin(TreeNode node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);
        new Solution().deleteNode(root, 3);
    }
}
