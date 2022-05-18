import helper.ListNode;

public class _21 {
    static class Solution1 {
        /**
         * Iterative solution to merge two sorted LinkedList.
         * Time: O(l1 + l2)
         * Space: O(1)
         *
         * @param list1 sorted list1 head
         * @param list2 sorted list2 head
         * @return the head of the merged linked list
         */
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            ListNode dummy = new ListNode(-1); // use a dummy node
            ListNode curr = dummy;
            while (list1 != null && list2 != null) {
                if (list1.val < list2.val) {
                    curr.next = list1;
                    list1 = list1.next;
                } else {
                    curr.next = list2;
                    list2 = list2.next;
                }
                curr = curr.next;
            }
            curr.next = list1 == null ? list2 : list1;
            return dummy.next;
        }
    }

    static class Solution2 {
        /**
         * Recursive solution to merge two sorted LinkedList.
         * Time: O(l1 + l2)
         * Space: O(l1 + l2)
         *
         * @param list1 sorted list1 head
         * @param list2 sorted list2 head
         * @return the head of the merged linked list
         */
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            // base case
            if (list1 == null) return list2;
            if (list2 == null) return list1;
            if (list1.val < list2.val) {
                list1.next = mergeTwoLists(list1.next, list2);
                return list1;
            } else {
                list2.next = mergeTwoLists(list1, list2.next);
                return list2;
            }
        }
    }
}
