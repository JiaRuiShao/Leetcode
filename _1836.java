import helper.ListNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 1836. Remove Duplicates From an Unsorted Linked List.
 */
public class _1836 {
	class Solution1_HashMap {
		public ListNode deleteDuplicatesUnsorted(ListNode head) {
			Set<Integer> duplicates = new HashSet<>();
			ListNode dummy = new ListNode(-1), prev = dummy, curr = head;
			findDuplicates(head, duplicates);
			
			while (curr != null) {
				if (!duplicates.contains(curr.val)) {
					prev.next = curr;
					prev = prev.next;
				}
				curr = curr.next;
			}
			prev.next = null;
			return dummy.next;
		}
		
		private void findDuplicates(ListNode head, Set<Integer> duplicates) {
			Map<Integer, Integer> freq = new HashMap<>();
			while (head != null) {
				freq.put(head.val, freq.getOrDefault(head.val, 0) + 1);
				if (freq.get(head.val) == 2) {
					duplicates.add(head.val);
				}
				head = head.next;
			}
		}
	}
}
