import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1944. Number of Visible People in a Queue.
 * Understand process of monotonic stack better
 */
public class _1944 {

    class Solution1_Monotonic_Stack {
        public int[] canSeePersonsCount(int[] heights) {
            int n = heights.length;
            int[] ans = new int[n];
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = n - 1; i >= 0; i--) {
                int height = heights[i];
                // count how many ppl are shorter than myself and NGL but still are visible (no higher ppl in betw), consider cases like: [10, 8, 6, 11, 9] and [10, 8, 6, 9, 11]
                int seenHightsBeforeNGL = 0; 
                while (!stack.isEmpty() && stack.peek() <= height) {
                    stack.pop();
                    seenHightsBeforeNGL++;
                }
                // # ppl seen is #seenHightsBeforeNGL + 1 if there's a valid NGL or + 0 if not
                ans[i] = stack.isEmpty() ? seenHightsBeforeNGL : seenHightsBeforeNGL + 1;
                stack.push(height);
            }
            return ans;
        }
    }
}
