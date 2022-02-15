import helper.ListNode;

import java.util.HashSet;
import java.util.Set;

public class _141 {

    public static class Solution1 {
        public boolean hasCycle(ListNode head) {
            Set<ListNode> set = new HashSet();
            while (head != null) {
                if (!set.add(head)) {
                    return true;
                }
                head = head.next;
            }
            return false;
        }
    }

    public class Solution2 {
        public boolean hasCycle(ListNode head) {
            // fast & slow pointer
            ListNode slow = head, fast = head;
            // terminate when the fast pointer reach the end of the linked list
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                // there's a cycle when the fast & slow pointer meet
                if (slow == fast) {
                    return true;
                }
            }
            // no cycle
            return false;
        }
    }
}
