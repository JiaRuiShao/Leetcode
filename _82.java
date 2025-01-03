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

	class Solution3_Iterative {
		/**
		 * Left pointer points the the right most unique node, right pointer uses as traversal pointer.
		 * @param head ListNode head
		 * @return head of the list node with no dup val nodes
		 */
		public ListNode deleteDuplicates(ListNode head) {
			int dummyVal = -101, prev = dummyVal;
			ListNode dummyHead = new ListNode(dummyVal, head);
			ListNode left = dummyHead, right = head;
			while (right != null) {
				int val = right.val;
				if (val != prev && (right.next != null && right.next.val != val || right.next == null)) {
					left.next = right;
					left = right;
				}
				prev = val;
				right = right.next;
			}
			left.next = null; // IMPORTANT: set next node of left pointer to null
			return dummyHead.next;
		}
	}
	
	class Solution4_Recursive {
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

	class Solution4 {
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
