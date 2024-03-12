import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 503. Next Greater Element II.
 * Traverse two times to make sure we get all NGE for all elements.
 * Use modulo to get the actual index.
 */
public class _503 {
    class Solution {
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
            int[] res = new int[n];
            Deque<Integer> stack = new ArrayDeque<Integer>(n);
            for (int i = 2 * n - 1; i >= 0; i--) {
                int idx = i % n;
                while (!stack.isEmpty() && stack.peek() <= nums[idx]) {
                    stack.pop();
                }
                res[idx] = stack.isEmpty() ? -1 : stack.peek();
                stack.push(nums[idx]);
            }
            return res;
        }
    }
}
