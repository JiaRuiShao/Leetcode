import java.util.ArrayDeque;
import java.util.Deque;

import helper.TreeNode;

/**
 * 230. Kth Smallest Element in a BST
 * Clarification:
 * - assume k is valid (1 <= k <= n)
 * - assume node val are integer
 * Followup:
 * - BST is modified frequently with inserts/deletes, and you need to find kth smallest multiple times?
 *   + Augmented BST with size info
 *   + Balanced BST (TreeMap)
 * - Solve in O(1) space
 *   + Morris Traversal
 * - Find kth largest instead
 *   + in-order traversal, go to right subtree then left
 */
public class _230 {
    // Time: O(h+k)
    // Space: O(h)
    static class Solution1_InOrderTraversal {
        private int rank, kthSmallest;
        public int kthSmallest(TreeNode root, int k) {
            rank = k;
            kthSmallest = -1;
            traverse(root);
            return kthSmallest;
        }

        private void traverse(TreeNode root) {
            if (root == null || kthSmallest != -1) return;
            traverse(root.left);
            if (--rank == 0) {
                kthSmallest = root.val;
            }
            traverse(root.right);
        }
    }

    static class Solution2_Iterative {
        public int kthSmallest(TreeNode root, int k) {
            Deque<TreeNode> stk = new ArrayDeque<>();
            TreeNode curr = root;
            while (curr != null || !stk.isEmpty()) {
                while (curr != null) {
                    stk.push(curr);
                    curr = curr.left;
                }
                curr = stk.pop();
                if (--k == 0) return curr.val;
                curr = curr.right;
            }
            return -1; // never reach
        }
    }

    static class BetterNode {
        int val, leftSize;
        BetterNode left, right;
        public BetterNode(int val) {
            this.val = val;
            this.leftSize = 0;
        }
    }

    // Time: O(h)
    // Space: O(h)
    static class Followup1_Recursive {
        private int rank, kthSmallest;
        public int kthSmallest(BetterNode root, int k) {
            rank = k;
            kthSmallest = -1;
            traverse(root);
            return kthSmallest;
        }

        private void traverse(BetterNode root) {
            if (root == null || kthSmallest != -1) return;
            if (rank <= root.leftSize) {
                traverse(root.left);
            } else if (rank == root.leftSize + 1) {
                kthSmallest = root.val;
            } else {
                rank -= (root.leftSize + 1);
                traverse(root.right);
            }
        }
    }

    // Time: O(h)
    // Space: O(1)
    static class Followup1_Iterative {
        public int kthSmallest(BetterNode root, int k) {
            while (root != null) {
                if (k <= root.leftSize) {
                    root = root.left;
                } else if (k == root.leftSize + 1) {
                    return root.val;
                } else {
                    k -= (root.leftSize + 1);
                    root = root.right;
                }
            }
            return -1;
        }
    }

    // Time: O(n)
    // Space: O(1)
    static class Followup2_Morris {
        public int kthSmallest(TreeNode root, int k) {
            TreeNode curr = root;
            while (curr != null) {
                if (curr.left == null) {
                    // Process current node
                    if (--k == 0) return curr.val;
                    curr = curr.right;
                } else { // has left child
                    // Find predecessor
                    TreeNode pred = curr.left;
                    while (pred.right != null && pred.right != curr) {
                        pred = pred.right;
                    }
                    
                    if (pred.right == null) {
                        // Create thread
                        pred.right = curr;
                        curr = curr.left;
                    } else {
                        // Remove thread and process
                        pred.right = null;
                        if (--k == 0) return curr.val;
                        curr = curr.right;
                    }
                }
            }
            return -1;
        }
    }
}
