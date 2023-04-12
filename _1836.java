import java.util.HashMap;
import java.util.Map;

/**
 * 1836. Remove Duplicates From an Unsorted Linked List.
 */
public class _1836 {
	public static class ListNode {
		int val;
		ListNode next;
		
		public ListNode() {
		}
		
		public ListNode(int val) {
			this.val = val;
		}
		
		public ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}
	/**
	 * Time: O(n)
	 * Space: O(n)
	 */
	class Solution1_HashMap_Two_Pointers {
		public ListNode deleteDuplicatesUnsorted(ListNode head) {
			ListNode dummy = new ListNode(-1, head);
			ListNode left = dummy, right = head;
			Map<Integer, Integer> freq = new HashMap<>();
			buildFreqMap(right, freq);
			
			while (right != null) {
				if (freq.get(right.val) == 1) {
					left.next = right;
					left = left.next;
				}
				right = right.next;
			}
			left.next = null;
			return dummy.next;
		}
		
		private void buildFreqMap(ListNode curr, Map<Integer, Integer> freq) {
			while (curr != null) {
				freq.put(curr.val, freq.getOrDefault(curr.val, 0) + 1);
				curr = curr.next;
			}
		}
	}
}
