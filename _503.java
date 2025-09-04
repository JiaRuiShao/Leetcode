import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 503. Next Greater Element II
 */
public class _503 {
    class Solution1_Mono_Stack {
        public int[] nextGreaterElements(int[] nums) {
            int n = nums.length;
            int[] ngl = new int[n];
            Deque<Integer> stk = new ArrayDeque<>(); // mono increasing stack
            for (int i = 2 * n - 1; i >= 0; i--) {
                int num = nums[i % n];
                while (!stk.isEmpty() && stk.peek() <= num) {
                    stk.pop();
                }
                ngl[i % n] = stk.isEmpty() ? -1 : stk.peek();
                stk.push(num);
            }
            return ngl;
        }
    }
}
