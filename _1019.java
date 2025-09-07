import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

import helper.ListNode;

/**
 * 1019. Next Greater Node In Linked List
 */
public class _1019 {
    // find ngl from right to left; update ngl for current idx after popped out smaller elements in maxStack
    class Solution1_MonoStack {
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

    // when we find ngl from left to right; we need to store the idx and update the ngl for that idx when that idx got popped out
    class Solution2_MonoStack_OnePass {
        public int[] nextLargerNodes(ListNode head) {
            List<Integer> answer = new ArrayList<>();
            Stack<int[]> stack = new Stack<>();
            // We use an integer 'cnt' to represent the index.
            int cnt = 0;

            while (head != null) {
                // Set the next greater value of the current value 'head.val' as 0 by default.
                answer.add(0);
                while (!stack.isEmpty() && head.val > stack.peek()[1]) {
                    int[] curr = stack.peek();
                    int id = curr[0], val = curr[1];
                    stack.pop();
                    answer.set(id, head.val);
                }
                // Add both the index and the value to stack.
                stack.add(new int[]{cnt++, head.val});
                head = head.next;
            }
        
            return answer.stream().mapToInt(i -> i).toArray();
        }
    }
}
