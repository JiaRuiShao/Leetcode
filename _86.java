/**
 * 86. Partition List.
 * Given the head of a linked list and a value x, partition it such that
 * all nodes less than x come before nodes greater than or equal to x.
 * You should preserve the original relative order of the nodes in each of the two partitions.
 */
public class _86 {
	/**
	 * Singly-Linked List*
	 */
	public class ListNode {
		int val;
		ListNode next;
		
		ListNode() {
		}
		
		ListNode(int val) {
			this.val = val;
		}
		
		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}
	
	class Solution1_Two_Pointers {
		public ListNode partition(ListNode head, int x) {
			ListNode smallerDummy = new ListNode(-1), largerDummy = new ListNode(-1);
			ListNode smaller = smallerDummy, larger = largerDummy;
			ListNode temp = head;
			while (temp != null) {
				if (temp.val < x) {
					smaller.next = new ListNode(temp.val);
					smaller = smaller.next;
				} else {
					larger.next = new ListNode(temp.val);
					larger = larger.next;
				}
				temp = temp.next;
			}
			smaller.next = largerDummy.next;
			return smallerDummy.next;
		}
	}
	
	class Solution2_Two_Pointers {
		public ListNode partition(ListNode head, int x) {
			ListNode lt = new ListNode(-1), p1 = lt;
			ListNode gte = new ListNode(-1), p2 = gte;
			
			while (head != null) {
				if (head.val < x) {
					p1.next = head;
					p1 = p1.next;
				} else {
					p2.next = head;
					p2 = p2.next;
				}
				head = head.next;
			}
			
			// connect two lists together
			p1.next = gte.next;
			p2.next = null;
			
			return lt.next;
		}
	}
}
