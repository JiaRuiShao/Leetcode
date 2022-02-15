import helper.TreeNode;

import java.util.*;

public class _501 {
    static class Solution1 {
        int maxCount = 0;

        private void traverse(TreeNode root, Map<Integer, Integer> freq) {
            if (root == null) return;
            freq.put(root.val, freq.getOrDefault(root.val, 0) + 1);
            maxCount = maxCount >= freq.get(root.val) ? maxCount : freq.get(root.val);
            traverse(root.left, freq);
            traverse(root.right, freq);
        }

        public int[] findMode(TreeNode root) {
            Map<Integer, Integer> freq = new HashMap<>(); // store the node val and their frequencies
            traverse(root, freq);
            List<Integer> res = new LinkedList<>();
            for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
                if (entry.getValue() == maxCount) res.add(entry.getKey());
            }
            return res.stream().mapToInt(Integer::valueOf).toArray();
        }
    }

    static class Solution2 {
        List<Integer> mode = new ArrayList<>();
        TreeNode prev = null;
        int curCount, maxCount;

        public int[] findMode(TreeNode root) {
            // in-order traversal
            traverse(root);

            int[] res = new int[mode.size()];
            for (int i = 0; i < res.length; i++) {
                res[i] = mode.get(i);
            }
            return res;
        }

        /**
         * In-order traverse, the node val should be either the same or larger than the prev one
         * @param root the given BST root node
         */
        void traverse(TreeNode root) {
            // base case
            if (root == null) return;
            traverse(root.left);
            // in-order traverse position
            if (prev == null) { // smallest val in the BST
                curCount = 1;
                maxCount = 1;
                mode.add(root.val);
            } else {
                if (root.val == prev.val) { // duplicate node val
                    curCount++;
                    if (curCount == maxCount) {
                        // root.val is the mode, add it to the mode
                        mode.add(root.val);
                    } else if (curCount > maxCount) {
                        // clear the mode, add the root.val in the mode, update the maxCount
                        mode.clear();
                        maxCount = curCount;
                        mode.add(root.val);
                    }
                } else { // different node val from prev
                    curCount = 1;
                    if (curCount == maxCount) {
                        mode.add(root.val);
                    }
                }
            }
            // update prev
            prev = root;
            traverse(root.right);
        }
    }
}
