import helper.ListNode;

public class _25 {
    class Solution {
        ListNode reverse(ListNode a) {
            ListNode prev = null, curr = a, next = a;
            while (curr != null) {
                next = curr.next;  // store the next node of curr
                curr.next = prev; // reverse
                prev = curr; //  update curr
                curr = next; // update next
            }
            // return the new head of the reversed linked list
            return prev;
        }

        /** reverse the linked list in range [a, b) */
        ListNode reverseRange(ListNode a, ListNode b) {
            ListNode prev = null, curr = a, next = a;
            while (curr != b) {
                next = curr.next;  // store the next node of curr
                curr.next = prev; // reverse
                prev = curr; //  update curr
                curr = next; // update next
            }
            // return the new head of the reversed linked list
            return prev;
        }

        /** reverse a linked list in k-element group **/
        ListNode reverseKGroup(ListNode head, int k) {
            if (head == null) return null;
            // range [a, b) contains k elements to be reversed
            ListNode end = head;
            for (int i = 0; i < k; i++) {
                if (end == null) return head; // no need to reverse
                end = end.next;
            }
            // reverse the nodes from a to (but not include) b
            ListNode newHead = reverseRange(head, end);
            head.next = reverseKGroup(end, k); // recursively reversing, starting from end node
            return newHead;
        }

    }

    /**
     * We use recursion to go all the way until the end: when the number of nodes are smaller than k;
     * then we start to reverse each group of k nodes from the end towards the start.
     */
    public static class Solution1 {
        public ListNode reverseKGroup(ListNode head, int k) {
            ListNode curr = head;
            int count = 0;

            while (curr != null && count != k) {
                // find the k+1 node
                curr = curr.next;
                count++;
            }

            if (count == k) {
                /**after this below recursive call finishes, it'll return head;
                 * then this returned "head" will become "curr", while the head
                 * in its previous callstack is the real head after this call.
                 * Setting up a break point will make all of this crystal clear.*/
                curr = reverseKGroup(curr, k);

                while (count-- > 0) {
                    ListNode temp = head.next;
                    head.next = curr;
                    curr = head;
                    head = temp;
                }
                head = curr;
            }

            return head; //we run out of nodes before we hit count == k, so we'll just directly return head in this case as well
        }
    }

    public static class Solution2 {

        private int getLength(ListNode curr) {
            int len = 0;
            if (curr != null) len++;
            return len;
        }

        public ListNode reverseKGroup(ListNode head, int k) {
            if (head == null || head.next == null || k == 1) return head;
            int n = getLength(head);

            ListNode prev = null, curr = head, next = null;
            ListNode newHead = null;
            ListNode prevTail = null; // previous reversed group list nodes tail
            ListNode currTail = head; // current reversed group tail

            while (n >= k) {
                // reverse nodes in blocks of k
                prev = null; // reset prev to null
                for (int i = 0; i < k; i++) {
                    // reverse
                    next = curr.next;
                    curr.next = prev;
                    prev = curr;
                    curr = next;
                }
                // update the newHead of the ll
                /** Notice the prev points to the current group's reversed head **/
                if (newHead == null) {
                    newHead = prev;
                }
                /** Connect the reversed group new head to the previous group tail **/
                if (prevTail != null) {
                    prevTail.next = prev;
                }
                currTail.next = curr; // in case the num of left elements < k
                prevTail = currTail;
                currTail = curr;

                // update the num of elements left
                n -= k;
            }

            return newHead == null ? head : newHead;
        }
    }

    public static class Solution3 {
        /**
         * My completely original solution on 10/25/2021. Beats 100% submissions on LeetCode in runtime.
         * Again, using a pen and paper to visualize the process helps a lot!
         * My helper function returns two nodes: reversed node head and the starting node for the next reversal.
         */
        public ListNode reverseKGroup(ListNode head, int k) {
            ListNode pre = new ListNode(-1);
            pre.next = head;
            ListNode tmp = pre;
            ListNode curr = head;
            ListNode[] result = new ListNode[]{null, curr};
            do {
                result = reverseKGroupHelper(result[1], k);
                if (result[0] == result[1]) {
                    tmp.next = result[0];
                    return pre.next;
                } else {
                    tmp.next = result[0];
                    while (tmp.next != null) {
                        tmp = tmp.next;
                    }
                }
            } while (true);
        }

        private ListNode[] reverseKGroupHelper(ListNode head, int k) {
            int originalK = k;
            ListNode tmp = head;
            while (tmp != null) {
                tmp = tmp.next;
                k--;
            }
            if (k > 0) {
                return new ListNode[]{head, head};
            } else {
                tmp = head;
                k = originalK;
                ListNode prev = null;
                while (tmp != null) {
                    ListNode next = tmp.next;
                    tmp.next = prev;
                    prev = tmp;
                    tmp = next;
                    k--;
                    if (k == 0) {
                        return new ListNode[]{prev, tmp};
                    }
                }
                return null;
            }
        }
    }
}
