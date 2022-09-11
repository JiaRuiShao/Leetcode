import helper.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 142. Linked List Cycle II.
 * Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
 */
public class _142 {
    static class Solution1_Fast_Slow_Pointers {
        ListNode detectCycle(ListNode head) {
            ListNode fast, slow;
            fast = slow = head;
            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
                if (fast == slow) break; // first meet point
            }
            // there's no cycle
            if (fast == null || fast.next == null) {
                return null;
            }
            // reset slow pointer
            slow = head;
            // slow & fast pointer move forward at the same step, the next intersection point is the cycle start node
            while (slow != fast) {
                fast = fast.next;
                slow = slow.next;
            }
            return slow; // or fast
        }

        /**
         * This comment explains it really well for this solution:
         * https://leetcode.com/problems/linked-list-cycle-ii/discuss/44774/Java-O(1)-space-solution-with-detailed-explanation./44281
         *
         * When fast and slow meet for the first time at point P, fast travelled (a + b + c + b)
         * and slow travelled (a + b), and we know fast travels twice fast as slow, so we have:
         * a + b + c + b = 2*(a + b), this gives us a == c;
         * so at point P, we start a new slow2 pointer from the head, when both slow and slow2 travelled distance a, they must meet
         * at cycle entrance point Q.
         */
        public ListNode detectCycle2(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) {
                    ListNode slow2 = head;
                    while (slow2 != slow) {
                        slow = slow.next;
                        slow2 = slow2.next;
                    }
                    return slow;
                }
            }
            return null;
        }
    }

    public static class Solution2_HashTable {
        public ListNode detectCycle(ListNode head) {
            Set<ListNode> seen = new HashSet<>(); // store the list nodes we already traversed
            while (head != null) {
                if (!seen.add(head)) {
                    return head;
                }
                head = head.next;
            }
            return null;
        }
    }
}
