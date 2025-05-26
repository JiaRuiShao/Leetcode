import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 503. Next Greater Element II
 * Traverse two times to make sure we get all NGE for all elements.
 * Use modulo to get the actual index.
 */
public class _503 {
    class Solution1_Mono_Stack {
        /**
         * Monotonic Increasing Stack.
         * Time: O(n)
         * Space: O(n)
         * 
         * @param nums circular array
         * @return next greater elements
         */
        public int[] nextGreaterElements(int[] nums) {
            int n = nums.length;
            int[] nge = new int[n];
            Deque<Integer> stack = new ArrayDeque<>(n);
            for (int i = 2 * n - 1; i >= 0; i--) {
                int idx = i % n;
                while (!stack.isEmpty() && stack.peek() <= nums[idx]) {
                    stack.pop();
                }
                nge[idx] = stack.isEmpty() ? -1 : stack.peek();
                stack.push(nums[idx]);
            }
            return nge;
        }
    }
    
    class Solution2_Mono_Stack {
        public int[] nextGreaterElements(int[] nums) {
            int n = nums.length;
            int[] nge = new int[n];
            Arrays.fill(nge, -1);
            Deque<Integer> stk = new ArrayDeque<>();
            for (int i = 2 * n - 1; i >= 0; i--) {
                int idx = i % n, curr = nums[idx];
                while (!stk.isEmpty() && stk.peekFirst() <= curr) {
                    stk.removeFirst();
                }
                if (!stk.isEmpty()) nge[idx] = stk.peekFirst();
                stk.addFirst(curr);
            }
            return nge;
        }
    }
}
