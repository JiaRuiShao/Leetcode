import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import helper.TreeNode;

/**
 * 653. Two Sum IV - Input is a BST
 */
public class _653 {
    class Solution1_Set {
        public boolean findTarget(TreeNode root, int k) {
            Set<Integer> seen = new HashSet<>();
            return traverse(root, seen, k);
        }

        private boolean traverse(TreeNode root, Set<Integer> seen, int k) {
            if (root == null) return false;
            if (seen.contains(k - root.val)) return true;
            seen.add(root.val);
            return traverse(root.left, seen, k) || traverse(root.right, seen, k);
        }
    }

    class Solution2_SortedArray {
        public boolean findTarget(TreeNode root, int k) {
            // in-order traversal to get the sorted list
            ArrayList<Integer> arr = traverse(root);
            // two pointers technique
            int i = 0, j = arr.size() - 1;
            while (i < j) {
                int sum = arr.get(i) + arr.get(j);
                if (sum < k) {
                    i++;
                } else if (sum > k) {
                    j--;
                } else {
                    return true;
                }
            }
            return false;
        }

        private ArrayList<Integer> traverse(TreeNode root) {
            ArrayList<Integer> res = new ArrayList<>();
            if (root == null) {
                return res;
            }
            res.addAll(traverse(root.left));
            res.add(root.val);
            res.addAll(traverse(root.right));
            return res;
        }
    }

    
    // Time: O(n) - each node visited at most once per iterator
    // Space: O(h) - two stacks for tree height
    // BST-specific: O(h) space instead of O(n)
    class Solution3_BSTIterator {
        public boolean findTarget(TreeNode root, int k) {
            if (root == null) return false;
            
            BSTIterator left = new BSTIterator(root, true);   // Forward (ascending)
            BSTIterator right = new BSTIterator(root, false); // Backward (descending)
            
            int l = left.next();
            int r = right.next();
            
            while (l < r) {
                int sum = l + r;
                
                if (sum == k) {
                    return true;
                } else if (sum < k) {
                    l = left.next();
                } else {
                    r = right.next();
                }
            }
            
            return false;
        }

        class BSTIterator {
            Stack<TreeNode> stack;
            boolean forward;
            
            public BSTIterator(TreeNode root, boolean forward) {
                this.stack = new Stack<>();
                this.forward = forward;
                push(root);
            }
            
            public int next() {
                TreeNode node = stack.pop();
                push(forward ? node.right : node.left);
                return node.val;
            }
            
            private void push(TreeNode node) {
                while (node != null) {
                    stack.push(node);
                    node = forward ? node.left : node.right;
                }
            }
        }
    }
}
