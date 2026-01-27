import helper.ListNode;

/**
 * 203. Remove Linked List Elements
 */
public class _203 {
    class Solution1_Iterative {
        public ListNode removeElements(ListNode head, int val) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode prev = dummy, curr = head; // prev points to last valid node
            while (curr != null) {
                if (curr.val == val) {
                    curr = curr.next;
                    prev.next = curr;
                } else {
                    prev = curr;
                    curr = curr.next;
                }
            }
            return dummy.next;
        }
    }

    class Solution_Recursive {
        public ListNode removeElements(ListNode head, int val) {
            if (head == null) {
                return null;
            }
            head.next = removeElements(head.next, val);
            if (head.val == val) {
                return head.next;
            }
            return head;
        }
    }
}
