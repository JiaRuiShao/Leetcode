import helper.TreeNode;

/**
 * 538. Convert BST to Greater Tree.
 */
public class _538 {
    class Solution_BFS_Traversal {

        private int sum = 0; // record the sum that larger then current node

        private void traverse(TreeNode root) {
            // base case
            if (root == null) return;
            // bfs
            traverse(root.right); // go right first
            /** in-order position **/
            root.val += sum;
            sum = root.val;
            traverse(root.left); // go left
        }

        public TreeNode convertBST(TreeNode root) {
            traverse(root);
            return root;
        }
    }
}
