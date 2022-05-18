import helper.ListNode;

public class _206 {
    class Solution1 {

        ListNode newHead = null;

        private ListNode reverse(ListNode curr) {
            if (curr == null) return null;
            ListNode prev = reverse(curr.next);
            curr.next = null;
            if (prev == null) newHead = curr;
            else prev.next = curr;
            return curr;
        }

        public ListNode reverseList(ListNode head) {
            reverse(head);
            return newHead;
        }
    }

    class Solution2 {
        public ListNode reverseList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode last = reverseList(head.next); // the new head
            head.next.next = head; // concise reverse
            head.next = null;
            return last; // return the new head
        }
    }

    class Solution3 {
        public ListNode reverseList(ListNode head) {
            ListNode prev = null, curr = head, next;
            while(curr != null) {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            return prev;
        }
    }
}
