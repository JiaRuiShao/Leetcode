import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helper.TreeNode;

/**
 * 1038. Binary Search Tree to Greater Sum Tree
 * Clarification:
 * - modify in-place or return a new tree?
 * - assume max node val for greater sum tree is within integer range?
 * Followup:
 * - Solve this iteratively
 * - Solve with O(1) space complexity: Morris Traversal
 * - What if the tree is not a BST
 * - Modify the code to create a Lesser Sum Tree instead
 */
public class _1038 {
    class Solution1_Recursive {
        private int sum = 0;

        private void traverse(TreeNode root) {
            // base case
            if (root == null) return;
            // go right first
            traverse(root.right); 
            // in-order update
            root.val += sum;
            sum = root.val;
            // go left
            traverse(root.left);
        }

        public TreeNode bstToGst(TreeNode root) {
            traverse(root);
            return root;
        }
    }

    class Followup1_Iterative {
        // right -> root -> left
        public TreeNode bstToGst(TreeNode root) {
            int sum = 0;
            TreeNode curr = root;
            Deque<TreeNode> stk = new ArrayDeque<>();
            while (curr != null || !stk.isEmpty()) {
                // go to the rightmost node
                while (curr != null) {
                    stk.push(curr);
                    curr = curr.right;
                }
                // process node
                curr = stk.pop();
                curr.val += sum;
                sum = curr.val;
                // go to the left
                curr = curr.left;
            }
            return root;
        }
    }

    class Followup2_Morris_Traversal_Optional {
        // use tree's null pointers to create temporary threads that eliminate the need for a stack
        public TreeNode bstToGst(TreeNode root) {
            TreeNode curr = root;
            int sum = 0;
            
            while (curr != null) {
                if (curr.right == null) {
                    // No right subtree, process current
                    sum += curr.val;
                    curr.val = sum;
                    curr = curr.left;
                } else {
                    // Find leftmost node in right subtree (successor)
                    TreeNode successor = curr.right;
                    while (successor.left != null && successor.left != curr) {
                        successor = successor.left;
                    }
                    
                    if (successor.left == null) {
                        // Create thread
                        successor.left = curr;
                        curr = curr.right;  // Explore right first
                    } else {
                        // Thread exists, remove and process
                        successor.left = null;
                        sum += curr.val;
                        curr.val = sum;
                        curr = curr.left;
                    }
                }
            }
            
            return root;
        }
    }

    class Followup3_GeneralBT {
        // two pass solution: get all values, sort and update the tree
        public TreeNode bstToGst(TreeNode root) {
            List<Integer> keys = new ArrayList<>();
            getKeys(root, keys);
            Collections.sort(keys, Comparator.reverseOrder());
            // list.sort((a, b) -> Integer.compare(b, a));
            Map<Integer, Integer> keysToSum = new HashMap<>();
            int sum = 0;
            for (int key : keys) {
                sum += key;
                keysToSum.put(key, sum);
            }
            updateVals(root, keysToSum);
            return root;
        }

        private void getKeys(TreeNode root, List<Integer> list) {
            if (root == null) return;
            list.add(root.val);
            getKeys(root.left, list);
            getKeys(root.right, list);
        }

        private void updateVals(TreeNode root, Map<Integer, Integer> map) {
            if (root == null) return;
            root.val = map.get(root.val);
            updateVals(root.left, map);
            updateVals(root.right, map);
        }
    }

    class Followup4_LesserSumTree {
        // exact like the original solution, just need to go traverse and update the left subtree first; then in order update; finally right subtree
    }
}
