import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class _508 {
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

        int maxCount = -1;

        private int dfs(TreeNode root, Map<Integer, Integer> freq) {
            // base case
            if (root == null) return 0;
            // traverse the left and right subtree
            int left = dfs(root.left, freq);
            int right = dfs(root.right, freq);
            int sum = left + right + root.val;
            // add the subtree sum to the freq hashmap
            freq.put(sum, freq.getOrDefault(sum, 0) + 1);
            // maxCount = freq.get(sum) > maxCount ? freq.get(sum) : maxCount;
            if (freq.get(sum) > maxCount) maxCount = freq.get(sum);
            return sum;
        }

        /**
         * Find highest frequency sum (of the subtree).
         * Time: O(N + N + N) = O(N)
         * Space: O(N + H + N) = O(N)
         *
         * @param root the given binary tree root node
         * @return the array of sum with the highest frequency
         */
        public int[] findFrequentTreeSum(TreeNode root) {
            // to store the subtree sum and frequencies
            Map<Integer, Integer> sumFreq = new HashMap<>();
            dfs(root, sumFreq);

            // to store the freq of each sum as key and the sum as value
            List<Integer> res = new LinkedList<>();
            for (Map.Entry<Integer, Integer> entry : sumFreq.entrySet()) {
                if (entry.getValue() == maxCount) res.add(entry.getKey());
            }
            // convert List<Integer> to int[]
            return res.stream().mapToInt(Integer::intValue).toArray();
        }
    }
}
