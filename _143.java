import java.util.ArrayList;
import java.util.List;

import helper.ListNode;

/**
 * 143. Reorder List
 */
public class _143 {
    // Time: O(n)
    // Space: O(n)
    class Solution1_List {
        public void reorderList(ListNode head) {
            if (head == null || head.next == null) return;
            
            // Store nodes in list
            List<ListNode> nodes = new ArrayList<>();
            ListNode curr = head;
            while (curr != null) {
                nodes.add(curr);
                curr = curr.next;
            }
            
            // Reorder using two pointers
            int left = 0, right = nodes.size() - 1;
            while (left < right) {
                nodes.get(left).next = nodes.get(right);
                left++;
                
                if (left >= right) break;
                
                nodes.get(right).next = nodes.get(left);
                right--;
            }
            
            nodes.get(left).next = null;
        }
    }
    
    // 1 - find middle ListNode
    // 2 - reverse 2nd half
    // 3 - merge 2 list
    // Time: O(n)
    // Space: O(1)
    class Solution2_Reverse_Merge_Two_LinkedList {
        public void reorderList(ListNode head) {
            // find middle node
            ListNode slow = head, fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            ListNode mid = slow;

            // reverse 2nd half
            ListNode prev = null, curr = mid.next;
            while (curr != null) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            mid.next = null; // disconnect the halves

            // merge two list
            ListNode head1 = head, head2 = prev;
            while (head1 != null && head2 != null) {
                ListNode next1 = head1.next;
                ListNode next2 = head2.next;
                head1.next = head2;
                head2.next = next1;
                head1 = next1;
                head2 = next2;
            }
        }
    }
}
