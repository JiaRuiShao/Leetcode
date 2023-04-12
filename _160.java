import helper.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 160. Intersection of Two Linked Lists
 */
public class _160 {
    public static class Solution1 {
        /**
         * Time: O(max(m, n))
         * Space: O(1)
         */
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            int lenA = findLen(headA);
            int lenB = findLen(headB);
            /**align headA and headB to the same starting point and then move together until we find the intersection point*/
            while (lenA < lenB) {
                headB = headB.next;
                lenB--;
            }

            while (lenB < lenA) {
                headA = headA.next;
                lenA--;
            }

            while (headA != headB) {
                headA = headA.next;
                headB = headB.next;
            }

            return headA;
        }

        private int findLen(ListNode head) {
            int len = 0;
            while (head != null) {
                head = head.next;
                len++;
            }
            return len;
        }

    }

    public static class Solution2 {
        /**
         * Most optimal solution:
         * O(m+n) time
         * O(1) space
         */
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            ListNode a = headA;
            ListNode b = headB;

            // if a and b don't intersect, then the loop will be terminated after second iteration
            // (1st iteration if they have the same length) when a == b == null
            while (a != b) {
                // for the first iteration, reset the pointer to the head of another linked-list
                a = a == null ? headB : a.next;
                b = b == null ? headA : b.next;
            }
            return a;
        }
    
        public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
            ListNode n1 = headA, n2 = headB;
            while (n1 != null && n2 != null) {
                if (n1 == n2) {
                    return n1;
                } else if (n1.next == null && n2.next == null) {
                    break;
                }
            
                n1 = n1.next == null ? headB : n1.next;
                n2 = n2.next == null ? headA : n2.next;
            }
            return null;
        }
    }

    public static class Solution3 {
        /**
         * O(m+n) time
         * O(Math.max(m, n)) space
         */
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            Set<ListNode> set = new HashSet<>();
            while (headA != null) {
                set.add(headA);
                headA = headA.next;
            }

            while (headB != null) {
                if (set.contains(headB)) {
                    return headB;
                }
                headB = headB.next;
            }
            return null;
        }
    }
}
