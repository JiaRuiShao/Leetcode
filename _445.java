import java.util.ArrayDeque;
import java.util.Deque;
import helper.ListNode;

/**
 * 445. Add Two Numbers II
 */
public class _445 {
    // Time: O(m+n)
    // Space: O(1)
    class Solution1_Reverse {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            return reverse(add(reverse(l1), reverse(l2)));
        }

        private ListNode reverse(ListNode root) {
            ListNode prev = null, curr = root;
            while (curr != null) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            return prev;
        }

        private ListNode add(ListNode l1, ListNode l2) {
            ListNode dummy = new ListNode(-1), prev = dummy;
            int carry = 0;
            while (l1 != null || l2 != null || carry > 0) {
                int sum = carry;
                if (l1 != null) {
                    sum += l1.val;
                    l1 = l1.next;
                }
                if (l2 != null) {
                    sum += l2.val;
                    l2 = l2.next;
                }
                prev.next = new ListNode(sum % 10);
                prev = prev.next;
                carry = sum / 10;
            }
            return dummy.next;
        }
    }

    // Time: O(m + n)
    // Space: O(m + n)
    class SOlution2_Stack {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            Deque<Integer> s1 = new ArrayDeque<>();
            Deque<Integer> s2 = new ArrayDeque<>();
            
            while (l1 != null) {
                s1.push(l1.val);
                l1 = l1.next;
            }
            while (l2 != null) {
                s2.push(l2.val);
                l2 = l2.next;
            }
            
            ListNode head = null;
            int carry = 0;
            while (!s1.isEmpty() || !s2.isEmpty() || carry > 0) {
                int sum = carry + (s1.isEmpty() ? 0 : s1.pop()) + (s2.isEmpty() ? 0 : s2.pop());
                carry = sum / 10;
                
                // build result in reverse (prepend)
                ListNode node = new ListNode(sum % 10);
                node.next = head;
                head = node;
            }
            
            return head;
        }
    }
}
