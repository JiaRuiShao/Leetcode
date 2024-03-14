import helper.ListNode;

/**
 * 83. Remove Duplicates from Sorted List.
 */
public class _83 {
    class Solution {
        public ListNode deleteDuplicates(ListNode head) {
            ListNode left = head, right = head;
            while (right != null) {
                if (right.val != left.val) {
                    left.next = right;
                    left = left.next;
                }
                right = right.next;
            }
            if (left != null) left.next = null;
            return head;
        }
    }
}
