import java.util.ArrayDeque;
import java.util.Deque;

public class _503 {
    class Solution {
        public int[] nextGreaterElements(int[] nums) {
            int n = nums.length;
            int[] res = new int[n];
            Deque<Integer> stack = new ArrayDeque<Integer>(n);
            for (int i = 2*n - 1; i >= 0; i--) {
                while (!stack.isEmpty() && stack.peekLast() <= nums[i % n]) {
                    stack.removeLast();
                }
                res[i % n] = stack.isEmpty() ? -1 : stack.peekLast();
                stack.addLast(nums[i % n]);
            }
            return res;
        }
    }
}
