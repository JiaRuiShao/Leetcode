import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class _652 {
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
         * Find Duplicate subtrees by recording the subtree structures.
         * Time: O(N)
         * Space: O(N)
         *
         * @param root the curr node
         * @param freq the frequency hashmap that stores the subtrees & their occurrences
         * @param res the list of node with duplicate subtrees
         * @return structure as String
         */
        private String findDupNodes(TreeNode root, Map<String, Integer> freq, List<TreeNode> res) {
            // base case
            if (root == null) return "#";

            String left = findDupNodes(root.left, freq, res);
            String right = findDupNodes(root.right, freq, res);
            String curr = Integer.toString(root.val);
            // **notice here the structure cannot be in-order, see below example:**
            //      TreeA           TreeB
            //    /                       \
            // 0                            0
            // they both have same in-order serialization "#0#0#" if we just use "#" to represent null.
            String structure = left + right + curr;
            int count = freq.getOrDefault(structure, 0);
            if (count == 1) res.add(root); // add curr node to the result list if there's a same structure in the hashmap
            freq.put(structure, count + 1); // update the freq hashmap

            return structure;
        }

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            List<TreeNode> res = new LinkedList<>();
            // used to store the left child, root, right child value and their occurrences
            Map<String, Integer> freq = new HashMap<>();
            findDupNodes(root, freq, res);
            return res;
        }
    }
}
