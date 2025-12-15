import helper.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 95. Unique Binary Search Trees II
 * This is the same recurrence as 96, but instead of: count += leftCount * rightCount
 * We do: 
 * for each leftTree
 *      for each rightTree
 *          construct tree by combining
 */
public class _95 {
    // Generate all unique BSTs using values in [left, right]
    // Time: super-exponential, much worse than O(Cₙ) because of repeated work
    // Space: O(n) recursion depth
    class Solution1_Recursive {
        public List<TreeNode> generateTrees(int n) {        
            return generateBST(1, n);
        }

        private List<TreeNode> generateBST(int left, int right) {
            List<TreeNode> bsts = new ArrayList<>();
            if (left > right) {
                bsts.add(null);
                return bsts;
            }
            for (int root = left; root <= right; root++) {
                List<TreeNode> leftTrees = generateBST(left, root - 1);
                List<TreeNode> rightTrees = generateBST(root + 1, right);
                for (TreeNode lNode : leftTrees) {
                    for (TreeNode rNode : rightTrees) {
                        TreeNode rootNode = new TreeNode(root);
                        rootNode.left = lNode;
                        rootNode.right = rNode;
                        bsts.add(rootNode);
                    }
                }
            }
            return bsts;
        }
    }

    // Time: O(n x Cn) where Cn is the Catalan numbers
    // Although there are O(n²) DP states, each state of size k only generates Cₖ trees
    // Summing across all subtree sizes gives O(n × Cₙ). We don’t multiply by n² because smaller states are exponentially cheaper
    class Solution1_RecursiveWithMemo {
        public List<TreeNode> generateTrees(int n) {        
            return generateBST(1, n, new HashMap<String, List<TreeNode>>());
        }

        private List<TreeNode> generateBST(int left, int right, Map<String, List<TreeNode>> memo) {
            String key = left + "," + right;
            if (memo.containsKey(key)) return memo.get(key);

            List<TreeNode> bsts = new ArrayList<>();
            if (left > right) {
                bsts.add(null);
                return bsts;
            }
            for (int root = left; root <= right; root++) {
                List<TreeNode> leftTrees = generateBST(left, root - 1, memo);
                List<TreeNode> rightTrees = generateBST(root + 1, right, memo);
                for (TreeNode lNode : leftTrees) {
                    for (TreeNode rNode : rightTrees) {
                        TreeNode rootNode = new TreeNode(root);
                        rootNode.left = lNode;
                        rootNode.right = rNode;
                        bsts.add(rootNode);
                    }
                }
            }
            memo.put(key, bsts);
            return bsts;
        }
    }
}
