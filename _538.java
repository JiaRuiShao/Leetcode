import helper.TreeNode;

public class _538 {
    class Solution {

        private int sum = 0;

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
