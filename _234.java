import helper.ListNode;

import java.util.LinkedList;
import java.util.List;

/**
 * 234. Palindrome Linked List.
 */
public class _234 {
    static class Solution1_Iterative_Stack {
        /**
         * Check if a LinkedList is a palindrome.
         * Time: O(3n/2) = O(n)
         * Space: O(n)
         *
         * @param head the head node of the given singly linked list
         * @return true if the given linked list is a palindrome; false if not
         */
        public boolean isPalindrome(ListNode head) {
            List<Integer> stack = new LinkedList<>(); // acts as a stack
            ListNode curr = head;
            int n = 0;
            while (curr != null) {
                stack.add(curr.val);
                curr = curr.next;
                n++;
            }

            curr = head;
            for (int i = 0; i < n/2; i++) {
                if (stack.remove(stack.size() - 1) != curr.val) {
                    return false;
                }
                curr = curr.next;
            }
            return true;
        }
    }

    static class Solution2_Recursive_Two_Pointers {
        ListNode left;

        /**
         * Post-order traversal, use call stack as a stack.
         *
         * @param curr current list node
         * @return true if the given linked list is a palindrome; false if not
         */
        private boolean traverse(ListNode right) {
            if (right == null || left == null) return true;
            boolean res = traverse(right.next);
            // post-order traversal
            res = res && right.val == left.val;
            left = left.next;
            return res;
        }

        public boolean isPalindrome(ListNode head) {
            this.left = head;
            return traverse(head);
        }
    }

    static class Solution3_Iterative_Fast_Slow_Pointers {
        private ListNode reverse(ListNode head) {
            ListNode prev = null, curr = head, next = head;
            while (curr != null) {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            return prev;
        }

        public boolean isPalindrome(ListNode head) {
            ListNode slow = head, fast = head;
            // find the middle node
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }

            if (fast != null) slow = slow.next; // make sure slow points to 1st node in the right half

            ListNode left = head;
            ListNode right = reverse(slow);
            while (right != null) {
                if (left.val != right.val)
                    return false;
                left = left.next;
                right = right.next;
            }

            return true;
        }
    }
}
