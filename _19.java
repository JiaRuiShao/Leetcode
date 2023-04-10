import helper.ListNode;

/**
 * 19. Remove Nth Node From End of List.
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 */
public class _19 {
    static class Solution1_Two_Pointers {
        /**
         * Use dummy node to simplify the case where the head is null or head is the node to be deleted.
         *
         * @param head head node
         * @param n nth from the end
         * @return modified list
         */
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(-1, head);
            ListNode p1 = dummy, p2 = dummy, prev = null;
    
            // move p1 forward by n steps
            for (int step = 0; step < n; step++) {
                p1 = p1.next;
                if (p1 == null) { // n > size
                    return dummy.next;
                }
            }
    
            // move p1 & p2 forward together until p1 is null
            // then p2 points to the node we need to remove
            while (p1 != null) {
                p1 = p1.next;
                prev = p2;
                p2 = p2.next;
            }
            prev.next = p2.next;
    
            return dummy.next;
        }

        /**
         * This solution is more clear & concise.
         *
         * @param head head of the singly-LinkedList
         * @param n target n
         * @return the head of the ll
         */
        public ListNode removeNthFromEnd2(ListNode head, int n) {
            ListNode dummy = new ListNode(-1);
            dummy.next = head;
            // to delete the nth node from the end, we need to find the (n + 1)th node from the end
            ListNode x = findFromEnd(dummy, n + 1);
            // delete the next nth node
            x.next = x.next.next;
            return dummy.next;
        }

        /**
         * Return the kth node from the end of the LinkedList.
         * Here we assume that the k is <= size of the LinkedList
         *
         * @param head teh head of the singly-ll
         * @param k kth node
         * @return the kth node from the end
         */
        ListNode findFromEnd(ListNode head, int k) {
            ListNode n1 = head, p2 = head;
            // n1 goes k steps
            for (int i = 0; i < k; i++) {
                n1 = n1.next;
            }
            // p1 & p2 go n - k steps
            while (n1 != null) {
                p2 = p2.next;
                n1 = n1.next;
            }
            // p2 now points to the (n - k)th node
            return p2;
        }
    }

    static class Solution2_One_Pointer {

    }
}
