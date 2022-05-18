import helper.ListNode;

public class _83 {
    class Solution {
        public ListNode deleteDuplicates(ListNode head) {
            if (head == null) return null;
            ListNode n1 = head, n2 = head.next;
            while (n2 != null) {
                if (n2.val != n1.val) {
                    n1 = n1.next;
                    if (n1 != n2) n1.val = n2.val;
                }
                n2 = n2.next;
            }
            n1.next = null; // disconnect the duplicate nodes
            return head;
        }
    }
}
