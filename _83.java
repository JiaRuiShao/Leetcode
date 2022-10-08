import helper.ListNode;

/**
 * 83. Remove Duplicates from Sorted List.
 */
public class _83 {
    class Solution {
        public ListNode deleteDuplicates(ListNode head) {
            if (head == null) return null;
            ListNode slow = head, fast = head.next;
            while (fast != null) {
                if (fast.val != slow.val) {
                    slow.next = fast;
                    slow = slow.next;
                }
                fast = fast.next;
            }
            slow.next = null; // disconnect the duplicate nodes
            return head;
        }
    }
}
