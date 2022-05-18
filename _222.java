public class _222 {
    private static class TreeNode {
        int val;
        TreeNode left, right;

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

    public static class NodesCountInCompleteBT {
        /**
         * Count the Nodes in a Complete Binary Tree.
         * Time: O(logN*logN)
         * Space: O(logN)
         *
         * @param root the current node in this call stack
         * @return the nodes number in this tree
         */
        public int countNodes(TreeNode root) {
            TreeNode l = root, r = root;
            // record left & right tree level/(height/depth + 1)
            int ll = 0, rl = 0;
            while (l != null) {
                l = l.left;
                ll++;
            }
            while (r != null) {
                r = r.right;
                rl++;
            }
            // if the left and right level is the same, then it's a perfect binary tree
            if (ll == rl) {
                return (int)Math.pow(2, ll) - 1;
            }

            // if the left and right level is not the same, then count nodes as an oridinary binary tree
            return 1 + countNodes(root.left) + countNodes(root.right);
        }
    }

    public static class NodesCountInPerfectBT {
        /**
         * Count the Nodes in a Complete Binary Tree.
         * Nodes# = 2^0+2^1+...+2^N = 2^(H+1) - 1 = 2^Level - 1.
         * Time: O(logN)
         * Space: O(logN)
         *
         * @param root the current node in this call stack
         * @return the nodes number in this tree
         */
        public int countNodes(TreeNode root) {
            int level = 0;
            // count tree level
            while (root != null) {
                root = root.left;
                level++;
            }
            // nodes num is 2^(h+1)-1, which is also 2^level - 1
            return (int)Math.pow(2, level) - 1;
        }
    }

    public static class NodesCountInOrdinaryBT {
        /**
         * Count the Nodes in a Ordinary Binary Tree.
         * Time: O(N)
         * Space: O(H) = O(N)
         *
         * @param root the current node in this call stack
         * @return the nodes number in this tree
         */
        public int countNodes(TreeNode root) {
            if (root == null) return 0;
            return 1 + countNodes(root.left) + countNodes(root.right);
        }
    }
}
