public class _106 {
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

    class Solution {

        /**
         * A recursive helper method to build a binary tree given the in-order arr and the post-order arr.
         * Time: O(N)
         * Space: O(H) -- O(N)
         *
         * @param in in-order arr
         * @param is in-order arr start idx
         * @param ie in-order arr end idx
         * @param post post-order arr
         * @param ps post-order arr start idx
         * @param pe post-order arr end idx
         * @param n the last valid index
         * @return the root node for the built binary tree
         */
        private TreeNode build(int[] in, int is, int ie, int[] post, int ps, int pe, int n) {
            if (is > ie || ps > pe || is > n || ie > n || ps > n || pe > n) return null;
            int rootVal = post[pe]; // root val is the last val in post arr
            TreeNode root = new TreeNode(rootVal);
            int rootIdx = -1; // root idx in the in-order arr
            for (int i = is; i <= ie; i++) {
                if (in[i] == rootVal) {
                    rootIdx = i;
                    break;
                }
            }
            // from the in-order root idx and the start idx, we could get the right subtree length
            int rightLength = ie - rootIdx;
            root.left = build(in, is, rootIdx - 1, post, ps, pe - rightLength - 1, n);
            root.right = build(in, rootIdx + 1, ie, post, pe - rightLength, pe - 1, n);
            return root;
        }

        public TreeNode buildTree(int[] inorder, int[] postorder) {
            int n = inorder.length - 1;
            return build(inorder, 0, n, postorder, 0, n, n);
        }

    }
}
