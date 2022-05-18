import helper.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class _23 {
    static class Solution {
        /**
         * Merge K sorted LinkedList.
         * Time: O(nlogk) where n is the total num of list nodes in the array of linked list,
         * & k is the num of linked list in the array
         * Space: O(k)
         *
         * @param lists the given array of list heads
         * @return the head of the merged linked list
         */
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0) return null;
            // use a minHeap to store the k head nodes
            int k = lists.length;
            ListNode dummy = new ListNode(-1), curr = dummy, temp;
            /** Notice that here the initialization size CANNOT be 0 */
            PriorityQueue<ListNode> minHeap = new PriorityQueue<>(k, (a, b) -> Integer.compare(a.val, b.val));
            // O(klogk)
            for (ListNode node : lists) {
                if (node != null) minHeap.offer(node);
            }
            // O(nlogk + (n-k)logk)
            while (!minHeap.isEmpty()) {
                temp = minHeap.poll();
                if (temp.next != null) minHeap.offer(temp.next);
                curr.next = temp;
                curr = curr.next;
            }
            return dummy.next;
        }
    }
}
