import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 739. Daily Temperatures
 */
public class _739 {
    static class Solution1_Mono_Stack {
        public int[] dailyTemperatures(int[] temperatures) {
            int n = temperatures.length;
            Deque<Integer> stk = new ArrayDeque<>(); // mono increasing stack for the indices
            int[] daysNeedToWait = new int[n];
            for (int i = n - 1; i >= 0; i--) {
                while (!stk.isEmpty() && temperatures[stk.peek()] <= temperatures[i]) {
                    stk.pop();
                }
                daysNeedToWait[i] = stk.isEmpty() ? 0 : stk.peek() - i;
                stk.push(i);
            }
            return daysNeedToWait;
        }
    }
}
