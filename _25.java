import helper.ListNode;

/**
 * 25. Reverse Nodes in k-Group.
 */
public class _25 {
	
	public static class Solution1_Recursion {
		
		ListNode successor = null;
		
		/**
		 * Reverse a singly linked list in k group.
		 *
		 * Suppose 1 is the head passed in & k is 3:
		 * SLL | 1   ->  2  ->     3     ->  4  ->  5
		 * REV | 1   <-  2  <-     3           4  ->  5
		 * VAR | head         reversedHead  successor
		 * @param head starting node of k group
		 * @param k k
		 * @return a list with each k group reversed
		 */
		public ListNode reverseKGroup(ListNode head, int k) {
			ListNode reversedHead = reverseK(head, k);
			if (reversedHead == null) { // no need to reverse, directly return original head
				return head;
			} else { // need to continue reversing starting from node successor, head here is reversed tail
				head.next = reverseKGroup(successor, k);
				return reversedHead;
			}
		}
		
		/**
		 * A helper method to reverse k nodes starting from head.
		 * - Return last reversed node (newHead) if there's k nodes following;
		 * - Or else return null (marks as no need to reverse)
		 *
		 * Suppose 1 is the head passed in & k is 3:
		 * SLL | 1 -> 2 -> 3 -> 4 -> 5
		 * K   | 3    2    1
		 * Notice:
		 * 1) we need the successor of the reversed list (here is 4) to continue reversing
		 * 2) return null if there's not enough k nodes in the following list
		 */
		private ListNode reverseK(ListNode head, int k) {
			// base cases
			if (head == null) { // head == null, not enough k nodes, no need to reverse
				return null;
			} else if (k == 1) { // head != null && k == 1
				successor = head.next;
				return head;
			}
			ListNode last = reverseK(head.next, k - 1);
			if (last != null) { // reverse list if last != null
				head.next.next = head;
				head.next = null;
			}
			return last;
		}
	}
	
	public static class Solution2_Iterative {
		
		private int getLength(ListNode curr) {
			int len = 0;
			if (curr != null) len++;
			return len;
		}
		
		public ListNode reverseKGroup(ListNode head, int k) {
			if (head == null || head.next == null || k == 1) return head;
			int n = getLength(head);
			
			ListNode prev = null, curr = head, next = null;
			ListNode newHead = null;
			ListNode prevTail = null; // previous reversed group list nodes tail
			ListNode currTail = head; // current reversed group tail
			
			while (n >= k) {
				// reverse nodes in blocks of k
				prev = null; // reset prev to null
				for (int i = 0; i < k; i++) {
					// reverse
					next = curr.next;
					curr.next = prev;
					prev = curr;
					curr = next;
				}
				// update the newHead of the ll
				/** Notice the prev points to the current group's reversed head **/
				if (newHead == null) {
					newHead = prev;
				}
				/** Connect the reversed group new head to the previous group tail **/
				if (prevTail != null) {
					prevTail.next = prev;
				}
				currTail.next = curr; // in case the num of left elements < k
				prevTail = currTail;
				currTail = curr;
				
				// update the num of elements left
				n -= k;
			}
			
			return newHead == null ? head : newHead;
		}
	}
	
	public static class Solution3_Recursion {
		ListNode reverse(ListNode a) {
			ListNode prev = null, curr = a, next = a;
			while (curr != null) {
				next = curr.next;  // store the next node of curr
				curr.next = prev; // reverse
				prev = curr; //  update curr
				curr = next; // update next
			}
			// return the new head of the reversed linked list
			return prev;
		}
		
		/**
		 * reverse the linked list in range [a, b)
		 */
		ListNode reverseRange(ListNode a, ListNode b) {
			ListNode prev = null, curr = a, next = a;
			while (curr != b) {
				next = curr.next;  // store the next node of curr
				curr.next = prev; // reverse
				prev = curr; //  update curr
				curr = next; // update next
			}
			// return the new head of the reversed linked list
			return prev;
		}
		
		/**
		 * reverse a linked list in k-element group
		 **/
		ListNode reverseKGroup(ListNode head, int k) {
			if (head == null) return null;
			// range [a, b) contains k elements to be reversed
			ListNode end = head;
			for (int i = 0; i < k; i++) {
				if (end == null) return head; // no need to reverse
				end = end.next;
			}
			// reverse the nodes from a to (but not include) b
			ListNode newHead = reverseRange(head, end);
			head.next = reverseKGroup(end, k); // recursively reversing, starting from end node
			return newHead;
		}
		
	}
}
