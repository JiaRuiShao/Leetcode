import helper.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 23. Merge k Sorted Lists
 */
public class _23 {
    static class Solution1_MinHeap {
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
            Queue<ListNode> minHeap = new PriorityQueue<>(k, (a, b) -> Integer.compare(a.val, b.val));
            for (ListNode node : lists) {
                if (node != null) minHeap.offer(node);
            }
            while (!minHeap.isEmpty()) {
                temp = minHeap.poll();
                if (temp.next != null) minHeap.offer(temp.next);
                curr.next = temp;
                curr = curr.next;
            }
            return dummy.next;
        }
    }

    // Time: O(nlogk)
    // Space: O(logk)
    class Solution2_Divide_And_Conquer {
        public ListNode mergeKLists(ListNode[] lists) {
            return merge(lists, 0, lists.length - 1);
        }

        // merge lists[start..end] as a sorted list
        private ListNode merge(ListNode[] lists, int start, int end) {
            if (start == end) return lists[start];

            int mid = start + (end - start) / 2;
            // merge left lists[start..mid] as one sorted list
            ListNode left = merge(lists, start, mid);

            // merge right lists[mid+1..end] as one sorted list
            ListNode right = merge(lists, mid + 1, end);

            // merge left & right list
            return mergeTwoLists(left, right);
        }

        private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            ListNode dummy = new ListNode(-1), p = dummy;
            ListNode p1 = l1, p2 = l2;
    
            while (p1 != null && p2 != null) {
                if (p1.val > p2.val) {
                    p.next = p2;
                    p2 = p2.next;
                } else {
                    p.next = p1;
                    p1 = p1.next;
                }
                p = p.next;
            }
    
            if (p1 != null) {
                p.next = p1;
            }
    
            if (p2 != null) {
                p.next = p2;
            }
    
            return dummy.next;
        }

        ListNode mergeRecursively(ListNode[] lists, int i) {
            if (i == lists.length - 1) {
                return lists[i];
            }
            // get next list head
            ListNode nextList = mergeRecursively(lists, i + 1);
        
            // recursively merge current list with next list from merge(k-2,k-1) to merge(0, 1)
            return mergeTwoLists(lists[i], nextList);
        }
    }
}
