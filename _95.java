import helper.TreeNode;

import java.util.LinkedList;
import java.util.List;

public class _95 {
    class Solution {

        /**
         * Time: O(N * Gn) where Gn is the Catalan Number
         * Space: O(N * Gn)
         *
         * @param lo the low interval
         * @param hi the high interval
         * @return the roots of the built BSTs given n
         */
        private List<TreeNode> buildBST(int lo, int hi) {
            List<TreeNode> nodes = new LinkedList<>();
            if (lo > hi) {
                nodes.add(null);
                return nodes;
            }

            for (int i = lo; i <= hi; i++) {
                List<TreeNode> leftTree = buildBST(lo, i - 1);
                List<TreeNode> rightTree = buildBST(i + 1, hi);
                // build BSTs with root val i
                for (TreeNode left : leftTree) {
                    for (TreeNode right : rightTree) {
                        TreeNode root = new TreeNode(i);
                        root.left = left;
                        root.right = right;
                        nodes.add(root);
                    }
                }

            }
            return nodes;
        }

        public List<TreeNode> generateTrees(int n) {
            return n == 0 ? new LinkedList<>() : buildBST(1, n);
        }
    }
}
