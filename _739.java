import java.util.Deque;
import java.util.LinkedList;

public class _739 {
    static class Solution {
        public int[] dailyTemperatures(int[] temperatures) {
            // used to store the next greater temperature index
            Deque<Integer> stack = new LinkedList<>();
            int[] res = new int[temperatures.length];
            for (int i = temperatures.length - 1; i >= 0; i--) {
                while (!stack.isEmpty() && temperatures[stack.peekLast()] <= temperatures[i]) {
                    stack.pollLast();
                }
                res[i] = stack.isEmpty() ? 0 : stack.peekLast() - i;
                stack.addLast(i); // add the current temperature index into the stack
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int[] temperatures = new int[]{73,74,75,71,69,72,76,73};
        new Solution().dailyTemperatures(temperatures);
    }
}
