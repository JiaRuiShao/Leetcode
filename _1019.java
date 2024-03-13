import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import helper.ListNode;

/**
 * 1019. Next Greater Node In Linked List.
 */
public class _1019 {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution {
        public int[] nextLargerNodes(ListNode head) {
            ArrayList<Integer> list = new ArrayList<>();
            ListNode curr = head;
            while (curr != null) {
                list.add(curr.val);
                curr = curr.next;
            }

            int n = list.size();
            int[] ans = new int[n];
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = n - 1; i >= 0; i--) {
                int val = list.get(i);
                while (!stack.isEmpty() && stack.peek() <= val) {
                    stack.pop();
                }
                ans[i] = stack.isEmpty() ? 0 : stack.peek();
                stack.push(val);
            }
            return ans;
        }
    }
}
