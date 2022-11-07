import helper.ListNode;

/**
 * 82. Remove Duplicates from Sorted List II.
 */
public class _82 {
	
	class Solution1_Iterative {
		public ListNode deleteDuplicates(ListNode head) {
			ListNode dummy = new ListNode(-1);
			ListNode left = dummy, right = head;
			while (right != null) {
				if (right.next != null && right.val == right.next.val) { // dup
					while (right.next != null && right.val == right.next.val) {
						right = right.next;
					}
					right = right.next;
				} else { // non-dup
					left.next = right;
					left = left.next;
					right = right.next;
				}
			}
			left.next = null;
			return dummy.next;
		}
	}
	
	class Solution2_Iterative {
		public ListNode deleteDuplicates(ListNode head) {
			ListNode dummy = new ListNode(-1, head);
			ListNode left = dummy, right = head;
			while (right != null) {
				while (right.next != null && right.val == right.next.val) { // dup
					right = right.next;
				}
				if (left.next == right) {
					left = left.next;
				} else {
					left.next = right.next;
				}
				right = right.next;
			}
			return dummy.next;
		}
	}
	
	class Solution3_Recursive {
		public ListNode deleteDuplicates(ListNode head) {
			if (head == null || head.next == null) {
				return head;
			}
			if (head.val != head.next.val) {
				head.next = deleteDuplicates(head.next);
				return head;
			} else {
				while (head.next != null && head.val == head.next.val) {
					head = head.next;
				}
				return deleteDuplicates(head.next);
			}
		}
	}
}
