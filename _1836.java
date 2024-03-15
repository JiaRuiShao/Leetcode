import java.util.HashMap;
import java.util.Map;
import helper.ListNode;

/**
 * 1836. Remove Duplicates From an Unsorted Linked List.
 */
public class _1836 {
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
	
	class Solution2_Array_Two_Pointers {
		/**
		 * Solution inspired by Cursor OpenAI: use array to store the freq.
		 * Note that the array size is 2*10^5 instead of because the problem statement mentions that the values of the linked list are between -10^5 and 10^5. We need to shift the negative val by 10^5 so that its index is non-negative.
		 * Time: O(n)
		 * Space: O(2*10^5) = O(1)
		 *
		 * @param head head node of the given list
		 * @return list without duplicate val nodes
		 */
		public ListNode deleteDuplicatesUnsorted(ListNode head) {
			ListNode dummy = new ListNode(-1, head);
			ListNode left = dummy, right = head;
			int[] freq = new int[200001];
			buildFreqArray(right, freq);
			
			while (right != null) {
				if (freq[right.val + 100000] == 1) {
					left.next = right;
					left = left.next;
				}
				right = right.next;
			}
			left.next = null;
			return dummy.next;
		}
		
		private void buildFreqArray(ListNode curr, int[] freq) {
			while (curr != null) {
				freq[curr.val + 100000]++;
				curr = curr.next;
			}
		}
		
	}
}
