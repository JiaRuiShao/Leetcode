import helper.ListNode;

public class _19 {
    static class Solution1 {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode n1 = head, n2 = head, prev = head;
            while (n1 != null && n > 0) { // n steps
                n1 = n1.next;
                n--;
            }
            if (n > 0) return head; // n > list size
            if (n == 0 && n1 == null) return n2.next; // head is the node to be deleted
            while (n1 != null) { // size - n steps
                n1 = n1.next;
                prev = n2;
                n2 = n2.next;
            }
            // now n2 points to the node to be deleted
            prev.next = n2.next;
            return head;
        }
    }

    static class Solution2 {
        /**
         * This solution is more clear & concise.
         *
         * @param head head of the singly-LinkedList
         * @param n target n
         * @return the head of the ll
         */
        public ListNode removeNthFromEnd(ListNode head, int n) {
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
}
