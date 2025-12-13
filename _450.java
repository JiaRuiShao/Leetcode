import helper.TreeNode;

/**
 * 450. Delete Node in a BST
 */
public class _450 {
    // My original recursive solution, more complicated than the value replacement recursive solution; not recommended
    static class Solution1_Recursion_NodeRelocation {
        // 1 - find node to delete
        // 2.1 node is a leaf node - direct delete
        // 2.2 node has only one child - delete and attach its only child to parent
        // 2.3 node has two childs - find rightmost node in left subtree OR leftmost node in right subtree as the replacement node; attach left child of predecessor OR right child of successor to their parent node
        public TreeNode deleteNode(TreeNode root, int key) {

            if (root == null) return null;
            if (root.val == key) { // found the node to delete
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                } else {
                    // node to delete has two childs, find rightmost node in left subtree
                    TreeNode predecessor = findPredecessor(root.left);
                    if (predecessor != root.left) predecessor.left = root.left;
                    predecessor.right = root.right;
                    root.left = null;
                    root.right = null;
                    return predecessor;
                }
            } else if (root.val < key) {
                root.right = deleteNode(root.right, key);
            } else {
                root.left = deleteNode(root.left, key);
            }
            return root;
        }

        private TreeNode findPredecessor(TreeNode curr) {
            TreeNode prev = null;
            while (curr.right != null) {
                prev = curr;
                curr = curr.right;
            }
            if (prev != null) { // attach replacement left subtree as prev right child
                prev.right = curr.left;
                // curr.left = null; // this logic is implemented in main method
            }
            
            return curr;
        }
    }

    static class Solution1_Recursion_ValueReplacement_FindPredecessor {
        /**
         * 1. Recursively find the node that has the same value as the key, while setting the left/right nodes equal to the returned subtree
         * 2. Once the node is found, have to handle the below 4 cases
         * a. node doesn't have left or right - return null
         * b. node only has left subtree- return the left subtree
         * c. node only has right subtree- return the right subtree
         * d. node has both left and right - find the minimum value in the right subtree, set that value to the currently found node, then recursively delete the minimum value in the right subtree
         */
        public TreeNode deleteNode(TreeNode root, int key) {
            if (root == null) return null;
            if (root.val == key) { // found the node to delete
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                } else {
                    // node to delete has two childs, find rightmost node in left subtree
                    TreeNode predecessor = findPredecessor(root.left);
                    root.val = predecessor.val;
                    // delete the replacement node
                    root.left = deleteNode(root.left, predecessor.val);
                }
            } else if (root.val < key) {
                root.right = deleteNode(root.right, key);
            } else {
                root.left = deleteNode(root.left, key);
            }
            return root;
        }

        private TreeNode findPredecessor(TreeNode curr) {
            while (curr.right != null) {
                curr = curr.right;
            }
            return curr;
        }
    }

    static class Solution1_Recursion_ValueReplacement_FindSuccessor {
        public TreeNode deleteNode(TreeNode root, int key) {
            if (root == null) return null;
            if (root.val == key) { // found the node to delete
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                } else {
                    // node to delete has two childs, find leftmost node in right subtree
                    TreeNode successor = findSuccessor(root.right);
                    root.val = successor.val;
                    root.right = deleteNode(root.right, successor.val);
                }
            } else if (root.val < key) {
                root.right = deleteNode(root.right, key);
            } else {
                root.left = deleteNode(root.left, key);
            }
            return root;
        }

        private TreeNode findSuccessor(TreeNode curr) {
            while (curr.left != null) {
                curr = curr.left;
            }
            return curr;
        }
    }

    static class Solution2_Iterative {

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

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);
        new Solution1_Recursion_NodeRelocation().deleteNode(root, 3);
    }
}
