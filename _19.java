import helper.ListNode;

/**
 * 19. Remove Nth Node From End of List
 */
public class _19 {
    // Convert this question to remove first (n-k+1)th node from top
    // Time: O(L)
    // Space: O(1)
    class Solution0_TwoPass {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;

            // First pass: count length
            int length = 0;
            ListNode curr = head;
            while (curr != null) {
                length++;
                curr = curr.next;
            }
            
            // Second pass: find (length - n)th node
            curr = dummy;
            for (int i = 0; i < length - n; i++) {
                curr = curr.next;
            }
            
            // Remove the nth node from end
            curr.next = curr.next.next;
            return dummy.next;
        }
    }

    // Time: O(L)
    // Space: O(1)
    class Solution1_Two_Pointers {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode slow = dummy, fast = dummy;
            // move fast pointer forward n+1 steps
            // here n <= head.length so no NPE
            for (int i = 0; i <= n; i++) {
                fast = fast.next;
            }
            // move both forward until fast is null
            while (fast != null) {
                slow = slow.next;
                fast = fast.next;
            }
            // slow points at the prev node of node to delete
            slow.next = slow.next.next;
            
            return dummy.next;
        }
    }

    // Time: O(L)
    // Space: O(L)
    class Solution2_Recursion {
        private int n;
        public ListNode removeNthFromEnd(ListNode head, int n) {
            this.n = n;
            return removeNthFromEnd(head);
        }

        private ListNode removeNthFromEnd(ListNode head) {
            if (head == null) {
                return null;
            }

            head.next = removeNthFromEnd(head.next);
            if (--n == 0) { // this is the node to remove
                return head.next;
            }
            return head;
        }
    }
}
