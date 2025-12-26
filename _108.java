import java.util.ArrayDeque;
import java.util.Queue;

import helper.TreeNode;

/**
 * 108. Convert Sorted Array to Binary Search Tree
 * 
 * Followup:
 * - Solve it iteratively
 * - What if it's a singly linked list instead of an array? LC 109 use slow/fast pointers or convert to array first
 * - Can you generate all possible height-balanced BSTs? LC 95
 * - Can you solve with O(1) extra space? No MOrris traversal doesn't apply here
 */
public class _108 {
    // Time: O(N)
    // Space: O(H) = O(logN)
    static class Solution1_Recursion {
        private TreeNode buildBST(int[] nums, int lo, int hi) {
            if (lo > hi) return null;
            int mid = lo + (hi - lo) / 2;
            TreeNode root = new TreeNode(nums[mid]);
            root.left = buildBST(nums, lo, mid - 1);
            root.right = buildBST(nums, mid + 1, hi);
            return root;
        }

        public TreeNode sortedArrayToBST(int[] nums) {
            return buildBST(nums, 0, nums.length - 1);
        }
    }

    // Time: O(N)
    // Space: O(W) = O(n)
    class Followup_IterativeBFS {
        static class Node {
            TreeNode node;
            int lo, hi;
            public Node(TreeNode node, int lo, int hi) {
                this.node = node;
                this.lo = lo;
                this.hi = hi;
            }
        }

        public TreeNode sortedArrayToBST(int[] nums) {
            TreeNode root = new TreeNode(0);
            Queue<Node> q = new ArrayDeque<>();
            q.offer(new Node(root, 0, nums.length - 1));

            while (!q.isEmpty()) {
                Node curr = q.poll();
                int mid = curr.lo + (curr.hi - curr.lo) / 2;
                curr.node.val = nums[mid];
                if (curr.lo < mid) { // there's left subtree value exist in nums
                    TreeNode left = new TreeNode(0);
                    q.offer(new Node(left, curr.lo, mid - 1));
                    curr.node.left = left;
                }
                if (curr.hi > mid) { // there's right subtree value exist in nums
                    TreeNode right = new TreeNode(0);
                    q.offer(new Node(right, mid + 1, curr.hi));
                    curr.node.right = right;
                }
            }
            return root;
        }
    }
}
