import helper.ListNode;

/**
 * 82. Remove Duplicates from Sorted List II
 */
public class _82 {
	/**
	 * Traverse this list, if a unique number is found, we append as the next node of last (lastUnique); if not, we continue traversal
	 */
	class Solution1_Two_Pointers {
		public ListNode deleteDuplicates(ListNode head) {
			ListNode dh = new ListNode();
			dh.next = head;
			ListNode prev = null, curr = head, last = dh;
			while (curr != null) {
				if ((prev == null || prev.val != curr.val) && (curr.next == null || curr.next.val != curr.val)) {
					last.next = curr;
					last = curr;
				}
				prev = curr;
				curr = curr.next;
			}
			last.next = null; // **IMPORTANT**
			return dh.next;
		}
	}
	
	class Solution2_Two_Pointers {
		public ListNode deleteDuplicates(ListNode head) {
			ListNode dummy = new ListNode(101);
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

	class Solution4_My_Other_Solution {
		public ListNode deleteDuplicates(ListNode head) {
			ListNode dummy = new ListNode(-101, head);
			ListNode lastValid = dummy;
	
			while (lastValid != null) {
				lastValid.next = findNext(lastValid.next);
				lastValid = lastValid.next;
			}
	
			return dummy.next;
		}
	
		private ListNode findNext(ListNode curr) {
			if (curr == null) return curr;
			ListNode prev = curr;
			curr = curr.next;
			while (curr != null && curr.val == prev.val) {
				curr = curr.next;
			}
			if (prev.next == curr) return prev;
			return findNext(curr);
		}
	}
}
