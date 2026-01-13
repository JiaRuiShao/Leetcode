import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 503. Next Greater Element II
 */
public class _503 {
    // Mono decreasing stack to find next greater elements
    class Solution1_Mono_Stack {
        public int[] nextGreaterElements(int[] nums) {
            int n = nums.length;
            int[] ngl = new int[n];
            Deque<Integer> minStack = new ArrayDeque<>();
            for (int i = 2 * n - 1; i >= 0; i--) {
                int num = nums[i % n];
                while (!minStack.isEmpty() && minStack.peek() <= num) {
                    minStack.pop();
                }
                ngl[i % n] = minStack.isEmpty() ? -1 : minStack.peek();
                minStack.push(num);
            }
            return ngl;
        }
    }
}
