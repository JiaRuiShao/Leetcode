import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import helper.TreeNode;

/**
 * 671. Second Minimum Node In a Binary Tree
 * 
 * Clarification:
 * - Are their duplicate values? Yes it's possible
 * 
 * Followup:
 * - Solve it iteratively
 * - Find Kth minimum instead
 * - Return all distinct values in sorted order
 */
public class _671 {
    // Time: O(n)
    // Space: O(h)
    class Solution1_Recursion {
        private Integer min, maxMin;

        public int findSecondMinimumValue(TreeNode root) {
            min = null;
            maxMin = root.val;
            traverse(root);
            return min == null ? -1 : min;
        }

        private void traverse(TreeNode root) {
            if (root == null) return;
            if (root.val != maxMin && (min == null || root.val < min)) {
                min = root.val;
            } 
            traverse(root.left);
            traverse(root.right);
        }
    }

    class Solution2_Recursion_Improved {
        public int findSecondMinimumValue(TreeNode root) {
            return traverse(root, root.val);
        }

        private int traverse(TreeNode root, int min) {
            if (root == null) return -1;
            if (root.val > min) {
                return root.val;
            }
            int left = traverse(root.left, min);
            int right = traverse(root.right, min);
            if (left != -1 && right != -1) {
                return Math.min(left, right);
            }
            return left != -1 ? left : right;
        }
    }

    // We can also do DFS
    // Time: O(n)
    // Space: O(w) where w is width of the tree
    class Solution3_IterativeBFS {
        public int findSecondMinimumValue(TreeNode root) {
            Queue<TreeNode> q = new ArrayDeque<>();
            q.offer(root);
            
            int min = root.val;
            Integer candidate = null;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    TreeNode curr = q.poll();
                    if (curr.val == min && curr.left != null) {
                        q.offer(curr.left);
                        q.offer(curr.right);
                    } else if (curr.val != min) {
                        if (candidate == null) candidate = curr.val;
                        else candidate = Math.min(candidate, curr.val);
                    }
                }
            }
            return candidate == null ? -1 : candidate;
        }
    }

    // Time: O(n)
    // Space: O(1)
    class Solution4_Morris {
        public int findSecondMinimumValue(TreeNode root) {
            if (root == null) return -1;
            
            int min = root.val;
            int secondMin = Integer.MAX_VALUE;
            boolean found = false;
            
            TreeNode current = root;
            
            while (current != null) {
                if (current.left == null) {
                    // Visit current (pre-order)
                    if (current.val > min && current.val < secondMin) {
                        secondMin = current.val;
                        found = true;
                    }
                    
                    current = current.right;
                } else {
                    // Find predecessor
                    TreeNode predecessor = current.left;
                    while (predecessor.right != null && predecessor.right != current) {
                        predecessor = predecessor.right;
                    }
                    
                    if (predecessor.right == null) {
                        // First visit - create thread and visit (pre-order)
                        if (current.val > min && current.val < secondMin) {
                            secondMin = current.val;
                            found = true;
                        }
                        
                        predecessor.right = current;
                        current = current.left;
                    } else {
                        // Second visit - remove thread
                        predecessor.right = null;
                        current = current.right;
                    }
                }
            }
            
            return found ? secondMin : -1;
        }
    }

    class Followup_KthMin {
        // Time: O(nlogk)
        // Space: O(n+k)
        public int findKthMinimumHeap(TreeNode root, int k) {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(
                Collections.reverseOrder()
            );
            Set<Integer> seen = new HashSet<>();
            
            collectWithHeap(root, maxHeap, seen, k);
            
            return maxHeap.size() == k ? maxHeap.peek() : -1;
        }

        private void collectWithHeap(TreeNode node, PriorityQueue<Integer> heap,
                                    Set<Integer> seen, int k) {
            if (node == null) return;
            
            if (!seen.contains(node.val)) {
                seen.add(node.val);
                heap.offer(node.val);
                
                if (heap.size() > k) {
                    heap.poll();  // Remove largest
                }
            }
            
            collectWithHeap(node.left, heap, seen, k);
            collectWithHeap(node.right, heap, seen, k);
        }
    }

    class Followup_SortedDistinctValues {
        public List<Integer> findAllDistinctValues(TreeNode root) {
            Set<Integer> values = new TreeSet<>();  // Auto-sorted
            collectValues(root, values);
            return new ArrayList<>(values);
        }

        private void collectValues(TreeNode node, Set<Integer> values) {
            if (node == null) return;
            values.add(node.val);
            collectValues(node.left, values);
            collectValues(node.right, values);
        }
    }
}
