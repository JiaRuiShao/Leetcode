import helper.ListNode;

/**
 * 2. Add Two Numbers
 * 
 * Clarification:
 * - Digits stored in reverse order? Yes
 * - Non-negative number? Yes
 * - Single digit per node? Yes
 * - Return result in reverse order as well? Yes
 * - No leading zeros in given list node? Yes
 * 
 * Followup:
 * - Digits are stored in normal order and not reversed? LC 445 Reverse list or convert to arraylist/stack first
 */
public class _2 {
    // Time: O(max(m, n))
    // Space: O(1)
    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
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
}
