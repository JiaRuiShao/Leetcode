import helper.ListNode;

/**
 * 143. Reorder List
 */
public class _143 {
    class Solution1_Reverse_Merge_Two_LinkedList {
        // 1 - find middle ListNode
        // 2 - reverse 2nd half
        // 3 - merge 2 list
        public void reorderList(ListNode head) {
            ListNode fast = head, slow = head;
            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            ListNode middle = slow;
            ListNode prev = null, curr = middle;
            while (curr != null) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            ListNode n1 = head, n2 = prev;
            while (n2 != null && n2.next != null) {
                ListNode tmp = n1.next;
                n1.next = n2;
                n1 = tmp;
                tmp = n2.next;
                n2.next = n1;
                n2 = tmp;
            }
        }
    }
}
