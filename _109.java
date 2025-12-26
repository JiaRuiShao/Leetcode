import java.util.ArrayList;
import java.util.List;

import helper.DoublyListNode;
import helper.ListNode;
import helper.TreeNode;

/**
 * 109. Convert Sorted List to Binary Search Tree
 * 
 * - S1: convert list to array then choose mid node as the root, see in LC 108, O(n), O(n)
 * - S2: use fast/slow pointers for SLL or normal two pointers for DLL to find mid val, O(nlogn), O(logn)
 * - S3: in-order simulation, use range indices to guarantee height balance & head pointer to get node val, O(n), O(logn) 
 * 
 * Clarification:
 * - Is it singly linked list or doubly linked list? Usually single
 * - No cycle in list? Yes
 */
public class _109 {
    // Time: O(n)
    // Space: O(n)
    class Solution1_ConvertToArray {
        public TreeNode sortedListToBST(ListNode head) {
            List<Integer> values = new ArrayList<>();
            while (head != null) {
                values.add(head.val);
                head = head.next;
            }
            return buildBST(values, 0, values.size() - 1);
        }

        private TreeNode buildBST(List<Integer> vals, int left, int right) {
            if (left > right) return null;
            int mid = left + (right - left) / 2;
            TreeNode root = new TreeNode(vals.get(mid));
            root.left = buildBST(vals, left, mid - 1);
            root.right = buildBST(vals, mid + 1, right);
            return root;
        }
    }

    // Time: O(nlogn)
    // Space: O(h) = O(logn)
    class Solution2_FastSlowPointers {
        public TreeNode sortedListToBST(ListNode head) {
            return buildBST(head, null); // [head, tail)
        }

        private TreeNode buildBST(ListNode head, ListNode tail) {
            if (head == tail) return null; // empty range
            
            // Find middle using slow/fast pointers
            ListNode slow = head, fast = head;
            while (fast != tail && fast.next != tail) {
                slow = slow.next;
                fast = fast.next.next;
            }
            
            TreeNode root = new TreeNode(slow.val);
            root.left = buildBST(head, slow);
            root.right = buildBST(slow.next, tail);
            return root;
        }
    }

    // Below is the optimal solution, it simulates inorder traversal, build left, process curr and then build right subtree
    // Time: O(n)
    // Space: O(logn)
    class Solution3_InOrderTraversal {
        ListNode curr;
        public TreeNode sortedListToBST(ListNode head) {
            curr = head;
            int len = 0;
            for (ListNode p = head; p != null; p = p.next) {
                len++;
            }
            return buildBST(0, len - 1);
        }

        private TreeNode buildBST(int lo, int hi) {
            if (lo > hi) return null;
            int mid = lo + (hi - lo) / 2;
            TreeNode left = buildBST(lo, mid - 1);
            TreeNode root = new TreeNode(curr.val);
            curr = curr.next;
            TreeNode right = buildBST(mid + 1, hi);
            root.left = left;
            root.right = right;
            return root;
        }
    }

    // Same overall logic as the SLL with the same time complexity as well
    class Followup_DDL {
        public TreeNode sortedListToBST(DoublyListNode head) {
            if (head == null) return null;
            
            DoublyListNode tail = head;
            while (tail.next != null) tail = tail.next;
            
            return buildBST(head, tail);
        }

        private TreeNode buildBST(DoublyListNode left, DoublyListNode right) {
            if (left == null || right == null || left.val > right.val) return null;
            if (left == right) return new TreeNode(left.val);
            
            // Find middle from both ends
            DoublyListNode l = left, r = right;
            while (l != r && l.next != r) {
                l = l.next;
                r = r.prev;
            }
            DoublyListNode mid = l;
            
            TreeNode root = new TreeNode(mid.val);
            root.left = buildBST(left, mid.prev);
            root.right = buildBST(mid.next, right);
            return root;
        }
    }
}