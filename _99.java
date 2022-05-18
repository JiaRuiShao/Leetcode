import helper.TreeNode;

public class _99 {
    static class Solution {
        TreeNode n1 = null, n2 = null, prev = null;

        private void traverse(TreeNode root) {
            if (root == null) return;
            traverse(root.left);
            // in-order position, check whether current node val > the prev val
            if (prev != null && root.val <= prev.val) {
                if (n1 == null) n1 = prev; /** IMPORTANT **/
                n2 = root;
            }
            prev = root;
            traverse(root.right);
        }

        private void swap() {
            int tmp = n1.val;
            n1.val = n2.val;
            n2.val = tmp;
        }

        public void recoverTree(TreeNode root) {
            traverse(root); // in-order traversal to find the two nodes that need to be swapped
            swap();
        }
    }
}
