import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1944. Number of Visible People in a Queue
 */
public class _1944 {
    // Time: O(n^2)
    // Space: O(1)
    class Solution0_BruteForce {

    }

    // Time: O(n)
    // Space: O(n)
    class Solution1_Monotonic_Stack {
        public int[] canSeePersonsCount(int[] heights) {
            int n = heights.length;
            int[] canSee = new int[n];
            // pop when peek height < curr height
            Deque<Integer> minStack = new ArrayDeque<>();
            for (int i = n - 1; i >= 0; i--) {
                while (!minStack.isEmpty() && minStack.peek() < heights[i]) {
                    canSee[i]++;
                    minStack.pop();
                }
                canSee[i] += (minStack.isEmpty() ? 0 : 1);
                minStack.push(heights[i]);
            }
            return canSee;
        }
    }
}
