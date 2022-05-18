public class _105 {

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

    /**
     * A private helper method to build a binary tree based on the given pre-order & in-order array.
     * Time: O(n)
     * Space: O(H) -- O(N)
     *
     * @param pre pre-order arr
     * @param ps pre-order arr start idx
     * @param pe pre-order arr end idx
     * @param in in-order arr
     * @param is in-order arr start idx
     * @param ie in-order arr end idx
     * @param n arr length - 1; the last valid index
     * @return the root of the binary tree
     */
    private TreeNode build(int[] pre, int ps, int pe, int[] in, int is, int ie, int n) {
        // base case
        if (ps > pe || is > ie || ps > n || pe > n || is > n || ie > n) return null;

        int rootVal = pre[ps];
        TreeNode root = new TreeNode(rootVal);
        // find root index in in-order traversal array
        int rootIdx = -1;
        for (int i = is; i <= ie; i++) {
            if (in[i] == rootVal) rootIdx = i;
        }

        int leftLength = rootIdx - is;

        root.left = build(pre, ps + 1, ps + leftLength, in, is, rootIdx - 1, n);
        root.right = build(pre, ps + leftLength + 1 , pe, in, rootIdx + 1, ie, n);

        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length - 1;
        return build(preorder, 0, n, inorder, 0, n, n);
    }
}
