import helper.ListNode;

/**
 * 92. Reverse Linked List II.
 */
public class _92 {

    public static class Solution1_Iterative_Two_Pointers {
        public ListNode reverseBetween(ListNode head, int m, int n) {
            // use four nodes, pre, start, next, dummy
            // just reverse the nodes along the way
            ListNode dummy = new ListNode(-1, head); // create a dummy node to mark the head of this list
            // predecessor node
            ListNode pre = dummy; // make a pointer pre as a marker for the node before reversing
            for (int i = 0; i < m - 1; i++) {
                pre = pre.next;
            }

            // 1 - 2 -3 - 4 - 5 ; m = 2; n = 4 ---> pre = 1, start = 2, then = 3
            // dummy-> 1 -> 2 -> 3 -> 4 -> 5
            ListNode start = pre.next; // start is the node prior to reversing, in the given example,
            // start is node with value 1
            ListNode next = start.next; // then is the node that we'll start to reverse, in the given
            // example, it's 2

            // assigning next.next to start.next, notice the start pointer doesn't change during the process
            // first reversing : dummy->1 - 3 - 2 - 4 - 5; pre = 1, start = 2, then = 4
            // second reversing: dummy->1 - 4 - 3 - 2 - 5; pre = 1, start = 2, then = 5 (finish)
            for (int i = 0; i < n - m; i++) {
                start.next = next.next;
                next.next = pre.next;
                pre.next = next;
                next = start.next;
            }

            return dummy.next;
        }
    }

    public static class Solution2_Iterative_Two_Pointers {
        public ListNode reverseBetween(ListNode head, int left, int right) {
            int pos = 0; // points to the position of the curr node
            ListNode dummy = new ListNode(-1, head);
            ListNode curr = dummy, prev = null; // prev points to the node where the pos is left - 1
            while (pos < left) {
                prev = curr;
                curr = curr.next;
                pos++;
            }
            ListNode first = curr, temp = null;
            ListNode next = curr.next;
            while (pos < right) {
                temp = next.next;
                next.next = curr;
                curr = next;
                next = temp;
                pos++;
            }
            first.next = next;
            if (prev != null) prev.next = curr;
            return dummy.next;
        }
    }

    static class Solution3_Recursion {

        ListNode prev = null, last = null, end = null;

        // 1    ->  2   ->  3   ->  4   ->  5
        // prev    left            last    end
        private ListNode reverse(ListNode curr, int left, int right, int pos) {
            if (curr == null) return null;
            ListNode next = reverse(curr.next, left, right, pos + 1);
            if (pos == left - 1) {
                prev = curr;
                prev.next = last;
            }
            else if (pos == right) last = curr;
            else if (pos == right + 1) end = curr;
            else if (pos >= left && pos < right) {
                if (pos == left) {
                    curr.next = end;
                }
                next.next = curr;
            }
            return curr;
        }

        public ListNode reverseBetween(ListNode head, int left, int right) {
            reverse(head, left, right, 1);
            return prev == null ? last : head;
        }
    }

    /**
     * Ref: https://labuladong.github.io/algo/2/17/17/
     */
    static class Solution4_Recursion {

        ListNode successor = null;
    
        /**
         * A helper recursive method to reverse the front n nodes of a singly linked list.
         *  1    ->   2   ->   3   ->   4   ->  5
         * prev     start              end     next
         * @param curr curr call stack ListNode
         * @param n nth
         * @return a list with first n nodes reversed
         */
        ListNode reverseN(ListNode curr, int n) {
            if (n == 1) {
                successor = curr.next; // record the (n+1)th node (next successor)
                return curr; // return the new head (end)
            }
            ListNode end = reverseN(curr.next, n - 1);
            curr.next.next = curr; // reverse, set the next node of curr.next to curr
            curr.next = successor; // only the start.next will not be overwritten
            return end;
        }
    
        /**
         * Deconstruct this problem into:
         * 1) m == 1 => use a helper method to help simplify this problem
         * 2) m != 1 => continue traversing, set head.next to reversedHead
         *
         * @param head head
         * @param m m
         * @param n n
         * @return reversed list
         */
        ListNode reverseBetween(ListNode head, int m, int n) {
            if (m == 1) return reverseN(head, n);
            head.next = reverseBetween(head.next, m - 1, n - 1);
            return head;
        }
    }
    
    static class Solution5_Recursion  {
        
        ListNode prev, start, end, next;
        // 1    ->   2   ->   3   ->   4   ->  5
        // prev    start              end     next
        public ListNode reverseBetween(ListNode head, int left, int right) {
            if (head == null || head.next == null || left == right) {
                return head;
            }
            reverse(head, left - 1, right - 1);
            if (prev != null) {
                prev.next = end;
            }
            if (start != null) {
                start.next = next;
            }
            return left == 1 ? end : head;
        }
        
        private void reverse(ListNode head, int left, int right) {
            if (left == 1) {
                prev = head;
            }
            if (left == 0) {
                start = head;
            }
            if (right == 0) {
                end = head;
            }
            if (right == -1) {
                next = head;
            }
            if (head == null || head.next == null) {
                return;
            }
            reverse(head.next, left - 1, right - 1);
            if (left <= 0 && right > 0) {
                head.next.next = head;
                head.next = null;
            }
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1), curr = head;
        for (int i = 2; i < 6; i++) {
            curr.next = new ListNode(i);
            curr = curr.next;
        }
        new Solution5_Recursion().reverseBetween(head, 2, 4);
    }


}
