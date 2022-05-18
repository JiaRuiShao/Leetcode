public class _889 {
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
         * Recursively Build a binary tree based on the given preorder[preStart..preEnd] and postorder[postStart..postEnd] arr.
         * Time: O(N)
         * Space: O(H) -- O(N)
         *
         * @param pre pre-order arr
         * @param ps pre-order arr start idx
         * @param pe pre-order arr end idx
         * @param post post-order arr
         * @param pos post-order arr start idx
         * @param poe post-order arr end idx
         * @return the root node of the built binary tree
         */
        private TreeNode build(int[] pre, int ps, int pe, int[] post, int pos, int poe) {
            // base cases
            if (ps > pe || pos > poe) return null;
            TreeNode root = new TreeNode(pre[ps]); // or post[poe]
            if (ps == pe) return root; // if preorder start idx is the last valid idx

            int leftVal = pre[ps + 1]; // left root val
            // find the left root in post order arr, thus find the left subtree length, which is left idx - pos
            int leftIdx = -1;
            for (int i = pos; i <= poe; i++) {
                if (post[i] == leftVal) {
                    leftIdx = i;
                    break;
                }
            }
            int leftLength = leftIdx - pos;
            // recursively build the left subtree & right subtree
            root.left = build(pre, ps + 1, ps + 1 + leftLength, post, pos, leftIdx);
            root.right = build(pre, ps + 2 + leftLength, pe, post, leftIdx + 1, poe);
            return root;
        }

        public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
            if (preorder == null || postorder == null || preorder.length == 0 || postorder.length == 0) return null;
            int n = preorder.length - 1;
            return build(preorder, 0, n, postorder, 0, n);
        }
    }
}
