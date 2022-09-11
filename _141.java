import helper.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 141. Linked List Cycle.
 */
public class _141 {

    public static class Solution1_HashTable {
        /**
         * Time: O(n)
         * Space: O(n)
         * @param head
         * @return
         */
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

    public class Solution2_Fast_Slow_Pointers {
        /**
         * Floyd's Algorithm.
         * Time: O(n+k) = O(n) where k is the cycle length if it exists
         * Space: O(1)
         * @param head
         * @return
         */
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
