import helper.ListNode;

/**
 * 206. Reverse Linked List.
 */
public class _206 {
	public static void main(String[] args) {
		Solution1 solution = new Solution1();
		// Test case 1: Reverse first 3 nodes
		ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
		int n1 = 3;
		ListNode newHead1 = solution.reverseFirstNNodes(head1, n1);
		System.out.println("Reversed List: " + listToString(newHead1));

		// Test case 2: Reverse entire list
		ListNode head2 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
		int n2 = 5;
		ListNode newHead2 = solution.reverseFirstNNodes(head2, n2);
		System.out.println("Reversed List: " + listToString(newHead2));

		// Test case 3: Reverse first 1 node
		ListNode head3 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
		int n3 = 1;
		ListNode newHead3 = solution.reverseFirstNNodes(head3, n3);
		System.out.println("Reversed List: " + listToString(newHead3));

		// Test case 4: Reverse first 0 node (empty list)
		ListNode head4 = null;
		int n4 = 0;
		ListNode newHead4 = solution.reverseFirstNNodes(head4, n4);
		System.out.println("Reversed List: " + listToString(newHead4));
		
		// Test case 5: Reverse first 100 node
		ListNode head5 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
		int n5 = 100;
		ListNode newHead5 = solution.reverseFirstNNodes(head5, n5);
		System.out.println("Reversed List: " + listToString(newHead5));
	}
	
	static class Solution1 {
		
		ListNode newHead;
		
		public ListNode reverseList(ListNode head) {
			reverse(head);
			return newHead == null ? head : newHead;
		}
		
		private ListNode reverse(ListNode head) {
			if (head == null) {
				return null;
			}
			ListNode prev = reverse(head.next); // reverse the list starting from curr node.next, and return the next node of curr node in the original list, or the parent node of the reversed list
			if (prev == null) { // head is the last node of the original list
				newHead = head; // update the newHead, no need to update prev.next
			} else {
				prev.next = head;
				head.next = null;
			}
			return head; // return the node itself as the parent node of the next node
		}
		
		/**
		 * Reverse the first n nodes of a given singly linked-list.
		 *
		 * @param head head node of a list
		 * @param n    nth node
		 * @return the head of the reversed list
		 */
		public ListNode reverseFirstNNodes(ListNode head, int n) {
			if (head == null || n <= 0) {
				return head;
			}
			head.next = reverseN(head, n - 1);
			return newHead;
		}
		
		/**
		 * Notice here we can choose to return either the new head or the newHead.next (successor).
		 * @param head
		 * @param n
		 * @return
		 */
		private ListNode reverseN(ListNode head, int n) {
			if (n < 0 || head == null) {
				return null;
			}
			ListNode successor = reverseN(head.next, n - 1);
			if (n == 0) {
				newHead = head;
				return head.next;
			} else if (head.next != null) { // && n > 0
				head.next.next = head;
				head.next = null;
			}
			return successor;
		}
	}
	
	static class Solution2 {
		public ListNode reverseList(ListNode head) {
			if (head == null || head.next == null) {
				return head;
			}
			ListNode last = reverseList(head.next); // reverse the list from head.next, return the head of the reversed list
			head.next.next = head; // concise reverse
			head.next = null; // disable the cyclic linked-list
			return last; // return the head of the reversed linked-list, aka the last node of the original list
		}
	}
	
	static class Solution3 {
		public ListNode reverseList(ListNode head) {
			ListNode prev = null, curr = head, next;
			while (curr != null) {
				next = curr.next;
				curr.next = prev;
				prev = curr;
				curr = next;
			}
			return prev;
		}
	}
	
	/**
	 * Returns the string representation of the given list.
	 */
	private static String listToString(ListNode head) {
		StringBuilder sb = new StringBuilder();
		while (head != null) {
			sb.append(head.val);
			if (head.next != null) {
				sb.append(" -> ");
			}
			head = head.next;
		}
		return sb.toString();
	}
}
