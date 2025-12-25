import helper.TreeNode;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * 501. Find Mode in Binary Search Tree
 * 
 * Clarification:
 * - Could there be multiple modes? Yes it's possible
 * 
 * Follow-up:
 * - Implement in O(1) space
 */
public class _501 {
    // Below solution is valid but doesn't take use of the BST property
    // Time: O(n) - visit each node once + iterate through map
    // Space: O(n) - HashMap stores all distinct values
    static class Solution1_HashMap {
        int maxCount = 0;
        private void traverse(TreeNode root, Map<Integer, Integer> freq) {
            if (root == null) return;
            freq.put(root.val, freq.getOrDefault(root.val, 0) + 1);
            maxCount = Math.max(maxCount, freq.get(root.val));
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

    // Below solution uses heap property: in-order traversal gets mono increasing array
    // this is how we can count adjacent values in a counter pointer
    // Notice below implementation is not strictly constant space, we need to have two traversal helper methods
    // one for counting maxFreq & count and another for adding the modes after result array is allocated
    // Time: O(n)
    // Space: O(h+k) where h is tree hight for callstack & k is number of modes
    class Solution2_HeapProperty {
        List<Integer> modes;
        int currFreq, maxFreq, prev;

        public int[] findMode(TreeNode root) {
            modes = new ArrayList<>();
            currFreq = 1;
            maxFreq = 1;
            prev = 100_001;

            traverse(root);

            int[] modesArr = new int[modes.size()];
            for (int i = 0; i < modes.size(); i++) {
                modesArr[i] = modes.get(i);
            }
            return modesArr;
        }

        private void traverse(TreeNode root) {
            if (root == null) return;
            traverse(root.left);

            if (root.val == prev) {
                currFreq++;
            } else {
                currFreq = 1;
            }
            
            if (currFreq == maxFreq) {
                modes.add(root.val);
            } else if (currFreq > maxFreq) {
                maxFreq = currFreq;
                modes.clear();
                modes.add(root.val);
            }

            prev = root.val;
            traverse(root.right);
        }
    }

    // Time: O(n) - two passes
    // Space: O(n) - still need linear auxiliary space
    class Followup_Morris {
        public int[] findMode(TreeNode root) {
            List<Integer> modes = new ArrayList<>();
            
            // First pass: find max frequency
            int maxFreq = 0;
            int currFreq = 0;
            Integer prev = null;
            TreeNode current = root;
            
            while (current != null) {
                if (current.left == null) {
                    // Visit current
                    if (prev != null && prev.equals(current.val)) {
                        currFreq++;
                    } else {
                        currFreq = 1;
                    }
                    maxFreq = Math.max(maxFreq, currFreq);
                    prev = current.val;
                    
                    current = current.right;
                } else {
                    TreeNode predecessor = current.left;
                    while (predecessor.right != null && predecessor.right != current) {
                        predecessor = predecessor.right;
                    }
                    
                    if (predecessor.right == null) {
                        predecessor.right = current;
                        current = current.left;
                    } else {
                        predecessor.right = null;
                        
                        // Visit current
                        if (prev != null && prev.equals(current.val)) {
                            currFreq++;
                        } else {
                            currFreq = 1;
                        }
                        maxFreq = Math.max(maxFreq, currFreq);
                        prev = current.val;
                        
                        current = current.right;
                    }
                }
            }
            
            // Second pass: collect values with max frequency
            currFreq = 0;
            prev = null;
            current = root;
            
            while (current != null) {
                if (current.left == null) {
                    // Visit current
                    if (prev != null && prev.equals(current.val)) {
                        currFreq++;
                    } else {
                        currFreq = 1;
                    }
                    if (currFreq == maxFreq) {
                        if (modes.isEmpty() || modes.get(modes.size() - 1) != current.val) {
                            modes.add(current.val);
                        }
                    }
                    prev = current.val;
                    
                    current = current.right;
                } else {
                    TreeNode predecessor = current.left;
                    while (predecessor.right != null && predecessor.right != current) {
                        predecessor = predecessor.right;
                    }
                    
                    if (predecessor.right == null) {
                        predecessor.right = current;
                        current = current.left;
                    } else {
                        predecessor.right = null;
                        
                        // Visit current
                        if (prev != null && prev.equals(current.val)) {
                            currFreq++;
                        } else {
                            currFreq = 1;
                        }
                        if (currFreq == maxFreq) {
                            if (modes.isEmpty() || modes.get(modes.size() - 1) != current.val) {
                                modes.add(current.val);
                            }
                        }
                        prev = current.val;
                        
                        current = current.right;
                    }
                }
            }
            
            return modes.stream().mapToInt(i -> i).toArray();
        }
    }
}
